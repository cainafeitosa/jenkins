jte{ 
  allow_scm_jenkinsfile = false
}

@merge libraries{
  agent {
    kubernetes {
      cloud = 'kubernetes'
    }
  }
}

stages{}
application_environments{} 
keywords{}