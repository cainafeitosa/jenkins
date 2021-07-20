#!/usr/bin/env groovy

package npm

class NpmWrapper implements Serializable {
    protected script
    protected config
    
    NpmWrapper(script, config) {
        this.script = script
        this.config = config
    }

    void call(String args) {
        if (script.env.CI_AGENT_RUNNER == "kubernetes") {
            script.container("npm") {
                sh "npm ${args}"
            }
        } else {
            script.withEnv(["PATH+NODE_HOME=${script.tool this.config.node_installation}/bin"]) {
                sh "npm ${args}"
            }
        }
    }
}