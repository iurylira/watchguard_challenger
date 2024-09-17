pipeline {
     agent {
            docker {
                image 'maven:3.8.4-jdk-11'  // Replace with your base image if needed
                label 'docker'
                args '-v /var/run/docker.sock:/var/run/docker.sock'
            }
        }

    environment {
        DOCKER_IMAGE = 'wg-challenger-app'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/iurylira/watchguard_challenger.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }

    }

    post {
        success {
            echo 'Build and Deployment Completed Successfully!'
        }
        failure {
            echo 'Build or Deployment Failed!'
        }
    }
}
