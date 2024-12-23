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

[![Build, Deploy, and Check Application Health](https://github.com/vikas0105/petclinic/actions/workflows/Build-&-Deploy.yml/badge.svg?event=status)](https://github.com/vikas0105/petclinic/actions/workflows/Build-&-Deploy.yml)

name: Build, Deploy, and Check Application Health

on:
  push:
    branches:
      - '**'  # Triggers on push to any branch, including feature branches.
  pull_request:
    branches:
      - master  # Triggers on PRs to the master branch from feature branches.

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout the repository
      uses: actions/checkout@v3
      with:
        fetch-depth: 0  # Fetch the full history for proper branch handling

    - name: Set up Java 17
      uses: actions/setup-java@v3
      with:
        java-version: 17
        distribution: temurin
        cache: maven

    - name: Cache Maven dependencies
      uses: actions/cache@v3
      with:
        path: ~/.m2/repository
        key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
        restore-keys: |
          ${{ runner.os }}-maven-

    - name: Build with Maven
      run: mvn clean install -DskipTests=true

    - name: Run Spring Boot application
      run: mvn spring-boot:run &
      # Run Spring Boot in the background

    - name: Wait for 1 minute (simulate a short-running process)
      run: sleep 60  # Sleep for 1 minute

    - name: Check application health
      run: |
        echo "Checking application health..."
        curl -s --head http://localhost:8080/actuator/health | head -n 10
      # Check if the health endpoint is up and responding

    - name: Stop Spring Boot application
      run: |
        echo "Stopping Spring Boot application..."
        mvn spring-boot:stop


===============================================================================================================================================================

pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'JDK 17', type: 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Set up Java 17') {
            steps {
                script {
                    // Ensuring that Java 17 is set up
                    sh 'java -version'
                }
            }
        }

        stage('Cache Maven dependencies') {
            steps {
                cache(path: '.m2/repository', key: "maven-${env.BUILD_ID}") {
                    sh 'mvn clean install -DskipTests=true'
                }
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install -DskipTests=true'
            }
        }

        stage('Run Spring Boot Application') {
            steps {
                // Run the Spring Boot application in the background
                script {
                    sh 'mvn spring-boot:run &'
                }
            }
        }

        stage('Wait for 1 minute') {
            steps {
                // Sleep for 1 minute to allow the app to start and run
                sleep(time: 1, unit: 'MINUTES')
            }
        }

        stage('Check application health') {
            steps {
                script {
                    def healthCheck = sh(script: 'curl -s --head http://localhost:8080/actuator/health | head -n 10', returnStdout: true).trim()
                    echo "Health check response: ${healthCheck}"
                }
            }
        }

        stage('Stop Spring Boot Application') {
            steps {
                // Stop the Spring Boot application gracefully
                script {
                    sh 'mvn spring-boot:stop'
                }
            }
        }
    }

    post {
        always {
            // Clean up any running processes if needed
            echo 'Cleaning up.'
        }
    }
}

============================================================================================================================================================================

