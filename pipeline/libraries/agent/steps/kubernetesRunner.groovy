#!/usr/bin/env groovy

void call(Closure body) {
    def cloud = config.kubernetes.cloud ?: "kubernetes"
    def podTemplates = pipelineConfig.libraries.findAll { library, config ->
        config.pod_template
    }.collect { library, config ->
        config.pod_template
    }.join(" ")

    podTemplate(cloud: cloud, inheritFrom: podTemplates) {
        node(POD_LABEL, body)
    }
}