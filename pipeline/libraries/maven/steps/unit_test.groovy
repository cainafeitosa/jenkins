#!/usr/bin/env groovy

void call() {
    stage("Test: Unit Tests") {
        def testResultsPath = config.test_results_path ?: "**/target/surefire-reports/TEST-*.xml"
        
        try {
            mvn "test"
        } finally {
            junit allowEmptyResults: true, skipPublishingChecks: true, testResults: testResultsPath
        }
    }
}