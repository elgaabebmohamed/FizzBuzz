    pipeline {
      agent any
      environment {
       SONARQUBE_URL = "http://localhost"
       SONARQUBE_PORT = "9000"
      }
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

          stage('Findbugs') {
           agent {
            docker {
             image 'maven:3.6.0-jdk-8-alpine'
             args '-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository'
             reuseNode true
            }
           }
           steps {
                script {
                   sh "mvn -B -V -U -e checkstyle:checkstyle pmd:pmd pmd:cpd"

                   def checkstyle = scanForIssues tool: checkStyle(pattern: '**/target/checkstyle-result.xml')
                   publishIssues issues: [checkstyle]

                   def pmd = scanForIssues tool: pmdParser(pattern: '**/target/pmd.xml')
                   publishIssues issues: [pmd]

                   def cpd = scanForIssues tool: cpd(pattern: '**/target/cpd.xml')
                   publishIssues issues: [cpd]


                   //def findbugs = scanForIssues tool: [$class: 'FindBugs'], pattern: '**/target/findbugsXml.xml'
                   //publishIssues issues:[findbugs]

                   def maven = scanForIssues tool: mavenConsole()
                   publishIssues issues: [maven]

                   def java = scanForIssues tool: [$class: 'Java']
                   publishIssues issues: [java]

                   def javadoc = scanForIssues tool: [$class: 'JavaDoc']
                   publishIssues issues: [javadoc]

                   publishIssues id: 'gatherJava', name: 'Java and JavaDoc',
                       issues: [java, javadoc],
                       filters: [includePackage('io.jenkins.plugins.analysis.*')]

                   publishIssues id: 'gatherAnalysis', name: 'All Issues',
                       issues: [checkstyle, pmd, cpd, maven]
               }
            //sh ' mvn findbugs:findbugs'
            // using findbugs plugin
            //findbugs pattern: '**/target/findbugsXml.xml'
           }
          }

          stage('SonarQube') {
            agent {
                docker {
                    image 'maven:3.6.0-jdk-8-alpine'
                    args "-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_datay:/root/.m2/repository"
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
           recordIssues aggregatingResults: true, sourceDirectory: '/var/jenkins_home/workspace/FizzBuzz_master/src/main/java', tools: [javaDoc(), checkStyle(pattern: '**/target/checkstyle-result.xml'), pmdParser(pattern: '**/target/pmd.xml')]
          }
         }
        }
      }
    }