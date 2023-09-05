# Automated testing web application by using Java, Selenide

This is an example of using Selenide test framework.

## Pre-requites

- Install Java 8 or above
- Install Maven

## Installation

- CD to the project folder
- Open CMD/terminal then type

```cmd
mvn clean install
```

## Execute

- You can run test suite by this command line, in this file you can change browser by change value of parameter "
  browser"

```cmd
mvn clean test -DsuiteXmlFile=\src\test\resources\suites\TestSuites.xml
```

- In case you want to change the configuration of browser, you can change it in "
  /src/main/resources/configuration/[browser].json"
- Commonly used configurations

| Config          |  Type   |                                                                          Description | 
|-----------------|:-------:|-------------------------------------------------------------------------------------:|
| browser         | String  |                                                                      Running browser |
| headless        | boolean |                                                   Browser using headless mode or not |
| remote          | String  |                                                                           URL of hub |
| assertionMode   | String  |                                               Using SOFT or STRICT in when asserting |
| timeout         |  long   |                                           Default timeout for waiting condition (ms) |
| pageLoadTimeout |  long   |                              Maximum time for waiting page is loaded completely (ms) |
| pollingInterval |  long   | The time interval in which Selenide starts searching again after the last failed try |
| startMaximized  | boolean |                                                   Browser start with maximize or not |

### Parallel testing

- Tests, classes, methods can be run in parallel by adding parallel attribute on xml suite
- For example:
  ```
  <suite name="My suite" parallel="classes" thread-count="2">
  ```

### Distributed testing

- First, install Selenium Grid according to this [link](https://www.selenium.dev/documentation/grid/getting_started/)
- Then config URL of hub into the configuration of browser as above. For examlple:
```
 "remote": "http://localhost:4444/wd/hub"
```

## Report

- Allure report is using in this framework

  ### Installation
    - You can refer to this link to install allure: [link](https://docs.qameta.io/allure/#_installing_a_commandline)
  ### Report generation
    - After running the test, the report is generated on the /allure-result directory
    - Use this command from project folder to generate report

```cmd
allure serve
```

## License

[MIT](https://choosealicense.com/licenses/mit/)