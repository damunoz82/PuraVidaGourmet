pipeline {
  agent any
  
  environment {
    registry = "karinasoft/social-networking"
    registryCredential = 'dockerhub'
    dockerImage = ''
  }
  
  stages {
    stage('Cloning Git') {
      steps {
        git url: 'https://github.com/damunoz82/practimep.git'
      }
    }
    
    stage('Clean') {
      steps {
        sh 'mvn clean'
      }
    }
    
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    
    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    
    stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
    
    stage('Remove Unused docker image') {
      steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }
  }
}