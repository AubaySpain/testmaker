--- Package

	mvn clean package -Dmaven.test.skip=true

--- Examples of execution from maven

	mvn test -Dsuite=SmokeTest -Ddriver=chrome -Dchannel=desktop -Dapplication=google
	mvn test -Dsuite=FactorySearch -Ddriver=chrome -Dchannel=mobile -Dapplication=google -threads 4
	mvn test -Dsuite=SmokeTest -Ddriver=chrome -Dchannel=desktop -Dapplication=google -Dtcases=GOO001{4-2}
	mvn test -Dsuite=SmokeTest -Ddriver=chrome -Dchannel=desktop -Dapplication=google -Dretry=2
	mvn test -Dsuite=SmokeTest -Ddriver=chrome -Dchannel=desktop -Dapplication=google -Drecord=true -Durl=https://www.google.com
	mvn test -Dsuite=SmokeTest -Ddriver=chrome -Dchannel=desktop -Dapplication=google -Ddynatracesd=oyy52961.apps.dynatrace.com
	mvn test -Dsuite=SmokeTest -Ddriver=chromehless -Dchannel=desktop -Dapplication=google -Durl=https://www.google.com

--- Execution from IDE

	you can execute directly from maven or executing the main class /runner/TestRunner.java passing parameters like: 
	-suite SmokeTest -driver chrome -channel desktop -application google -tcases GOO001
	
--- Executing from Command Line

	unzip target/${artifactId}.zip
	java -jar ./${artifactId}/${artifactId}.jar -suite SmokeTest -driver chrome -channel desktop -application google

--- Test Report

	The html report can be found in ./output-library/{suite_name}/{id_testrun}/ReportTSuite.html

--- Execution exposing REST Server

	unzip target/${artifactId}.zip
	Expose REST Server: java -cp ./${artifactId}/${artifactId}.jar ${groupId}.${artifactId}.runner.ServerRest -port 80
	Execute Tests: curl -X POST localhost/suiterun/ -d "suite=SmokeTest&driver=chrome&channel=desktop&application=google&asyncexec=false"
	
	The html report can be viewed in two manners using the "idExecSuite" field returned by the previous POST call:
		1. Opening ./output-library/SmokeTest/{id_testrun}/ReportTSuite.html
		2. Executing the follow url in the browser: localhost/suiterun/{id_testrun}/report

--- Distributed execution example 
	
	Expose REST Server-1 (hub) in port 80
	Expose REST Server-2 (slave) in port 81
	Subscribe Server-2 to Server-1: curl -X GET localhost/subscription?urlslave=http://localhost:81
	Execute Tests: curl -X POST localhost/suiterun/ -d "suite=SmokeTest&driver=chrome&channel=desktop&application=google&asyncexec=false"
	
	The tests are managed by Server-1 and executed by Server-2. 
	
	You can subscribe many slaves to hub. 