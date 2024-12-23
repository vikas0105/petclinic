install on ec2
### Install Java 17 & Maven on EC2 (Ubuntu)
Run the following commands to install Java 17 and Maven on your EC2 instance:

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

===============================================================================================================
mvn clean install : it will build the app
mvn spring-boot:run : this would run the jar file

public ip with port 8080
===============================================================================================================

name: Build and Test Spring Boot Application

on:
  push:
    branches:
      - main  # or any branch you want to trigger the workflow on
  pull_request:
    branches:
      - main  # or any branch you want to trigger the workflow on

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    # Step 1: Checkout code
    - name: Checkout code
      uses: actions/checkout@v3

    # Step 2: Set up Java JDK (use the version compatible with your project)
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'  # Use the appropriate Java version for your project
        distribution: 'adoptopenjdk'

    # Step 3: Cache Maven dependencies to speed up builds
    - name: Cache Maven dependencies
      uses: actions/cache@v3
      with:
        path: ~/.m2/repository
        key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
        restore-keys: |
          ${{ runner.os }}-maven-

    # Step 4: Install Maven dependencies and run tests
    - name: Build with Maven
      run: mvn clean install

    # Step 5: Run tests
    - name: Run tests
      run: mvn test

    # Step 6: Run the Spring Boot Application (optional)
    - name: Run Spring Boot Application
      run: mvn spring-boot:run
      env:
        SPRING_PROFILES_ACTIVE: github-actions

===============================================================================================================================================================

pipeline {
    agent any

    environment {
        // Define environment variables (optional)
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk'
        MAVEN_HOME = '/usr/share/maven'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Checkout the repository
                checkout scm
            }
        }

        stage('Set up JDK') {
            steps {
                // Install Java (JDK 17 in this case)
                script {
                    sh 'sudo apt update'
                    sh 'sudo apt install openjdk-17-jdk -y'
                }
            }
        }

        stage('Cache Maven Dependencies') {
            steps {
                // Cache Maven dependencies (to speed up builds)
                script {
                    // You can use the Jenkins cache mechanism here if needed, for example, storing dependencies in a directory
                    // Example: sh 'mvn dependency:go-offline'
                }
            }
        }

        stage('Build with Maven') {
            steps {
                // Run Maven to clean and install the project
                script {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests using Maven
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy (optional)') {
            steps {
                // Optional deployment stage (depends on your needs)
                script {
                    // For example, deploying to a remote server or cloud platform
                    // sh 'mvn deploy'
                    echo 'Deployment step can be added here'
                }
            }
        }

        stage('Run Spring Boot Application') {
            steps {
                // Run the Spring Boot application (optional)
                script {
                    // Running Spring Boot with Maven
                    sh 'mvn spring-boot:run'
                }
            }
        }
    }

    post {
        always {
            // Clean up after the build (e.g., remove temp files)
            echo 'Cleaning up after build'
        }
        success {
            // Actions to take if the build is successful (e.g., notify, deploy, etc.)
            echo 'Build successful'
        }
        failure {
            // Actions to take if the build fails (e.g., send failure notification)
            echo 'Build failed'
        }
    }
}
============================================================================================================================================================================

