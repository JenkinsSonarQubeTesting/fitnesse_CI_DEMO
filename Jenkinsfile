// Testing build triggers
pipeline {
    agent any
    environment {
          def scannerHome = tool 'sonar_scanner'
    }
    stages {
        stage('Build') {
            steps {
                  sh './gradlew'
            }
            
        }
        /*
        stage('Test') {
            steps {
                  sh './gradlew test'
            }
           
        }
        */
        stage('Sonarqube'){
            steps{
                withSonarQubeEnv('sonar') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
    }
}
