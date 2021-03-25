#!/usr/bin/env groovy

void call(String mavenOpts, String mavenPhase) {
    def commandLine = "mvn -B -e -V ${mavenOpts}"
    
    def mavenPhases = [
        "compile": {
            sh "${commandLine} compile"
        },
        "test": {
            try {
                sh "${commandLine} test"
            } finally {
                junit allowEmptyResults: true, skipPublishingChecks: true, testResults: '**/target/surefire-reports/TEST-*.xml'
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

void call(String mavenPhase){
    this.call(mavenOpts = "", mavenPhase)
}