def call(String ARTIFACTORY_CREDS_ID, ARTIFACTORY_SERVER, String PACKAGE_PATH, String REPO="libs-release-local",
         String PACKAGE_NAME, String BUILD_ENV, String BUILD_PREFIX, String BUILD_NUMBER) {
    withCredentials([usernameColonPassword(credentialsId: $ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh '''
            curl -i -u "$ARTIFACTORY_USER_PASS" \
                -T $PACKAGE_PATH \
                "$ARTIFACTORY_SERVER/$REPO/$PACKAGE_NAME-$BUILD_ENV/$PACKAGE_NAME-$BUILD_ENV-$BUILD_PREFIX.$BUILD_NUMBER.zip"
        '''
    }
}
