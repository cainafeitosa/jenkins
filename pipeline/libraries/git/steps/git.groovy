#!/usr/bin/env groovy

void checkout() {
    defaults = [
        excludedMessage = ''
        excludedRegions = ''
        includedRegions = ''
    ]

    def excludedMessage = config.excludedMessage ?: defaults.excludedMessage
    def excludedRegions = config.excludedRegions ?: defaults.excludedRegions
    def includedRegions = config.includedRegions ?: defaults.includedRegions

    println scm
    println env
}