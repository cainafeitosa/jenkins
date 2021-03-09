void call() {
  stage("Maven: Test") {
    podTemplate(
      containers: [
        containerTemplate(name: 'maven', image: 'maven:3-jdk-11', command: 'sleep', args: '9999999', ttyEnabled: true)
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
