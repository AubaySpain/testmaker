
# TestMaker
[![Maven](https://img.shields.io/maven-central/v/com.github.jorge2m/testmaker.svg?label=Maven%20Central)](https://search.maven.org/#search|ga|1|com.github.jorge2m.testmaker) [![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://github.com/Jorge2M/testmaker/blob/master/LICENSE)

A Java artifact that serves as a framework for the development of e2e tests based on WebDriver allowing its *structuring* in TestsCases / Steps / Validations and automatically providing additional functionalities such as *evidence capture*, generation of *detailed reports*, exposition of execution from *command line* and *API REST*, *parallelization*, *distributed execution* in many machines and more.

![](/images_doc/MinimumSchema.png?raw=true)


## Structure your tests

Easily structure your Test Cases with the **@Test**, **@Step** and **@Validation** annotations. 
```java
@Test (
	groups={"Canal:desktop_App:google"},
	description="Type \"Hello World!\" and Check the result")
public void BUS001_GoogleSearchHelloWorld() {
	WebDriver driver = TestCaseTM.getDriverTestCase();
	searchInGoogle("Hello World!", driver);
}
	
@Step (
	description="Input the text #{textToSearch} and click button Search with Google",
	expected="At leas one entry with the text #{textToSearch} appears")
public void searchInGoogle(String textToSearch, WebDriver driver) {
	//Input Text to Search
	By byInputSearch = By.xpath("//input[@name='q']");
	driver.findElement(byInputSearch).sendKeys(textToSearch);
		
	//Click Search Button
	By buttonSearchBy = By.xpath("//input[@class='gNO89b']");
	click(buttonSearchBy, driver).exec();
	checkSearch(textToSearch, 2, driver);
}

@Validation (
	description="Appears at least an entry that contains the text #{textSearched}",
	level=State.Defect)
public boolean checkSearch(String textSearched, int maxWait, WebDriver driver) {
	By entryWithTextBy = By.xpath("//h3[text()[contains(.,'" + textSearched + "')]]");
	return state(Visible, entryWithTextBy, driver).wait(maxWait).check();
}
```

## Capture evidences

Automatic capture page hardcopy when a problem is detected or configure each @Step for capture each type of evidence (Image, Html and Nettraffic) as needed.
```java
@Step (
	description="Input the text #{textToSearch} and click button \"Search with Google\"",
	expected="At leas one entry with the text #{textToSearch} appears",
	saveImagePage=SaveWhen.Always,
	saveHtmlPage=SaveWhen.IfProblem,
	saveNettraffic=SaveWhen.Always)
public void searchInGoogle(String textToSearch, WebDriver driver) {
```



## Get detailed report about the execution

Report that shows in detail all the Steps and Validations executed in each TestCase, including links to each Step-Evidence and classifying the result in many levels: `NOK`, `Defect`, `Warn`, `Info` and `OK`.
![](/images_doc/ResultExample.png?raw=true)

## Expose execution from Command Line

Allows you to launch a new Test Suite execution from Command Line and the use of parameters for configure that execution: select the *suite* to execute, *driver* to use, *channel*, list of *testcases*, initial *url* and many more.

`-suite SmokeTest -driver chrome -channel desktop -application google -tcases BUS002,BUS001{4-2} -url https://www.google.com`

**Note** in that case the content of the `tcases` parameter indicates the execution of the two TestCases identificated by codes `BUS002` and `BUS001` but it will execute the `BUS001` 4 times parallelizing it in 2 diferent chrome browsers.   

## Expose API REST for manage Test Suite execution

Manage the execution of the Test Suites using the many services exposed by the TestMaker API REST that includes:

+ Execution of new parametrized Test Suite.
+ Consult Test Suite executed
+ Get Report HTML of Test Suite executed
+ Stop Test Suite in execution
+ Add/Remove TestMaker slaves installed in other machines

... and more.


## Parallelize

Parallelize the execution of your TestCases using the parameter `threads`for indicate the max number of tests in parallel. 

TestMaker will launch the tests on the machine it is running on, if the resources of that machine at the CPU/memory level are not enough for support that number of drivers/browser in parallel, there is the possibility of easily connecting other machines as where TestMaker is also running as a slaves.


## Run it distributed in many machines

After built your proyect based on TestMaker you'll have the possibility to start it as a Server with that we will expose an API REST from wich you can execute your TestSuites or connect Server-TestMaker-slaves.

For example, if you have your project based on TestMaker packaged in `myTestsUnderTM.jar` and distributed in three diferent machines,  you can start your project as a server-TestMaker and connect those of the machines 2 and 3 to the server of the machine 1 as follows.

`machine-1 > java -jar myTestsUnderTM.jar -port 80 `

`machine-2 > java -jar myTestsUnderTM.jar -port 80 -urlhub http://ip-machine-1:80  `

`machine-3 > java -jar myTestsUnderTM.jar -port 80 -urlhub http://ip-machine-1:80  `

Then, you can use the API REST exposed in the machine-1 for execute a TestSuite against this server-1 that will automatically take the rol of hub and distribute remotely each of the TestCases in the TestMaker-Servers running in the machines 2 and 3. When the TestSuite Run finishes all the results will be ubicated in the machine-1 as if all tests had been run on that machine.

![](/images_doc/RemoteDistribution.png?raw=true)

## And more...

### Reuse of WebDriver / Browsers

By default *TestMaker* creates a new *WebDriver* for each TestCase, this means that the WebDriver start/stop time is added to the total test time, in the case of Firefox or Chrome this time is usually between 4 or 5 seconds. Further, starting a browser consumes very CPU with what affects performance of the machine that hosts TestMaker and can reduce the maximum number of parallel TestCases that can be run on that machine.

TestMaker has an execution mode in wich it stores the WebDriver/Browsers already started for reuse them in the new TestCases that are created, this increases the speed of the tests and improves the performance of the host machine. 

To activate this mode, simply add the parameter `-reciclewd true` to the execution of a new TestSuite.

### Integration with BrowseStack

You can launch your automated TestCases implemented under TestMaker against your account of **BrowserStack** simply indicating as  `driver` parameter the value `browserstack` and including the rest of parameters related to BrowserStack either for desktop or mobile. 

| Parameter              | Desktop | Mobile | Description                                                  | Example  Value Desktop | Example Value Mobile   |
| ---------------------- | :-----: | :----: | :----------------------------------------------------------- | ---------------------- | ---------------------- |
| bStack_user            |    X    |   X    | User of your BrowserStack Automation service                 |                        |                        |
| bStack_password        |    X    |   X    | Password of your BrowserStack Automation service             |                        |                        |
| bStack_os              |    X    |   X    | Operating System of the machine where to launch the tests    | Windows                | android                |
| bStack_os_version      |    X    |   X    | Operating System version                                     | 8                      | 9.0                    |
| bStack_browser         |    X    |   X    | Browser where to launch the tests                            | Firefox                | Chrome                 |
| bStack_browser_version |    X    |   -    | Browser version                                              | 68.0                   |                        |
| bStack_resolution      |    X    |   -    | Resolution of the screen                                     | 1920x1080              |                        |
| bStack_device          |    -    |   X    | Name of the mobile device                                    |                        | Samsung Galaxy S9 Plus |
| bStack_realMobile      |    -    |   X    | Flag that indicates if you want to use a real device or a emulation |                        | true                   |

TestMaker will connect with BrowserStack and run the TestCases against it. So in the resultant report of TestMaker will appear a link to the build in BrowserStack Automate.

![](/images_doc/TmToBrowserStack.png?raw=true)

### Google Test A/Bs management

Tests A/Bs that randomize the aspect of your GUI in the search for the variant most suitable for real users can be a real headcache for an automatic TestCase that uses that GUI. Imagine that you have a Test AB that randomly shows 3 visual variants of a HTML Page with a 33% probability for each one, then an Automatic Test than only supports one variant will fail 66% of the times it runs.

One solution is to modify the Test to indentify and support all three variants of the page. Sometimes this may be feasible but other times the costs of implementing and maintaining these adaptations can be really high.

TestMaker brings a solution for those Test AB's backed by **Google Experiments** and **Google Optimize** giving the option of establish a certain variant of each Test AB in each execution of an Automatic Test. For this you only need to create an object with the TestAB data and pass it to TestMaker along with the variant that you want to active.



# Getting Started from Zero

Lets go build a new Maven Project 1.8+ based on TestMaker that will:
- Expose access via Command Line and HTTP Rest API. Then we will can use many available parameters to customize the test suite execution.
- Expose a TestSuite with one test that checks the "Hello World" input in Google.

**Note**. this example is available cloning the git repository  https://github.com/Jorge2M/testmaker.git, the project in question named '*hello-world-example*' Is located in the folder */examples*. Further, inside that project you can view a example of a execution-result browsing the file */output-library/SmokeTest/200419_123930335/ReportTSuite.html*  


## Components to build
That project will include a pom.xml in addition to a few Java Classes, for simplicity I include all that clases in a same package.

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
	<artifactId>getstarted</artifactId>
	<groupId>org.testmaker</groupId>
	<version>0.0.1</version>
	<packaging>jar</packaging>
	<name>examples</name>
	<url>http://maven.apache.org</url>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>
		
	<dependencies>
		<dependency>
			<groupId>com.github.jorge2m</groupId>
			<artifactId>testmaker-core</artifactId>
			<version>1.3.16</version>
		</dependency>
	</dependencies>
	
	<build>
		<plugins>
			<plugin>
				<groupId>com.github.m50d</groupId>
				<artifactId>aspectj-maven-plugin</artifactId>
				<version>1.11.1</version>
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
That class implements the user access via Command Line.

```java
package org.testmaker.getstarted;

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
package org.testmaker.getstarted;

import org.testmaker.getstarted.CmdLineAccess.Apps;
import org.testmaker.getstarted.CmdLineAccess.Suites;

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
package org.testmaker.getstarted;

import java.util.Arrays;
import org.testmaker.getstarted.CmdLineAccess.Suites;
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
package org.testmaker.getstarted;

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
**Tip** by default 3 is the number ot tests in parallell that TestMaker will execute for each suite but yo can modify it in two ways.
1. In the present class using the method setThreadCount(int threadCount) of the parent class
2. Using the user parameter 'threads', for example -threads 5 for the access via command line. This method takes precedence over point one.

### TestsGoogle.java
Finally in that class we implement the @Test that must be executed structured in @Step's and @Validation's. In that example there is only a @Test but there may be as many as necessary an can be filtered in the moment of the execution with the user-parameter 'tests'.
```java
package org.testmaker.getstarted;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
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
**Note** the label "*Canal:desktop_App:google*" in the attribute '*groups*' of the *@Test* is used for label the channel and application to witch belongs the test. That is useful to be able of filter the tests to execute via the user parameters '*channel*' and '*application*' respectively. If you want that a @Test not to be filtered, you must use a label of type "*Canal:all_App:all*"

**Tip.** In the text associated to the attribute '*description*' of the *@Step* and *@Validation* annotations you can print any parameter from the method associated throught tye syntax #{nameParameter}. Yo also can get a innermost value using #{nameParameter.getX()} or #{nameParameter.getX().getY()}

**Note.** By default TestMaker gets a Hardcopy of the browser-page associated with a @Step only when a problem happens (tipically a @Validation fails). In the example we use the method saveImagePage=SaveWhen.Always to force the capture of the page in all cases, including the Ok one.


## Execution
Build the project via a "*mvn clean package*" and then you are ready for execute the automatic test.

### Execution via Command Line.
Run the class **CmdLineAccess**. Yo can use different parameters for configure execution, here I leave several possibilities:

+ Execution TestCase BUS001 in desktop mode against Chrome

> `-suite SmokeTest -driver chrome -channel desktop -application google -tcases BUS001 -url https://www.google.com`

+ 4 times execution TestCase BUS001 in desktop mode against Chrome paralellizing 2 browsers/testcases

> `-suite SmokeTest -driver chrome -channel desktop -application google -tcases BUS001{4-2} -url https://www.google.com`

+ Idem to previous execution but against Firefox and reusing the browsers then increasing speed of execution

> `-suite SmokeTest -driver firefox -channel desktop -application google -tcases BUS001{4-2} -reciclewd true -url https://www.google.com`

Then a *ReportTSuite.html* with the results of the execution will appear in the directory  */output-library/SmokeTest/idTestTimestamp* inside the project.

**Note** TestMaker uses default versions for the webdriver of Chrome (*ChromeDriver*) and Firefox (*GeckoDriver*). But perhaps that versions of the drivers doesn't support the version of chrome/firefox installed in your machine so the execution will fail. In this case you will have to locate the correct version number of the driver (you don't have to download anyting, only get the version number) in the oficial pages for *ChromeDriver* (https://sites.google.com/a/chromium.org/chromedriver/downloads) and *GeckoDriver* (https://github.com/mozilla/geckodriver/releases)  and enter it as a new parameter '*driverVersion*'. For example:

> -suite SmokeTest -driver chrome -driverVersion 85.0.4183.83 -channel desktop -application google -tcases BUS001 -url https://www.google.com
> -suite SmokeTest -driver firefox -driverVersion 0.27.0 -channel desktop -application google -tcases BUS001 -url https://www.google.com

### Execution via Http Rest API
Run the class **RestApiAccess** with the parameter -port 80 (if you what to expose a secure port use the parameter -secureport). If the server starts correctly you'll see the message:
> Started Jetty Server!
> HttpPort: 80

Then you must invoke the POST resource '*suiterun*' with body parameters similar to those defined in the access via command line, only with the diference that you must add the parameter store=true for persist the resource asociated to the suiterun. For example, call to http://localhost:80/suiterun/ using the body parameters:

| parameter                    | value            |
|:-----------------------------|:----------------------------|
| suite                  | SmokeTest |
| driver | chrome |
| channel | desktop |
| application    | google |
| tcases          | BUS001{4-2} |
| url                | https://www.google.com |
| store | true |
(Adding the parameter *driverVersion* if necessary)

The execution will return a response that includes an attribute '*idExecSuite*' with the identificator of the test in timestamp format. You can find the HTML result in the same directory that in the case of the execution via command line or you can visualize it calling to the resource of the API Rest: http://localhost:80/suiterun/{{idExecSuite}}/report (for example, http://localhost:80/suiterun/200418_192217511/report). If you run that URL form a browser you'll obtain the HTML report.

### Result Report
In both executions we will obtain a HTML report with the same testcase BUS001 repeated 4 times. In each testcase there will be the step/validation executed together with links to the capture of the page and their HTML. Here you can see an example of an execution:
![](/images_doc/ResultHelloWorld.png?raw=true)



# Getting Started from Archetype

There is an archetype **testmaker-archetype** published in Maven Central that  can be used for create a base maven-project and its equivalent **testmaker-archetype-pageobject** for create the same project where the test-script described in the TestGoogle.java class uses a PageObject Pattern.

When you'll create a project with any of these archetypes you'll have a project with java classes similar to those described in the previous version unlike that they are located in the corresponding packages:

​	.access.**CmdLineAccess.java**

​	.access.**RestApiAccess.java**

​	.access.**MyCreatorSuiteRun.java**

​	.test.suite.**SuiteSmokeTest.java**

​	.test.script.**TestsGoogle.java**



Then you can execute the tests and analize the results in the same manner described in the  "Getting Started from Zero" (from "Execution" sub-section). Note that the test **BUS001** testcase in that case is a little more complex and instead of searching for the "Hello World" literal it performs the following actions:

+ Searches "Mario Maker" in Google and stores the number of results
+ Searches "Mario Maker" in Bing and stores the number of results
+ Checks that the search in google gets more results that in Bing.



### Expand from base project

Creating a project from a testmaker archetype you'll have an operational project. The modifications to adapt it to the specific needs of each project are described below:

+ **Implement new TestCases**: 

  + Rename and modify the *TestsGoogle.java* (or create a new specific java-class) and add your news @Test/s scripts making use of the @Step and @Validation annotations. 
  + Modify the *SuiteSmokeTest class* adding the new java-class to the list of java-classes that contains all the @Tests of the Suite.

+  **Implement a new TestSuite**

  + Implement a new class analogous to the SuiteSmokeTest and include the list of java-classes that contains the @Tests of the Suite.
  + Modify the enum *Suites* adding a new entry for the new TestSuite.
  + Open the *MyCreatorSuiteRun* class and modify the *getSuiteMaker()* method adding the new TestSuite in the switch.

+ **Modify the chromedriver/geckodriver version** (for support new versions of Chrome or Firefox). They are 2 possibilities:

  + Invoque the TestSuite via Command Line/API Rest indicating the version of the driver in the parameter *driverVersion*. For example *driverVersion=85.0.4183.83* correspond to a ChromeDriver that supports Chrome83.

  + Modify the *MyCreatorSuiteRun* class where they are the default versions of each webdriver:

    ```java
    public class MyCreatorSuiteRun extends CreatorSuiteRun {
    	private final String ChromeDriverVersionDefault = "85.0.4183.83";
    	private final String GeckoDriverVersionDefault = "0.27.0";
    ```

* **Configure specific user WebDrivers**. TestMaker makes available 2 embebbded WebDrivers configured for manage Chrome and Firefox respectively. But you have the possibility of override any of them or add new ones creating classes that extenns the abstract class *DriverMaker* and passing them as a parameter-list to the *TestRunaker.setDriverMaker* method. For example, lets go to create:

  * A new Chrome-WebDriver wich will be used when *driver=michrome*
  * A new Firefox-WebDriver wich will be used when  *driver=mifirefox*
  * Both WebDriver associated to the TestSuite *SmokeTest*

  Then you'll have to modify the class *SuiteSmokeTest* as follows:
  
  ```java
  public class SuiteSmokeTest extends SuiteMaker {
      
      public SuiteSmokeTest(InputParamsTM iParams) {
          super(iParams);
          setParameters(new HashMap<>());
          TestRunMaker testRun = TestRunMaker.from(
                  iParams.getSuiteName(), 
                  Arrays.asList(TestsGoogle.class, SearchFactory.class));
          testRun.setDriverMaker(iParams.getDriver(), makeListDrivers()); 
          addTestRun(testRun);
          setParallelMode(ParallelMode.METHODS);
          setThreadCount(3);
      }
  
      private static List<DriverMaker> makeListDrivers() {
          return Arrays.asList(
              new ChromeDriverMaker(),
              new FirefoxDriverMaker()
          );
      }
      private static class ChromeDriverMaker extends DriverMaker {
          public String getTypeDriver() {
              return "michrome";
          }
          public void setupDriverVersion(String driverVersion) {
              ChromeDriverManager.chromedriver().version(driverVersion).setup();
          }
          public WebDriver build() {
              return new ChromeDriver();
          }
      }
      private static class FirefoxDriverMaker extends DriverMaker {
          public String getTypeDriver() {
              return "mifirefox";
          }
          public void setupDriverVersion(String driverVersion) {
              FirefoxDriverManager.firefoxdriver().version(driverVersion).setup();
          }
          public WebDriver build() {
              return new FirefoxDriver();
          }
      }
  }
  ```



### Dockerization

The project created from the testmaker-archetype incorporates a *Dockerfile* that you can use for expose the ApiREST of the project. The Dockerfile needs the packaged project in the target directory. So these are the necessary steps to build and run the project in docker (assuming a tag image of "jorge2m/tmarchetype:latest"):

1. package the project: *mvn clean package*
2. build the docker image: *docker build -t jorge2m/tmarchetype:latest .*
3. execute the docker: *docker run -d -p 80:80 -p 443:443 --privileged -v "%CD%/output-library:/output-library" jorge2m/tmarchetype:latest*

Then you'll have the project exposed to the ports 80/443 and executable in the same manner described in the previous section "Execution via Http Rest API". You'll obtain the report results in the output-library directory of your host.



### TestCase Factories

Via the @Factory annotation TestMaker brings the possibility of create many @Test parametrized. For example, for the previous @Test BUS001, that search for "Mario Maker" in Google and Bing, could be useful execute the same @Test but varying the text to search. The project generated by the testmaker-artifact contains a class *test.factory.**SearchFactory*** that implements a test-factory of this type:

```java
public class SearchFactory {

	private final List<String> searchValues;
	
	public SearchFactory() {
		searchValues = Arrays.asList( 
			"Zelda Breath of The Wild",
			"Enter the gungeon",
			"Xenoblade Chronicles",
			"Undertale",
			"Salamander Game",
			"XCOM Enemy Unknown",
			"Super Mario 64",
			"Zelda ocarina of time",
			"Baldur's Gate 2",
			"Head over Hells Game");
	}
	
	@Factory
	public Object[] FAC001_createTests() {
		List<Object> listTests = new ArrayList<>();
		for (String searchValue : searchValues) {
			listTests.add(new TestsGoogle(searchValue));
		}
		return listTests.toArray(new Object[listTests.size()]);
	}
}
```
That class is included in the list of classes defined in the *SuiteSmokeTest*, then you can execute it including the code FAC001 in the list of testcases (*tcases* param), for example with the parameters:

`-suite SmokeTest -driver chrome -channel desktop -application google -tcases FAC001 -url https://www.google.com`

And after the execution of the tests you'll obtain a report with 10 BUS001 TestCases, one for each of literal defined in the @Factory.![](/images_doc/FactoryReport.png?raw=true)



# Invocation Reference Guide

## Command Line
A project built under TestMaker can be called from Command Line for run a new TestSuite invoking the corresponding main class that implements that access. You can see a description for the list of parameters using the parameter `-help`. The list with all the parameters is as follows (to wich should be added the specific ones for BrowserStack described in the previous section "**Integration with BrowserStack**") 



| Parameter     | Required | Function_Description                                         | Values                                                       | Example                                          |
| ------------- | -------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------ |
| suite         | yes      | Name of the TestSuite to execuite                            | The defined by each project in the corresponding enum        | SmokeTest                                        |
| driver        | yes      | Name of the driver                                           | chrome, chromehless, firefox, firefoxhless, chromedriver [^1] | chrome                                           |
| channel       | yes      | Channel where the tests will be run                          | desktop or mobile                                            | desktop                                          |
| application   | yes      | Aplication to wich the tests refer                           | The defined by each project in the corresponding enum        | google                                           |
| url           | no       | Initial url that will be loaded in the webdriver             | Any URL with correct format                                  | http://www.google.com                            |
| tcases        | no       | Filter with the list of test cases that must be executed     | A comma-separated list with the name or code of the metod marked with @Test [^2] | BUS001, FIC002                                   |
| groups        | no       | Filter with the groups of tests that must be executed        | A comma-separated list with the name of the groups existent in the atribute 'groups' of the @Test | Buscador, Ficha                                  |
| version       | no       | Value to label the execution[^3]                             | Any identifier                                               | test_payment                                     |
| threads       | no       | Number of threads for parallelize TestCases                  | A integer number (3 by default)                              | 5                                                |
| reciclewd     | no       | Flag to reuse the drivers of TestCases already finished (false by default) | true, false (by default)                                     | true                                             |
| asyncexec     | no       | Flag to indicate if you don't want wait for the completion of execution | true, false (by default)                                     | true                                             |
| net           | no       | Flag for capture the Http Nettraffic for each @Step          | true, false (by default)                                     | true                                             |
| storebd       | no       | Level of item store in bd[^ 4]                               | nostore, suite, testrun, testcase (by default), step, validation | validation                                       |
| mails         | no       | List of emails to wich you want to send a email with the Report of the TestSuite execution | Comma-separated list of emails                               | jorge.and.2m@gmail.com,jorge.munoz.sge@mango.com |
| driverVersion | no       | The version of the driver [^ 5]                              | Driver version of (ChromeDriver, GeckoDriver, etc.)          | 85.0.4183.83                                     |

[^1]: TestMaker goes with that  embedded drivers out of the box, but from each project yo can add new webdrivers or overwrite the embedded ones

[^2]: In each TestCase identifier you can add {number} that specifies the times that TestCase will be executed. Further, you can add a second number that identifies the paralellization for the execution of these TestCases, for example, a valid value could be: "BOR001,FIC002{5-2}"

[^3]: For user use. In many occasions it may be useful for the script to work differently based on the value of this parameter.

[^ 4]: There are some services of the TestMaker API Rest who depends on this previous storage, for example the consult of a TestSuite execution. 

[^5]: TestMaker uses a default version for each driver that you can override with this parameter 

## API REST

The execution of a new TestCase Run is an operation also exposed in the TestMaker REST API. But in this type of access yo also have requests for manage the TestCase Run executions (detail, list, stop, purge, etc.) and for connect/disconnect servers and create the infraestructure for execute tests in a distributed way.

[The complete TestMaker API Documentation](https://documenter.getpostman.com/view/3252940/Szf9XTDg?version=latest)

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/afeff74f2d3b29fa9729)