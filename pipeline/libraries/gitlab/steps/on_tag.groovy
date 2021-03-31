#!/usr/bin/env groovy

void call(Map args = [:], Closure body) {
    def tag = env.TAG_NAME

    if (!tag)
        return

    println "running because of a push tag ${tag}"
    body()
}