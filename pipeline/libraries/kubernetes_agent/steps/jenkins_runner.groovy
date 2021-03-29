#!/usr/bin/env groovy

void call(Closure body) {

    def cloud = config.cloud ?: "kubernetes"
    def podTemplates = pipelineConfig.libraries.findAll { library, config ->
        config.runs_on?.pod_template
    }.collect { library, config ->
        config.runs_on.pod_template
    }.join(" ")

    podTemplate(cloud: cloud, inheritFrom: podTemplates) {
        node(POD_LABEL) {
            try { unstash "workspace" }
            catch(ignored) { 
                println "'workspace' stash not present. To change this behavior, ensure the 'git' library is loaded"
                return
            }
            body()
        }
    }
    
}