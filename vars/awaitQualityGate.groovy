def call(Map params) {
    sleep(60)
    timeout(time: 1, unit: 'HOURS') {
            withCredentials([usernameColonPassword(credentialsId: params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {

        waitForQualityGate abortPipeline: true
    }
}