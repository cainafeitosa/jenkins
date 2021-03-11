skip_default_checkout = true

jte { 
  allow_scm_jenkinsfile = false
  skip_default_checkout = true
}

@merge libraries {
  agent {
    kubernetes {
      @override podTemplates = 'docker sonar'
    }
  }
  docker
  sonarqube
}

stages{}
application_environments{} 
keywords{}