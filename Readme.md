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
1. Optionally condenses output to print only assert-less tests 

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

To see the plugin in action, `cd .\client-gradle` 
and run `gradle cleanTest test` . You should see the summary table:
 
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

I: add the `java-lint-plugin` dependency to the `buildscript` section: 
```
buildscript {
   dependencies {
        classpath 'org.lint:plugin:0.2.1-SNAPSHOT'
    }
}
```
II: Add the plugin: `apply plugin: org.lint.azzert.LintTestsPlugin`

III: Configure lint:
```
test{
   ...   
    lintAssert{
        packageName = "sample" //optional or scan all
    }
}
```
IV: run `gradle clean test`

#### Maven:
In your `pom.xml` 

I: Include the plugin in the build plugins and optionally overwrite default values in the <configuration/> section
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
                <groupId>com.github.jpmorganchase.lint-assert</groupId>
                <artifactId>lint-assert-maven-plugin</artifactId>
                <version>0.2.1-SNAPSHOT</version>
                <configuration>
                    <!-- optional or scan all -->
                    <packageName>sample</packageName>
                </configuration>
            </plugin>
            ...
        </plugins>
        ...
    </build>
```
#### Available configuration options:
|  Option | Required? | Default value  | Values | Purpose |
| -------------: |:-------------:| :-------------:|  :-------------:| :-------------:|  
| includeClasspathJars | No| false | true, false | If true, scans classpath dependencies for test classes|
| verbose    | No |  false  | true, false | If true, produced a lot of output before it prints the summary table|
| packageName| No |   | tests package name (for ex. org.lint in Maven or "org.lint" in Gradle)| A root package to start scanning for test classes. If not specified, scans all packages. |
| printMode  | No | ASSERTLESS_ONLY | Maven: {ALL, ASSERTLESS_ONLY}, Gradle: {"ALL", "ASSERTLESS_ONLY"} | Print ALL avalible test methods or ASSERTLESS_ONLY | 

## Future features: 
 1. Exclude tests that throw expected exceptions 
 1. Display results in alphabetic order of fully qualified test class name - `org.lint.PlaceholderTest` - or order by number of asserts  
 1. Print the linting summary: number of PASS/FAIL and a list of assertless tests
 1. Support 3 output modes info, warn, and error:
    * in _warn_ mode, warn if linting found assertless tests
    * in _error_ mode, fail the 'test' phase if linting found assertless tests
 1. Allow users to specify additional test frameworks
 1. When running in an IntelliJ console, make package.class.method "clickable" and navigate to the method declaration
 1. Display a ratio of # of asserts to the size of the "method under test" and number of its conditions
 1. Lint for assertness in nested test classes
 1. Lint for assertness in nested methods (testing utility methods for ex.) 

## License

The Apache 2.0 License). Please see [License](https://choosealicense.com/licenses/apache-2.0/) for more information.

