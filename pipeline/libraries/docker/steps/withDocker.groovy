#!/usr/bin/env groovy

def call(Closure body) {
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
void docker_env() {
    imageName = config.image_repository ? "${config.image_repository}/${config.image_name}" : config.image_name
    imageTag  = env.GIT_COMMIT_SHORT
}