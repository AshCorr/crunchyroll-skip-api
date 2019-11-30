pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }

    stages {
        stage('Clone repository') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -Dproduction'
            }
        }

        stage('Build image') {
            steps {
                app = docker.build("ashcorr/crunchyrollapi")
                docker.withRegistry('https://registry.hub.docker.com', 'ashcorr-docker-hub-credentials') {
                            app.push("${env.BUILD_NUMBER}")
                            app.push("latest")
                        }
            }
        }

        stage('Deploy') {
            steps {
                withKubeConfig([credentialsId: 'ashcorr-kubeconf-credentials']) {
                    sh 'kubectl set image --namespace crunchyroll deployment/crunchyrollapi crunchyrollapi=ashcorr/crunchyrollapi:${BUILD_NUMBER}'
                }
            }
        }
    }
}