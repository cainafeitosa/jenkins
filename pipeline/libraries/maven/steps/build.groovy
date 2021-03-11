#!/usr/bin/env groovy

void call() {
    stage('Maven: Compile') {
        mvn 'clean compile'
    }

    stage('Maven: Unit Tests') {
        sh 'env'
        when(!config.skipTests || env.BRANCH == 'develop') {
            try {
                mvn 'test'
            } finally {
                junit allowEmptyResults: true, skipPublishingChecks: true, testResults: '**/target/surefire-reports/TEST-*.xml'
            }
        }
    }

    stage('Maven: Package') {
        mvn '-DskipTests install'
        archiveArtifacts 'target/*.jar'
    }
}