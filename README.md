# pet-clinic

#!/bin/bash

# Update the package list
echo "Updating package list..."
sudo apt update

# Install OpenJDK 11 (or another version if needed)
echo "Installing OpenJDK 11..."
sudo apt install -y openjdk-11-jdk

# Verify Java installation
echo "Verifying Java installation..."
java -version

# Install Maven
echo "Installing Maven..."
sudo apt install -y maven

# Verify Maven installation
echo "Verifying Maven installation..."
mvn -version

# Set JAVA_HOME environment variable
echo "Setting up JAVA_HOME..."
JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
echo "JAVA_HOME=${JAVA_HOME}" | sudo tee -a /etc/environment

# Set MAVEN_HOME environment variable
echo "Setting up MAVEN_HOME..."
MAVEN_HOME=/usr/share/maven
echo "MAVEN_HOME=${MAVEN_HOME}" | sudo tee -a /etc/environment

# Reload environment variables
source /etc/environment

# Confirm environment variables
echo "Verifying environment variables..."
echo "JAVA_HOME=${JAVA_HOME}"
echo "MAVEN_HOME=${MAVEN_HOME}"

# Clean up unnecessary packages
echo "Cleaning up unnecessary packages..."
sudo apt autoremove -y

echo "Java and Maven installation completed successfully!"
=============================================================================================================================================================================
http://ec2-ip:8080/petclinic/pets
=============================================================================================================================================================================
GH:
name: Build and Start Spring Boot App

on:
  push:
    branches:
      - main  # Trigger the pipeline on push to the main branch
  pull_request:
    branches:
      - main  # Trigger the pipeline on PRs to the main branch

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
      # Step 1: Checkout the code
      - name: Checkout repository
        uses: actions/checkout@v2

      # Step 2: Set up Java JDK 17 (adjust if you're using a different version)
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'  # Update to match the Java version used in your project
          distribution: 'adoptopenjdk'

      # Step 3: Cache Maven dependencies
      - name: Cache Maven dependencies
        uses: actions/cache@v2
        with:
          path: ~/.m2/repository
          key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{ runner.os }}-maven-

      # Step 4: Build the Spring Boot application using Maven
      - name: Build with Maven
        run: mvn clean install -DskipTests=true

      # Step 5: Start the Spring Boot application (in background)
      - name: Start Spring Boot Application
        run: |
          nohup java -jar target/petclinic.jar &  # Start Spring Boot app in the background
          
      # Step 6: Check if the app is running (optional, just to confirm)
      - name: Check if Spring Boot app is running
        run: |
          curl --silent --fail http://localhost:8080 || exit 1  # Check if the app is running on localhost

=========================================================================================================================================================================
pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'JDK 17', type: 'JDK'  // Set the path for JDK 17
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                checkout scm
            }
        }

        stage('Set up Maven') {
            steps {
                // Install Maven (assuming it's not already installed)
                script {
                    if (!fileExists('/usr/local/bin/mvn')) {
                        sh 'sudo apt-get install maven -y'
                    }
                }
            }
        }

        stage('Build Application') {
            steps {
                // Build the Spring Boot application without running tests
                sh 'mvn clean install -DskipTests=true'
            }
        }

        stage('Start Application') {
            steps {
                // Start the Spring Boot application in the background
                script {
                    sh 'nohup java -jar target/petclinic.jar &'
                }
            }
        }

        stage('Verify Application') {
            steps {
                // Verify if the application is running
                script {
                    def response = sh(script: 'curl --silent --fail http://localhost:8080', returnStatus: true)
                    if (response != 0) {
                        error 'Application is not running!'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build and start process completed successfully.'
        }
        failure {
            echo 'Build or start process failed.'
        }
    }
}
============================================================================================================================================================================

