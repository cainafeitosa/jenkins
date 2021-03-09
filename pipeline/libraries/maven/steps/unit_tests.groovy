void call() {
  stage("Maven: Test") {
    println "mvn -B test"
  }
}
