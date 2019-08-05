<h1 align="center">Pluggable linting utility for Java tests</h1>


[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://choosealicense.com/licenses/apache-2.0/)

The plugin for Java Gradle `test` task that reports presence of `assert`s in a test method body. 

## Features:
1. Prints the number of assert calls in each test method to the console
1. Excludes test _methods_ annotated with @Ignore (JUnit4) or @Disabled (JUnit5) from linting 
1. Limits the lint to recursively search from a top level package (for ex. 'com.jpmorgan')
1. Allows verbose output


## Supported Testing Frameworks:
- JUnit 4
- JUnit 5

## Supported build types:
- Gradle 4.9 and above

## Installation:
1. git clone [https://github.com/jpmorganchase/java-lint-assert.git](https://github.com/jpmorganchase/java-lint-assert.git)
2. run `gradle clean build publish` from the root of the project. This will
* build core module
* build plugin module
* install core and plugin jars into your local maven repo and into `build` dir under the project root 

To see the plugin in action, `cd .\client` 
and run `gradle cleanTest test -i` . You should see the summary table:
 
| Package  | Test file name | Test method name  | # asserts  |
| :-------------: |:-------------:| :-------------:|  :-------------:|  
| org/lint  | PlaceholderTest.java | dummy | 0 | 
 
 
## Use:


#### Gradle:

In your `build.gradle` 

I: add the `java-lint-plugin` dependency to the `buildscript` section: 
```
buildscript {
   dependencies {
        classpath 'org.lint:plugin:0.1.0-SNAPSHOT'
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
    }
}
```
IV: run your tests with `-i` on: `gradle test -i`


## Future features:
 1. Exclude test _classes_ annotated with @Ignore (JUnit4) and @Disabled (JUnit5) from linting 
 1. Exclude tests annotated with @Expected (JUnit 4) and Assertions.assertThrows (JUnit 5) exception from linting
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

