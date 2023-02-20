def call(Map params) {
    withCredentials([usernameColonPassword(credentialsId: params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh "apk add curl"
        sh "mkdir -p ./packages"
        sh "cd ./packages && curl -u ${ARTIFACTORY_USER_PASS} -O ${params.ARTIFACTORY_SERVER}/libs-release-local/${params.PACKAGE_REPO_PATH}"
        dir('./packages') {
            stash(name: 'artifactstash')
        }
        // stash name: 'artifactstash', includes: '/packages/reactivities-client-dev-1.0.226.tar.gz'
    }
}