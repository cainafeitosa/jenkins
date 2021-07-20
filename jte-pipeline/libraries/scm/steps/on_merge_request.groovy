#!/usr/bin/env groovy

void call(Map args = [:], Closure body) {
    def targetBranch = env.CHANGE_TARGET

    // do nothing if not merge
    if (!targetBranch)
        return

    def sourceBranch = env.CHANGE_BRANCH

  // do nothing in source branch doesn't match
  if (args.from)
  if (!(sourceBranch ==~ (~args.from)))
    return

  // do nothing if target branch doesnt match
  if (args.to)
  if (!(targetBranch ==~ (~args.to)))
    return

    println "running because of a merge request from ${sourceBranch} to ${targetBranch}"
    body()
}