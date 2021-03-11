#!/usr/bin/env groovy

void checkout() {
    stage('Checkout SCM') {
        checkout scm
    }
}