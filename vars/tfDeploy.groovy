def call(Map params) {
    withCredentials([string(credentialsId: "AWS_ACCESS_KEY_ID_${params.DEPLOY_ENV}", variable: 'AWS_ACCESS_KEY_ID')]) {
        withCredentials([string(credentialsId: "AWS_SECRET_ACCESS_KEY_${params.DEPLOY_ENV}", variable: 'AWS_SECRET_ACCESS_KEY')]) {
            sh '''
                apk update && apk add terraform
                rm -rf ./artifacts && mkdir ./artifacts
            '''
            dir('./artifacts') {
                unstash 'artifactstash'
            }
            sh """
                cd ./artifacts && mkdir ./release && cd ./release
                tar -xzvf ../${params.PACKAGE_NAME} .
                terraform init -no-color -input=false -compact-warnings
            #    AWS_REGION=${params.AWS_REGION} AWS_AVAILABILITY_ZONE=${AWS_AVAILABILITY_ZONE} AWS_BUNDLE_ID=${AWS_BUNDLE_ID} DEPLOY_ENV=${params.DEPLOY_ENV} \
            #        terraform plan -input=false -compact-warnings plan.file
                
                TF_VAR_region=${params.AWS_REGION} \
                TF_VAR_export deploy_env=${params.DEPLOY_ENV} \
                TF_VAR_export availability_zone=${params.AWS_AVAILABILITY_ZONE} \
                TF_VAR_export bundle_id=${params.AWS_BUNDLE_ID} \
                TF_VAR_export app_name=${params.APPLICATION_NAME} \
                terraform apply -input=false -auto-approve -compact-warnings plan.file

                echo Deployed the ${params.BUILD_ENV} build to ${params.DEPLOY_ENV}.
            """
        }
    }
}