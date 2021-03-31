#!/usr/bin/env groovy

void call() {
    stage("Publish") {
        login_to_registry()
        docker "push ${env.CI_REGISTRY_IMAGE}:${env.CI_REGISTRY_IMAGE_TAG}"
        docker "push ${env.CI_REGISTRY_IMAGE}:latest"
    }
}