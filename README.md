# ConReg4J--1.1.0 

ConReg4J is a dataset for Java concurrency regressions, supporting the research on concurrency regression in the community.

# Contents of ConReg4J

## DataBase structure

ConReg4J contains 20  real-world bugs from the following open-source projects:

| Bug index | Identifier                                                   | Project name        |
| --------- | ------------------------------------------------------------ | ------------------- |
| pool1     | [pool-376](https://issues.apache.org/jira/browse/POOL-376)   | apache/commons-pool |
| groovy1   | [groovy-6873](https://issues.apache.org/jira/browse/GROOVY-6873) | apache/groovy       |
| jdk1      | [jdk-8217606](https://bugs.openjdk.java.net/browse/JDK-8217606) | openjdk/jdk         |
| jdk2      | [jdk-8068184](https://bugs.openjdk.java.net/browse/JDK-8068184) | openjdk/jdk         |
| jdk3      | [jdk-8237368](https://bugs.openjdk.java.net/browse/JDK-8237368) | openjdk/jdk         |
| jdk4      | [jdk-8212197](https://bugs.openjdk.java.net/browse/JDK-8212197) | openjdk/jdk         |
| jdk5      | [jdk-8212795](https://bugs.openjdk.java.net/browse/JDK-8212795) | openjdk/jdk         |
| jdk9      | [jdk-8217364](https://bugs.openjdk.java.net/browse/JDK-8217364) | openjdk/jdk         |
| jdk10     | [jdk-8206965](https://bugs.openjdk.java.net/browse/JDK-8206965) | openjdk/jdk         |
| jdk17     | [jdk-8059269](https://bugs.openjdk.java.net/browse/JDK-8059269) | openjdk/jdk         |
| jdk19     | [jdk-8048020](https://bugs.openjdk.java.net/browse/JDK-8048020) | openjdk/jdk         |
| derby2    | [derby-3887](https://issues.apache.org/jira/browse/DERBY-3887) | apache/derby        |
| derby3    | [derby-6880](https://issues.apache.org/jira/browse/DERBY-6880) | apache/derby        |
| derby5    | [derby-6692](https://issues.apache.org/jira/browse/DERBY-6692) | apache/derby        |
| derby8    | [derby-4910](https://issues.apache.org/jira/browse/DERBY-4910) | apache/derby        |
| derby11   | [derby-4671](https://issues.apache.org/jira/browse/DERBY-4671) | apache/derby        |
| derby1    | [derby-4193](https://issues.apache.org/jira/browse/DERBY-4193) | apache/derby        |
| lucene1   | [lucene-621](https://issues.apache.org/jira/browse/LUCENE-621) | apache/lucene-solr  |
| dbcp1     | [dbcp-415](https://issues.apache.org/jira/browse/DBCP-415)   | apache/commons-dbcp |
| flink1    | [flink-17514](https://issues.apache.org/jira/browse/FLINK-17514) | apache/flink        |

## The bugs

Each bug has the following properties:

- Hold three versions (working, regression, fixed), and test cases can lead them to behave as (pass, failure, pass) .
- Introduced and fixed by modifying the source code (as opposed to configuration files, documentation, or test files).
- Take concurrency character or  in  concurrency-related.

# Setting up ConReg4J

## Requirments

-  Java 1.8

-  Docker>=19.03.8


##### Java version

Jdk is used to support the basic operation of the ConReg4J.

##### Docker version

Two docker images are provided in ConReg4J:

- ConReg4J-gcc-Java
  - gcc/g++>=4.8
  - git
  - make3.82
  - glic2.23
  - Jdk1.7～13
- ConReg4J-java-plain
  - gradle
  - maven
  - ant
  - git
  - Jdk1.4～12

The ConReg4J-gcc-Java image contains  gcc/g++ make freetype boot-jdk and other stable environments needed to build open-jdk.We have tried to build projects from 2013 to the present, most of the open-jdk can be easily built.

The ConReg4J-java-plain image contains a pure jdk environment, we support the oldest version of jdk1.4 project, and latest support to jdk1.8 project.What's more,if you have newer requirements, you can expand the environment through the `dev` function in ConReg4J.

 You can also load the code to the local through the `load` command of ConReg4J, and deploy the environment according to the dependencies in the bundlefile.

## Steps to set up ConReg4J

### Set up from source code

1.Clone ConReg4J:

```
git clone https://github.com/SongXueZhi/ConReg4J.git
```

2.Import  the project  to Eclipse as a general program or open in Neatbeans

3.Initialize ConReg4J(download Docker images, create containers and finally generate logs )

```
cd ConReg4J
./initdocker
```

4.Run as a java application in Eclipse or Neatbeans,enter the `help` command in the running terminal to get guidance.

### Set up from releases

1.Dowanload .zip file from GitHub release

```
https://github.com/SongXueZhi/ConReg4J/releases
```

2.Unzip the file. 

3.Initialize ConReg4J(download Docker images, create containers and finally generate logs )

4.Run ConReg4J and get more help

```
cd ConReg4J
java -jar ConReg4J.jar
help
```

# Using ConReg4J

##### Example commands:

1.Get bug list for the specific project(commons-pool):

```
ls grep pool
```

2.Pull a specific bug version from Version Manager system(Git):(pool-376,regression)

```
pull pool-376 regression
```

3.Get  information for a specific bug (pool-376):

```
info pool-376 
```

4.Get overview for a specific version of the bug(pool-376,regression):

```
info pool-376 regression
```

5.Get difference details between two versions  for a specific bug(pool-376,working,regression):

```
diff pool-376 working regression
```

6.Test a specific bug version(pool-376,regression):

```
test pool-376 regression
```

## Command-line interface

After ConReg4J runs successfully, the command line will enter continuous interactive mode.

| Command | Description                                                  |
| ------- | ------------------------------------------------------------ |
| ls      | View the complete bug list or specific project bug list      |
| info    | View information about a bug or specific version             |
| diff    | Get the change set between the two versions, the change set will also highlight information such as <u>potential missing code</u> and <u>root cause</u> |
| test    | Run test for a specific bug version                          |
| load    | Load the source code of a specific bug in docker to the local |
| add     | Add bug to database                                          |
| pull    | Pull a specific bug version from the version management system (Git) |
| del     | Delete a specific bug from database                          |
| help    | Get more features in ConReg4J                                |
| exit    | Exit interactive mode                                        |

## Implementation details

```
 conreg4j
     |
     |--- libs:              			Libraries used in the core ConReg4J.
     |
     |--- bundlefile         			Dependency information for each project.
     |
     |--- ConReg4J:							 	Framework run files.
     |
     |--- initdocker:							Docker initialization script 
     |
     |--- metadata:         		  Database Tree and Docker Info.
     		|
     		|--- DataBase.xml:   			The tree structure of the database.
     		|
     		|--- db.properties:  			Docker images and containers info.
```

# Contributes

Contribute bugs via fork git repository,  the merger request will be checked by the developer. To guarantee each bug comes in a real commit, create a branch on the commit and then submit  a new commit with the test case added (including necessary refactoring and modification).

