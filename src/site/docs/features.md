# Features

## **Simple and Intuitive Structure**

**Probato** provides an organized and modular structure for implementing tests, facilitating the reuse of components and the maintenance of scripts. This allows teams to focus more on test logic rather than implementation structure.

---

## **Object Injection with Annotations**

Using annotations simplifies test configuration, enabling clear and concise object injection without additional implementations.

---

## **Page Object Model (POM) Pattern**

**Probato** follows the **Page Object Model** pattern, helping to separate layers and organize code. This approach simplifies the maintenance and readability of scripts, especially in frameworks like Selenium.

---

## **Test Procedure Organization**

Tests are organized into three stages:

1. **Preconditions**
2. **Procedures**
3. **Postconditions**

This structure allows for quickly identifying the source of failures and understanding whether errors are in the target functionalities or preparatory steps.

---

## **Implicit Data Injection**

Scripts can be executed with different data sets, enabling comprehensive testing without duplicating code. This functionality enhances test efficiency and coverage.

---

## **SQL File Executor**

The integrated SQL executor connects to multiple databases to modify the application's state according to test preconditions. This offers flexibility when configuring test scenarios.

---

## **Intuitive Test Workflow Creation**

Workflows can be created with code, descriptions, and weights based on the relevance and complexity of functionalities. This helps prioritize and analyze the quality of the tested software.

---

## **Timeout and Interval Settings**

**Probato** allows:

- Configuring _timeouts_ for wait times during test execution.
- Adjusting intervals between actions, optimizing test performance.

---

## **Cross-Browser Execution**

Supports running tests on multiple browsers, with options such as:

- Maximized, normal, or custom mode (specific dimensions).
- Selecting the monitor for execution (primary or secondary).

---

## **Data Management**

**Probato** includes a web application for:

- Managing execution data.
- Analyzing software quality.
- Creating bugs from test results.
- Viewing histories and versioning.
- Generating detailed reports with logs and coverage charts.

---

## **Data Capture During Execution**

Collects and stores information such as:

- Test suites and workflows.
- Executed steps.
- Applied data.
- SQL scripts.
- Videos and screenshots (in case of failures).

The quality of the images can be adjusted for detailed failure analysis.

---

## **Execution Notifications**

Sends notifications to collaborators when new executions occur, keeping everyone updated on the test status.

---

## **Extensibility**

**Probato** allows customizations through plugins, including:

- Support for new browsers.
- Additional validations.
- Data input in new formats.
- Custom SQL or NoSQL executors.

---

## **Integration with CI/CD Tools**

Easily integrates with continuous integration systems, such as:

- **Jenkins**
- **Travis CI**
- **GitLab CI**

This enables automatic test execution on each commit, ensuring continuous and agile validations.
