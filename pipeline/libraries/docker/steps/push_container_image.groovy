#!/usr/bin/env groovy

void call(builtImage) {
    docker.withRegistry(config.registry, config.credentials_id) {
        builtImage.push('latest')
    }
}