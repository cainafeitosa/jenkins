#!/usr/bin/env groovy

void call(sourceTag, targetTag) {
    stage("Release") {
        login_to_registry()
        docker "pull ${env.CI_REGISTRY_IMAGE}:${sourceTag}"
        docker "tag ${env.CI_REGISTRY_IMAGE}:${sourceTag} ${env.CI_REGISTRY_IMAGE}:${targetTag}"
        docker "push ${env.CI_REGISTRY_IMAGE}:${targetTag}"
    }
}