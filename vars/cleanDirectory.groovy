def call(Map params) {
    sh "rm -rf ${params.DIRECTORY}"
}