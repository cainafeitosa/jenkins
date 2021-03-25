#!/usr/bin/env groovy

@Init
void call(){

    try { unstash "workspace" }
    catch(ignored) { 
        println "'workspace' stash not present. Skipping git library environment variable initialization. To change this behavior, ensure the 'checkout_scm' step is called"
        return
    }

    env.GIT_URL = scm.getUserRemoteConfigs()[0].getUrl()
    env.GIT_CREDENTIAL_ID = scm.getUserRemoteConfigs()[0].credentialsId.toString()
    def parts = env.GIT_URL.split("/")
    for (part in parts){
        parts = parts.drop(1)
        if (part.contains(".")) break
    }
    env.ORG_NAME = parts.getAt(0)
    env.REPO_NAME = parts[1..-1].join("/") - ".git"
    env.GIT_COMMIT_SHA = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
    env.GIT_COMMIT_SHORT_SHA = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()

    if (env.CHANGE_TARGET) {
        env.GIT_BUILD_CAUSE = "pr"
    } else {
        env.GIT_BUILD_CAUSE = sh (
            script: 'git rev-list HEAD --parents -1 | wc -w', // will have 2 shas if commit, 3 or more if merge
            returnStdout: true
        ).trim().toInteger() > 2 ? "merge" : "commit"
    }

    println "Found Git Build Cause: ${env.GIT_BUILD_CAUSE}"
    
}