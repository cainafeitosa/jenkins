jte{ 
  allow_scm_jenkinsfile = false
}

libraries{
  maven
  agent {
    kubernetes {
      cloud = 'kubernetes'
    }
  }
}

stages{}
application_environments{} 
keywords{}