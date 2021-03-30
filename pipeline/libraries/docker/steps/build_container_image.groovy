#!/usr/bin/env groovy

void call() {
    stage("Build: Docker") {
        imageName       = config.registry ? "${config.registry.replaceAll("http(s)?://", "")}/${config.image_name}" : config.image_name
        def dockerfile  = config.dockerfile ?: "Dockerfile"
        def contextPath = config.context_path ?: "."
        def buildArgs   = []

        config.build_args.each { argument, value ->
            buildArgs << "--build-arg ${argument}='${value}'"
        }

        def buildOpts = "${buildArgs.join(" ")} -f ${dockerfile} ${contextPath}"

        login_to_registry()
        docker "pull ${imageName}:latest || true"
        docker "build --cache-from ${imageName}:latest -t ${imageName}:${env.GIT_COMMIT_SHORT} -t ${imageName}:latest ${buildOpts}"
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