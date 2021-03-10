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

stages{
  continuous_integration {
    build
  }
}
application_environments{} 
keywords{}