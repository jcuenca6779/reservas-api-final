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
                // Compila y genera el reporte de cobertura JaCoCo usando Maven Wrapper
                sh './mvnw clean verify'
            }
        }

        stage('Análisis SonarQube') {
            steps {
                withSonarQubeEnv('SonarServer') {
                    // Envía el reporte a Sonar usando Maven Wrapper
                    sh "./mvnw sonar:sonar -Dsonar.projectKey=reservas-api"
                }
            }
        }
    }
}