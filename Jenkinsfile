def serverTmIp = ""
def pathSuites = ""
def machineCreated = false;

pipeline {

	agent { label 'java-docker-slave' }
	environment {
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
		stage("Package") {
			steps {
				dir("core") {
					sh label: 'Install Core', script: 'mvn -P CI -Dmaven.test.skip=true clean install'  
				}
				dir("examples/bom-examples") {
					sh label: 'Package bom-examples', script: 'mvn clean package'
				}
				dir("examples/example-test") {
					sh label: 'Package example-test', script: 'mvn clean package'
				}
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

		stage("Create instance Google Cloud") {
			steps {
				dir("examples/example-test") {
					script {
						sh 	label: 'Point to project testmaker', 
							script: '$GCLOUD_PATH/gcloud config set project testmaker-example'
						machineCreated = true;
						sh 	label: 'Create Instance in Google Cloud',
							script: ''' 
								$GCLOUD_PATH/gcloud compute instances create-with-container testmaker-hub --machine-type=n1-highcpu-8 --zone europe-west1-b --container-mount-host-path mount-path=/output-library,host-path=/home/jenkins/output-library,mode=rw --tags http-server,https-server --container-image=$TAG_IMAGE_DOCKER --container-privileged
							'''
						serverTmIp = sh script: '''
								$GCLOUD_PATH/gcloud compute instances describe testmaker-hub --zone europe-west1-b --format=\'get(networkInterfaces.accessConfigs[0].natIP)\' 
							''', returnStdout: true
						echo "IP of the GC Instance:" + serverTmIp
						//if serverTmIp="" -> error
					}
				}
			}
		}
		stage("Integration Tests") {
			steps {
				dir("examples/example-test") {
					withEnv(["SERVER=$serverTmIp"]) {
						script {
							sh 	label: 'Execute end-to-end integration-tests', 
								script: 'mvn -PCI clean verify -Dserver.port=80 -Dserver.ip=${SERVER}'
							sh	label: 'Purge output-library',
								script: 'rm -R ${WORKSPACE}/output-library'
							sh 	label: 'Get reports from GC-Instance', 
								script: '$GCLOUD_PATH/gcloud compute scp --recurse testmaker-hub:/home/jenkins/output-library ${WORKSPACE}/output-library --zone=europe-west1-b'
							pathSuites = sh  script: '''
								for entry in $(ls ${WORKSPACE}/output-library/SmokeTest); do
									echo "SmokeTest\\\\${entry}\\\\ReportTSuite.html"
								done 
								''', returnStdout: true
							pathSuites = pathSuites.replace('\n',',')
						}
					}
				}
			}
		}
		stage("Publish HTML Reports") {
			steps {
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
	}
	post {
		always {
			script {
				if ( machineCreated == true) {
					sh label: 
						'Destroy intance in Google Cloud', 
						script: '$GCLOUD_PATH/gcloud compute instances delete testmaker-hub --zone europe-west1-b'
				}
			}
		}
	}
}