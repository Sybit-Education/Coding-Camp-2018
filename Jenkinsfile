node{
    try{

        properties: [
            disableConcurrentBuilds(),
            buildDiscarder( logRotator(numToKeepStr: '5'))
        ]

        def dockerInstanceName = "battleship"
        
        stage('Checkout'){
            //notifyBuild('STARTED')
            deleteDir() //cleanup workspace

            //sh 'export GRADLE_OPTS="-Dorg.gradle.daemon=false"'
            sh 'mkdir -p ~/.gradle && echo "org.gradle.daemon=false" >> ~/.gradle/gradle.properties'

            checkout scm
            sh 'chmod +x gradlew'
        }

        stage('Build'){
            gradle 'clean assemble'
        }

        stage('Test'){
            gradle 'test -Dspring.profiles.active=junit'
            //gradle 'sonarqube -Dsonar.host.url=http://lysithea.sybit.de:9000 -Dsonar.login=6ac485962bd4416e600b8dc41c794e70ef621ea0'
            step( [ $class: 'JacocoPublisher' ] )
        }

        stage('Archive'){
            archiveArtifacts allowEmptyArchive: true, artifacts: 'build/libs/*.*ar', fingerprint: true
        }

        stage('Docker') {

            def branchName = env.BRANCH_NAME.toLowerCase()
            if (branchName.contains("/")) {
              // ignore branch type
              branchName = branchName.split("/")[1]
            }
            branchName = branchName.replace("-", "")

            if(env.BRANCH_NAME == 'master'){

                def imageName = dockerInstanceName + ":" +
                  ((env.BRANCH_NAME == "master") ? "" : "${branchName}-") +
                  env.BUILD_ID
                echo "Build Docker ${imageName}"
                def customImage = docker.build("${imageName}")

                timeout(time: 20, unit: 'MINUTES') {
                    //Push the Docker image to registry with Tag
                    docker.withRegistry('https://coding-camp.artifactory.sybit.de', 'docker-artifactory-credentials') {
                        /* Finally, we'll push the image with two tags:
                        * First, the incremental build number from Jenkins
                        * Second, the 'latest' tag.
                        * Pushing multiple tags is cheap, as all the layers are reused. */
                        customImage.push("${branchName}-${env.BUILD_NUMBER}")                        
                        customImage.push("latest")
                    }

                }
            }

            if(env.BRANCH_NAME == 'develop'){

                def imageName = dockerInstanceName + ":" + "develop"
                echo "Build Docker ${imageName}"
                def customImage = docker.build("${imageName}")

                timeout(time: 20, unit: 'MINUTES') {
                    //Push the Docker image to registry with Tag
                    docker.withRegistry('https://coding-camp.artifactory.sybit.de', 'docker-artifactory-credentials') {
                        /* Finally, we'll push the image with two tags:
                        * First, the incremental build number from Jenkins
                        * Second, the 'latest' tag.
                        * Pushing multiple tags is cheap, as all the layers are reused. */
                        customImage.push("${branchName}")
                    }

                }
            }


            if(env.BRANCH_NAME == 'develop'){
                try {
                    sh 'docker rm -f battleship-test'
                } catch (e) { }
                docker.withRegistry('https://coding-camp.artifactory.sybit.de', 'docker-artifactory-credentials') {
                    sh "docker run -d -p 12000:8080 --name battleship-test coding-camp.artifactory.sybit.de/battleship:develop"
                }
            }

            if(env.BRANCH_NAME == 'master'){

                echo 'Deploy master on PROD-Server ...'

                withCredentials([sshUserPrivateKey(
                    credentialsId: 'coding-camp.sybit.de',
                    keyFileVariable: 'AAAAB3NzaC1yc2EAAAADAQABAAACAQDF6PI3dF+D8gSIyQe1Y/pSsJ9LxFmbFrzVnPIR1OGIdxON/OHIwWtDhs9tnSy+XbGXe88mJ2k/61iSymneHvG7lBRmRmLe9dQhYjn1rFGzCvDutpPjxrKl0U7vDV9dWChkCF3gF9kwax3ckDb54EFWqvg0imiasjA3d+CF+AD4KGTeGwfM3F+f9af0WxrZQmyfp1egePvRlexvhdL0kCFU4QJ2g6zEnNRUWDc3QzXdc9GPKIInWgrmbUH/7iDb2l1zAOYza5p4b0is0BR9NXbqeo9Q32kg891rjkw/aiWpzbTSMUqVWUb3j5HtLx+qLR5udfNKt9t+I1hjElMx+3WCEsyCA6ZV1DVLb03oEeXc7k9GUPYmMleaVxuq594GoVLusD98MHumvIHsDNiAuDjyya8QAhHMy08KCWSuUqnTjN+W8pNXF6l/Z1gDggNnuhijEYyhgCQnkB0w5qo/Ikpqm4AlpNcNNvgSWe0VgniJIk8mTWaMfK5cDL8XAAm8g7vi9KbJmUSZL4iFN382ICXCeDOl4y4FopHj5X3TLTHCijH6sfQfNsPYlrTarxet3xfssqHodIAABWWVXBtmUIp9t6UFRDg18JjbKgsRISEvWSS12mAhz52h0qvuKu/Zt4RgokH7aL4l4Rg1u2q8UCYIb/8xlbAwuN5jP5mGx9Ok8Q==',
                    passphraseVariable: '',
                    usernameVariable: 'userName'
                    )]) {

                    def remote = [:]
                    remote.name = "coding-camp"
                    remote.host = "coding-camp.sybit.de"

                    remote.allowAnyHosts = false
                    remote.user = "root"
                    //remote.password = "${PASSWORD}"
                    remote.identityFile = '/data/jenkins/secrets/keyfile'
                    remote.passphrase = '100%Hacker'
                    remote.knownHosts = '/data/jenkins/secrets/known_hosts'
     
                    sshCommand remote: remote, command: "uname -a"
                    sshCommand remote: remote, command: "docker rm -f battleship", failOnError: false
                    sshCommand remote: remote, command: "cd /home/docker/battleship"
                    sshCommand remote: remote, command: "docker-compose.yml up -d"
               
                }            
                
            }
        }   

    } catch (e){
        currentBuild.result = "FAILED"
        throw e
    } finally {
        junit allowEmptyResults: true, testResults: 'build/test-results/test/TEST-*.xml'

        // Success or failure, always send notifications
        notifyBuild(currentBuild.result)
    }
}

def gradle(command) {
    if (isUnix()) {
        sh "./gradlew --info ${command}"
    } else {
        bat "gradlew.bat --info ${command}"
    }
}


def notifyBuild(String buildStatus = 'STARTED') {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'
 
  // Default values
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def details = """<p>Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>"""

  emailext (
      subject: subject,
      mimeType: 'text/html',
      body: details,
      attachLog: true,
      recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
}
