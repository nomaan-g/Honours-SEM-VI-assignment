pipeline {
    agent any

    stage('Checkout') {
    steps {
        git branch: 'main', url: 'https://github.com/nomaan-g/Honours-SEM-VI-assignment'
    }
}


        stage('Build') {
            steps {
                dir('Flightbooking') {
                    sh '/usr/local/maven/bin/mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                dir('Flightbooking') {
                    sh '/usr/local/maven/bin/mvn test'
                }
            }
        }

        stage('Deploy and Run on EC2') {
            steps {
                withCredentials([file(credentialsId: 'flightbooking-key', variable: 'PEMFILE')]) {
                    sh '''
                        echo "Copying JAR to EC2..."
                        scp -o StrictHostKeyChecking=no -i $PEMFILE Flightbooking/target/Flightbooking-0.0.1-SNAPSHOT.jar ec2-user@16.171.21.116:~/

                        echo "Deploying JAR on EC2..."
                        ssh -o StrictHostKeyChecking=no -i $PEMFILE ec2-user@16.171.21.116 '
                            echo "Killing existing app if running..."
                            pkill -f Flightbooking-0.0.1-SNAPSHOT.jar || true
                            echo "Starting new app..."
                            nohup java -jar Flightbooking-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
                            echo "Deployment complete."
                        '
                    '''
                }
            }
        }
    }
}
