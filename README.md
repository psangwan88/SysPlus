# SysPlus
System plus projet


**Tech Stack : **
  Automation : Playwright library

  Coding language : Java

  Reporting : Extent Reports

  Build tool : Maven

  Test Runner : TestNg/Maven Surefire Plugin

  Framework : Hybrid ( Based on Page Object Model/ POM)


**Features :**
  Page Object Model : page data/locators/methods are taken from respective page classes, which create a layer of abstraction, also decreases maintainence in case if there are any changes

  Parallel Execution : Parallel execution is done using testNG here. For distributed testing we can use selenium Grid also but need separate configuration for that which is not implemented yet

  Data Driver : test data is not hardcoded with test cases, but taken from external source here which is json file. We can use another external source, xml file or google sheets, all we have to do is update in generic lib to return test data in jsonobject form only.

  Scalable : we can add number of tests without interupting much and add multiple pages and tests. We have facility to run tests in parallel as well to finish execution as fast as possible. 

  Maintainable : less maintenance due to multilayers and resuable code.


  **Note** : Parallel execution for very large products say ( 1000s test cases we need distributed system which can be achieved with Selenium grid rather than testNG but will take some time for implementations) 


**Important Directories :**
  src : main contains main code for libraries/utilities and page objects

  testNGFiles : testng files to run full suite/respective classes or tests. 

  TestData : test data for test cases, used json file here, we can use other type of external data sources as well like google sheets etc

  reports : this will be generated at run time, containing reports, extent report, screenshots, video recordings, trace views 

  config : config file where we can provide configurations like browser to use, screenshot configs when to take, video recording or not etc

  Note : a lot of config is taken to Constants interface in package "org.factory", these are the configs which we dont change very frequently


How to use trave viewer logs  

Running in parallel

Running on different browsers

Running on different browsers without changing config file, via command line

Running same Test class on different browsers : 


  
