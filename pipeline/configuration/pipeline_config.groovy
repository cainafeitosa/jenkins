jte { 
  allow_scm_jenkinsfile = false
}

@merge libraries {
  agent {
    kubernetes {
      @override podTemplates = 'docker sonar'
    }
  }
  git {
    excludedMessage = '^(?s)\\[maven-release-plugin\\].*'
  }
  docker
  sonarqube 
}

stages{}
application_environments{} 
keywords{}