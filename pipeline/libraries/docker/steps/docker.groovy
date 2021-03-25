#!/usr/bin/env groovy

def build() {
    def imageName = config.image_repository ? "${config.image_repository}/${config.image_name}" : config.image_name
    def dockerfile = config.dockerfile ?: "Dockerfile"
    def contextPath = config.context_path ?: "."
    def buildArgs = config.build_args ? "${config.build_args} -f ${dockerfile} ${contextPath}" : "-f ${dockerfile} ${contextPath}"

    docker.build(imageName, buildArgs)
}

void push(builtImage) {
    docker.withRegistry(config.registry, config.credentials_id) {
        builtImage.push('latest')
    }
}