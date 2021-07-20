#!/usr/bin/env groovy

import helm.HelmWrapper

void call() {
    stage("Helm: Lint") {
        def chartPath = config.chart_path ?: "."
        helm = new HelmWrapper(this)

        helm "lint ${chartPath}"
    }
}