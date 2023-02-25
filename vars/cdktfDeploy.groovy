def call(Map params) {
    withCredentials([string(credentialsId: "AWS_ACCESS_KEY_ID_${params.DEPLOY_ENV}", variable: 'AWS_ACCESS_KEY_ID')]) {
        withCredentials([string(credentialsId: "AWS_SECRET_ACCESS_KEY_${params.DEPLOY_ENV}", variable: 'AWS_SECRET_ACCESS_KEY')]) {
            def AWS_AVAILABILITY_ZONE = "${params.AWS_REGION}a";
            def AWS_BUNDLE_ID = "nano_2_0";
            switch (params.DEPLOY_ENV) {
                case "qa":
                    AWS_BUNDLE_ID = "small_2_0"
                    break;
                // case "prod":
                //     AWS_BUNDLE_ID = "medium_2_0"
                //     break;
            }
            sh '''
                apk update && apk add terraform
                rm -rf ./artifacts && mkdir ./artifacts
            '''
            dir('./artifacts') {
                unstash 'artifactstash'
            }
            sh """
                cd ./artifacts
                terraform init
                AWS_REGION=${params.AWS_REGION} AWS_AVAILABILITY_ZONE=${AWS_AVAILABILITY_ZONE} AWS_BUNDLE_ID=${AWS_BUNDLE_ID} DEPLOY_ENV=${params.DEPLOY_ENV} \
                terraform plan && terraform apply --auto-approve
                echo Deployed the ${params.BUILD_ENV} build to ${params.DEPLOY_ENV}.
            """
            input message="done?"
        }
    }
}