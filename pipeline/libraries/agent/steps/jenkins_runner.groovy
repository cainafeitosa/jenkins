#!/usr/bin/env groovy

void call(Closure body) {
    if (config.kubernetes) {
        def cloud = config.kubernetes.cloud ?: 'kubernetes'

        podTemplate(cloud: cloud, inheritFrom: 'maven') {
            node(POD_LABEL) {
                body()
            }
        }
    } else {
        def label = config.label ?: ''
        
        node(config.label) {
            body()
        }
    }
}