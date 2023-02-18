def call(Map params) {
    withCredentials([usernameColonPassword(credentialsId: $params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh '''
            apk add curl
            curl -i -u "$params.ARTIFACTORY_USER_PASS" \
                -T $params.PACKAGE_PATH \
                "$params.ARTIFACTORY_SERVER/$params.REPO/$params.PACKAGE_NAME-$params.BUILD_ENV/$params.PACKAGE_NAME-$params.BUILD_ENV-$params.BUILD_PREFIX.$params.BUILD_NUMBER.zip"
        '''
    }
}
