#!/usr/bin/env groovy

void call(Closure body) {
    stage('Maven: Compile') {
        if (pipelineConfig.libraries.agent.kubernetes) {
            container('maven') {
                sh 'mvn -B clean compile'
            }
        }
    }
}