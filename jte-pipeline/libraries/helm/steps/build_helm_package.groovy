#!/usr/bin/env groovy

import helm.HelmWrapper

@Init
void setAgentTemplates() {
    def agentImage = config.agent?.image ?: "quay.io/roboll/helmfile:helm3-v0.138.7"
    pipelineConfig.agent_templates = (pipelineConfig.agent_templates instanceof Map) ? pipelineConfig.agent_templates : [:]

    pipelineConfig.agent_templates.kube = [
        containers: [
            containerTemplate(
                name: "kube-tools",
                image: agentImage,
                command: "sleep",
                args: "9999999"
            )
        ]
    ]
}

void call() {
    stage("Helm: Build") {
        def chartPath = config.chart_path ?: "."
        helm = new HelmWrapper(this)

        helm "package ${chartPath} -u"
    }
}