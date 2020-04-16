Under construction... 

[![Maven](https://img.shields.io/maven-central/v/com.github.jorge2m/testmaker.svg?label=Maven%20Central)](https://search.maven.org/#search|ga|1|com.github.jorge2m.testmaker)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://github.com/Jorge2M/testmaker/blob/master/LICENSE)


## Basic Example
Lets go build a new Maven Project that will:
- Expose access via Command Line and HTTP Rest API. Then we will can use the many parameters to customize the test suite execution.
- Expose a TestSuite with one test that checks the "Hello World" input in Google.

That project will include a pom.xml plus a few Java Classes, for simplicity I include all that clases in a same package.

### pom.xml
The pom.xml has to include:
- The dependency to the testmaker artifact
- The artifact maven-compiler-plugin with a Java Version >= 1.8
- The artifact TestMaker is based in AspectJ technology, then we need to include the aspectj-maven-plugin for weave the aspects from TestMaker.

A possible pom.xml can be:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.jorge2m</groupId>
  <artifactId>hello-world-example</artifactId>
  <version>0.0.1-SNAPSHOT</version>
	
  <dependencies>
    <dependency>
      <groupId>com.github.jorge2m</groupId>
      <artifactId>testmaker-core</artifactId>
      <version>1.3.2</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.3</version>  
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
      </plugin>
		
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>aspectj-maven-plugin</artifactId>
        <version>1.11</version>
        <configuration>
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
	public enum Apps { google }
	
	//Defines the suites of test that can be executed from that project
	public enum Suites { SmokeTest }
	
	public static void main(String[] args) throws Exception {
		
		//Creates the TestMaker class with the input parameters allowed
		InputParamsBasic inputParams = new InputParamsBasic(Suites.class, Apps.class);
		
		//Creates the via Command Line access based in the input parameters
		CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, inputParams);
		
		//Checks the user input parameters
		if (cmdLineAccess.checkOptionsValue().isOk()) {
			
			//Creates and executes the TestSuite based in the user input parameters.
			CreatorSuiteRun creatorSuiteRun = MySuiteRunCreator.getNew(inputParams);
			creatorSuiteRun.execTestSuite(false);
		}
	}
}
```

### RestApiAccess.java (optional)
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
			CreatorSuiteRun creatorSuiteRun = MySuiteRunCreator.getNew();
			
			//Start the server that exposes a Rest Api for the management of the tests (execution, consult, stop...)
			ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, Apps.class)
				.setWithParams(result)
				.build();
			serverRest.start();
		}
	}
}
```

### MySuiteRunCreator.java
That class invoqued previously from CmdLineAccess and RestApiAccess.java must extend from CreatorSuiteRun and only has to override the metod getSuiteMaker() that returns a TestSuite in function of the 'suite' parameter introduced by the user:
```java

package org.github.jorge2m.test;

import java.util.Arrays;
import java.util.HashMap;

import org.github.jorge2m.test.CmdLineAccess.Suites;
import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.CreatorSuiteRun;
import com.github.jorge2m.testmaker.domain.InputParamsBasic;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;

public class MySuiteRunCreator extends CreatorSuiteRun {

	//from CmdLineAccess
	public MySuiteRunCreator(InputParamsBasic inputParams) throws Exception {
		super(inputParams);
	}
	
	//from RestApiAccess
	public MySuiteRunCreator() throws Exception {
		super();
	}
	
	@Override
	public SuiteMaker getSuiteMaker() throws Exception {
		switch ((Suites)inputParams.getSuite()) {
		case SmokeTest:
			return (new SmokeTestSuite(inputParams)); 
		default:
			System.out.println("Suite Name not valid. Posible values: " + Arrays.asList(Suites.values()));
			return null;
		}
	}
}
``
