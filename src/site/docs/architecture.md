# Architecture

**Probato** was designed with a modular and highly extensible architecture to support functional test automation efficiently and scalably. It employs modern software design concepts, such as the **Page Object Model (POM)** and **annotation-based dependency injection**, enabling the creation of reusable, maintainable, and expandable scripts.

---

## **Modular Layers and Responsibility Isolation**

**Probato**'s architecture is composed of multiple layers, each with well-defined responsibilities, which simplifies the framework's maintenance and evolution.

### **Interaction Layer (Page Object Model)**

* Implements the **POM** pattern, encapsulating the logic for interacting with the user interface.
* Each page, screen, or component is represented as an object containing methods for possible interactions (clicks, data entry, verifications, etc.).
* Promotes code reuse and facilitates maintenance when the application interface changes.

### **Testing Layer (Scripts and Procedures)**

* Tests are organized into scripts composed of actions divided into:
    * **Preconditions**
    * **Procedures**
    * **Postconditions**
* Responsibility separation helps isolate failures and simplifies error diagnosis.

### **Data Injection Layer**

* Allows flexible and dynamic use of test input data.
* Supports data injection via CSV files, with future plans for JSON, YAML, and database support through custom plugins.

### **Persistence Layer and SQL Connectors**

* Provides an integrated SQL executor that connects to multiple databases.
* Enables defining database preconditions, dynamically altering states before tests, and restoring states after execution.

---

## **Dependency Injection with Annotations**

* Adopts a **dependency injection** model via Java annotations, promoting **Inversion of Control (IoC)**.
* Simplifies manual configuration by automatically injecting required objects based on annotation declarations.
* Enhances modularity and component reuse.

---

## **JUnit 5-Based Test Executor**

**Probato** integrates with the **JUnit 5** lifecycle, using dynamic tests and the `@TestFactory` annotation to generate test cases at runtime.

### **Lifecycle and Structure**

![Probato Life Cycle](/assets/images/introduction/probato-life-cycle.png)

* **BeforeAll**:  
  Loads extension points, configurations, and performs code and configuration validations. Also creates _Dynamic tests_.

* **BeforeEach**:  
  Loads necessary datasets and scripts and starts the execution of test scenarios.

* **TestFactory**:  
  Dynamically generates tests based on script classes, procedures, and Page Objects. Supports **data-driven testing**, allowing multiple executions with different data sets.

* **AfterEach**:  
  Submits the data collected during test execution to the **Probato Manager** and stores images and videos in storage.

* **AfterAll**:  
  Calculates software quality based on metrics and execution data and notifies collaborators about the execution's completion.

---

## **Multibrowser Execution Support**

* Built on the Selenium API, enabling automation across multiple browsers.
* Extensible support for adding new browsers and execution contexts (different operating systems or browser versions).

---

## **Extensibility and Plugins**

* Designed to be **extensible**, allowing new features to be added without modifying the core framework.
* Plugin support for:
    * New browser drivers.
    * Additional input data formats.
    * New types of validation and data manipulation.

---

## **Execution Management and Data Collection**

* During tests, captures data such as:
    * Execution logs.
    * Screenshots.
    * Videos and executed steps.
* Processes and sends data to an integrated web application that provides:
    * Centralized monitoring of executions.
    * Detailed report generation.
    * Bug tracking and versioning analysis.
* Supports integration with tools such as **TestLink** and **Mantis Bug Tracker**.

---

## **Advanced Configurations and Customizations**

* Offers options to:
    * Configure **timeouts** and intervals between actions.
    * Adjust the quality of captured images and videos.
    * Define execution on screen (primary or secondary monitors).

---

## **Notifications and Continuous Integration**

* Sends automatic notifications to collaborators after each test execution.
* Easily integrates with **CI/CD** tools such as **Jenkins**, enabling full automation of test processes in the development cycle.
