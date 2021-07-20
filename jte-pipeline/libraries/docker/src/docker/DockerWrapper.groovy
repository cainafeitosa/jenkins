#!/usr/bin/env groovy

package docker

class DockerWrapper implements Serializable {
    protected script
    protected config
    
    DockerWrapper(script, config) {
        this.script = script
        this.config = config
    }

    void call(Closure body) {
        if (script.env.CI_AGENT_RUNNER == "kubernetes") {
            script.container("docker") {
                script.docker.withRegistry(this.config.registry, this.config.credentials_id) {
                    body()
                }
            }
        } else {
            script.docker.withRegistry(this.config.registry, this.config.credentials_id) {
                body()
            }
        }
    }
}