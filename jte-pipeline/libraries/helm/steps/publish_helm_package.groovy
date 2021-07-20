#!/usr/bin/env groovy

void call() {
    stage("Helm: Release") {
        def chartPath = config.chart_path ?: "."

        dir(chartPath) {
            chartInfo    = readYaml file: "Chart.yaml"
            chartName    = chartInfo.name
            chartVersion = chartInfo.version
        }

        withCredentials([usernameColonPassword(credentialsId: config.credentials_id, variable: 'USERPASS')]) {
            sh "curl -u \${USERPASS} ${config.registry}/repository/${config.repository}/ --upload-file ${chartName}-${chartVersion}.tgz -v"
        }
    }
}