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
        stage("init") {
            steps {
                echo "building application version:${params.VERSION}"
            }
        }
        stage("build") {
            steps {
                echo "building application version:${params.VERSION}"
                }
            }
        }
        stage("test") {
            when {
                expression {
                    params.executeTests
                }
            }
            steps {
                echo "testing application version:${params.VERSION}"
            }
        }
        stage("deploy") {
            steps {
                echo "Deploying application version:${NEW_VERSION}"
            }
        }
    }
}
