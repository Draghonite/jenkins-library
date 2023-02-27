def call(Map params) {
    withCredentials([string(credentialsId: "AWS_ACCESS_KEY_ID_${params.DEPLOY_ENV}", variable: 'AWS_ACCESS_KEY_ID')]) {
        withCredentials([string(credentialsId: "AWS_SECRET_ACCESS_KEY_${params.DEPLOY_ENV}", variable: 'AWS_SECRET_ACCESS_KEY')]) {
            sh """
                apk update && apk add terraform aws-cli
                cd ${params.TF_PROJECT_PATH}
                echo Getting Terraform state remotely
                (AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} AWS_DEFAULT_REGION=${params.AWS_REGION} aws s3 cp ${params.TF_STATE_S3_BUCKET_URL} terraform.tfstate --quiet || exit 0) 
            """
        }
    }
}