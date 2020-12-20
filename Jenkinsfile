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

    stage('Basic Quality Report') {

        sh 'mvn site'

        def java = scanForIssues tool: java()
        def javadoc = scanForIssues tool: javaDoc()

        publishIssues id: 'analysis-java', name: 'Java Issues', issues: [java, javadoc]  //, filters: [includePackage('io.jenkins.plugins.analysis.*')]

        def checkstyle = scanForIssues tool: checkStyle(pattern: '**/target/checkstyle-result.xml')
        publishIssues issues: [checkstyle]

        def pmd = scanForIssues tool: pmdParser(pattern: '**/target/pmd.xml')
        publishIssues issues: [pmd]

        def cpd = scanForIssues tool: cpd(pattern: '**/target/cpd.xml')
        publishIssues issues: [cpd]

        def spotbugs = scanForIssues tool: spotBugs(pattern: '**/target/findbugsXml.xml')
        publishIssues issues: [spotbugs]

        def maven = scanForIssues tool: mavenConsole()
        publishIssues issues: [maven]

        publishIssues id: 'analysis-all', name: 'All Issues',
                issues: [checkstyle, pmd, spotbugs] //, filters: [includePackage('io.jenkins.plugins.analysis.*')]
    }
  }
}