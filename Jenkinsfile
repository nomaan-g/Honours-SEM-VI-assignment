pipeline {
    agent any

    environment {
        IMAGE = 'amanpatne/flightbooking:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/nomaan-g/Honours-SEM-VI-assignment'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo """
                    docker run --rm -v ${pwd()}:/app -w /app ${IMAGE} mvn clean package -DskipTests
                    """
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo """
                    docker run --rm -v ${pwd()}:/app -w /app ${IMAGE} mvn test
                    """
                }
            }
        }
    }
}