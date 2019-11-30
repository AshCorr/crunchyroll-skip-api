pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package -Dproduction'
      }
    }

    stage('Build Image') {
      steps {
        script {
          app = docker.build("ashcorr/crunchyrollapi")
          docker.withRegistry('https://registry.hub.docker.com', 'ashcorr-docker-hub-credentials') {
            app.push("${env.BUILD_NUMBER}")
            app.push("latest")
          }
        }

      }
    }

  }
}