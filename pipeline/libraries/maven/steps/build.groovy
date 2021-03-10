#!/usr/bin/env groovy

void call(Closure body) {
    stage('Maven: Compile') {
        println 'mvn -B compile'
    }
}