pipeline {
    agent any

    environment {
        JAR_NAME = 'Flightbooking-0.0.1-SNAPSHOT.jar'
        PEM_PATH = '/Users/ayeshagagan/Downloads/sem6_honors_fightbooking-main/Flightbooking/flightbooking_key.pem'
        EC2_USER = 'ec2-user'
        EC2_HOST = '16.171.21.116'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/nomaan-g/Honours-SEM-VI-assignment'
            }
        }

        stage('Build') {
            steps {
                sh 'cd Flightbooking && mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'cd Flightbooking && mvn test'
            }
        }

        stage('Deploy and Run on EC2') {
            steps {
                sh """
                scp -o StrictHostKeyChecking=no -i "${PEM_PATH}" Flightbooking/target/${JAR_NAME} ${EC2_USER}@${EC2_HOST}:~/

                ssh -o StrictHostKeyChecking=no -i "${PEM_PATH}" ${EC2_USER}@${EC2_HOST} '
                    pkill -f ${JAR_NAME} || true
                    nohup java -jar ${JAR_NAME} > app.log 2>&1 &
                '
                """
            }
        }
    }
}
