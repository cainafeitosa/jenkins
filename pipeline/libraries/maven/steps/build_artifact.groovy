#!/usr/bin/env groovy

void call() {
    stage("Maven: Package") {
        mvn "-Dmaven.test.skip=true package"
    }
}