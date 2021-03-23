#!/usr/bin/env groovy

void call() {
    stage('SCM Checkout') {
        def defaults = [
            excluded_message: '',
            excluded_regions: '',
            included_regions: '',
        ]

        def excludedMessage = config.excluded_message ?: defaults.excluded_message
        def excludedRegions = config.excluded_regions ?: defaults.excluded_regions
        def includedRegions = config.included_regions ?: defaults.included_regions

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
}