#!/usr/bin/env groovy

void call() {
    stage('Maven: Compile') {
        mvn 'clean compile'
    }

    stage('Maven: Unit Tests') {
        when(!config.skip_tests) {
            try {
                mvn 'test'
            } finally {
                junit allowEmptyResults: true, skipPublishingChecks: true, testResults: '**/target/surefire-reports/TEST-*.xml'
            }
        }
    }

    stage('Maven: Package') {
        mvn '-DskipTests install'
    }
}