pipeline {
    agent any
    environment {
          def scannerHome = tool 'sonar_scanner'
          def prNo = '${CHANGE_ID}'
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
                withCredentials([[$class: 'StringBinding', credentialsId: 'Carter-Admin', variable: 'GITHUB_TOKEN']]) {
                    withSonarQubeEnv('sonar') {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.analysis.mode=preview "+
                        "-Dsonar.github.repository=JenkinsSonarQubeTesting/fitnesse_CI_DEMO "+
                        "-Dsonar.github.pullRequest=${prNo} "+
                        "-Dsonar.github.oauth=${GITHUB_TOKEN} "+
                        "-Dsonar.github.endpoint=https://api.github.com/"
                    }
                }
            }
        }
    }
}
