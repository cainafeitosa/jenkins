#!/usr/bin/env groovy

void call() {
    stage("Release") {
        docker "push ${imageName}:${env.GIT_COMMIT_SHORT}"
        docker "push ${imageName}:latest"
    }
}