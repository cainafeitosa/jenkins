#!/usr/bin/env groovy

void call() {

    def testResultsPath = config.test_results_path ?: "**/target/surefire-reports/TEST-*.xml"
    
    stage("Maven: Unit Tests") {
        try {
            mvn "clean test"
        } finally {
            junit allowEmptyResults: true, skipPublishingChecks: true, testResults: testResultsPath
        }
    }

}