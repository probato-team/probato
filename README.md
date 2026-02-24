# Probato

## Open Source Test Automation Framework powered by JUnit 5

**Probato** is an open source framework for end-to-end (E2E) functional
test automation, built on top of JUnit 5 and designed with a
declarative, business-oriented, and extensible approach.

🌐 **Official Website:** https://probato.org  
🚀 **Concepts:** https://probato.org/concepts  
📚 **Full Documentation:** https://probato.org/documentation/  

It does not aim to replace established tools like Selenium or Playwright, but to centralize, standardize, and organize automation practices into a cohesive structure.

------------------------------------------------------------------------

## 🚀 Why Probato?

Test automation in real-world projects often becomes:

-   Hard to maintain\
-   Poorly standardized\
-   Highly dependent on local decisions\
-   Disconnected from observability and reporting

Probato proposes a structured foundation that:

-   Encourages modularity\
-   Promotes best practices\
-   Reduces accidental complexity\
-   Improves organization and scalability

Learn more about the project vision at: 👉 https://probato.org/about

------------------------------------------------------------------------

## 🧱 Core Architecture

Probato follows a modular structure based on:

-   **Suites** -- Functional groupings (features/use cases)
-   **Scripts** -- Test scenarios
-   **Procedures** -- Execution logic
-   **Page Objects** -- UI abstraction layer
-   **Datasets** -- Externalized test data
-   **SQL Scripts** -- Database state preparation

Example structure:

    src/test/java/
     └── org.probato.manager.automation
         ├── model
         ├── page
         └── usecase

Architecture details are available at: 👉
https://probato.org/architecture

------------------------------------------------------------------------

## ⚙️ Requirements

-   Java JDK 11+
-   Maven 3+
-   An IDE (IntelliJ, Eclipse, VS Code, etc.)

Environment setup guide: 👉 https://probato.org/documentation

Validate installation:

    java --version
    mvn -version

------------------------------------------------------------------------

## 📦 Installation (Maven)

Add Probato dependencies to your `pom.xml`:

``` xml
<properties>
    <probato.version>0.1.0</probato.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.probato</groupId>
        <artifactId>probato-core</artifactId>
        <version>${probato.version}</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.probato</groupId>
        <artifactId>probato-browser-chrome</artifactId>
        <version>${probato.version}</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.probato</groupId>
        <artifactId>probato-datasets-csv</artifactId>
        <version>${probato.version}</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.probato</groupId>
        <artifactId>probato-database-sql</artifactId>
        <version>${probato.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Dependency reference: 👉 https://probato.org/documentation/project-configuration

------------------------------------------------------------------------

## 🧪 Basic Example

Full example available at: 👉 https://probato.org/examples

``` java
@Suite(
  code = "UC01",
  name = "Perform login",
  description = "Allow user to login")
class UC01_PerformLogin implements TestSuite {

  @TestCase
  private UC01TC01_PerformLoginSuccessfully loginTest;
}
```

``` java
@Script(
  code = "UC01TC01",
  name = "Perform login successfully",
  description = "Validate successful login")
public class UC01TC01_PerformLoginSuccessfully {

  @Page
  private LoginPage loginPage;

  @Procedure
  private void procedure(LoginModel model) {
    loginPage.checkPage();
    loginPage.fillEmail(model.getEmail());
    loginPage.fillPassword(model.getPassword());
    loginPage.pressAccessButton();
  }
}
```

------------------------------------------------------------------------

## 🗂 Configuration

Create a `configuration.yml` file in:

    src/test/resources/configuration.yml

Example:

``` yaml
execution:
   target:
      url: http://localhost:8099

browsers:
-  type: CHROME
   headless: false

datasources:
   probato:
      url: jdbc:postgresql://localhost:5444/probato
      driver: org.postgresql.Driver
      username: root
      password: root
```

Configuration reference: 👉 https://probato.org/configuration

------------------------------------------------------------------------

## 🎯 Philosophy

Developers focus on **what to test**.\
Probato handles **how to execute**.

Read more about the philosophy: 👉 https://probato.org/philosophy

------------------------------------------------------------------------

## 🌍 Community & Contribution

Probato is fully **Open Source** and community-driven.

Contributions are welcome: - Real-world usage reports\
- Architecture discussions\
- Pattern improvements\
- Bug reports\
- Feature proposals

Community & roadmap: 👉 https://probato.org/community

------------------------------------------------------------------------

## 📜 License

See the LICENSE file for details.

For more information, visit: 🌐 https://probato.org
