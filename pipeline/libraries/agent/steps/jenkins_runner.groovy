#!/usr/bin/env groovy

void call(Closure body) {
    println config.kubernetes
    println config.label
}