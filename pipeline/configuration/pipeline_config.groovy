jte { 
  allow_scm_jenkinsfile = false
}

@merge libraries {
  agent {
    kubernetes {
      @override podTemplates = 'docker sonar'
    }
  }
  git
  docker
  sonarqube 
}

stages{}
application_environments{} 
keywords{}