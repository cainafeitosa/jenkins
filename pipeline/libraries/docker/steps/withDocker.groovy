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