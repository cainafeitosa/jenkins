#!/usr/bin/env groovy

void call(Closure body) {
    if (config.containsKey("kubernetes") && !(config.containsKey("node"))) {
        env.CI_AGENT_RUNNER = "kubernetes"
        kubernetesRunner(body)
    } else {
        env.CI_AGENT_RUNNER = "node"
        nodeRunner(body)
    }
}

private void kubernetesRunner(Closure body) {
    def cloud           = config.kubernetes?.cloud ?: "kubernetes"
    def inheritFrom     = config.kubernetes?.inherit_from ?: "default"
    def agentContainers = []
    def agentVolumes    = []

    pipelineConfig.agent_templates.each { key, value ->
        if (value.containers != null) {
            agentContainers += value.containers
        }
        if (value.volumes != null) {
            agentVolumes += value.volumes
        }
    }

    podTemplate(cloud: cloud, inheritFrom: inheritFrom, containers: agentContainers, volumes: agentVolumes) {
        nodeRunner(body, POD_LABEL)
    }
}

private void nodeRunner(Closure body, String label = "") {
    label = label ?: config.node?.label ?: ""

    node(label) {
        ansiColor("xterm") {
            timestamps {
                try { checkout_scm() }
                catch(ignored) { 
                    println "step checkout_scm not present. Skipping job. To change this behavior, ensure the 'scm' library is loaded"
                    return
                }
                body()
            }
        }
    }
}