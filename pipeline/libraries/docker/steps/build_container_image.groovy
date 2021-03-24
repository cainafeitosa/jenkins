#!/usr/bin/env groovy

void call() {
    def imageName = config.image_repository ? "${config.image_repository}/${config.image_name}" : config.image_name
    def imageTag = ''
    def buildArgs = config.build_args ?: '.'

    stage('Docker: Build Image') {
        container('docker') {
            builtImage = docker.build(imageName, buildArgs)
        }
    }

    stage('Docker: Push Image') {
        container('docker') {
            docker.withRegistry(config.registry, config.credentials_id) {
                builtImage.push(imageTag)
                builtImage.push('latest')
            }
        }
    }
}