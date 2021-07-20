#!/usr/bin/env groovy

import helmfile.HelmfileWrapper

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

void call(appEnv) {
    stage("Deploy To: ${appEnv.long_name}") {
        def kubeCredentials         = appEnv.k8s_credentials_id ?:
                                      config.k8s_credentials_id ?: {error "Kubernetes Credential Not Defined"}()

        def kubeServerUrl           = appEnv.k8s_server_url ?:
                                      config.k8s_server_url ?: {error "Kubernetes Server URL Not Defined"}()

        def helmfileRemote          = appEnv.helmfile_remote != null ? appEnv.helmfile_remote.toBoolean() :
                                      config.helmfile_remote != null ? config.helmfile_remote : false

        def helmfileRepoCredentials = appEnv.helmfile_repository_credentials ?:
                                      config.helmfile_repository_credentials ?: env.CI_REPOSITORY_CREDENTIALS

        def helmfileRepoUrl         = appEnv.helmfile_repository_url ?:
                                      config.helmfile_repository_url ?: env.CI_REPOSITORY_URL.replaceAll(/\/([\w\d\-_]+)(?:\.git)?$/, '/helm-charts.git')

        def helmfileRepoBranch      = appEnv.helmfile_repository_branch ?:
                                      config.helmfile_repository_branch ?: "master"

        def helmfilePath            = appEnv.helmfile_path ?:
                                      config.helmfile_path ?: null

        def versionFile             = appEnv.version_file ?: "environments/${appEnv.long_name.toLowerCase()}/version.yaml"

        def workDir                 = helmfileRemote ? "deploy" : "."

        dir(workDir) {
            if (helmfileRemote) {
                git credentialsId: helmfileRepoCredentials, url: helmfileRepoUrl, branch: helmfileRepoBranch
            }

            if (appEnv.promote_version_from) {
                def sourceEnv             = appEnv.promote_version_from
                def sourceVersionFile     = "${sourceEnv}".version_file ?: "environments/" + "${sourceEnv}".long_name.toLowerCase() + "/version.yaml"
                sh "cp ${sourceVersionFile} ${versionFile}"
            }

            if (!(pipelineConfig.skip_ci) && pipelineConfig.docker_images != null) {
                def versionFileYaml = [:]

                if (fileExists(versionFile)) {
                    versionFileYaml = readYaml file: versionFile
                }

                pipelineConfig.docker_images.each { name, image ->
                    versionFileYaml[name] = image.tag
                }

                writeYaml file: versionFile, data: versionFileYaml, overwrite: true
            }

            helmfile = new HelmfileWrapper(this)
            helmfile.kubeCredentialsId = kubeCredentialsId
            helmfile.kubeServerUrl     = kubeServerUrl
            helmfile.file              = helmfilePath
            helmfile.environment       = appEnv.long_name.toLowerCase()
            helmfile "apply"
        }
    }
}