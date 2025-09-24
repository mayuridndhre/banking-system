pipeline {
    agent any

    tools {
        jdk 'JDK-21'       // Jenkins मध्ये configure केलेले JDK 21 tool name
    }

    environment {
        TOMCAT_HOME = 'C:\\apache-tomcat-9.0.100' // तुमचा Tomcat folder path
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/mayuridndhre/banking-system.git',
                    credentialsId: 'github-creds'  // Jenkins मध्ये add केलेला GitHub credential
            }
        }

        stage('Build') {
            steps {
                echo 'No build required for JSP project, just copying files...'
            }
        }

   stage('Deploy to Tomcat') {
    steps {
        script {
            bat """
            if exist "%TOMCAT_HOME%\\webapps\\banking-system" rmdir /S /Q "%TOMCAT_HOME%\\webapps\\banking-system"
            mkdir "%TOMCAT_HOME%\\webapps\\banking-system"
            xcopy /E /Y "%WORKSPACE%\\src\\main\\webapp\\*" "%TOMCAT_HOME%\\webapps\\banking-system\\"
            """
        }
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
