def call(Map params) {
    withCredentials([string(credentialsId: 'AWS_ACCESS_KEY_ID', variable: 'AWS_ACCESS_KEY_ID')]) {
        withCredentials([string(credentialsId: 'AWS_SECRET_ACCESS_KEY', variable: 'AWS_SECRET_ACCESS_KEY')]) {
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
            // TODO: fix 'cdktf deploy' results in ERROR Raw mode is not supported on the current process.stdin
            sh """
                cd ./artifacts
                mkdir ./release && cd ./release
                tar -xzvf ../${params.PACKAGE_NAME} .
                cdktf deploy --auto-deploy
                echo TODO: Deployed the ${params.BUILD_ENV} build to ${params.DEPLOY_ENV}.
            """
        }
    }
}