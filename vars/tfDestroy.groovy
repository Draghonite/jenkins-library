def call(Map params) {
    withCredentials([string(credentialsId: "AWS_ACCESS_KEY_ID_${params.DEPLOY_ENV}", variable: 'AWS_ACCESS_KEY_ID')]) {
        withCredentials([string(credentialsId: "AWS_SECRET_ACCESS_KEY_${params.DEPLOY_ENV}", variable: 'AWS_SECRET_ACCESS_KEY')]) {
            sh '''
                apk update && apk add terraform aws-cli
                rm -rf ./artifacts && mkdir ./artifacts
            '''
            dir('./artifacts') {
                unstash 'artifactstash'
            }
            sh """
                cd ./artifacts && mkdir ./release && cd ./release
                tar -xzvf ../${params.PACKAGE_NAME} .
                terraform init -no-color -input=false -compact-warnings

                echo Loading Terraform state remotely if exists
                (AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} AWS_DEFAULT_REGION=${params.AWS_REGION} aws s3 cp ${params.TF_STATE_S3_BUCKET_URL} terraform.tfstate --quiet || exit 0) 
                
                TF_VAR_region=${params.AWS_REGION} \
                TF_VAR_availability_zone=${params.AWS_REGION}a \
                TF_VAR_deploy_env=${params.DEPLOY_ENV} \
                TF_VAR_app_name=${params.APPLICATION_NAME} \
                terraform destroy -var-file=public-${params.DEPLOY_ENV}.tf-vars -input=false -no-color -auto-approve -compact-warnings

                echo Deployed the ${params.BUILD_ENV} build to ${params.DEPLOY_ENV}.

                echo Saving Terraform state remotely
                AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} AWS_DEFAULT_REGION=${params.AWS_REGION} aws s3 cp terraform.tfstate ${params.TF_STATE_S3_BUCKET_URL}
            """
            sh 'rm -rf ./artifacts'
        }
    }
}