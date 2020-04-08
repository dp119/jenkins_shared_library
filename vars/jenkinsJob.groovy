def call(){
    node {
        stage('Checkout') {
            checkout scm
        }

        // Execute different stages depending on the job
		
		test()
		
		compile()
		
		sonarScan()
		
		packageArtifact()

		dbScriptDeploy()
    }
}

def test(){
    stage("Backend tests"){
        bat "mvn test"
    }
}

def compile(){
    stage("Compile") {
        bat "mvn compile"
    }
}

	
def sonarScan(){
    stage("Sonar scan"){
        bat "mvn sonar:sonar"
    }
}

def packageArtifact(){
    stage("Package artifact") {
        bat "mvn package"
    }
}
	
def dbScriptDeploy(){
    stage('dbScriptDeploy') {
	// One or more steps need to be included within the steps block.
	flywayrunner commandLineArgs: '', credentialsId: 'mysql', flywayCommand: 'migrate', installationName: 'Flyway', locations: 'filesystem:sql', url: 'jdbc:mysql://127.0.0.1:3306/database1'
    }
}
