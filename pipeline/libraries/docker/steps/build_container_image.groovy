#!/usr/bin/env groovy

void call() {
    if (config?.runs_on) {
        container("docker") {
            dockerBuild()
        }
    } else {
        docker.withTool(config.docker_installation) {
            dockerBuild()
        }
    }
}

private void dockerBuild() {
    def imageName = config.image_repository ? "${config.image_repository}/${config.image_name}" : config.image_name
    def dockerfile = config.dockerfile ?: "Dockerfile"
    def contextPath = config.context_path ?: "."
    def buildArgs = []
    config.build_args.each { argument, value ->
        buildArgs << "--build-arg ${argument}='${value}'"
    }
    def buildOpts = "${buildArgs.join(" ")} -f ${dockerfile} ${contextPath}"

    docker.withRegistry(config.registry, config.credentials_id) {
        def builtImage = docker.build(imageName, buildOpts)
        def shortCommit = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
        builtImage.push("${env.BUILD_NUMBER}-${shortCommit}")
        builtImage.push('latest')
    }
}

@Validate
void validate_docker_build(){
    if(!config.containsKey("build_args")){
        return 
    }

    if(!(config.build_args instanceof Map)){
        error "docker library 'build_args' is a ${config.build_args.getClass()} when a block was expected"
    }
}