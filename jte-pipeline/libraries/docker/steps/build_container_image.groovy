#!/usr/bin/env groovy

import docker.DockerWrapper

@Validate
void configs() {
    if (config.containsKey("modules")) {
        if (!(config.modules instanceof Map)) {
           error "Library parameter 'modules' is a ${config.build_args.getClass()} when a block was expected" 
        }configs

        config.modules.each { module, module_config ->
            if (module_config.containsKey("build_args")) {
                if (!(module_config.build_args instanceof Map)) {
                    error "Library parameter 'build_args' is a ${module_config.build_args.getClass()} when a block was expected"
                }
            }
        }
    }

    if (config.containsKey("build_args")) {
        if (!(config.build_args instanceof Map)) {
            error "Library parameter 'build_args' is a ${config.build_args.getClass()} when a block was expected"
        }
    }
}

@Init
void setAgentTemplates() {
    def agentImage = config.agent?.image ?: "docker:19.03"
    def daemonArgs = config.agent?.daemon_options ?: ""
    pipelineConfig.agent_templates = (pipelineConfig.agent_templates instanceof Map) ? pipelineConfig.agent_templates : [:]

    pipelineConfig.agent_templates.docker = [
        containers: [
            containerTemplate(
                name: "daemon",
                image: "${agentImage}-dind",
                privileged: true,
                args: daemonArgs,
                envVars: [
                    envVar(key: "DOCKER_TLS_CERTDIR", value: "")
                ]
            ),
            containerTemplate(
                name: "docker",
                image: agentImage,
                command: "sleep",
                args: "9999999",
                envVars: [
                    envVar(key: "DOCKER_HOST", value: "tcp://localhost:2375")
                ]
            )
        ]
    ]
}

private def getImagesToBuild() {
    def registry = config.registry ? config.registry.replaceAll(/^(https?:\/\/)/, "") : ""
    def images = [:]

    if (config.modules) {
        config.modules.each { module, module_config ->
            def imageName      = module_config.image_name ?: "${env.CI_PROJECT_PATH}-${module.replaceAll('_', '-')}"
            def dockerfilePath = module_config.dockerfile_path ?: "${module}/Dockerfile"
            def contextPath    = module_config.context_path ?: "${module}/"
            def argsList       = []

            module_config.build_args.each { argument, value ->
                argsList.add("--build-arg ${argument}='${value}'")
            }

            images[module] = [
                registry: registry,
                name: imageName,
                tag: env.CI_TAG_VERSION,
                build_args: argsList.join(" "),
                dockerfile: dockerfilePath,
                context: contextPath
            ]
        }
    } else {
        def imageName      = config.image_name ?: env.CI_PROJECT_PATH
        def dockerfilePath = config.dockerfile_path ?: "Dockerfile"
        def contextPath    = config.context_path ?: "."
        def argsList       = []

        config.build_args.each { argument, value ->
            argsList.add("--build-arg ${argument}='${value}'")
        }

        images[env.CI_PROJECT_NAME] = [
            registry: registry,
            name: imageName,
            tag: env.CI_TAG_VERSION,
            build_args: argsList.join(" "),
            dockerfile: dockerfilePath,
            context: contextPath
        ]
    }

    return images
}

void call() {
    stage("Docker: Build") {
        when (!(pipelineConfig.skip_ci)) {
            withDocker = new DockerWrapper(this, config)

            pipelineConfig.docker_images = getImagesToBuild()

            withDocker {
                pipelineConfig.docker_images.each { name, image ->
                    println "==============================[ Building ${name} ]=============================="

                    try {
                        docker.image("${image.name}:latest").pull()
                    } catch(ignored) {}

                    def cacheFrom = image.registry ? "${image.registry}/${image.name}:latest" : "${image.name}:latest"
                    docker.build("${image.name}:${image.tag}", "--cache-from ${cacheFrom} ${image.build_args} -f ${image.dockerfile} ${image.context}")
                }
            }
        }
    }
}