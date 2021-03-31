#!/usr/bin/env groovy

void call(){
    withCredentials([usernamePassword(credentialsId: config.credentials_id, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        def commandLogin = 'login -u ${USER} -p ${PASS} "${env.CI_REGISTRY}"'
        docker commandLogin
    }
}