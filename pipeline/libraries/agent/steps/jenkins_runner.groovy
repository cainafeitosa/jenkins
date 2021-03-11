#!/usr/bin/env groovy

void call(Closure body) {
    defaults = [
        cloud: 'kubernetes',
        podTemplates: '',
        label: ''
    ]

    if (config.kubernetes) {
        println 'Running on Kubernetes'
        
        def cloud = config.kubernetes.cloud ?: defaults.cloud
        def podTemplates = config.kubernetes.podTemplates ?: defaults.podTemplates

        podTemplate(cloud: cloud, inheritFrom: podTemplates) {
            node(POD_LABEL) {
                stage('Checkout SCM') {
                    git.checkout()
                }
                body()
            }
        }
    } else {
        println 'Running on Node'

        def label = config.label ?: defaults.label

        node(config.label) {
            stage('Checkout SCM') {
                git.checkout()
            }
            body()
        }
    }
}