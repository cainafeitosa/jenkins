#!/usr/bin/env groovy

import hudson.plugins.sonar.SonarGlobalConfiguration

def call() {
    def defaults = [
        enabled: true,
        credential_id: "sonarqube",
        wait_for_quality_gate: true, 
        enforce_quality_gate: true,
        installation_name: "SonarQube",
        timeout_duration: 15,
        timeout_unit: "MINUTES",
        cli_parameters: []
    ]

    Boolean enableSonarQubeAnalysis = config.enabled ?: defaults.enabled
    def wait = config.wait_for_quality_gate ?: defaults.wait_for_quality_gate
    def enforce = config.enforce_quality_gate ?: defaults.enforce_quality_gate
    def installationName = config.installation_name ?: defaults.installation_name
    def timeoutDuration = config.timeout_duration ?: defaults.timeout_duration
    def timeoutUnit = config.timeout_unit ?: defaults.timeout_unit
    def unstashList = config.unstash ?: defaults.unstash

    stage('SonarQube analysis') {
        when(enableSonarQubeAnalysis) {
            validateInstallationExists(installationName)

            withSonarQubeEnv(installationName) {
                if(jte.libraries.maven) {
                    mvn 'sonar:sonar'
                } else {
                    sonar_scanner ''
                }
            }

            if(wait) {
                timeout(time: timeoutDuration, unit: timeoutUnit) {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK' && enforce) {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }
    }
}

void validateInstallationExists(installationName){
    boolean exists = SonarGlobalConfiguration.get().getInstallations().find{
        it.getName() == installationName
    } as boolean
    if(!exists){
        error "SonarQube: installation '${installationName}' does not exist"
    }
}