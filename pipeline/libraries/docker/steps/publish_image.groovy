#!/usr/bin/env groovy

void call() {
    stage("Release") {
        withDocker {
            docker.withRegistry(config.registry, config.credentials_id) {
                try {
                    def imageCache = docker.image("${imageName}:latest")
                    imageCache.pull()
                } catch(ignored) {
                    println "Failed to pull image ${imageName}:latest!"
                }

                builtImage.push(imageTag)
                builtImage.push("latest")
            }
        }
    }
}