#!/usr/bin/env groovy

void call(String mavenOpts, String mavenPhase) {
    def commandLine = "mvn -B -e -V ${mavenOpts}"
    def testResultsPath = config.test_results_path ?: '**/target/surefire-reports/TEST-*.xml'
    def mavenPhases = [
        "compile": {
            sh "${commandLine} compile"
        },
        "test": {
            try {
                sh "${commandLine} test"
            } finally {
                junit allowEmptyResults: true, skipPublishingChecks: true, testResults: testResultsPath
            }
        },
        "install": {
            sh "${commandLine} install"
        }
    ]

    def c = mavenPhases.get(mavenPhase)
    c.resolveStrategy = Closure.DELEGATE_FIRST
    c.delegate = this        
    c.call()
}

void call(String mavenPhase) {
    String mavenOpts = ''
    this.call(mavenOpts, mavenPhase)
}