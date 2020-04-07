def call(){
    node {
        stage('Checkout') {
            checkout scm
        }

        // Execute different stages depending on the job
		
		test()
		
		packageArtifact()

		sonarScan()
		
		dbScriptDeploy()
    }
}

def test(){
    stage("Backend tests"){
        bat "mvn test"
    }
}

def packageArtifact(){
    stage("Package artifact") {
        bat "mvn compile"
        bat "mvn package"
    }
}


	
def sonarScan(){
    stage("Sonar scan"){
        bat "mvn sonar:sonar"
    }
}
	
def dbScriptDeploy(){
    stage('dbScriptDeploy') {
	// One or more steps need to be included within the steps block.
	flywayrunner commandLineArgs: '', credentialsId: 'mysql', flywayCommand: 'info', installationName: 'Flyway', locations: 'src\\main\\resources\\db\\migration', url: 'jdbc:mysql://127.0.0.1:3306/database1'
    }
}

