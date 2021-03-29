#!/usr/bin/env groovy

void call(Closure body) {
    def nodeLabel = config.node.label ?: ""
    node(nodeLabel, body)
}