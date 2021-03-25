#!/usr/bin/env groovy

void call(builtImage) {
    docker.withRegistry(config.registry, config.credentials_id) {
        def shortCommit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
        builtImage.push("${env.BUILD_NUMBER}-${shortCommit}")
        builtImage.push('latest')
    }
}