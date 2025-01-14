# Understanding Best Practices for Testing

In this section, before implementing test scripts, we will align our understanding of what test scripts are, their composition, structure, and objectives. Understanding and applying best practices in testing improves efficiency, reliability, and reduces costs in software development.

---

## **What is a Test Suite?**

A test suite is an organized collection of test cases or scripts grouped to validate one or more aspects of a system, application, or functionality. These tests can be organized based on criteria such as functionality, priority, type of testing (functional, performance, security, etc.), or phases of the software lifecycle.

### **Objective of a Suite**

The main objective of a test suite is to enable coordinated and structured execution of multiple tests, ensuring:

- **Comprehensive coverage:** Validation of different usage scenarios and system requirements.
- **Reusability:** Organization of related tests to facilitate maintenance and reuse.
- **Automation:** Continuous test execution in the continuous integration or delivery process (CI/CD).
- **Quality analysis:** Collect results and identify critical areas in the system.

### **Suite Composition**

A test suite typically includes:

- **Test cases or scripts:** Specific scenarios that must be validated.
- **General configurations:** Shared parameters, such as URLs and credentials.
- **Test data:** Shared or specific data for each test.
- **Dependencies:** Relationships between tests, in case one depends on another.
- **Success or failure criteria:** Rules to determine the suite's outcome.

### **Types of Suites**

| Type             | Objective                                 | Example                                    |
|------------------|-----------------------------------------|-------------------------------------------|
| Functional        | Validate functional requirements         | Test login and registration               |
| Regression        | Ensure changes do not introduce errors   | Run tests after bug fixes                 |
| Performance      | Evaluate performance under different conditions | Test API response time                   |
| Integration       | Validate communication between modules   | Test payment service integration          |
| Smoke             | Verify basic functionality               | Test homepage loading                     |
| Exploratory       | Discover unexpected behaviors            | Test new flows without fixed scripts      |

---

## **Structure of a Test Script**

Test scripts are composed of preconditions, procedures, and postconditions. This structure ensures organized tests and verifiable results.

### **Preconditions**

Conditions or states that need to be established before the test is executed.

**Examples:**        

* Configured environment (server running).
* Test data loaded.
* External APIs available.

**Best Practices:**

* Automate the configuration of preconditions.
* Define dependencies and ensure reproducible states.

**Example in Probato:**     
Precondition for altering the database state using the SQL executor before the test.

### **Procedures**

Steps that make up the test, simulating user actions or system interactions.

**Examples:**

1. Navigate to the login page.
2. Fill in the 'Email' field with `user@probato.org`.
3. Fill in the 'Password' field with `p@ssword`.
4. Trigger the 'Login' action.

**Best Practices:**

* Each step should be atomic and focus on a single action.
* Document actions clearly and accessibly.
* Include checkpoints to validate intermediate states.

**Example in Probato:**     
Procedures organized into well-defined steps, with detailed logs of each action.

### **Postconditions**

Expected results or final states after test execution.

**Examples:**

* User authenticated.
* Redirected to the homepage.
* Data persisted in the database.

**Best Practices:**

* Verify relevant conditions without redundancy.
* Automate validations for greater precision.
* Track errors with logs, videos, and screenshots.
* Validate whether the application state complies after executing the target procedure.

**Example in Probato:**     
After the test, capture logs and validate conditions of success or failure.

---

## **Use Case: Logging In**

The login use case allows users to access the application using registered email and password. It includes the following flows:

| Flow                  | Preconditions                        | Procedure                              | Postconditions                           |
|------------------------|--------------------------------------|----------------------------------------|------------------------------------------|
| Successful login      | Active user                          | Fill in email and password, click login | Main dashboard displayed                 |
| User not found        | Unregistered user                    | Fill in email and password, click login | "User not found" message displayed      |
| Inactive user         | Inactive user                        | Fill in email and password, click login | "Inactive user" message displayed       |
| Incorrect password    | Incorrect password                   | Fill in email and incorrect password, click login | "Incorrect password" message displayed |
| Required fields       | Empty email and password fields      | Click login                             | Error messages displayed                 |

---

## **Test Data**

Test data refers to the set of data used in tests to validate different aspects of the system.

### **Objective:**
- Simulate real scenarios.
- Ensure test coverage with valid, invalid, and edge inputs.
- Identify failures caused by unexpected data.

### **Types:**
- **Valid data:** Inputs that meet requirements.
- **Invalid data:** Inputs that do not meet requirements.
- **Edge data:** Values at acceptable limits.
- **Massive data:** Large volumes to evaluate performance.

**Example Data:**
```plaintext
Valid data:
- Email: user@example.com
- Password: Password123!

Invalid data:
- Email: user@example
- Password: 123

Edge data:
- Email: [256 characters]
- Password: [0 characters]
```
---

## **Page Object**

_Page Object_ is a design pattern that organizes and abstracts interactions with the user interface (UI). It encapsulates interaction logic, promoting reusability, maintenance, and readability.

### **Structure:**
- **Page class:** Represents the page or UI component.
- **UI elements:** Buttons, fields, and links as attributes.
- **Interaction methods:** Encapsulated logic to interact with the elements.

**Example:**
```java  title="LoginPage" linenums="1"
public class LoginPage {
    
    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement emailInput;
    
    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"login-btn\"]")
    private WebElement accessButton;

    public void fillEmail(String email) {
        emailInput.sendKeys(email);
    }
    
    public void fillPassword(String password) {
        passwordInput.sendKeys(password);
    }
    
    public void pressAccessButton() {
        accessButton.click();
    }
}
```

**Best Practices:**

* Avoid business logic in Page Objects.
* Name methods intuitively.
* Keep the code modular and simple.

## **Final Considerations**

In this section, we discussed the main elements that ensure well-structured tests aligned with automation objectives. Applying best practices and using tools like **Probato** facilitates the implementation of robust, scalable, and maintainable tests. In the next chapters, we will explore each concept in greater depth with practical examples.

### **General References:**

- ISTQB: [Software Testing Fundamentals](https://www.istqb.org/)
- Selenium: [Official Selenium Guide](https://www.selenium.dev/documentation/)
- Selenium: [Page Object Design Pattern](https://www.selenium.dev/documentation/en/guidelines_and_recommendations/page_object_models/)
- OWASP: [Best Practices for Data Security](https://owasp.org/)
- CI/CD: [Continuous Integration Practical Guide](https://martinfowler.com/articles/continuousIntegration.html)

