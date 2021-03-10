jte{ 
  allow_scm_jenkinsfile = false
}

libraries{
  agent {
    kubernetes {
      cloud = 'kubernetes'
    }
  }
}

stages{}
application_environments{} 
keywords{}