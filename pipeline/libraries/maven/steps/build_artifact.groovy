#!/usr/bin/env groovy

void call() {
    stage("Release: Maven") {
        mvn "-Dmaven.test.skip=true clean package"
    }
}