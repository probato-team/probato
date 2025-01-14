# Environment Setup

Setting up the development environment correctly is the first step to unlocking the full potential of **Probato**, a powerful framework for test automation. This guide details the steps required to configure essential tools like JDK, Maven, and an IDE, ensuring an environment ready for creating and running automated tests.

---

## **Install JDK**

The **Java Development Kit (JDK)** is required to compile and run Java code used by Probato. Make sure to install version 11 or higher.

### **Download JDK**

1. Access one of the following links to download the JDK:      
      * [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
      * [OpenJDK](https://jdk.java.net/)
      * [Adoptium](https://adoptium.net/)
      * [Amazon Corretto](https://aws.amazon.com/corretto/)

!!! warning

    Download and install version 11 or higher.


### **Configure Environment Variables**

1. **On Windows**:
      * Open "Edit system environment variables."
      * Under **System Variables**, click **New**:
         * Name: `JAVA_HOME`
         * Value: `C:\dev\java\jdk-11`
      * Edit the `Path` variable and add: `%JAVA_HOME%\bin`

2. **On Mac/Linux**:
      * Add the following lines to the `~/.bash_profile` or `~/.zshrc` file:
      ```bash
      export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home
      export PATH=$JAVA_HOME/bin:$PATH
      ```

3. **Validate the installation**:
   ```bash
   java --version
   ```
   Expected output:
   ```plaintext
   java version "11.0.X"
   Java(TM) SE Runtime Environment (build 11.0.X)
   Java HotSpot(TM) 64-Bit Server VM (build 11.0.X)
   ```
   🎉 **JDK installed successfully!**

---

## **Install Maven**

Maven is used to manage project dependencies and automate build processes.

### **Download and Install**

1. Visit the official [Apache Maven](https://maven.apache.org/download.cgi) website.
2. Download the ZIP file and extract it to `C:\dev\maven` (Windows) or `/usr/local/maven` (Mac/Linux).

### **Configure Environment Variables**

1. **On Windows**:
      * Add a new variable:
         * Name: `MAVEN_HOME`
         * Value: `C:\dev\maven`
      * Include `%MAVEN_HOME%\bin` in the `Path` variable.

2. **On Mac/Linux**:
      * Add the following lines to the `~/.bash_profile` or `~/.zshrc` file:
      ```bash
      export MAVEN_HOME=/usr/local/maven
      export PATH=$MAVEN_HOME/bin:$PATH
      ```

3. **Validate the installation**:
   ```bash
   mvn -version
   ```
   Expected output:
   ```plaintext
   Apache Maven 3.X.X
   Maven home: /usr/local/maven
   Java version: 11.0.X, vendor: Oracle Corporation
   OS name: "mac os x", version: "10.15.7", arch: "x86_64"
   ```
   🎉 **Maven installed successfully!**

---

## **Choose an IDE**

An Integrated Development Environment (IDE) simplifies writing, running, and debugging Java code. Here are some popular options:

| **IDE**      | **Main Advantage**                 | **Recommended For**           |
|--------------|------------------------------------|-------------------------------|
| :simple-eclipseide: Eclipse      | Lightweight and free              | Beginners                     |
| :simple-intellijidea: IntelliJ     | Advanced features and rich plugins| Advanced developers           |
| :material-microsoft-visual-studio-code: VS Code      | Modern and extensible             | Simple Java projects          |

!!! note
    Choose the IDE that best suits your needs. Ensure you install the necessary plugins for Java support.

---

## **Final Checklist**

Before proceeding to project creation, verify that:

- ✅ JDK is installed and configured (`java --version` works).
- ✅ Maven is installed and configured (`mvn -version` works).
- ✅ IDE is installed and ready to use.

🎉 **Your environment is set up! You're now ready to create projects with Probato.**

---

## **Ready to continue?**

Head to the next chapter: **Project Creation and Configuration**.