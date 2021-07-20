#!/usr/bin/env groovy

import maven.MavenWrapper

void call() {
    stage("Maven: Test") {
        when (!(config.skip_test) && !(pipelineConfig.skip_ci)) {
            mvn = new MavenWrapper(this, config)

            try {
                mvn "test"
            } finally {
                def testResultsPath = config.test_results_path ?: "**/target/surefire-reports/TEST-*.xml"
                junit allowEmptyResults: true, skipPublishingChecks: true, testResults: testResultsPath
            }
        }
    }
}