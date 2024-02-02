/* Requires the Docker Pipeline plugin */
pipeline {
    agent any
    /*agent { docker { image 'maven:3.9.6-eclipse-temurin-17-alpine' } }*/
    stages {
        stage('build') {
            steps {
                bat 'java --version'
				bat 'chcp 65001'
				bat 'dir'
				bat 'del README.txt'
				bat 'cd block_1_homework_1'
				bat 'dir'
            }
        }
    }
}