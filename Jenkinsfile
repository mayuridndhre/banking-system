pipeline {
    agent any

    tools {
        jdk 'JDK-11'       // Jenkins मध्ये configure केलेले JDK नाव
    }

    environment {
        TOMCAT_HOME = "C:\\apache-tomcat-9.0.100"
        DEPLOY_PATH = "${TOMCAT_HOME}\\webapps\\BankingSystem"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/mayuri-dandhare/BankingSystem.git',
                    credentialsId: 'github-creds'  // तुझ्या credentials ID ने replace कर
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling JSP/Servlet project...'
                // जर Maven वापरला असेल तर इथे "mvn clean install" चालवू
                // JSP project असल्याने build step साधा ठेवला आहे
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                bat """
                if not exist "%DEPLOY_PATH%" mkdir "%DEPLOY_PATH%"
                xcopy /E /Y "WebContent\\*" "%DEPLOY_PATH%\\"
                """
            }
        }
    }

    post {
        success {
            echo '✅ Deployment successful!'
        }
        failure {
            echo '❌ Build/Deploy failed!'
        }
    }
}
