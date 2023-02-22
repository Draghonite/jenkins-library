def call(Map params) {
    sh '''
        apk update && apk add terraform
        npm install -y -g cdktf-cli
        npm install -y -g typescript
        npm install
        rm -rf ./artifacts && mkdir ./artifacts
    '''
    dir('./artifacts') {
        unstash 'artifactstash'
    }
    sh '''
        cd ./artifacts
        mkdir ./release && cd ./release
        tar -xzvf ../$PACKAGE_NAME .
        cdktf deploy --auto-deploy
    '''
    // TODO: fix 'cdktf deploy' results in ERROR Raw mode is not supported on the current process.stdin
    sh "echo TODO: Deploy the ${BUILD_ENV} build to ${DEPLOY_ENV}."
    sh "./artifacts"
}