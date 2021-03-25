#!/usr/bin/env groovy

import hudson.AbortException

void call(){
    stage("Checkout SCM") {
        cleanWs()
        try {
            checkout scm
        } catch(AbortException ex) {
            println "scm var not present, skipping source code checkout" 
        } catch(err){
            println "exception ${err}" 
        } 
    
        stash name: 'workspace', allowEmpty: true, useDefaultExcludes: false
    }
}