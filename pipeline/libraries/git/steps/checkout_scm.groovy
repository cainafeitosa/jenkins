#!/usr/bin/env groovy

import hudson.AbortException

@Validate
void call() {
    node {
        stage("Checkout SCM") {
            cleanWs()
            try {
                checkout scm
            } catch(AbortException ex) {
                println "scm var not present, skipping source code checkout"
            } catch(err) {
                println "exception ${err}"
            }

            stash name: 'workspace', allowEmpty: true, useDefaultExcludes: false
        }

        env.CI_PROJECT_URL = scm.getUserRemoteConfigs()[0].getUrl()
        env.CI_PROJECT_CREDENTIAL_ID = scm.getUserRemoteConfigs()[0].credentialsId.toString()

        def matcher = env.CI_PROJECT_URL =~ /^((?<protocol>https?|ssh)(:\/\/)|(?<user>[\w\d\._]+)@)(?<domain>[a-zA-Z0-9\.\-_]+(?<port>:[0-9]+)?)(:|\/)(?<namespace>[a-zA-Z0-9\-\/]+)\/(?<project>[a-zA-Z0-9\-]+)(\.git)?/

        if (matcher.matches()) {
            env.CI_PROJECT_NAME = matcher.group("project") - ".git"
            env.CI_PROJECT_NAMESPACE = matcher.group("namespace")
            env.CI_PROJECT_ROOT_NAMESPACE = env.CI_PROJECT_NAMESPACE.split('/')[0]
        }

        env.CI_COMMIT_SHA = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
        env.CI_COMMIT_SHORT_SHA = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()

        if (env.CHANGE_TARGET){
            env.CI_BUILD_CAUSE = "pr"
        } else {
            env.CI_BUILD_CAUSE = sh (
              script: 'git rev-list HEAD --parents -1 | wc -w', // will have 2 shas if commit, 3 or more if merge
              returnStdout: true
            ).trim().toInteger() > 2 ? "merge" : "commit"
        }

        println "Found Git Build Cause: ${env.CI_BUILD_CAUSE}"
    }
}