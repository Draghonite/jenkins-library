def call(Map params) {
    withSonarQubeEnv(params.SONAR_SERVER_ID) {
        sh "cd ${params.SONAR_PROJECT_BASE_PATH} && sonar-scanner -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN -Dsonar.projectKey=${params.SONAR_PROJECT_KEY} -Dsonar.exclusions=${params.SONAR_EXCLUSIONS}"
    }
}