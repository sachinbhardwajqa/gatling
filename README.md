Gatling  
   
Gatling is an open-source load testing framework based on Scala, Akka and Netty. It is designed for ease of use, maintainability and high performance. Gatling is suitable for performance testing of web applications, APIs and microservices. It is a powerful tool that can simulate hundreds of thousands of users with minimal hardware requirements.      

Gatling provides a powerful DSL for creating test scenarios. It supports HTTP, WebSockets, Server-Sent Events, JMS and JDBC protocols. Gatling can be integrated with build tools like Maven and Gradle. It generates detailed reports in HTML format that can be used to analyze the test results.     

Gatling is a popular choice for performance testing due to its scalability, flexibility and extensibility. It is widely used by developers, testers and DevOps engineers to ensure the performance and reliability of their applications.       

Key Features of Gatling:

- Easy to use DSL for creating test scenarios
- Support for HTTP, WebSockets, Server-Sent Events, JMS and JDBC protocols
- Integration with build tools like Maven and Gradle
- Detailed HTML reports for analyzing test results
- Scalable and extensible architecture
- Minimal hardware requirements
- Suitable for testing web applications, APIs and microservices
- Open-source and actively maintained
- Community support through forums and mailing lists
- Gatling is a powerful and flexible load testing tool that can help you identify performance bottlenecks in your applications. It is easy to use, scalable and extensible, making it a popular choice for performance testing. Whether you are a developer, tester or DevOps engineer, Gatling can help you ensure the performance and reliability of your applications.
- Gatling is actively maintained and has a large community of users who can provide support and guidance. If you are looking for a reliable and efficient load testing tool, Gatling is a great choice.
- Gatling is an open-source tool that is free to use. You can download it from the official website and start using it for your load testing needs. Give Gatling a try and see how it can help you improve the performance of your applications.
- Gatling is a powerful load testing tool that can simulate hundreds of thousands of users with minimal hardware requirements. It is suitable for testing web applications, APIs and microservices. Gatling provides a powerful DSL for creating test scenarios and generates detailed reports in HTML format for analyzing test results. It is easy to use, scalable and extensible, making it a popular choice for performance testing.
- Gatling is actively maintained and has a large community of users who can provide support and guidance. If you are looking for a reliable and efficient load testing tool, Gatling is a great choice. Give Gatling a try and see how it can help you improve the performance of your applications.

Below is the high level comparison with other tools like JMeter, LoadRunner, K6, Locust etc.

| Feature                 | Gatling                                      | JMeter                                           | LoadRunner                                         | K6                                          | Locust                                |
|-------------------------|----------------------------------------------|--------------------------------------------------|----------------------------------------------------|---------------------------------------------|---------------------------------------|
| **Scripting Language**  | Scala (or Java DSL)                          | Java, Beanshell, Groovy                          | Proprietary (with support for C/JavaScript, etc.)  | JavaScript (ES6)                           | Python                                |
| **Open Source / Cost**  | Open Source (with Enterprise options)        | Open Source                                      | Commercial (enterprise-grade)                      | Open Source (Apache License 2.0)             | Open Source (MIT License)             |
| **Protocol Support**    | HTTP, WebSockets, JMS, SSE                   | HTTP, FTP, JDBC, JMS, SOAP, etc.                 | Extensive (including ERP, Citrix, etc.)            | HTTP, WebSockets                           | HTTP, WebSockets                      |
| **User Interface**      | Code-based DSL                               | GUI-driven (with code customization options)     | GUI-based with script customization                | Code-based                                 | Code-based (Python scripts + web UI)    |
| **Reporting**           | Detailed HTML reports, graphs                | Basic reports (extendable with plugins)          | Comprehensive, enterprise-level reporting          | CLI summary, JSON outputs, Grafana integration | Real-time monitoring via web UI       |
| **Scalability**         | High (supports distributed testing)          | Scalable with distributed mode (resource intensive) | Enterprise-level scalability                | Lightweight, cloud-native distributed execution | Easily scaled with Python multiprocessing |
| **Ease of Use**         | Requires programming (Scala/Java)            | Beginner-friendly GUI; advanced use can be complex | Steep learning curve; requires training            | Simple API and code-centric                | Intuitive for Python developers       |
| **Community & Ecosystem** | Active, growing community                   | Very large community with many plugins           | Strong vendor and enterprise support               | Active modern community                    | Vibrant Python community              |
| **Pros**              | Customizable DSL, detailed reporting, scalable, open-source | Extensive protocol support, beginner-friendly, plugin-rich | Robust, enterprise-grade features, extensive protocol support | Modern, lightweight, simple API, cloud-native | Easy to use, real-time monitoring, scalable with Python |
| **Cons**              | Requires programming skills, limited protocol support beyond core features | Resource intensive at scale, complex advanced scenarios, basic reporting without plugins | Expensive, steep learning curve, requires professional training | Limited protocol support, less advanced reporting options, smaller ecosystem | Limited protocol support, less out-of-the-box reporting, additional coding may be required |


**Specific to this project :**
Supported Languages : Java, Kotlin, Scala   
Used Java Version : Java 11  

Gatling Installation and Setup on Mac with Maven and IntelliJ IDEA
Check first commit to start from basic setup
------------
Dependencies : gatling-core, gatling-core-java, gatling-charts-highcharts
Plugin : gatling-maven-plugin
------------

Run using :
- mvn clean               // Clean the project
- mvn clean test-compile gatling:test      // Run the Gatling test
- mvn clean test-compile gatling:test -Dgatling.simulationClass=<packageName>.<directoryOfProject>.<simulationClass>     // Run the specific Gatling test

Gatling supported protocol list below
- HTTP        
- WebSockets      
- Server-Sent Events      
- JMS     
- JDBC        

Every gatling script consist of three distinct parts 
- HttpProtocolBuilder ( Base URL, Base Settings, Headers, Encoding etc. )
- ScenarioBuilder ( These are the actual steps which need to be performed )
- setUp  ( Load Simulation Design)

Feeders in Gatling (Use `#` or `$` to access data. Syntax: `${columnName}` or `#{columnName}`)
CSV Feeder ( CSV file with data )       
Syntax Example :        
FeederBuilder.Batchable feeder1 = csv("filename.csv").circular(), csv("filename.csv").random()


Open and Closed Workload Models in Gatling
Closed Workload Model : Gatling will keep the number of users constant throughout the test duration.
Examples :
constantConcurrentUsers(10) during(10 minutes) // 10 users for 10 minutes
rampConcurrentUsers(10) to(20) during(10 minutes) // 10 to 20 users for 10 minutes

Open Workload Model : Gatling will keep adding users to the system until the maximum number of users is reached.        
Examples :
rampUsersPerSec(10) to(20) during(10 minutes) // 10 to 20 users per second for 10 minutes


------------------------------
https://demostore.gatling.io/ - Demo Store Application


Checks in Gatling   
Feeders in Gatling      
Session API in Gatling      
Cookies in Gatling
Custom Code in Gatling      
Refactoring in Gatling      


Load Testing Simulation Design in Gatling
------------------------------
Static Variables    
Selectors to capture Data       
Parameterization        
Session API     

------------------------------
**Clone and Run using below command - change numbers as needed.**

mvn clean test-compile gatling:test -Dgatling.simulationClass=simulation.SimulationDemoStore4UserJourneys -DUSERS=2 -DRAMP_DURATION=20 -DTEST_DURATION=30



