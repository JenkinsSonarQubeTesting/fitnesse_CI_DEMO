// test
import groovy.json.*
pipeline {
    agent {
        label 'testNode'
    }
    environment {
          def prNo = "${CHANGE_ID}"
          def repo_url = "${env.GIT_URL}"
          def repo_name = repo_url.replace("https://github.com/","").replace(".git","")
          def jenkins_credentials_ID = 'Carter-Admin'
    }
    stages {
        stage('Build') {
            steps {
                echo "${PATH}"
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
        stage('SonarQube Analysis'){
            steps{
                script{
                    def scannerHome = tool 'sonar_scanner'
                    if(env.BRANCH_NAME.startsWith("PR-")){
                        withCredentials([[$class: 'StringBinding', credentialsId: "${jenkins_credentials_ID}", variable: 'GITHUB_TOKEN']]){
                            withSonarQubeEnv('sonar'){
                                sh "${scannerHome}/bin/sonar-scanner -Dsonar.analysis.mode=preview "+
                                    "-Dsonar.github.repository=${repo_name} "+
                                    "-Dsonar.github.pullRequest=${prNo} "+
                                    "-Dsonar.github.oauth=${GITHUB_TOKEN} "+
                                    "-Dsonar.github.endpoint=https://api.github.com/"
                            }
                        }
                    }
                    else{
                        withSonarQubeEnv('sonar'){
                            sh "${scannerHome}/bin/sonar-scanner"
                        }
                    }
                }
            }
        }
        /*
        stage('Run Terraform'){
            steps{
                script{
                    def terraformHome = tool 'terraform'
                    withEnv(["PATH+terraform=${tool 'terraform'}"]){
                        withCredentials([
                            string(credentialsId: 'Carter-Research-ID', variable: 'USER_ID'),
                            string(credentialsId: 'aws-role-deploy', variable: 'ROLE_NAME')
                        ]){
                            sh "./gradlew deployTerraform -PUSER_ID=${USER_ID} -PROLE_NAME=${ROLE_NAME} -PterraformHome=${terraformHome}"
                            sh "terraform init"
                            sh "terraform apply -var 'aws_user_ID=${USER_ID}' -var 'role_name=${ROLE_NAME}' -var 'region=us-east-1' -input=false -auto-approve"
                        }
                    }
                }
            }
        }
        */
        stage('Check Security Risk'){
            when {
                expression {
                    "${prNo}" != "null"
                }
            }
            steps{
                script{
                    def changed_files = getChangedFiles()
                    if(isLowsecrisk(getLowsecriskConditions(), changed_files.filename)){
                       echo 'Low security risk, approving PR...'
                       postReview(getLowsecriskComment())
                    }
                }
            }
        }
    }
}

def parseJson(jsonText) {
    json_map = readJSON text: jsonText
    return json_map
}

def getChangedFiles(){
    script{
        withCredentials([[$class: 'StringBinding', credentialsId: "${jenkins_credentials_ID}", variable: 'GITHUB_TOKEN']]) {
            def pr_files = sh (script: "curl -s -H \"Authorization: token ${GITHUB_TOKEN}\" \"https://api.github.com/repos/${repo_name}/pulls/${prNo}/files\"", returnStdout: true).trim()
            return parseJson(pr_files)
        }
    }
}

def postReview(message){
    script{
        withCredentials([[$class: 'StringBinding', credentialsId: "${jenkins_credentials_ID}", variable: 'GITHUB_TOKEN']]) {
            sh "curl -s -H \"Authorization: token ${GITHUB_TOKEN}\" -X POST -d '{\"body\": \"${message}\", \"event\": \"APPROVE\"}' \"https://api.github.com/repos/${repo_name}/pulls/${prNo}/reviews\""
        }
    }
}

def getLowsecriskConditions(){
    return ['test', '.xml']
}

def getLowsecriskComment(){
    return '@lowsecrisk (Automated review)'
}

def isLowsecrisk(conditions, edited_files){
    for(file in edited_files){
        if (!(conditions.any{file.toLowerCase().contains(it)})){
           return false
       }
    }
    return true
}
