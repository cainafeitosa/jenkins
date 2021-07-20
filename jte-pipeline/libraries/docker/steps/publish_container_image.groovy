#!/usr/bin/env groovy

import docker.DockerWrapper

void call() {
    stage("Docker: Release") {
        when (!(pipelineConfig.skip_ci) && pipelineConfig.docker_images != null) {
            withDocker = new DockerWrapper(this, config)

            withDocker {
                pipelineConfig.docker_images.each { name, image ->
                    println "==============================[ Pushing ${name} ]=============================="

                    println "[INFO] checking if image tag already exists..."
                    response = httpRequest url: "${config.registry}/v2/${image.name}/manifests/${image.tag}",
                                           authentication: config.credentials_id,
                                           ignoreSslErrors: true,
                                           validResponseCodes: "100:499"

                    if (response.status != 400) {
                        def dockerImage = docker.image("${image.name}:${image.tag}")
                        dockerImage.push()
                        dockerImage.push("latest")
                    } else {
                        error response.content
                    }
                }
            }
        }
    }
}