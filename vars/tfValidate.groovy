def call(Map params) {
    sh '''
        apk update && apk add terraform
    '''
    withCredentials([string(credentialsId: "AWS_ACCESS_KEY_ID_${params.BUILD_ENV}", variable: 'AWS_ACCESS_KEY_ID')]) {
        withCredentials([string(credentialsId: "AWS_SECRET_ACCESS_KEY_${params.BUILD_ENV}", variable: 'AWS_SECRET_ACCESS_KEY')]) {
            sh """
                TF_VAR_deploy_env=${params.BUILD_ENV} \
                    terraform init -no-color -input=false -compact-warnings && \
                    terraform validate
            """
        }
    }
    stash(name: 'sourceartifacts')
}