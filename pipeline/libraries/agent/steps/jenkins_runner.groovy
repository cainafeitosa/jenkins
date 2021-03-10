#!/usr/bin/env groovy

void call(Closure body) {
    println config.kubernetes
    println config.label
    println config.kubernetes.toBoolean()
    if (config.kubernetes.toBoolean()) {
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