pipeline {
    agent none
    environment {
        WORKSPACE_DIR = "${env.WORKSPACE}"
        PROJECT_DIR = "/main-service"
        GRADLE_CACHE = "/tmp/.gradle"
    }
    stages {
        stage('Checkout') {
            agent { label 'agent' }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-creds', usernameVariable: 'GITHUB_USER', passwordVariable: 'GITHUB_TOKEN')]) {
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: "*/master"]],
                            userRemoteConfigs: [[
                                url: 'https://github.com/Recwayer/cdok.git',
                                credentialsId: 'github-creds'
                            ]]
                        ])
                    }
                }
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'gradle:8.10.2-jdk17'
                    args "-u root -v ${GRADLE_CACHE}:/home/gradle/.gradle -v ${WORKSPACE_DIR}:${PROJECT_DIR} --workdir=${PROJECT_DIR}"
                    reuseNode true
                }
            }
            steps {
                script {
                    sh 'gradle clean build -x test'
                }
            }
        }
        stage('Deploy') {
            agent { label 'agent' }
            steps {
                script {
                    sh 'docker-compose up -d'
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}