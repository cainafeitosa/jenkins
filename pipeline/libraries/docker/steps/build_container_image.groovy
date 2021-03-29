#!/usr/bin/env groovy

void call() {
    stage("Release: Docker") {
        def imageName = config.image_repository ? "${config.image_repository}/${config.image_name}" : config.image_name
        def dockerfile = config.dockerfile ?: "Dockerfile"
        def contextPath = config.context_path ?: "."
        def buildArgs = []
        config.build_args.each { argument, value ->
            buildArgs << "--build-arg ${argument}='${value}'"
        }
        def buildOpts = "${buildArgs.join(" ")} -f ${dockerfile} ${contextPath}"
        
        withDocker {
            docker.withRegistry(config.registry, config.credentials_id) {
                try {
                    def imageCache = docker.image("${imageName}:latest")
                    imageCache.pull()
                } catch(ignored) {
                    println "Failed to pull image ${imageName}:latest!"
                }
                def builtImage = docker.build(imageName, buildOpts)

                builtImage.push("${env.GIT_COMMIT_SHORT}")
                builtImage.push("latest")
            }
        }
    }
}

private void withDocker(Closure body) {
     if (config.pod_template) {
        container("docker") {
            body()
        }
    } else {
        docker.withTool(config.docker_installation) {
            body()
        }
    }
}

@Validate
void validate_docker_build() {
    if (!config.containsKey("build_args")) {
        return 
    }

    if (!(config.build_args instanceof Map)) {
        error "docker library 'build_args' is a ${config.build_args.getClass()} when a block was expected"
    }
}

@Validate
void validate_runs_on() {
    if (!config.containsKey("runs_on")) {
        return 
    }

    if (!(config.runs_on instanceof Map)) {
        error "docker library 'runs_on' is a ${config.runs_on.getClass()} when a block was expected"
    }
}