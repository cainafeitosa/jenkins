void call() {
  stage("Maven: Compile") {
    println "mvn -B clean compile"
  }
}
