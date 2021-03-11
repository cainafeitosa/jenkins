#!/usr/bin/env groovy

void call() {
    if (pipelineConfig.libraries.agent.kubernetes) {
        container('maven') {
            compile()
            unit_test()
            package_artifact()
        }
    }
}