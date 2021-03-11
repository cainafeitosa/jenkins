#!/usr/bin/env groovy

void call() {
    stage('Maven: Compile') {
        sh 'mvn -B clean compile'
    }
}