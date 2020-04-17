Under construction... 

[![Maven](https://img.shields.io/maven-central/v/com.github.jorge2m/testmaker.svg?label=Maven%20Central)](https://search.maven.org/#search|ga|1|com.github.jorge2m.testmaker)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://github.com/Jorge2M/testmaker/blob/master/LICENSE)


# Basic Example
Lets go build a new Maven Project based on TestMaker that will:
- Expose access via Command Line and HTTP Rest API. Then we will can use many available parameters to customize the test suite execution.
- Expose a TestSuite with one test that checks the "Hello World" input in Google.

## Components to build
That project will include a pom.xml plus a few Java Classes, for simplicity I include all that clases in a same package.

#### pom.xml
The pom.xml has to include:
- The dependency to the testmaker artifact
- The artifact maven-compiler-plugin with a Java Version >= 1.8
- The artifact TestMaker is based in AspectJ technology, then we need to include the aspectj-maven-plugin for weave the aspects from TestMaker.

A possible pom.xml can be:
```xml
<?xml version="1.0"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<modelVersion>4.0.0</modelVersion>
	<artifactId>examples</artifactId>
	<groupId>com.github.jorge2m</groupId>
	<version>1.3.3</version>
	<packaging>pom</packaging>
	<name>examples</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>

	<modules>
		<module>example-test</module>
		<module>windriver-test</module>
	</modules>
		
	<dependencies>
		<dependency>
			<groupId>com.github.jorge2m</groupId>
			<artifactId>testmaker-core</artifactId>
			<version>1.3.3</version>
		</dependency>
	</dependencies>
	
	<build>
		<plugins>
			<!-- Seguramente si lo ejecuto sin testmaker se quejará de que no encuentra el aspectj en .m2 -->
			<!-- https://bugs.eclipse.org/bugs/show_bug.cgi?id=562209 -->
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>aspectj-maven-plugin</artifactId>
				<version>1.11</version>
				<configuration>
					<complianceLevel>1.8</complianceLevel>
					<source>1.8</source>
					<target>1.8</target>
					<showWeaveInfo>false</showWeaveInfo>
					<verbose>true</verbose>
					<Xlint>ignore</Xlint>
					<encoding>UTF-8 </encoding>
					<weaveDependencies>
						<weaveDependency> 
							<groupId>com.github.jorge2m</groupId>
							<artifactId>testmaker-core</artifactId>
						</weaveDependency>
					</weaveDependencies>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>compile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>

			<plugin>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.3</version>  
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
	
</project>
```

### CmdLineAccess.java
With that class we'll implement the user access via Command Line.

```java
package org.github.jorge2m.test;

import com.github.jorge2m.testmaker.boundary.access.CmdLineMaker;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;

public class CmdLineAccess {

	//Defines the aplications that can be tested from that project
	public enum Apps { google; }
	
	//Defines the suites of test that can be executed from that project
	public enum Suites { SmokeTest; }
	
	public static void main(String[] args) throws Exception {
		
		//Creates the TestMaker class with the input parameters allowed
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, Apps.class);
		
		//Creates the via Command Line access based in the input parameters
		CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
		
		//Checks the user input parameters
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			
			//Defines the creator of TestSuites based in the user input parameters.
			CreatorSuiteRun creatorSuiteRun = new MyCreatorSuiteRun(inputParams);
			creatorSuiteRun.execTestSuite(false);
		}
	}
}
```

### RestApiAccess.java
If we also want manage the execution of the tests via the a HttpRestAPI we must implement a class like this:

```java
package org.github.jorge2m.test;

import org.github.jorge2m.test.CmdLineAccess.Apps;
import org.github.jorge2m.test.CmdLineAccess.Suites;

import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine;
import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine.ResultCmdServer;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.restcontroller.ServerRestTM;

public class RestApiAccess {

	public static void main(String[] args) throws Exception {
		
		//Parse and check of the input parameters for the Server-Rest-Api Start 
		ResultCmdServer result = ServerCmdLine.parse(args);
		if (result!=null && result.isOk()) {
			
			//Defines the creator of TestSuites
			CreatorSuiteRun creatorSuiteRun = new MyCreatorSuiteRun();
			
			//Start the server that exposes a Rest Api for the management of the tests (execution, consult, stop...)
			ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, Apps.class)
				.setWithParams(result)
				.build();
			serverRest.start();
		}
	}
}
```

### MyCreatorSuiteRun.java
That class invoqued previously from CmdLineAccess and RestApiAccess.java must extend from CreatorSuiteRun and only has to override the metod getSuiteMaker() that returns a TestSuite in function of the 'suite' parameter introduced by the user:
```java
package org.github.jorge2m.test;

import java.util.Arrays;
import org.github.jorge2m.test.CmdLineAccess.Suites;
import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.SuiteMaker;

public class MyCreatorSuiteRun extends CreatorSuiteRun {

	//from CmdLineAccess
	public MyCreatorSuiteRun(InputParamsBasic inputParams) throws Exception {
		super(inputParams);
	}
	
	//from RestApiAccess
	public MyCreatorSuiteRun() throws Exception {
		super();
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		switch ((Suites)inputParams.getSuite()) {
		case SmokeTest:
			return (new SuiteSmokeTest(inputParams)); 
		default:
			//That shouldn't happen because the access via CommandLine/RestApi validates 
			//that the parameter 'suite' is a value of the Suites enum.
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
			return null;
		}
	}
}
```

### SuiteSmokeTest.java
Class that must extend from SuiteMaker and that creates a specific TestSuite.
```java
package org.github.jorge2m.test;

import java.util.Arrays;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class SuiteSmokeTest extends SuiteMaker {

	//Creation of the TestRun indicating the name of the suite and the classes with the @Test's to execute.
	public SuiteSmokeTest(InputParamsTM iParams) {
		super(iParams);
		TestRunMaker testRun = TestRunMaker.from(
				iParams.getSuiteName(), 
				Arrays.asList(TestsGoogle.class));
		
		//Assignate the TestRun to the suite
		addTestRun(testRun);
	}
}
```

### TestsGoogle.java
Finally in that class we implement the @Test that must be executed structured in @Step's and @Validation's. In that example there is only a @Test but there may be as many as necessary an can be filtered in the moment of the execution with the user-parameter 'tests'.
```java
package org.github.jorge2m.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class TestsGoogle {
	
	@Test (
		groups={"Canal:desktop_App:google"},
		description="Type \"Hello World!\" and Check the result")
	public void BUS001_CheckGoogleMoreResults() {
		//Get the WebDriver associated to the @Test with a initial URL defined in the 'url' user-parameter
		WebDriver driver = TestCaseTM.getDriverTestCase();
		
		//Execution of a Step/Validation
		searchInGoogle("Hello World!", driver);
	}
	
	@Step (
		description="Input the text <b>#{textToSearch}</b> and click button \"Search with Google\"",
		expected="At leas one entry with the text #{textToSearch} appears",
		saveImagePage=SaveWhen.Always,
		saveHtmlPage=SaveWhen.Always)
	public void searchInGoogle(String textToSearch, WebDriver driver) {
		//Input Text to Search
		By byInputSearch = By.xpath("//input[@name='q']");
		driver.findElement(byInputSearch).sendKeys(textToSearch);
		
		//Click Search Button
		By buttonSearchBy = By.xpath("//input[@class='gNO89b']");
		click(buttonSearchBy, driver).exec();
		checkTextSearched(textToSearch, driver);
	}
	
	@Validation (
		description="Appears at least an entry that contains the text #{textSearched}",
		level=State.Defect)
	public boolean checkTextSearched(String textSearched, WebDriver driver) {
		By entryWithTextBy = By.xpath("//h3[text()[contains(.,'" + textSearched + "')]]");
		return state(Visible, entryWithTextBy, driver).check();
	}
}
```


## Execution
Execute a "maven clean compile" and then you are ready for execute the automatic test.

### Execution via Command Line.
run the main class **CmdLineAccess**. Yo can use different parameters for configure execution, here I leave several possibilities:

+ Execution TestCase BUS001 in desktop mode against Chrome

> `-suite SmokeTest -browser chrome -channel desktop -application google -tcases BUS001 -url https://www.google.com`

+ 4 times execution TestCase BUS001 in desktop mode against Chrome paralellizing 2 browsers/testcases

> `-suite SmokeTest -browser chrome -channel desktop -application google -tcases BUS001{4-2} -url https://www.google.com`

+ Idem to previous execution but against Firefox and reusing the browsers then increasing speed of execution

> `-suite SmokeTest -browser firefox -channel desktop -application google -tcases BUS001{4-2} -reciclewd true -url https://www.google.com`


Then a *ReportTSuite.html* with the results of the execution will appear in the directory *output-library/SmokeTest/[timestamp]*
