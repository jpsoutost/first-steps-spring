pipeline {
    agent any
    tools{
        maven 'maven'
	dockerTool 'docker'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/jpsoutost/first-steps-spring.git']]])
                sh 'mvn -f ./athletes-database-service clean install'
		sh 'mvn -f ./RESTful-Web-Service clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t jpsoutost/athlete-database ./athletes-database-service'
		    sh 'docker build -t jpsoutost/user-database ./RESTful-Web-Service'
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
		   sh 'docker push jpsoutost/user-database'
                }
            }
        }
        stage('Deploy to k8s - athletes-data'){
            steps{
                script{
                    kubernetesDeploy configs: 'athlete-data-service.yaml', kubeConfig: [path: ''], kubeconfigId: 'k8sconfigpwd', secretName: '', ssh: [sshCredentialsId: '*', sshServer: ''], textCredentials: [certificateAuthorityData: '', clientCertificateData: '', clientKeyData: '', serverUrl: 'https://']
                }
            }
        }
	stage('Deploy to k8s - user-data'){
            steps{
                script{
		    kubernetesDeploy configs: 'user-web-service.yaml', kubeConfig: [path: ''], kubeconfigId: 'k8sconfigpwd', secretName: '', ssh: [sshCredentialsId: '*', sshServer: ''], textCredentials: [certificateAuthorityData: '', clientCertificateData: '', clientKeyData: '', serverUrl: 'https://']
                }
            }
        }
    }
}