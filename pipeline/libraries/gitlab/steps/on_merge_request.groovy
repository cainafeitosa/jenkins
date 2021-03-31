#!/usr/bin/env groovy

void call(Map args = [:], Closure body) {
    // do nothing if not merge
    if (!env.CHANGE_TARGET)
        return

    def sourceBranch = env.CHANGE_BRANCH
    def targetBranch = env.CHANGE_TARGET

    // do nothing if source branch doesn't match
    if (args.from)
    if (!sourceBranch.collect{ it ==~ args.from}.contains(true))
        return

    // do nothing if target branch doesnt match
    if (args.to)
    if (!(targetBranch ==~ args.to))
        return

    println "running because of a merge_request_event from ${sourceBranch} to ${targetBranch}"
    body()
}