#!/usr/bin/env groovy

package helm

class HelmWrapper implements Serializable {
    protected script
    
    HelmWrapper(script) {
        this.script = script
    }

    void call(String args) {
        if (script.env.CI_AGENT_RUNNER == "kubernetes") {
            script.container("kube-tools") {
            sh "helm ${args}"
            }
        } else {
            sh "helm ${args}"
        }
    }
}