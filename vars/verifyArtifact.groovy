def call(Map params) {
    withCredentials([usernameColonPassword(credentialsId: params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh "apk add curl jq"
        sh "cd ${params.PACKAGES_PATH} && curl -isu ${ARTIFACTORY_USER_PASS} ${params.ARTIFACTORY_SERVER}/api/storage/libs-release-local/${params.PACKAGE_REPO_PATH} | jq -r '.checksums.md5' > sum2.txt"
    }
}