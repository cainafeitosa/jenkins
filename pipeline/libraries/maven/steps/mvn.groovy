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

    invalidPhase = mavenPhase.findAll{ !(it.getKey() in mavenPhases.keySet()) }
    if (invalidPhase) error "Unknown maven phase: ${invalidPhase.collect{ it.getKey() }.join(", ")}"
  
    if (!(mavenPhase.subMap(mavenPhases.keySet()))) error "maven: You must use an phase: ${mavenPhases.keySet().join(", ")}"

    def c = mavenPhases.get(mavenPhase)
    c.resolveStrategy = Closure.DELEGATE_FIRST
    c.delegate = this        
    c.call()
}

void call(String mavenPhase) {
    String mavenOpts = ''
    this.call(mavenOpts, mavenPhase)
}