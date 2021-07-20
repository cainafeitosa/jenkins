#!/usr/bin/env groovy

void call() {
    stage("Checkout SCM") {
        cleanWs()
        checkout scm

        env.CI_COMMIT_SHA             = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
        env.CI_COMMIT_SHORT_SHA       = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
        env.CI_REPOSITORY_URL         = scm.getUserRemoteConfigs()[0].getUrl()
        env.CI_REPOSITORY_CREDENTIALS = scm.getUserRemoteConfigs()[0].credentialsId.toString()

        def prefixVersion             = env.BRANCH_NAME.split('/').last()
        env.CI_TAG_VERSION            = "${prefixVersion}-${env.CI_COMMIT_SHORT_SHA}"

        def matcherUrl = env.CI_REPOSITORY_URL =~ /^(?:(?:(?<protocol>https?|ssh)(?::\/\/))?(?:(?<user>[\w\d\._]+)@)?)(?<server>[\w\d\.\-_]+(?::\d+)?)(?::|\/)(?<namespace>[\w\d\-\/]+)\/(?<project>[\w\d\-_]+)(?:\.git)?$/

        if (matcherUrl) {
            env.CI_REPOSITORY_PROTOCOL    = matcherUrl.group("protocol")
            env.CI_PROJECT_NAMESPACE      = matcherUrl.group("namespace")
            env.CI_PROJECT_NAME           = matcherUrl.group("project")
            env.CI_PROJECT_PATH           = "${env.CI_PROJECT_NAMESPACE}/${env.CI_PROJECT_NAME}"
            env.CI_PROJECT_ROOT_NAMESPACE = env.CI_PROJECT_NAMESPACE.split('/').first()
        }

        def changeLogSets      = currentBuild.changeSets
        pipelineConfig.skip_ci = changeLogSets.size() > 0 ? false : true
    }
}