#!/usr/bin/env groovy

void call(String args) {
    def commandLine = "mvn -B ${config.cli_options ?: ""} ${args}"

    if (config?.runs_on?.kubernetes) {
        def cloud = config.runs_on.kubernetes?.cloud ?: "kubernetes"

        podTemplate(cloud: cloud, inheritFrom: config.runs_on.kubernetes.pod_template) {
            node(POD_LABEL) {
                container("maven") {
                    sh commandLine
                }
            }
        }
    } else {
        def nodeLabel = config?.runs_on?.node?.label ?: ""
        
        node(nodeLabel) {
            withMaven(maven: config.mvn_installation, jdk: config.jdk_installation) {
                sh commandLine
            }
        }
    }
}

@Validate
void call() {
    if (config?.runs_on?.kubernetes) {
        if (! config.runs_on.kubernetes.pod_template) {
            error "Library parameter runs_on.kubernetes.pod_template is undefined"
        }
    }
}