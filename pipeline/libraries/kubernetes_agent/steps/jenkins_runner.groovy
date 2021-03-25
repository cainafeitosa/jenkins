#!/usr/bin/env groovy

void call(Closure body) {

    def cloud = config.cloud ?: "kubernetes"
    def podTemplates = pipelineConfig.libraries.findAll { library, config ->
        config?.runs_on
    }.collect { key, value ->
        value.runs_on
    }.join(" ")

    podTemplate(cloud: cloud, inheritFrom: podTemplates) {
        node(POD_LABEL) {
            body()
        }
    }
    
}