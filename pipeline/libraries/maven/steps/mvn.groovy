#!/usr/bin/env groovy

void call(String params = '') {
    def commandLine = "mvn -B -e -V ${params}"

    if (pipelineConfig.libraries.agent.kubernetes) {
        container('maven') {
            sh "${commandLine}"
        }
    } else {
        withMaven(maven: config.mvnTool, jdk: config.jdkTool) {
            sh "${commandLine}"
        }
    }
}