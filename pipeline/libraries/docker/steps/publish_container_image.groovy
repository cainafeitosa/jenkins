#!/usr/bin/env groovy

void call() {
    stage("Publish") {
        login_to_registry()
        docker "push ${env.CI_REGISTRY_IMAGE}:${env.CI_COMMIT_SHORT_SHA}"
        docker "push ${env.CI_REGISTRY_IMAGE}:latest"
    }
}