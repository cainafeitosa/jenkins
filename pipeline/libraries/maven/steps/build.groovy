#!/usr/bin/env groovy

void call(Closure body) {
    if (pipelineConfig.libraries.agent.kubernetes) {
        container('maven') {
            compile()
            unit_test()
            package_artifact()
        }
    }
}