#!/usr/bin/env groovy

void call(body) {
    def defaults = [
        cloud: 'kubernetes',
        pod_templates: '',
        label: ''
    ]

    if(config.kubernetes) {
        def cloud = config.kubernetes.cloud ?: defaults.cloud
        def podTemplates = config.kubernetes.pod_templates ?: defaults.pod_templates

        podTemplate(cloud: cloud, inheritFrom: podTemplates) {
            node(POD_LABEL) {
                body()
            }
        }
    } else {
        def label = config.label ?: defaults.label

        node(label) {
            body()
        }
    }
}