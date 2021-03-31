#!/usr/bin/env groovy

import hudson.AbortException

void call() {
    stage("Checkout SCM") {
        cleanWs()
        try {
            checkout scm
        } catch(AbortException ex) {
            println "scm var not present, skipping source code checkout"
        } catch(err) {
            println "exception ${err}"
        }

        env.CI_COMMIT_SHA = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
        env.CI_COMMIT_SHORT_SHA = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    }
}

@Init
void init_env() {
    env.CI_PROJECT_URL = scm.getUserRemoteConfigs()[0].getUrl()
    env.CI_PROJECT_CREDENTIAL_ID = scm.getUserRemoteConfigs()[0].credentialsId.toString()

    def matcher = env.CI_PROJECT_URL =~ /^((?<protocol>https?|ssh)(:\/\/)|(?<user>[\w\d\._]+)@)(?<domain>[a-zA-Z0-9\.\-_]+(?<port>:[0-9]+)?)(:|\/)(?<namespace>[a-zA-Z0-9\-\/]+)\/(?<project>[a-zA-Z0-9\-]+)(\.git)?/

    if (matcher.matches()) {
        env.CI_PROJECT_NAME = matcher.group("project") - ".git"
        env.CI_PROJECT_NAMESPACE = matcher.group("namespace")
        env.CI_PROJECT_PATH = "${env.CI_PROJECT_NAMESPACE}/${env.CI_PROJECT_NAME}"
        env.CI_PROJECT_ROOT_NAMESPACE = env.CI_PROJECT_NAMESPACE.split('/')[0]
    }
}