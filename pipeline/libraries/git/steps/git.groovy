#!/usr/bin/env groovy

void checkout() {
    defaults = [
        excludedMessage: '',
        excludedRegions: '',
        includedRegions: '',
    ]

    def excludedMessage = config.excludedMessage ?: defaults.excludedMessage
    def excludedRegions = config.excludedRegions ?: defaults.excludedRegions
    def includedRegions = config.includedRegions ?: defaults.includedRegions

    checkout poll: false, scm: [
        $class: 'GitSCM',
        branches: scm.branches,
        extensions: [
            [$class: 'MessageExclusion', excludedMessage: excludedMessage],
            [$class: 'LocalBranch'],
            [$class: 'PathRestriction', excludedRegions: excludedRegions, includedRegions: includedRegions]
        ],
        userRemoteConfigs: scm.userRemoteConfigs
    ]
}