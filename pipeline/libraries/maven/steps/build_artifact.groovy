#!/usr/bin/env groovy

void call() {
    stage("Build: Maven") {
        mvn "-Dmaven.test.skip=true clean package"
    }
}