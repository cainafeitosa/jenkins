#!/usr/bin/env groovy

void call(){
    withCredentials([usernamePassword(credentialsId: config.credentials_id, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        docker "login -u ${USERNAME} -p ${PASSWORD} ${registry ?: ""}"
    }
}