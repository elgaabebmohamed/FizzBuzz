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
         args '-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository'
         // to use the same node and workdir defined on top-level pipeline for all docker agents
         reuseNode true
        }
       }
       steps {
        sh ' mvn clean compile'
       }
      }

    stage('Unit Tests') {
     //when {
     // branch 'develop'
     //}
     agent {
      docker {
       image 'maven:3.6.0-jdk-8-alpine'
       args '-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository'
       reuseNode true
      }
     }
     steps {
      sh 'mvn test'
     }
     post {
      always {
       junit 'target/surefire-reports/**/*.xml'
      }
     }
    }

      stage('Code Quality Analysis') {
       parallel {
        stage('PMD') {
         agent {
          docker {
           image 'maven:3.6.0-jdk-8-alpine'
           args '-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository'
           reuseNode true
          }
         }
         steps {
          sh ' mvn pmd:pmd'
          // using pmd plugin
          step([$class: 'PmdPublisher', pattern: '**/target/pmd.xml'])
         }
        }
        stage('Findbugs') {
         agent {
          docker {
           image 'maven:3.6.0-jdk-8-alpine'
           args '-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository'
           reuseNode true
          }
         }
         steps {
          sh ' mvn findbugs:findbugs'
          // using findbugs plugin
          findbugs pattern: '**/target/findbugsXml.xml'
         }
        }
        stage('JavaDoc') {
         agent {
          docker {
           image 'maven:3.6.0-jdk-8-alpine'
           args '-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository'
           reuseNode true
          }
         }
         steps {
          sh ' mvn javadoc:javadoc'
          step([$class: 'JavadocArchiver', javadocDir: './target/site/apidocs', keepAll: 'true'])
         }
        }
        stage('SonarQube') {
         agent {
          docker {
           image 'maven:3.6.0-jdk-8-alpine'
           args "-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository"
           reuseNode true
          }
         }
         steps {
           sh " mvn sonar:sonar -Dsonar.host.url=$SONARQUBE_URL:$SONARQUBE_PORT"
         }
        }
       }
       post {
        always {
         // using warning next gen plugin
         recordIssues aggregatingResults: true, tools: [javaDoc(), checkStyle(pattern: '**/target/checkstyle-result.xml'), findBugs(pattern: '**/target/findbugsXml.xml', useRankAsPriority: true), pmdParser(pattern: '**/target/pmd.xml')]
        }
       }
      }
  }
}