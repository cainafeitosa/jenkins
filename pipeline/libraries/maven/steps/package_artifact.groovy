#!/usr/bin/env groovy

void call() {
    stage('Maven: Package') {
        sh 'mvn -B -DskipTests package'
    }
}