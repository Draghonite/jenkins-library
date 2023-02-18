def call(Map params) {
    // ARTIFACTORY_CREDS_ID, ARTIFACTORY_SERVER, PACKAGE_PATH, PACKAGE_NAME, BUILD_ENV, BUILD_PREFIX, BUILD_NUMBER, REPO="libs-release-local"
    withCredentials([usernameColonPassword(credentialsId: $params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh '''
            apk add curl
            curl -i -u "$params.ARTIFACTORY_USER_PASS" \
                -T $params.PACKAGE_PATH \
                "$params.ARTIFACTORY_SERVER/$params.REPO/$params.PACKAGE_NAME-$params.BUILD_ENV/$params.PACKAGE_NAME-$params.BUILD_ENV-$params.BUILD_PREFIX.$params.BUILD_NUMBER.zip"
        '''
    }
*/
}
