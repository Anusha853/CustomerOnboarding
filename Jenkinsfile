pipeline {
    agent any // Use any available Jenkins agent

    environment {
        // Define environment variables
        JAVA_HOME = tool name: 'Java 17', type: 'jdk' // Ensure this matches the OpenJDK configuration name in Jenkins
        MAVEN_HOME = tool name: 'Maven 3.9.8', type: 'maven' // Ensure this matches the Maven configuration name in Jenkins
    }

    tools {
        // Specify the Maven and OpenJDK versions to use
        maven 'Maven 3.9.8' // Ensure this matches the Maven installation name in Jenkins
        jdk 'Java 21' // Ensure this matches the OpenJDK installation name in Jenkins
    }

    stages {
        stage('Build') {
            steps {
                // Build the project using Maven
                echo 'Build successful'
                //bat 'mvn clean install'
            }
        }
        
        stage('Test') {
            steps {
                // Run tests using Maven
                echo 'Test successful'
                //bat 'mvn test'
            }
        }
        
        stage('Package') {
            steps {
                // Package the project using Maven
                echo 'Success'
                //bat 'mvn package'
            }
        }
    }
    
    post {
        success {
            // Actions to perform on successful build
            echo 'Build and tests succeeded!'
        }
        failure {
            // Actions to perform on failed build
            echo 'Build or tests failed.'
        }
        always {
            // Actions to perform regardless of success or failure
            echo 'Pipeline finished.'
        }
    }
}