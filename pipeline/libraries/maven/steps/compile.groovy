#!/usr/bin/env groovy

void call() {

    stage("Build") {
        mvn "clean compile"
    }

}