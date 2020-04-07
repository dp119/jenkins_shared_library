def call(){
    node {
        stage('Checkout') {
            checkout scm
        }

        // Execute different stages depending on the job
		
		packageArtifact()
		test()
		sonarScan()
    }
}

def packageArtifact(){
    stage("Package artifact") {
        bat "mvn compile"
        bat "mvn package"
    }
}

def test(){
    stage("Backend tests"){
        bat "mvn test"
    }
}
	
def sonarScan(){
    stage("Sonar scan"){
        bat "mvn sonar:sonar"
    }
}
