def call(Map params) {
    withCredentials([usernameColonPassword(credentialsId: params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh "apk add curl jq"
        // TODO: resume here -- download the report checksum and compare with current checksum -- acquire path to the downloaded artifact from params
        sh "cd ${params.PACKAGES_PATH} && md5sum ${params.PACKAGE_NAME} > sum1.txt && curl -su ${ARTIFACTORY_USER_PASS} ${params.ARTIFACTORY_SERVER}/api/storage/libs-release-local/${params.PACKAGE_REPO_PATH} | jq -r '.checksums.md5 +\"  ${params.PACKAGE_NAME}\"' > sum2.txt"
        input message:'verify file'
    }
}