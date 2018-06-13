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
                prNo = env.CHANGE_ID
                withCredentials([[$class: 'StringBinding', credentialsId: 'Carter-Admin', variable: 'GITHUB_TOKEN']]) {
                    withSonarQubeEnv('sonar') {
                        mvn "-Dsonar.analysis.mode=preview "+
                            "-Dsonar.github.pullRequest=${prNo} "+
                            "-Dsonar.github.oauth=${GITHUB_TOKEN} "+
                            "-Dsonar.github.endpoint=https://api.github.com/ org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar"
                    }
                }
            }
        }
    }
}
