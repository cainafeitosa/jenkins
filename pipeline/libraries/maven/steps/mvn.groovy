#!/usr/bin/env groovy

void call(String args) {

    def commandLine = "mvn -B ${config.cli_options ?: ""} ${args}"

    if (config?.runs_on) {
        container("maven") {
            sh commandLine
        }
    } else {
        withMaven(maven: config.mvn_installation, jdk: config.jdk_installation) {
            sh commandLine
        }
    }

}