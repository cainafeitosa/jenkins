#!/usr/bin/env groovy

void call(String args) {

    def commandLine = "mvn -B ${config.cli_options ?: ""} ${args}"

    if (config.runs_on?.pod_template) {
        container("maven") {
            sh commandLine
        }
    } else {
        withMaven(maven: config.mvn_installation, jdk: config.jdk_installation) {
            sh commandLine
        }
    }

}

@Validate
void validate_runs_on() {

    if (!config.containsKey("runs_on")) {
        return 
    }

    if (!(config.runs_on instanceof Map)) {
        error "maven library 'runs_on' is a ${config.runs_on.getClass()} when a block was expected"
    }

}