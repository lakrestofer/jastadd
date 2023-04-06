#!/usr/bin/env groovy

// This Jenkinsfile builds ExtendJ with Gradle.
pipeline {
  agent any
  environment {
    JAVA_HOME = "${tool 'jdk-8'}"
    ANT_HOME = "${tool 'ant-1.10.5'}"
    PATH = "${env.JAVA_HOME}/bin:${env.ANT_HOME}/bin:${env.PATH}"
  }

  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  tools {
    // Use JDK8 to avoid TLS protocol version problem for Maven.
    // https://stackoverflow.com/questions/51090914/received-fatal-alert-protocol-version-build-failure-gradle-maven
    jdk 'jdk-8'
  }

  triggers {
    pollSCM('H/15 * * * *')
    cron('H H(1-7) * * *')
  }

  stages {
    stage('Checkout') {
      steps {
        // https://stackoverflow.com/questions/42290133/jenkins-pipeline-git-command-submodule-update
        sh 'git submodule update --init --recursive'
      }
    }

    stage('Build') {
      steps {
        sh './gradlew clean jar'
      }
    }

    stage('Test') {
      steps {
        dir('test') {
          sh 'ant test -Djastadd.jar="../jastadd2.jar" -Dthreads=1 -Dtimeout=20000'
          junit 'reports/**/*.xml'
        }
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'jastadd2.jar', fingerprint: true
    }
  }
}
