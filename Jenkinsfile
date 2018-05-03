// testttt
pipeline {
    agent any
    environment {
          def scannerHome = tool 'Sonarqube Scanner'
    }
    stages {
        stage('Build') {
            steps {
              
                withSonarQubeEnv('sonar') {
                    sh "${scannerHome}/bin/sonar-scanner"
                   //sh './gradlew --info sonarqube'
                }
            }
        }
    }
}
