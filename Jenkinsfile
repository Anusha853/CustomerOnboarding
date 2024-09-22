pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven3' // Replace with the actual name of your Maven installation
        JAVA_HOME = tool 'JDK 17' // Replace with the actual name of your JDK installation
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/AdityaPawar1005/Customer_Onboarding.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh "${MAVEN_HOME}/bin/mvn clean install"
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh "${MAVEN_HOME}/bin/mvn test"
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh "${MAVEN_HOME}/bin/mvn package"
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the project...'
                // Customize deployment steps here
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Pipeline succeeded.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
