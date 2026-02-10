pipeline {
    agent any

    stages {
        stage('Clonar repositorio') {
            steps {
                checkout scm
            }
        }

        stage('Compilar proyecto') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Ejecutar pruebas') {
            steps {
                bat 'mvn test'
            }
        }
    }

    post {
        success {
            echo 'Pipeline ejecutado correctamente'
        }
        failure {
            echo 'Pipeline falló'
        }
    }
}
