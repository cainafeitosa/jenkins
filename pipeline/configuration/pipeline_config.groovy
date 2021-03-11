jte{ 
  allow_scm_jenkinsfile = false
}

@merge libraries{
  agent {
    kubernetes {
      @override podTemplates = 'docker sonar'
    }
  }
}

stages{}
application_environments{} 
keywords{}