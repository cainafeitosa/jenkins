#!/usr/bin/env groovy

void call() {
    stage("Release") {
        def registry           = config.registry ?: ""
        def registryCredential = config.credentials_id ?: ""
        
        withDocker {
            docker.withRegistry(registry, registryCredential) {
                dockerImage.push(imageTag)
                dockerImage.push("latest")
            }
        }
    }
}