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

name: Build, Deploy, and Upload Artifacts

on:
  push:
    branches:
      - '**'
  pull_request:
    branches:
      - master
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout the repository
      uses: actions/checkout@v3
      with:
        fetch-depth: 0

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

    - name: Upload Build Artifacts
      uses: actions/upload-artifact@v4
      with:
        name: petclinic-artifacts
        path: target/

    - name: Run Spring Boot application
      run: mvn spring-boot:run &
    
    - name: Wait for 1 minute
      run: sleep 60

    - name: Check application health
      run: |
        echo "Checking application health..."
        curl -s --head http://localhost:8080/actuator/health | head -n 10

    - name: Stop Spring Boot application
      run: mvn spring-boot:stop

===============================================================================================================================================================

pipeline {
    agent any

    options {
        skipDefaultCheckout() // Skip the default checkout to control repository fetching
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out repository...'
                checkout scm
            }
        }

        stage('Set up Environment') {
            steps {
                echo 'Setting up Java environment...'
                sh '''
                    export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
                    echo "JAVA_HOME=$JAVA_HOME"
                '''
            }
        }

        stage('Build') {
            steps {
                echo 'Building the application with Maven...'
                sh 'mvn clean install -DskipTests=true'
            }
        }

        stage('Run Spring Boot Application') {
            steps {
                echo 'Running Spring Boot application...'
                sh 'mvn spring-boot:run &'
                sleep(time: 1, unit: 'MINUTES') // Wait for 1 minute
            }
        }

        stage('Check Application Health') {
            steps {
                echo 'Checking application health...'
                sh '''
                    echo "Fetching health endpoint..."
                    curl -s --head http://localhost:8080/actuator/health | head -n 10
                '''
            }
        }

        stage('Stop Spring Boot Application') {
            steps {
                echo 'Stopping Spring Boot application...'
                sh 'mvn spring-boot:stop'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo 'Archiving build artifacts...'
                archiveArtifacts artifacts: 'target/**/*', fingerprint: true
            }
        }

        stage('Manual Approval') {
            steps {
                input message: 'Do you want to proceed?', ok: 'Yes, proceed!'
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}

============================================================================================================================================================================

