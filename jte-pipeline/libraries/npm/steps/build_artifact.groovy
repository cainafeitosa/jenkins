#!/usr/bin/env groovy

import npm.NpmWrapper

@Init
void setAgentTemplates() {
    def agentImage = config.agent?.image ?: "node:10-alpine"
    pipelineConfig.agent_templates = (pipelineConfig.agent_templates instanceof Map) ? pipelineConfig.agent_templates : [:]

    pipelineConfig.agent_templates.npm = [
        containers: [
            containerTemplate(
                name: "npm",
                image: agentImage,
                command: "sleep",
                args: "9999999"
            )
        ],
        volumes: [persistentVolumeClaim(mountPath: "/root/.npm", claimName: "npm-cache", readOnly: false)]
    ]
}

@Init
void setEnv() {
    env.CI = true
    env.npm_config_ci_name = "jenkins"
    env.npm_config_registry = config.registry ?: ""
    env.npm_config_strict_ssl = false
}

void call() {
    stage("NPM: Build") {
        when (!(pipelineConfig.skip_ci)) {
            def workDir = config.work_dir ?: "."

            dir(workDir) {
                npm = new NpmWrapper(this, config)
                npm "ci --verbose"
                npm "run build --verbose"
            }
        }
    }
}