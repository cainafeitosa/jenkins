void call() {
  stage("Maven: Compile") {
    podTemplate(
      containers: [
        containerTemplate(name: 'maven', image: 'maven:3-jdk-8', command: 'sleep', args: '9999999', ttyEnabled: true)
      ]
    ) {
      node(POD_LABEL) {
        container('maven') {
          sh 'mvn --version'
        }
      }
    }
  }
}
