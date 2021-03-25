#!/usr/bin/env groovy

void call(Map args) {
    def commandLine = "mvn -B -e -V"
    def actions = [
        "compile": { mvnArgs ->
            sh "${commandLine} ${mvnArgs} clean compile"
        },
        "test": { mvnArgs ->
            try {
                sh "${commandLine} ${mvnArgs} test"
            } finally {
                junit allowEmptyResults: true, skipPublishingChecks: true, testResults: '**/target/surefire-reports/TEST-*.xml'
            }
        },
        "install": { mvnArgs ->
            sh "${commandLine} ${mvnArgs} install"
        }
    ]

    args.each{ action, value ->
        def c = actions.get(action)
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c.delegate = this        
        c.call(value)
    }
}

void call(String action){
    String a = action.toString()
    this.call([
        (a): null
    ])
}