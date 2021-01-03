    pipeline {
      agent any
      environment {
       SONARQUBE_URL = "http://192.168.0.24"
       SONARQUBE_PORT = "9002"
       NEXUS_VERSION = "nexus3"
       NEXUS_PROTOCOL = "http"
       NEXUS_URL = "192.168.0.24:8084"
       NEXUS_REPOSITORY = "maven-snapshots"
       NEXUS_CREDENTIAL_ID = "nexus-credentials"
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
                    args "-v /share/CACHEDEV1_DATA/Container/container-station-data/lib/docker/volumes/783e694a6bccfac21f65586fe4e751c58d9bd9773a9ae660010dd3dac362419b/_data:/root/.m2/repository"
                    reuseNode true
                }
            }
            steps {
                sh " mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar -X -Dsonar.host.url=$SONARQUBE_URL:$SONARQUBE_PORT"
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

          stage('Deploy Artifact To Nexus') {
           when {
            branch 'master'
           }
           steps {
            script {
             unstash 'pom'
             unstash 'artifact'
             // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
             pom = readMavenPom file: "pom.xml";
             // Find built artifact under target folder
             filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
             // Print some info from the artifact found
             echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
             // Extract the path from the File found
             artifactPath = filesByGlob[0].path;
             // Assign to a boolean response verifying If the artifact name exists
             artifactExists = fileExists artifactPath;
             if (artifactExists) {
              nexusArtifactUploader(
               nexusVersion: NEXUS_VERSION,
               protocol: NEXUS_PROTOCOL,
               nexusUrl: NEXUS_URL,
               groupId: pom.groupId,
               version: pom.version,
               repository: NEXUS_REPOSITORY,
               credentialsId: NEXUS_CREDENTIAL_ID,
               artifacts: [
                // Artifact generated such as .jar, .ear and .war files.
                [artifactId: pom.artifactId,
                 classifier: '',
                 file: artifactPath,
                 type: pom.packaging
                ],
                // Lets upload the pom.xml file for additional information for Transitive dependencies
                [artifactId: pom.artifactId,
                 classifier: '',
                 file: "pom.xml",
                 type: "pom"
                ]
               ]
              )
             } else {
              error "*** File: ${artifactPath}, could not be found";
             }
            }
           }
           }

      }
    }