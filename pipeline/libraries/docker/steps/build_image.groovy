#!/usr/bin/env groovy

void call() {
    stage("Build: Docker") {
        def dockerfile  = config.dockerfile ?: "Dockerfile"
        def contextPath = config.context_path ?: "."
        def buildArgs   = []

        config.build_args.each { argument, value ->
            buildArgs << "--build-arg ${argument}='${value}'"
        }

        def buildOpts = "${buildArgs.join(" ")} -f ${dockerfile} ${contextPath}"

        withDocker {
            dockerImage = docker.build(imageName, buildOpts)
        }
    }
}

@Validate
void validate_builds_args() {
    if (!config.containsKey("build_args")) {
        return 
    }

    if (!(config.build_args instanceof Map)) {
        error "docker library 'build_args' is a ${config.build_args.getClass()} when a block was expected"
    }
}