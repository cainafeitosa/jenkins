#!/usr/bin/env groovy

package helmfile

class HelmfileWrapper implements Serializable {
    protected script
    String kubeCredentialsId
    String kubeServerUrl
    String file
    String environment
    
    HelmfileWrapper(script) {
        this.script = script
    }

    void call(String args) {
        if (script.env.CI_AGENT_RUNNER == "kubernetes") {
            script.container("kube-tools") {
                kubeconfig(credentialsId: kubeCredentialsId, serverUrl: kubeServerUrl) {
                    sh "helmfile ${createCommandLineArgs(args)}"
                }
            }
        } else {
            kubeconfig(credentialsId: kubeCredentialsId, serverUrl: kubeServerUrl) {
                sh "helmfile ${createCommandLineArgs(args)}"
            }
        }
    }

    private String createCommandLineArgs(String args) {
        globalOptions = file ? "-f ${file}" : ""
        globalOptions = environment ? globalOptions + " -e ${environment}" : globalOptions
        return "${globalOptions} ${args}"
    }
}