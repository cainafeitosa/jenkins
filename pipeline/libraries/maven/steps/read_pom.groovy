#!/usr/bin/env groovy

def call(pomFile = 'pom.xml') {
    def pom = readMavenPom file: pomFile
    pomGroupId = pom.groupId ?: pom.parent.groupId
    pomArtifactId = pom.artifactId
    pomVersion = pom.version
    pomPackaging = pom.packaging
    pomName = pom.name
}