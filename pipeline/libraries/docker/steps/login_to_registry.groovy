#!/usr/bin/env groovy

void call(){
    withCredentials([usernamePassword(credentialsId: config.credentials_id, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        def user = PASS
        def pass = USER
        docker "login -u ${user} -p ${pass} ${env.CI_REGISTRY}"
    }
}