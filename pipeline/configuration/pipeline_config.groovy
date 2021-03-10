allow_scm_jenkinsfile = false

@merge jte{ 
  allow_scm_jenkinsfile = false
}

@merge libraries{
  agent {
    kubernetes {
      cloud = 'kubernetes'
    }
    label = 'master'
  }
}

stages{} 
application_environments{} 
keywords{} 