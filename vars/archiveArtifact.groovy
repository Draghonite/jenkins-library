def call(Map params) {
    withCredentials([usernameColonPassword(credentialsId: params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh "apk add curl"
        // sh "ARTIFACT_MD5_CHECKSUM = \$(md5sum ${params.PACKAGE_BUILD_PATH}) | awk '{ print \$1 }'"
        // sh "ARTIFACT_SHA1_CHECKSUM = \$(sha1sum ${params.PACKAGE_BUILD_PATH}) | awk '{ print \$1 }'"
        // sh "ARTIFACT_SHA256_CHECKSUM = \$(sha256sum ${params.PACKAGE_BUILD_PATH}) | awk '{ print \$1 }'"
        sh "ARTIFACT_MD5_CHECKSUM=\$(md5sum ${params.PACKAGE_BUILD_PATH}) | awk '{ print \$1 }' && curl --H \"X-Checksum-MD5:${ARTIFACT_MD5_CHECKSUM}\" -i -u ${ARTIFACTORY_USER_PASS} -T ${params.PACKAGE_BUILD_PATH} ${params.ARTIFACTORY_SERVER}/libs-release-local/${params.PACKAGE_REPO_PATH}"
    }
}
