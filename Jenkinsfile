pipeline {
    agent any
    
    tools {
        maven 'M3' 
    }

    stages {
        stage('Descargar Código') {
            steps {
                git branch: 'main', url: 'https://github.com/jcuenca6779/reservas-api-final.git'
            }
        }

        stage('Compilar y Pruebas') {
            steps {
                // Dar permisos de ejecución al wrapper de Maven (necesario en agentes Linux)
                sh 'chmod +x mvnw'
                // Compila y genera el reporte de cobertura JaCoCo usando Maven Wrapper
                sh './mvnw clean verify'
            }
        }

        stage('Análisis SonarQube') {
            steps {
                withSonarQubeEnv('SonarServer') {
                    // Asegura permisos de ejecución y envía el reporte a Sonar usando Maven Wrapper
                    sh 'chmod +x mvnw'
                    sh "./mvnw sonar:sonar -Dsonar.projectKey=reservas-api"
                }
            }
        }
    }
}