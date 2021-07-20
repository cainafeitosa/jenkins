#!/usr/bin/env groovy

void call(Map args = [:], Closure body) {
    def branch = env.BRANCH_NAME

    if (branch == env.TAG_NAME)
        return

    if (args.to)
    if (!(branch ==~ args.to))
        return

    println "running because of a push to ${branch}"
    body()
}