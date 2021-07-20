#!/usr/bin/env groovy

import maven.MavenWrapper

@Init
void setAgentTemplates() {
    def agentImage = config.agent?.image ?: "maven:3-jdk-11-alpine"
    pipelineConfig.agent_templates = (pipelineConfig.agent_templates instanceof Map) ? pipelineConfig.agent_templates : [:]

    pipelineConfig.agent_templates.maven = [
        containers: [
            containerTemplate(
                name: "maven",
                image: agentImage,
                command: "sleep",
                args: "9999999"
            )
        ],
        volumes: [persistentVolumeClaim(mountPath: "/root/.m2/repository", claimName: "maven-repo", readOnly: false)]
    ]
}

void call() {
    stage("Maven: Build") {
        when (!(pipelineConfig.skip_ci)) {
            def workDir = config.work_dir ?: "."

            dir(workDir) {
                mvn = new MavenWrapper(this, config)
                mvn "-Dmaven.test.skip=true clean package"
            }
        }
    }
}