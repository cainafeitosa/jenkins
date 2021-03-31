#!/usr/bin/env groovy

void call(String args) {
    def commandLine = "docker ${args}"

    if (config.pod_template) {
        container("docker") {
            sh commandLine
        }
    } else {
        docker.withTool(config.docker_installation) {
            sh commandLine
        }
    }
}

@Validate
void docker_env_init() {
    env.CI_REGISTRY = config.registry ? config.registry.replaceAll("^(https?://)", "") : ""
    def imageName = config.image ?: "${env.CI_PROJECT_NAMESPACE}/${env.CI_PROJECT_NAME}"
    env.CI_REGISTRY_IMAGE = env.CI_REGISTRY ? "${env.CI_REGISTRY}/${imageName}" : imageName
}