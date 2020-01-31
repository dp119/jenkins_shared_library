def call(){
    node {
        stage('Checkout') {
            checkout scm
        }

        packageArtifact()

        archiveArtifact()

        deploy()

        health_check()

        // Execute different stages depending on the job
        // if(env.JOB_NAME.contains("deploy")){
        //     packageArtifact()
        // } else if(env.JOB_NAME.contains("test")) {
        //     buildAndTest()
        // }
    }
}


def packageArtifact(){
    stage("Package artifact") {
        bat "mvn -f E:/Deepu/Jenkins/atmosphere2/spring-boot-samples/spring-boot-sample-atmosphere/pom.xml compile && exit %%ERRORLEVEL%%"
        bat "mvn -f E:/Deepu/Jenkins/atmosphere2/spring-boot-samples/spring-boot-sample-atmosphere/pom.xml package && exit %%ERRORLEVEL%%"
    }
}

def archiveArtifact(){
    stage("Archive artifact") {
        bat "echo %cd%"
        archiveArtifacts E:\Deepu\Jenkins\atmosphere2\spring-boot-samples\spring-boot-sample-atmosphere\target\*.jar    }
}

def deploy(){
   stage('Deploy Artifact') {
    copyArtifacts(
          projectName: currentBuild.projectName,
          filter: 'E:/Deepu/Jenkins/atmosphere2/spring-boot-samples/spring-boot-sample-atmosphere/target/*.jar',
          fingerprintArtifacts: true,
          target: 'D:/Tomcat/webapps/',
          flatten: true        )
    }
}

def health_check(){
    stage("Health Check"){
        pwsh '''curl -sL --connect-timeout 20 --max-time 30 -w "%{http_code}\\n" "$url" -o /dev/null
                if [ "$code" = "200" ]; then
                    echo "Website $url is online."
                else
                    echo "Website $url seems to be offline."
                fi '''
    }
}