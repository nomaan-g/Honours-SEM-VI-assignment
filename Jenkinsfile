pipeline {
    agent any

    environment {
        MAVEN_OPTS = "-Dmaven.repo.local=.m2/repository"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AmanPatne/sem6_honors_fightbooking.git'
            }
        }

        stage('Build') {
            steps {
                dir('Flightbooking') {
                    sh './mvnw clean package -DskipTests || mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir('Flightbooking') {
                    sh './mvnw test || mvn test'
                }
            }
        }
    }
}
