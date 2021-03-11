#!/usr/bin/env groovy

void call(Closure body) {
    if (config.kubernetes) {
        println 'Running on Kubernetes'
        
        def cloud = config.kubernetes.cloud ?: 'kubernetes'
        def podTemplates = config.kubernetes.podTemplates ?: ''

        podTemplate(cloud: cloud, inheritFrom: podTemplates) {
            node(POD_LABEL) {
                stage('Checkout SCM') {
                    checkout scm
                }
                body()
            }
        }
    } else {
        println 'Running on Node'

        def label = config.label ?: ''

        node(config.label) {
            stage('Checkout SCM') {
                checkout scm
            }
            body()
        }
    }
}