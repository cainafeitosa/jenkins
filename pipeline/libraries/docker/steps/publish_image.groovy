#!/usr/bin/env groovy

void call() {
    stage("Release") {
        def registry           = config.registry ?: ""
        def registryCredential = config.credentials_id ?: ""
        
        withDocker {
            docker.withRegistry(registry, registryCredential) {
                try {
                    def imageCache = docker.image("${imageName}:latest")
                    imageCache.pull()
                } catch(ignored) {
                    println "Failed to pull image ${imageName}:latest!"
                }

                dockerImage.push(imageTag)
                dockerImage.push("latest")
            }
        }
    }
}