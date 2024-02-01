/* Requires the Docker Pipeline plugin */
pipeline {
    agent {
        docker { image 'node:20.11.0-alpine3.19' }
    }
    /*agent any*/
    /*agent { docker { image 'maven:3.9.6-eclipse-temurin-17-alpine' } }*/
    stages {
        stage('build') {
            steps {
                bat 'java --version'
            }
        }
    }
}