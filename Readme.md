<h1 align="center">Pluggable linting utility for Java tests</h1>


[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://choosealicense.com/licenses/apache-2.0/)
[![Build Status](https://travis-ci.org/jpmorganchase/java-lint-assert.svg?branch=master)](https://travis-ci.org/jpmorganchase/java-lint-assert)

The Java _Gradle_ and _Maven_ plugin for the `test` task that reports presence of `assert`s in a test method body. 

## Features:
1. Prints the number of assert calls in each test method to the console
1. Excludes ignored/disabled test _classes_ and _methods_ from linting 
1. Limits the lint to recursively search from a top level package (for ex. 'org.samples')
1. Allows verbose output
1. Optionally loads test classes from the classpath

## Supported Testing Frameworks:
- JUnit 4
- JUnit 5
- TestNG

## Supported build types:
- Gradle 4.9 and above
- Maven 3.5 and above

## Installation:
1. git clone [https://github.com/jpmorganchase/java-lint-assert.git](https://github.com/jpmorganchase/java-lint-assert.git)
#### Gradle:
2. run `gradle clean build publish` from the root of the project. This will
* build core module
* build plugin-gradle module
* build samples module
* install core and plugin-gradle jars into your local maven repo and into `build` dir under the project root 

To see the plugin in action, 
for Gradle `cd .\client-gradle` and run `gradle cleanTest test` . 
for Maven  `cd .\client-maven` and run `mvn lint-assert:lint-assert` . 

You should see the summary table:
 
| Package  | Test file name | Test method name  | # asserts  |
| :-------------: |:-------------:| :-------------:|  :-------------:|  
|    sample/junit4    |    AssertJunit4Style.java    |     withoutAsserts     |        0        |
|    sample/junit5    |    AssertJunit5Style.java    |      withAsserts       |        2        |
|    sample/testng    |       TestNgStyle.java       |      withAsserts       |        1        |
|    sample/junit4    |    AssertJunit4Style.java    |      withAsserts       |        1        |
|    sample/testng    |       TestNgStyle.java       |     withoutAsserts     |        0        |
|       sample        |        DummyTest.java        |         dummy          |        0        |
|    sample/junit5    |    AssertJunit5Style.java    |     withoutAsserts     |        0        |
 
#### Maven: 
2. run `mvn clean install` from the root of the project. This will
* build core module
* build plugin-maven module
* build samples module
* install core jar into your local maven repo 

## Use:

#### Gradle:

In your `build.gradle` 

I: add the dependency to the `buildscript` section: 
```
buildscript {
   dependencies {
        classpath 'org.lint.assert:plugin:0.1.0-SNAPSHOT'
    }
}
```
II: Add the plugin: `apply plugin: org.lint.azzert.LintTestsPlugin`

III: Configure lint:
```
test{
   ...   
    lintAssert{
        packageName = "org.lint" //optional or scan all
        verbose = true //optional, defaults to false
        includeClasspathJars = true //optional, defaults to false, scan alls jars found on the classpath
    }
}
```
IV: run `gradle clean test`

#### Maven:
In your `pom.xml` 

I: Add the dependency to the `plugins` section in the `build`: 
```
  <build>
  ...
        <plugins>
            ...
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-plugin-plugin</artifactId>
                <version>${maven-mojo-plugin.version}</version>
            </plugin>
            <plugin>
                <groupId>org.lint.assert</groupId>
                <artifactId>lint-assert-maven-plugin</artifactId>
                <version>0.1.0-SNAPSHOT</version>
            </plugin>
            ...
        </plugins>
        ...
    </build>
```
II: optionally overwrite default values in the `<configuration/>` section
```
 <plugin>
    <groupId>org.lint.assert</groupId>
    <artifactId>lint-assert-maven-plugin</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <configuration>
        <!-- optional, defaults to false -->
        <includeClasspathJars>false</includeClasspathJars>
         <!-- optional, defaults to false -->
        <verbose>true</verbose>
         <!-- optional or scan all -->
        <packageName>org.lint</packageName>
    </configuration>
  </plugin>
```
IV: run `mvn lint-assert:lint-assert`

## Future features: 
 1. Exclude tests that throw expected exceptions 
 1. Display results in alphabetic order of fully qualified test class name - `org.lint.PlaceholderTest`  
 1. Print the linting summary: number of PASS/FAIL and a list of assertless tests
 1. Support a condensed output mode when only assertless tests are being printed 
 1. Support 3 output modes info, warn, and error:
    * in _warn_ mode, warn if linting found assertless tests
    * in _error_ mode, fail the 'test' phase if linting found assertless tests
 1. Allow users to specify additional test frameworks
 1. When running in an IntelliJ console, make package.class.method "clickable" and navigate to the method declaration
 1. Display a ratio of # of asserts to the size of the "method under test" and number of its conditions
 1. Lint for assertness in nested test classes

## License

The Apache 2.0 License). Please see [License](https://choosealicense.com/licenses/apache-2.0/) for more information.

