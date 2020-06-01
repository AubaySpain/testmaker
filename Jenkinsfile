//def serverTmIp = ""
//def pathSuites = ""
//def machineCreated = false;

pipeline {

	agent { label 'java-docker-slave' }
	environment {
		SERVER_TM_IP = ""
		PATH_SUITES = ""
		MACHINE_CREATED = false

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

		stage("Create instance Google Cloud") {
			steps {
				dir("examples/example-test") {
					script {
						sh 	label: 'Point to project testmaker', 
							script: '$GCLOUD_PATH/gcloud config set project testmaker-example'
						env.MACHINE_CREATED = true;
						sh 	label: 'Create Instance in Google Cloud',
							script: ''' 
								$GCLOUD_PATH/gcloud compute instances create-with-container testmaker-hub --machine-type=n1-highcpu-8 --zone europe-west1-b --container-mount-host-path mount-path=/output-library,host-path=/home/jenkins/output-library,mode=rw --tags http-server,https-server --container-image=$TAG_IMAGE_DOCKER --container-privileged
							'''
						env.SERVER_TM_IP = sh script: '''
								$GCLOUD_PATH/gcloud compute instances describe testmaker-hub --zone europe-west1-b --format=\'get(networkInterfaces.accessConfigs[0].natIP)\' 
							''', returnStdout: true
						echo "IP of the GC Instance:" + env.SERVER_TM_IP
						//if serverTmIp="" -> error
					}
				}
			}
		}
		stage("Integration Tests") {
			steps {
				dir("examples/example-test") {
					//withEnv(["SERVER=$serverTmIp"]) {
						script {
							sh 	label: 'Execute end-to-end integration-tests', 
								script: 'mvn -PCI clean verify -Dserver.port=80 -Dserver.ip=${SERVER_TM_IP}'
						}
						post {
							always {
								script {
									sh	label: 'Purge output-library',
										script: 'rm -R ${WORKSPACE}/output-library'
									sh 	label: 'Get reports from GC-Instance', 
										script: '$GCLOUD_PATH/gcloud compute scp --recurse testmaker-hub:/home/jenkins/output-library ${WORKSPACE}/output-library --zone=europe-west1-b'
									def pathSuites = sh  script: '''
										for entry in $(ls ${WORKSPACE}/output-library/SmokeTest); do
											echo "SmokeTest\\\\${entry}\\\\ReportTSuite.html"
										done 
										''', returnStdout: true
									env.PATH_SUITES = pathSuites.replace('\n',',')
								}
							}
						}
					//}
				}
			}
		}
	}
	post {
		always {
			script {
				if ( env.MACHINE_CREATED == true) {
					sh label: 
						'Destroy intance in Google Cloud', 
						script: '$GCLOUD_PATH/gcloud compute instances delete testmaker-hub --zone europe-west1-b'
				}
			}
			//withEnv(["PATHSUITES=$pathSuites"]) {
				publishHTML([
					allowMissing: false, 
					alwaysLinkToLastBuild: false, 
					keepAll: false, 
					reportDir: 'output-library', 
					reportFiles: "${PATH_SUITES}", 
					reportName: 'HTML Report', reportTitles: ''])  
			//}
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