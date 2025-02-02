# SysPlus
System plus project

**Tech Stack**

  Automation : Playwright library

  Coding language : Java

  Reporting : Extent Reports

  Build tool : Maven

  Test Runner : TestNg/Maven Surefire Plugin

  Framework : Hybrid ( Based on Page Object Model/ POM)

  Execution : Local only, though if we want to execute on cloud platforms then we need to update in driver factory only and credentials can be taken from config.properties

**How to execute** 
  use any of below commands, we have arguments which are optional to update config properties, either update config.properties or we can pass as arguments also

  **mvn test -DsuiteXmlFile=testNGFiles/tests.xml**
          :  run testng file tests.xml which contains all test files as of now

  **mvn test -DsuiteXmlFile=testNGFiles/tests_parallel.xml**
          :  run testng file tests_parallel.xml which contains all test files and running them in parallel
          
  **mvn test -DsuiteXmlFile=testNGFiles/tests.xml -Dheadless=false -DvideoRecording=true -DscreenshotonPass=false -Dbrowser=firefox**
          : properties updates like headless or not,  videorecording or not, screenshotonpassed steps or not, browser like chrome/firefox etc

  

**Features :**

  **Page Object Model** : page data/locators/methods are taken from respective page classes, which create a layer of abstraction, also decreases maintainence in case if there are any changes

  **Parallel Execution** : Parallel execution is done using testNG here. For distributed testing we can use selenium Grid also but need separate configuration for that which is not implemented yet

  **Data Driven** : test data is not hardcoded with test cases, but taken from external source here which is json file. We can use another external source, xml file or google sheets, all we have to do is update in generic lib to return test data in jsonobject form only.

  **Scalable** : we can add number of tests without interupting much and add multiple pages and tests. We have facility to run tests in parallel as well to finish execution as fast as possible. 

  **Maintainable** : less maintenance due to multilayers and resuable code.


  **Note** : Parallel execution for very large products say ( 1000s test cases we need distributed system which can be achieved with Selenium grid rather than testNG but will take some time for implementations) 


**Important Directories :**

  **src** : main contains main code for libraries/utilities and page objects

  **testNGFiles** : testng files to run full suite/respective classes or tests. 

  **TestData** : test data for test cases, used json file here, we can use other type of external data sources as well like google sheets etc

  **reports** : this will be generated at run time, containing reports, extent report, screenshots, video recordings, trace views 

  **config** : config file where we can provide configurations like browser to use, screenshot configs when to take, video recording or not etc

  **Note** : a lot of config is taken to Constants interface in package "org.factory", these are the configs which we dont change very frequently


**CI/CD**

  **Self Repo** : in this repo, on commit to master or on pull request to master, it will run test suite via testng file testNGFiles/tests.xml,  for longer suite we should run only sanity not full suite.
                  artifacts can also be downloaded in case of failures.
                  Example of artifact https://github.com/psangwan88/SysPlus/actions/runs/13096695312

  **Dev Repo** :  with dev repo this should be integrated with jenkins pipeline of dev Repository, where we can add it as a part of jenkins pipeline as a build step, where test cases will be run via docker image for this repo

How to use trave viewer logs  

**Running in parallel**  : currently supported via testNG runner, for distributed systems or cloud farms we need to use selenium grid.

**Running on different browsers**  : current implementation provides facility to run on different browsers by updating in config.properties file, need to add parameter for testng files like running one class on one browser

**Running on different browsers without changing config file, via command line**  : yet to be done



  
