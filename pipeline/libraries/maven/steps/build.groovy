#!/usr/bin/env groovy

void call() {
    stage('Maven: Compile') {
        mvn 'clean compile'
    }

    stage('Maven: Unit Tests') {
        mvn 'test'
    }

    stage('Maven: Package') {
        mvn '-DskipTests install'
        junit '**/target/surefire-reports/*.xml'
        archiveArtifacts 'target/*.jar'
    }
}