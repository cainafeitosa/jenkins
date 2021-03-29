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