def serverHubTmIp = ""
def serverSlaveTmIp = ""
def pathSuites = ""
def machineCreated = false;

pipeline {

	agent { label 'java-docker-slave' }
	environment {
		//SERVER_TM_IP = ""
		//PATH_SUITES = ""
		//MACHINE_CREATED = false

		GCLOUD_PATH = "/home/jenkins/tools/com.cloudbees.jenkins.plugins.gcloudsdk.GCloudInstallation/gcloud/bin"
		PATH = "$GCLOUD_PATH:$PATH"
		TAG_IMAGE_DOCKER = "gcr.io/testmaker-example/example-test:latest"
	}
	stages {
		stage("Git Checkout") {
			steps {
				checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/AubaySpain/testmaker.git']]])
			}
		} 
		stage("Unit Tests") {
			steps {
				sh label: 'Unit Tests', script: 'mvn -P CI clean test'
			}
			post {
				always {
					junit 'core/target/surefire-reports/*.xml'
				}
			}
		}
		stage("Package example-test") {
			steps {
				sh label: 'Package', script: 'mvn -P CI -Dmaven.test.skip=true clean install'  
			}
		}
		stage("Build Docker for example-test project") {
			steps {
				dir("examples/example-test") {
					sh label: 'Dockerize example-test', script: 'docker build -t $TAG_IMAGE_DOCKER .'
				}
			}
		}
		stage("Push Docker to Google Cloud") {
			steps {
				withCredentials([file(credentialsId: 'key-gc', variable: 'GC_KEY')]) {
					sh  label: 'Autenticate in Google Cloud', 
						script: '$GCLOUD_PATH/gcloud auth activate-service-account --key-file=${GC_KEY}'
				}
				sh 	label: 'Set project testmaker in Google Cloud', 
					script: '$GCLOUD_PATH/gcloud config set account testmaker@testmaker-example.iam.gserviceaccount.com'
				sh 	label: 'Autorize docker to push to Google Cloud', 
					script: '$GCLOUD_PATH/gcloud auth configure-docker'
				sh 	label: 'Push docker to Google Cloud', 
					script: 'docker push $TAG_IMAGE_DOCKER'
			}		
		}

		stage("Create instances (Hub/Slave) Google Cloud") {
			steps {
				dir("examples/example-test") {
					script {
						sh 	label: 'Point to project testmaker', 
							script: '$GCLOUD_PATH/gcloud config set project testmaker-example'
						machineCreated = true;
						sh 	label: 'Create Instance Hub in Google Cloud',
							script: ''' 
								$GCLOUD_PATH/gcloud compute instances create-with-container testmaker-hub --machine-type=n1-highcpu-8 --zone europe-west1-b --container-mount-host-path mount-path=/output-library,host-path=/home/jenkins/output-library,mode=rw --tags http-server,https-server --container-image=$TAG_IMAGE_DOCKER --container-privileged
							'''
						serverHubTmIp = sh script: '''
								$GCLOUD_PATH/gcloud compute instances describe testmaker-hub --zone europe-west1-b --format=\'get(networkInterfaces.accessConfigs[0].natIP)\' 
							''', returnStdout: true
						echo "IP of the GC Instance-Hub:" + serverHubTmIp
						
						sh 	label: 'Create Instance Slave in Google Cloud',
							script: ''' 
								$GCLOUD_PATH/gcloud compute instances create-with-container testmaker-slave --machine-type=n1-highcpu-8 --zone europe-west1-b --container-mount-host-path mount-path=/output-library,host-path=/home/jenkins/output-library,mode=rw --tags http-server,https-server --container-image=$TAG_IMAGE_DOCKER --container-privileged
							'''
						serverSlaveTmIp = sh script: '''
								$GCLOUD_PATH/gcloud compute instances describe testmaker-slave --zone europe-west1-b --format=\'get(networkInterfaces.accessConfigs[0].natIP)\' 
							''', returnStdout: true
						echo "IP of the GC Instance-Slave:" + serverSlaveTmIp
					}
				}
			}
		}
		stage("Integration Tests") {
			steps {
				dir("examples/example-test") {
					withEnv(["SERVER_HUB=$serverHubTmIp", "SERVER_SLAVE=$serverSlaveTmIp"]) {
						script {
							sh 	label: 'Execute end-to-end integration-tests', 
								script: 'mvn -PCI clean verify -Dserver_hub.ip=${SERVER_HUB} -Dserver_hub.port=80 -Dserver_slave.ip=${SERVER_SLAVE} -Dserver_slave.port=80'
						}
					}
				}
			}
		}
	}
	post {
		always {
			script {
				if ( machineCreated == true) {
					sh	label: 'Purge output-library',
						script: 'rm -rf ${WORKSPACE}/output-library/*'
						
					sh label: 'Create void output-library',
						script: 'mkdir -p ${WORKSPACE}/output-library/SmokeTestHub'
					sh 	label: 'Get reports from GC-Hub-Instance', 
						script: '$GCLOUD_PATH/gcloud compute scp --recurse testmaker-hub:/home/jenkins/output-library/SmokeTest/* ${WORKSPACE}/output-library/SmokeTestHub --zone=europe-west1-b'

					sh label: 'Create void output-library',
						script: 'mkdir -p ${WORKSPACE}/output-slave/SmokeTestSlave'
					sh 	label: 'Get reports from GC-Slave-Instance', 
						script: '$GCLOUD_PATH/gcloud compute scp --recurse testmaker-slave:/home/jenkins/output-library/SmokeTest/* ${WORKSPACE}/output-library/SmokeTestSlave --zone=europe-west1-b'
						
					pathSuites = sh  script: '''
						for entry in $(ls ${WORKSPACE}/output-library/SmokeTestHub); do
							echo "SmokeTest\\\\${entry}\\\\ReportTSuite.html"
						done 
						for entry in $(ls ${WORKSPACE}/output-library/SmokeTestSlave); do
							echo "SmokeTest\\\\${entry}\\\\ReportTSuite.html"
						done 
						''', returnStdout: true
					pathSuites = pathSuites.replace('\n',',')
				
					sh label: 
						'Destroy Hub-Instance in Google Cloud', 
						script: '$GCLOUD_PATH/gcloud compute instances delete testmaker-hub --zone europe-west1-b & $GCLOUD_PATH/gcloud compute instances delete testmaker-slave --zone europe-west1-b'
				}

				withEnv(["PATHSUITES=$pathSuites"]) {
					publishHTML([
						allowMissing: false, 
						alwaysLinkToLastBuild: false, 
						keepAll: false, 
						reportDir: 'output-library', 
						reportFiles: "${PATHSUITES}", 
						reportName: 'HTML Report', reportTitles: ''])  
				}
			}
		}
		success {
			withCredentials([string(credentialsId: 'GitHubToken', variable: 'GITHUB_TOKEN')]) {
				sh "curl https://api.GitHub.com/repos/AubaySpain/testmaker/statuses/$GIT_COMMIT?access_token=$GITHUB_TOKEN -H 'Content-Type: application/json' -X POST -d '{\"state\": \"success\",\"context\": \"continuous-integration/jenkins\", \"description\": \"Jenkins\", \"target_url\": \"https://jenkins.aubay.es/job/TEST-MAKER/job/TestMakerCI/$BUILD_NUMBER/console\"}'"
			}
		}
		failure {
			withCredentials([string(credentialsId: 'GitHubToken', variable: 'GITHUB_TOKEN')]) {
				sh "curl https://api.GitHub.com/repos/AubaySpain/testmaker/statuses/$GIT_COMMIT?access_token=$GITHUB_TOKEN -H 'Content-Type: application/json' -X POST -d '{\"state\": \"failure\",\"context\": \"continuous-integration/jenkins\", \"description\": \"Jenkins\", \"target_url\": \"https://jenkins.aubay.es/job/TEST-MAKER/job/TestMakerCI/$BUILD_NUMBER/console\"}'"
			}			
		}	
	}
}