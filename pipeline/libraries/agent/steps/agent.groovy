#!/usr/bin/env groovy

void call(Closure body) {
    def agentRunner = config.size() == 0 ? null : config.keySet().first()

    switch (agentRunner) {
        case "kubernetes":
            kubernetesRunner(body)
            break;
        case "node":
            nodeRunner(body)
            break;
        default:
            kubernetesRunner(body)
            break;
    }
}