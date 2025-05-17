pipeline {
    agent any

    environment {
        JAR_NAME = 'Flightbooking-0.0.1-SNAPSHOT.jar'
        PEM_PATH = '/Users/ayeshagagan/Downloads/sem6_honors_fightbooking-main/Flightbooking/flightbooking_key.pem'
        EC2_USER = 'ec2-user'
        EC2_HOST = '16.171.21.116'
        MVN_PATH = '/usr/local/maven/bin/mvn'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/nomaan-g/Honours-SEM-VI-assignment'
            }
        }

        stage('Build') {
            steps {
                sh '''
                cd Flightbooking
                ${MVN_PATH} clean package -DskipTests
                '''
            }
        }

        stage('Test') {
            steps {
                sh '''
                cd Flightbooking
                ${MVN_PATH} test
                '''
            }
        }

        stage('Deploy and Run on EC2') {
    steps {
        script {
            def deployScript = """
                scp -o StrictHostKeyChecking=no -i /Users/ayeshagagan/Downloads/sem6_honors_fightbooking-main/Flightbooking/flightbooking_key.pem Flightbooking/target/Flightbooking-0.0.1-SNAPSHOT.jar ec2-user@16.171.21.116:~/

                ssh -o StrictHostKeyChecking=no -i /Users/ayeshagagan/Downloads/sem6_honors_fightbooking-main/Flightbooking/flightbooking_key.pem ec2-user@16.171.21.116 '
                    echo "Killing existing application if running..."
                    pkill -f Flightbooking-0.0.1-SNAPSHOT.jar || true
                    echo "Starting application..."
                    nohup java -jar Flightbooking-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
                    echo "Deployment successful."
                '
            """
            sh deployScript
        }
    }
}


    }
}
