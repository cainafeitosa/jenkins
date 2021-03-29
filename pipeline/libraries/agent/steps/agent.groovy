#!/usr/bin/env groovy

void call(Closure body) {
    def agentRunner = config.size() == 0 ? null : config.keySet().first()

    switch (agentRunner) {
        case "kubernetes":
            kubernetesRunner(body)
            break;
        case "node":
            nodeRunner(body)
            break;
        default:
            nodeRunner(body)
            break;
    }
}

private void kubernetesRunner(Closure body) {
    def cloud = config.kubernetes?.cloud ?: "kubernetes"
    def podTemplates = pipelineConfig.libraries.findAll { library, config ->
        config.pod_template
    }.collect { library, config ->
        config.pod_template
    }.join(" ")

    podTemplate(cloud: cloud, inheritFrom: podTemplates) {
        node(POD_LABEL) {
            checkout_scm()
            body()
        }
    }
}

private void nodeRunner(Closure body) {
    def nodeLabel = config.node?.label ?: ""
    
    node(nodeLabel) {
        checkout_scm()
        body()
    }
}