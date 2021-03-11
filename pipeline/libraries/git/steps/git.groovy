#!/usr/bin/env groovy

package libraries.git

void checkout() {
    defaults = [
        excludedMessage = ''
        excludedRegions = ''
        includedRegions = ''
    ]

    def excludedMessage = config.excludedMessage ?: defaults.excludedMessage
    def excludedRegions = config.excludedRegions ?: defaults.excludedRegions
    def includedRegions = config.includedRegions ?: defaults.includedRegions

    checkout([
        $class: 'GitSCM',
        branches: scm.branches,
        extensions: [
            [$class: 'MessageExclusion', excludedMessage: excludedMessage],
            [$class: 'LocalBranch'],
            [$class: 'PathRestriction', excludedRegions: excludedRegions, includedRegions: includedRegions]
        ],
        userRemoteConfigs: scm.userRemoteConfigs
    ])
}