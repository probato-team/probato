# User Guide

Welcome to the quick guide for **Probato**! This document was created to help you set up, use, and customize this test automation framework. Here, you will find practical examples and clear instructions to start your journey with **Probato**.

---

## **How to Use This Guide**

This guide is divided into four chapters, each covering an essential aspect of using Probato:

<div class="grid cards" markdown>

-   :octicons-gear-24:{ .md .middle } __Environment Setup__

    ---

    Learn how to install and configure the tools needed to start using Probato, including JDK, Maven, and IDEs.

    [:octicons-arrow-right-24: Go](/quick-guide/configure-environment/)

-   :octicons-file-code-24:{ .md .middle } __Project Creation and Configuration__

    ---

    Discover how to create and set up a base project, structure directories, add dependencies, and configure the `configuration.yml`.

    [:octicons-arrow-right-24: Go](/quick-guide/configure-project/)

-   :material-code-json:{ .md .middle } __Basic Test Implementation__

    ---

    Understand how to create suites, scripts, and procedures to automate simple use cases in Probato.

    [:octicons-arrow-right-24: Go](/quick-guide/implement-script/)

-   :simple-cachet:{ .md .middle } __Execution and Result Analysis__

    ---

    Learn how to execute the project, interpret logs, and validate results to ensure the quality of your software.

    [:octicons-arrow-right-24: Go](/quick-guide/execution-result-analysis/)

</div>

---

## **Testing Workflow in Probato**

Below is the typical workflow for running tests with Probato:

**Testing Workflow**

``` mermaid
graph LR
  A[Implement Tests] --> B[Execute Suites];
  B --> C{Integrated?};
  C --> |Yes| D[Probato Manager];
  C --> |No| B;
  D --> E[Analyze Results];
```

**Workflow Description**:

1. **Implement Tests**: Develop suites, scripts, and procedures to cover test cases.
2. **Execute Suites**: Run automated tests and collect results.
3. **Analyze Results**: Review logs, reports, and generated evidence to identify issues or validate expected behavior.

---

## **Ready to Start?**

Head to the first chapter: **Environment Setup**.