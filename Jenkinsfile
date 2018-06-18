import groovy.json.JsonSlurper
pipeline {
    agent any
    environment {
          def scannerHome = tool 'sonar_scanner'
          def prNo = '${CHANGE_ID}'
          def repo_url = "${env.GIT_URL}"
          def repo_name = repo_url.replace("https://github.com/","").replace(".git","")
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
        stage('Check Security Risk'){
            steps{
               script{
                   withCredentials([[$class: 'StringBinding', credentialsId: 'Carter-Admin', variable: 'GITHUB_TOKEN']]) {
                       if(prNo){
                           changed_files = sh (script: "curl -s -H \"Authorization: token ${GITHUB_TOKEN}\" \"https://api.github.com/repos/${repo_name}/pulls/${prNo}/files\"", returnStdout: true).trim()
                           def slurper = new groovy.json.JsonSlurper()
                           def map = slurper.parseText(changed_files)
                           // test echo
                           map.filename.each {echo "${it}"}
                           if(isLowsecrisk(getLowsecriskConditions(), map.filename)){
                               echo 'Low security risk, posting comment...'
                               postComment(getLowsecriskComment())
                           }
                       }
                   }
               }
            }
        }
    }
}

def postComment(message){
    script{
        withCredentials([[$class: 'StringBinding', credentialsId: 'Carter-Admin', variable: 'GITHUB_TOKEN']]) {
            sh "curl -s -H \"Authorization: token ${GITHUB_TOKEN}\" -X POST -d '{\"body\": \"${message}\"}' \"https://api.github.com/repos/${repo_name}/issues/${prNo}/comments\""
        }
    }
}

def getLowsecriskConditions(){
    return ['test', '.xml']   
}

def getLowsecriskComment(){
    return '@lowsecrisk (Automated comment)'   
}

def isLowsecrisk(conditions, edited_files){
    for(file in edited_files){
        if (!(conditions.any{file.toLowerCase().contains(it)})){
           return false
       }
    }
    return true
}
