pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/jpsoutost/first-steps-spring.git']]])
                sh 'mvn -version'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t jpsoutost/athlete-database ./athletes-database-service'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                   sh 'docker login -u jpsoutost -p ${dockerhubpwd}'

}
                   sh 'docker push jpsoutost/athlete-database'
                }
            }
        }
        stage('Deploy to k8s'){
            steps{
                script{
                    kubernetesDeploy configs: './sports-database/k8s-config-files/athlete-data-service.yaml', kubeConfig: [path: ''], kubeconfigId: 'k8sconfigpwd', secretName: '', ssh: [sshCredentialsId: '*', sshServer: ''], textCredentials: [certificateAuthorityData: '', clientCertificateData: '', clientKeyData: '', serverUrl: 'https://']
                }
            }
        }
    }
}