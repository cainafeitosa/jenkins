libraries {
  runner
  maven
  docker
  sonarqube
}

stages {
  continuous_integration {
    unit_tests
    package_artifact
    static_code_analysis
    build_container_image
  }
}