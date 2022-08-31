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
                sh 'mvn clean install -f ./athletes-database-service'
		sh 'mvn clean install -f ./RESTful-Web-Service'
		sh 'mvn clean install -f ./sports-database'
                
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t jpsoutost/athlete-database:${BUILD_NUMBER} ./athletes-database-service'
		    sh 'docker build -t jpsoutost/user-database:${BUILD_NUMBER} ./RESTful-Web-Service'
		    sh 'docker build -t jpsoutost/sports-database:${BUILD_NUMBER} ./sports-database'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                   sh 'docker login -u jpsoutost -p ${dockerhubpwd}'

}
                   sh 'docker push jpsoutost/athlete-database:${BUILD_NUMBER}'
		   sh 'docker push jpsoutost/user-database:${BUILD_NUMBER}'
		   sh 'docker push jpsoutost/sports-database:${BUILD_NUMBER}'
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
	stage('Deploy to k8s - sports-data'){
            steps{
                script{
		    kubernetesDeploy configs: 'sports-data-web-service.yaml', kubeConfig: [path: ''], kubeconfigId: 'k8sconfigpwd', secretName: '', ssh: [sshCredentialsId: '*', sshServer: ''], textCredentials: [certificateAuthorityData: '', clientCertificateData: '', clientKeyData: '', serverUrl: 'https://']
                }
            }
        }
    }
}