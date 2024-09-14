# I1: Setup, Testing and Bug-Fixing

Submission made by **Abhishek Paul (ap4623)**

## Setup

Use the following command based on your OS

**mvn spring-boot:run -Dspring-boot.run.arguments="setup"** -for MacOS

**mvn spring-boot:run -D"spring-boot.run.arguments=setup"** -for Windows

## Tools Used

Given below are the additional tools used in the project along with the command line instructions to execute those.

### Testing and Code Coverage

Jacoco is used for coverage which is added as a plugin in the *pom.xml* file.

``` xml
<groupId>org.jacoco</groupId>
<artifactId>jacoco-maven-plugin</artifactId>
<version>0.8.11</version>
```

Use the following commands to run Jacoco.

```bash
mvn test
```

This runs all the tests and creates the jacoco.exe file.

```bash
mvn jacoco:report
```

The report can be found in an HTML file stored in *IndividualProject/target/site/jacoco/index.html*

### Static Code Analysis

PMD is used for static code analysis which is added as a plugin in the *pom.xml* file. It uses the quickstart.xml ruleset along with the default PMD ruleset.

```xml
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-pmd-plugin</artifactId>
<version>3.23.0</version>
<configuration>
  <rulesets>rulesets/java/quickstart.xml</rulesets>
</configuration>
```

Use the following command to run PMD.

```bash
mvn pmd:pmd
```

This creates the pmd.html file in *IndividualProject/target/site/pmd.html*. Opening this file in a browser will list all the PMD violations.

### Checkstyle Issues

It uses the follwing plugin in *pom.xml* to detect checkstyle issues.

```xml
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-checkstyle-plugin</artifactId>
<version>3.2.0</version>
```

Use the following command to run it.

```bash
mvn checkstyle:check
```

This creates an xml file in *IndividualProject/target/checkstyle-result.xml*. Opening this file will show the violations.
