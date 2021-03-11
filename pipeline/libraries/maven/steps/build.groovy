#!/usr/bin/env groovy

void call(Closure body) {
    stage('Maven: Compile') {
        if (config.kubernetes) {
            container('maven') {
                sh 'mvn -B clean compile'
            }
        }
    }
}