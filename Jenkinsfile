pipeline {
    agent any
    environment {
          def scannerHome = tool 'sonar_scanner'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
              
                //withSonarQubeEnv('sonar') {
                    //sh "${scannerHome}/bin/sonar-scanner"
                   //sh './gradlew --info sonarqube'
                //}
            }
        }
        stage('Sonarqube'){
            steps{
                withSonarQubeEnv('sonar') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
    }
}
