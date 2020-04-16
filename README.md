Under construction... 

[![Maven](https://img.shields.io/maven-central/v/com.github.jorge2m/testmaker.svg?label=Maven%20Central)](https://search.maven.org/#search|ga|1|com.github.jorge2m.testmaker)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://github.com/Jorge2M/testmaker/blob/master/LICENSE)


## Basic Example
Lets go build a new Maven Project that will:
- Expose access via Command Line and HTTP Rest API.
- Expose a TestSuite with one test that checks the "Hello World" input in Google

That project will include a pom.xml plus 4 Java Classes. 

## pom.xml
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
