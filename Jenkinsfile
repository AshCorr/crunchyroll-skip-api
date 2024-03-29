pipeline {
  agent none
  stages {
    stage('Build') {
      agent {
        docker {
          image 'maven:3-alpine'
          args '-v /root/.m2:/root/.m2'
        }

      }
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

    stage('Deploy') {
      agent {
        docker {
          image 'k3integrations/kubectl'
        }

      }
      steps {
        withCredentials([file(credentialsId: 'ashcorr-kubeconf-credentials', variable: 'KUBECONFIG')]) {
          sh 'kubectl set image --namespace crunchyroll deployment/crunchyroll-skip-api crunchyroll-skip-api=ashcorr/crunchyrollapi:${BUILD_NUMBER}'
        }
      }
    }

  }
}