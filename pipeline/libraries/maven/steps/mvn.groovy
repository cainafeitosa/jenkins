#!/usr/bin/env groovy

void call(params = '') {
    def workDir = config.work_dir ?: '.'
    def commandLine = "mvn -B -e -V ${params}"

    if(pipelineConfig.libraries?.agent?.kubernetes) {
        container('maven') {
            dir(workDir) {
                sh "${commandLine}"
            }
        }
    } else {
        withMaven(maven: config.mvn_tool, jdk: config.jdk_tool) {
            dir(workDir) {
                sh "${commandLine}"
            }
        }
    }
}