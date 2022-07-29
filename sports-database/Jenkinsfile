pipeline {
    agent any

    environment{
        NEW_VERSION="just a test"
    }
    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }
    stages {
        stage('clone repo') {
            steps {
                // The below will clone your repo and will be checked out to master branch by default.
                git url: 'https://github.com/jpsoutost/first-steps-spring.git'
                }
        stage("build") {
            steps {
                stage("Docker build"){
                        sh 'docker build -t jpsoutost/athlete-database ./athletes-database-service'
                }
            }
        }
        stage("deploy") {
            steps {
                sh 'kubectl apply -f ./sports-database/k8s-config-files/zipkin.yaml'
                sh 'kubectl apply -f ./sports-database/k8s-config-files/athlete-data-service.yaml'
                sh 'minikube service zipkin-service'
                sh 'minikube service athlete-database-service'
            }
        }
    }
}
