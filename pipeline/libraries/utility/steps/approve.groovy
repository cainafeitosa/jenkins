#!/usr/bin/env groovy

void call() {
    stage("Approval") {
        timeout(time: 5, unit: "DAYS") {
            input message: "Approve Deploy?", ok: "Yes"
        }
    }
}