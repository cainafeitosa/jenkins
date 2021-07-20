#!/usr/bin/env groovy

void call(Map args, Closure body) {
    // check required parameters
    if (!args.url)
        error """
        withGit syntax error.
        Input Parameters:
          url: https git url to repository (required)
          cred: jenkins credential ID for git. (optinal)
          branch: branch in the repository to checkout. defaults to master. (optional)
        """
    
    def credentialsId = args.cred ?: env.CI_REPOSITORY_CREDENTIALS
    def branch = args.branch ?: "master"
    def matcher = args.url =~ /^((?<protocol>https?|ssh)(:\/\/)|(?<user>[\w\d\._]+)@)(?<domain>[a-zA-Z0-9\.\-_]+(?<port>:[0-9]+)?)(:|\/)(?<namespace>[a-zA-Z0-9\-\/]+)\/(?<project>[a-zA-Z0-9\-_]+)(\.git)?/

    if (matcher.matches()) {
        protocol = matcher.group("protocol")
        project_name = matcher.group("project") - ".git"
    }

    sh "rm -rf ${project_name}"

    dir(project_name) {
        git url: args.url,
            credentialsId: credentialsId,
            branch: branch,
            changelog: false,
            poll: false

        sh """
            git config user.name '${config.author_name ?: "Jenkins"}'
            git config user.email '${config.author_email ?: "jenkins@nothing.com"}'
            git branch -u origin/${branch}
        """

        if (protocol ==~ "ssh") {
            sshagent (credentials: [credentialsId]) {
                body()
            }
        } else {
            withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                sh "git config --local credential.helper '!f() { echo username=\${GIT_USERNAME}; echo password=\${GIT_PASSWORD}; }; f'"
                body()
            }
        }
    }
}