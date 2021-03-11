#!/usr/bin/env grovoy

void checkout() {
    defaults = [
        excludedRegions = ''
        includedRegions = ''
    ]

    def excludedRegions = config.excludedRegions ?: defaults.excludedRegions
    def includedRegions = config.includedRegions ?: defaults.includedRegions

    println env
    println scm

    checkout([
        $class: 'GitSCM',
        branches: scm.branches,
        extensions: [
            [$class: 'MessageExclusion', excludedMessage: config.excludedMessage],
            [$class: 'LocalBranch'],
            [$class: 'PathRestriction', excludedRegions: excludedRegions, includedRegions: includedRegions]
        ],
        userRemoteConfigs: scm.userRemoteConfigs
    ])
}