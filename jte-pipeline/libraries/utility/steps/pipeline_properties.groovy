#!/usr/bin/env groovy

@Init
void call() {
    pipelineProperties = []

    if (config.notify?.microsoft_teams_webhook) {
        pipelineProperties.add(
            office365ConnectorWebhooks([
                [notifyAborted: true, notifyFailure: true, notifySuccess: true, notifyUnstable: true, startNotification: true, url: config.notify.microsoft_teams_webhook]
            ])
        )
    }

    properties(pipelineProperties)
}