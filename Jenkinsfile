// testttt
pipeline {
    agent any
    environment {
          def scannerHome = tool 'sonar_scanner'
    }
    stages {
        stage('Build') {
            steps {
              
                withSonarQubeEnv('Sonarqube Scanner') {
                    sh "${scannerHome}/bin/sonar-scanner"
                   //sh './gradlew --info sonarqube'
                }
            }
        }
    }
}
