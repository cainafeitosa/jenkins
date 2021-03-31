#!/usr/bin/env groovy

void call(){
    withCredentials([usernamePassword(credentialsId: config.credentials_id, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        docker "login -u ${USER} -p ${PASS} ${env.CI_REGISTRY}"
    }
}