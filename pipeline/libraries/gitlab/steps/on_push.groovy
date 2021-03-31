#!/usr/bin/env groovy

void call(Map args = [:], Closure body) {
    def branch = env.BRANCH_NAME

    // do nothing if a push tag
    if (branch == env.TAG_NAME)
        return

    // do nothing if branch doesn't match regex
    if (args.to)
    if (!(branch ==~ args.to))
        return

    println "running because of a push to ${branch}"
    body()
}