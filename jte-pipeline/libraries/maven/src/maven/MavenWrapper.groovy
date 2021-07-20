#!/usr/bin/env groovy

package maven

class MavenWrapper implements Serializable {
    protected script
    protected config
    
    MavenWrapper(script, config) {
        this.script = script
        this.config = config
    }

    void call(String args) {
        if (script.env.CI_AGENT_RUNNER == "kubernetes") {
            script.container("maven") {
                script.configFileProvider([script.configFile(fileId: this.config.mvn_settings, variable: 'MAVEN_SETTINGS')]) {
                    sh "mvn -s ${MAVEN_SETTINGS} -B -U -e ${this.config.cli_options ?: ""} ${args}"
                }
            }
        } else {
            script.withMaven(maven: this.config.mvn_installation, jdk: this.config.jdk_installation, mavenSettingsConfig: this.config.mvn_settings) {
                sh "mvn -B ${this.config.cli_options ?: ""} ${args}"
            }
        }
    }
}