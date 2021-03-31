#!/usr/bin/env groovy

void call(Map args = [:], Closure body) {
    if (!env.TAG_NAME)
        return

    println "running because of a push tag ${env.TAG_NAME}"
    body()
}