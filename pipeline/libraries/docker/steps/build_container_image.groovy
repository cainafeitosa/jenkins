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

        login_to_registry()
        docker "pull ${env.CI_REGISTRY_IMAGE}:latest || true"
        docker "build --cache-from ${env.CI_REGISTRY_IMAGE}:latest -t ${env.CI_REGISTRY_IMAGE}:${env.CI_COMMIT_SHORT_SHA} -t ${env.CI_REGISTRY_IMAGE}:latest ${buildOpts}"
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