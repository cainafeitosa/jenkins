#!/usr/bin/env groovy

void call(Closure body) {
    def cloud = config.cloud ?: "kubernetes"
    println jte.libraries
    println pipelineConfig

    podTemplate(cloud: cloud, inheritFrom: ) {
        node(POD_LABEL) {
            body()
        }
    }
}