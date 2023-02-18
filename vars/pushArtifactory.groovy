def call(ARTIFACTORY_CREDS_ID) {
        echo "pushArtifactory called!"
// ARTIFACTORY_CREDS_ID, ARTIFACTORY_SERVER, PACKAGE_PATH, PACKAGE_NAME, BUILD_ENV, BUILD_PREFIX, BUILD_NUMBER, REPO="libs-release-local"
/*
    withCredentials([usernameColonPassword(credentialsId: $ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh '''
            apk add curl
            curl -i -u "$ARTIFACTORY_USER_PASS" \
                -T $PACKAGE_PATH \
                "$ARTIFACTORY_SERVER/$REPO/$PACKAGE_NAME-$BUILD_ENV/$PACKAGE_NAME-$BUILD_ENV-$BUILD_PREFIX.$BUILD_NUMBER.zip"
        '''
    }
*/
}
