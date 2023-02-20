def call(Map params) {
    withCredentials([usernameColonPassword(credentialsId: params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh "apk add curl"
        sh "mkdir -p ${params.PACKAGES_PATH}"
        sh "cd ${params.PACKAGES_PATH} && curl -u ${ARTIFACTORY_USER_PASS} -O ${params.ARTIFACTORY_SERVER}/libs-release-local/${params.PACKAGE_REPO_PATH}"
        // dir('/packages') {
        //     stash(name: 'artifactstash', includes: '/packages/.')
        // }
        stash name: 'artifactstash', includes: '/packages/reactivities-client-dev-1.0.226.tar.gz'
    }
}