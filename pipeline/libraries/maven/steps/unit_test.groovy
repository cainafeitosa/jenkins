#!/usr/bin/env groovy

void call() {
    stage('Maven: Unit Test') {
        sh 'mvn -B test'
    }
}