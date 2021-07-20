#!/usr/bin/env groovy

import npm.NpmWrapper

void call() {
    stage("NPM: Test") {
        when (!(pipelineConfig.skip_ci) && !(config.skip_test)) {
            def workDir = config.work_dir ?: "."

            dir(workDir) {
                npm = new NpmWrapper(this, config)
                npm "test"
            }
        }
    }
}