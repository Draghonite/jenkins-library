def call(Map params) {
    withCredentials([usernameColonPassword(credentialsId: params.ARTIFACTORY_CREDS_ID, variable: 'ARTIFACTORY_USER_PASS')]) {
        sh "apk add curl"
        sh "rm -rf ./artifacts && mkdir ./artifacts"
        sh 'cd ./artifacts && curl -u $ARTIFACTORY_USER_PASS -O "${params.ARTIFACTORY_SERVER}"/libs-release-local/"${params.PACKAGE_REPO_PATH}"'
        dir('./artifacts') {
            stash(name: 'artifactstash')
        }
        sh "rm -rf ./artifacts"
    }
}