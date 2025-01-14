# Project Creation and Configuration

This section details how to create and configure a Maven project to use **Probato**, ensuring an efficient base structure for test automation. The goal is to prepare an environment with organized dependencies and essential configurations to effectively run automated tests.

---

## **Creating a Maven Project**

### **Using the terminal or command prompt**

1. Open the terminal or command prompt.
2. Navigate to the directory where you want to create the project.
3. Execute the following command:
    ```bash
    mvn archetype:generate -DgroupId=com.example.automation -DartifactId=my-project-automation -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```

    **Parameters:**
    
    * **groupId:** Group identifier for your organization, usually related to the domain (e.g., `com.example.automation`).
    * **artifactId:** Name of the test automation project (e.g., `my-project-automation`).

    !!! note
         If you prefer, the project can also be created directly through your IDE.

4. After execution, the following structure will be created:
    ```plaintext
    my-project-automation/
    ├── src/
    │   ├── main/
    │   │   └── java/
    │   │       └── com/example/automation/App.java
    │   └── test/
    │       └── java/
    │           └── com/example/automation/AppTest.java
    └── pom.xml
    ```

### **About Maven Archetype**

The `mvn archetype:generate` command uses **Maven Archetype** to generate a project with a standard initial structure. It is useful for quickly creating projects, including directories and basic files.

---

## **Configuring the Folder and Package Structure**

1. Open the directory `src/main/java/`.
2. Remove the `com.*` package generated automatically.
3. In the package `com.example.automation` in `src/test/java/`, remove the `AppTest.java` file.
4. Create the directory `src/test/resources/` and add a file named `configuration.yml`.

The project structure will look like this:

```plaintext
my-project-automation/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/example/automation/
│       │       ├── model/
│       │       ├── page/
│       │       └── usecase/
│       └── resources/
│           ├── dataset/
│           ├── sql/
│           └── configuration.yml
└── pom.xml
```

**Folder Descriptions:**

* `src/test/java/`: Location where test automation implementation will reside.
* `src/test/resources/`: Folder to store configurations, datasets, SQL scripts, and other files required for testing.

    !!! note
         The `configuration.yml` file will be configured in detail in future sections.

---

## **Adding Dependencies to Maven**

1. Open the `pom.xml` file located in the project root directory.
2. Add the following dependencies:
    ```xml title="pom.xml"
    <project
        xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        
        <modelVersion>4.0.0</modelVersion>

        <groupId>com.example.automation</groupId>
        <artifactId>my-project-automation</artifactId>
        <version>1.0.0</version>

        <properties>
            <probato.version>0.1.0</probato.version>
        </properties>

        <dependencies>
            <!-- Probato Dependencies -->
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-api</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-browser</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-dataset-csv</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-database-sql</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-record</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-manager</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <!-- Application target database dependency -->
            <dependency>
                <groupId>org.postgresql</groupId>
                <artifactId>postgresql</artifactId>
                <version>42.7.3</version>
                <scope>test</scope>
            </dependency>
            <!-- JUnit 5 Dependency -->
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter</artifactId>
                <version>5.9.3</version>
                <scope>test</scope>
            </dependency>
        </dependencies>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>3.0.0-M7</version>
                    <configuration>
                        <includes>
                            <include>**/*.java</include>
                        </includes>
                    </configuration>
                </plugin>
            </plugins>
        </build>

    </project>
    ```

### **Dependency Descriptions**
- **`probato-api`**, **`probato-browser`**, **`probato-dataset-csv`**, **`probato-database-sql`**, **`probato-record`**, **`probato-manager`**: Provides access to **Probato** functionalities.
- **`postgresql`**: Required for database connections; ensure the database dependency matches the target application.
- **`junit-jupiter`**: Required to run tests using JUnit 5.

    !!! note
         Additional dependencies will be included as we progress through the tutorial.

---

## Adding and Configuring Probato

1. In the `configuration.yml` file, add the following configurations:
    ```yaml title="configuration.yml"

    execution:

       target:
          url: http://localhost:8099
          version: 0.0.0

       delay:
          waitingTimeout: 5000
          actionInterval: 500
      
        video:
            enabled: true
            frameRate: 1000
            quality: MEDIUM
            
        manager:
            submit: true
            url: http://localhost:8080
            token: [TOKEN]

    browsers:

    -  type: CHROME
       headless: false
       dimension:
          mode: FULLSCREEN

    -  type: FIREFOX
       headless: false
       dimension:
          mode: MAXIMIZED

    -  type: EDGE
       headless: false
       dimension:
          mode: CUSTOM
          width: 1256
          height: 1018

    datasources:

       probato:
          url: jdbc:postgresql://localhost:5444/testano
          driver: org.postgresql.Driver
          username: root
          password: root

    ```

    **Properties:**

    * **execution.delay.waitingTimeout:** Maximum wait time for action execution.
    * **execution.delay.actionInterval:** Time interval between actions to be executed.
    * **browsers.[*].type:** Specifies which browser will be executed.
    * **browsers.[*].headless:** Default is `false`. If `true`, the browser window will remain invisible during execution.
    * **browsers.[*].dimension.mode:** Default is `MAXIMIZED`. Possible values are `FULLSCREEN`, `MAXIMIZED`, and `CUSTOM`. If `CUSTOM`, the `width` and `height` properties are mandatory.
    * **browsers.[*].dimension.width:** Specifies the browser's width dimension during execution.
    * **browsers.[*].dimension.height:** Specifies the browser's height dimension during execution.
    * **datasources.[name]:** Specifies the name of the resource to be accessed.
    * **datasources.[name].url:** Specifies the URL of the resource to be accessed.
    * **datasources.[name].driver:** Specifies the connection driver for the resource to be accessed.
    * **datasources.[name].schema:** Specifies the schema for the resource to be accessed.

---

## **Final Checklist**

Before proceeding to the next section, confirm the following:

- ✅ Maven project created (`my-project-automation`).
- ✅ Folder structure adjusted as per the example.
- ✅ `configuration.yml` file created in `src/test/resources/`.
- ✅ Dependencies added to the `pom.xml` file.
- ✅ Configurations added to the `configuration.yml` file.

🎉 **Your project is ready to start test automation with Probato!**

---

## **Ready to continue?**

Proceed to the next chapter: **Basic Test Implementation**.

