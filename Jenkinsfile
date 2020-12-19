pipeline {
  agent any
  stages {
    stage('SCM') {
     steps {
      checkout scm
     }
    }
    stage('Compile') {
     agent {
      docker {
       image 'maven:3.6.0-jdk-8-alpine'
       args '-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_datay:/root/.m2/repository'
       // to use the same node and workdir defined on top-level pipeline for all docker agents
       reuseNode true
      }
     }
     steps {
      sh ' mvn clean compile'
     }
    }
  }
}