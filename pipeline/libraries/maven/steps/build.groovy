#!/usr/bin/env groovy

void call(Closure body) {
    stage('Maven: Compile') {
        println pipelineConfig
        if (config.kubernetes) {
            container('maven') {
                sh 'mvn -B clean compile'
            }
        }
    }
}