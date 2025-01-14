# Basic Script Implementation

In this section, a basic test script will be implemented. **Probato** adopts a modular structure that includes scripts, procedures, page objects, and test suites. To illustrate the automation of a real feature, we will proceed with the automation of the **Probato Manager** application. **Probato Manager** has the login page as its main screen, so we will start from this functionality to develop automated tests: **Perform Login**

---

## **Basic Application Structure**

We will assume that the project has the following structure:

  ``` title="Estrutura"
  probato-manager-automation/
  ├── src/
  │   └──  test/
  │       ├── java/
  │       |   └── org.probato.manager.automation
  │       |       ├── model
  │       |       ├── page
  │       |       └── usecase
  |       └── resources/
  │           ├── dataset/
  │           ├── sql/
  |           └── configuration.yml
  └── pom.xml
  ```

---

## **Implementing the Suite Class**

1. No pacote `org.probato.manager.usecase` vamos criar o novo pacote `UC01` .
2. No pacote `org.probato.manager.usecase.UC01` vamos criar a classe `UC01_PerformLogin.java`.
3. Na classe `UC01_PerformLogin.java` vamos implementar o código abaixo.
```java title="UC01_PerformLogin.java" linenums="1"
package org.probato.manager.automation.usecase.UC01;

import org.probato.manager.automation.usecase.UC01.script.UC01TC01_PerformLoginSuccessfully;

import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.api.TestSuite;

@SQL(
	datasource = "probato", 
	scriptPath = { "sql/init/init.sql" })
@Suite(
	code = "UC01", name = "Perform login", 
	description = "This feature aims to allow the user to login to this application")
class UC01_PerformLogin implements TestSuite {
	
	@TestCase
	private UC01TC01_PerformLoginSuccessfully uc01tc01;

  // Add additional test cases to suite

}
```

---

## **Implementing the Script Class**

1. No pacote `org.probato.manager.usecase.UC01` vamos criar o novo pacote `script` .
2. No pacote `org.probato.manager.usecase.UC01.script` vamos criar a classe `UC01TC01_PerformLoginSuccessfully.java`.
3. Na classe `UC01TC01_PerformLoginSuccessfully.java` vamos implementar o código abaixo.
```java title="UC01TC01_PerformLoginSuccessfully.java" linenums="1"
package org.probato.manager.automation.usecase.UC01.script;

import org.probato.manager.automation.model.LoginModel;
import org.probato.manager.automation.page.DashboardPage;
import org.probato.manager.automation.page.LoginPage;

import org.probato.api.Dataset;
import org.probato.api.Page;
import org.probato.api.Procedure;
import org.probato.api.SQL;
import org.probato.api.Script;

@Dataset("dataset/UC01/UC01TC01.csv")
@SQL(
	datasource = "probato", 
	scriptPath = { "sql/user/insert-user.sql" })
@Script(
	code = "UC01TC01", 
	name = "Perform login successfully", 
	description = "This script aims to validate the user's login in the application successfully")
public class UC01TC01_PerformLoginSuccessfully {

	@Page
	private LoginPage loginPage;

	@Page
	private DashboardPage dashboardPage;

	@Procedure
	private void procedure(LoginModel model) {
		loginPage.selectEnglishTranslate();
		loginPage.checkPage();
		loginPage.fillEmail(model.getEmail());
		loginPage.fillPassword(model.getPassword());
		loginPage.pressAccessButton();
		dashboardPage.checkPage();
	}

}
```

---

## **Implementing the Page Object**

1. No pacote `org.probato.manager.page` vamos criar a classe `LoginPage.java`.
```java title="LoginPage.java" linenums="1"
package org.probato.manager.automation.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.probato.api.Action;
import org.probato.api.Param;
import org.probato.page.WebPage;

public class LoginPage extends WebPage {
  
  @FindBy(xpath = "//*[@id=\"public-container\"]/app-login/form/div/div[1]/h1")
  private WebElement titlePage;

  @FindBy(xpath = "//*[@id=\"email\"]")
  private WebElement emailInput;
  
  @FindBy(xpath = "//*[@id=\"password\"]")
  private WebElement passwordInput;

  @FindBy(xpath = "//*[@id=\"login-btn\"]")
  private WebElement accessButton;

  @Action("Check `Login` page")
  public void checkPage() {
    assertEquals("Login", titlePage.getText());
    assertTrue(emailInput.isDisplayed());
    assertTrue(passwordInput.isDisplayed());
    assertTrue(accessButton.isDisplayed());
  }
  
  @Action("Fill in the 'Email' field with '{{email}}' value")
  public void fillEmail(@Param("email") String email) {
    emailInput.sendKeys(email);
  }
  
  @Action("Fill in the 'Password' field with {{password}} value")
  public void fillPassword(@Param("password") String password) {
    passwordInput.sendKeys(password);
  }
  
  @Action("Press the 'Access' button")
  public void pressAccessButton() {
    accessButton.click();
  }
  
}

```
2. No pacote `org.probato.manager.page` vamos criar a classe `DashboardPage.java`.
```java title="DashboardPage.java" linenums="1"
package org.probato.manager.automation.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.probato.api.Action;
import org.probato.api.Param;
import org.probato.page.WebPage;

public class DashboardPage extends WebPage {
  
  @FindBy(xpath = "//*[@id=\"private-container\"]/app-view-dashboard/div/div[1]/div/h1")
  private WebElement titlePage;

  @Action("Check `Dashboard` page")
  public void checkPage() {
    assertEquals("Dashboard", titlePage.getText());
  }
  
}
```

---

## **Creating Dataset File**

1. Na pasta `src/test/resources/dataset` vamos criar a nova pasta `UC01`.
2. Na pasta `src/test/resources/dataset/UC01` vamos criar o arquivo chamado `UC01TC01.csv`.
3. No arquivo `UC01TC01.csv` vamos adicionar o conteúdo abaixo.
``` title="UC01TC01.csv" linenums="1"
EMAIL, PASSWORD
user01@probato.org, p@ssword
user02@probato.org, p@ssword123
user02@probato.org, p@ssword321

```

### Implementing Input Mapping Class

1. No pacote `org.probato.manager.automation.model` vamos criar a classe `LoginPage.java`.
```java title="LoginModel.java" linenums="1"
package org.probato.manager.automation.model;

import org.probato.model.Datamodel;

public class LoginModel extends Datamodel {

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
```

--- 

## **Creating SQL Files**

1. Na pasta `src/test/resources/sql` vamos criar a nova pasta `init`.
2. Na pasta `src/test/resources/sql` vamos criar a nova pasta `user`.
3. Na pasta `src/test/resources/sql/init` vamos criar o arquivo chamado `init.sql`.
4. Na pasta `src/test/resources/sql/user` vamos criar o arquivo chamado `insert-user.sql`.
5. No arquivo `init.sql` vamos adicionar o conteúdo abaixo.
```sql title="init.sql" linenums="1"

DELETE FROM testano_app.users;

```
6. No arquivo `insert-user.sql` vamos adicionar o conteúdo abaixo.
```sql title="insert-user.sql" linenums="1"

INSERT INTO testano_app.users
(id, "name", email, "password", gender, active)
VALUES('a02b03e6-c462-4980-9997-c1203a094c9d'::uuid, 'User 01', 'user01@probato.org', '$2a$10$Kml4nk3ADhnWrJg0GkStVeTJoslDBir/Fgyw2gkLR0FukujfIxZQ2', 'MALE',  true);

INSERT INTO testano_app.users
(id, "name", email, "password", gender, active)
VALUES('bd84a2c8-d315-40ea-80aa-1841254b20c9'::uuid, 'User 02', 'user02@probato.org', '$2a$10$oHZ7er1/2/xKjgOq0znXnOPcvoOXpX.in6XO/4mf2xf5ZV7OMyvq6', 'MALE',  true);

INSERT INTO testano_app.users
(id, "name", email, "password", gender, active)
VALUES('b86a08ac-2ee8-42c7-a46f-c589b1d26503'::uuid, 'User 03', 'user03@probato.org', '$2a$10$81pSkjzZTqgn3/nU5DzxVemmr0rjJ7NHtK/UiGhzomEwTyHZgFliC', 'MALE',  true);

```

---

## **Final Considerations**

We successfully implemented a basic script using **Probato**. Ao longo desta seção, foi possível demonstrar como o framework proporciona uma estrutura organizada e modular para a automação de testes, abrangendo desde a criação de **Suites**, **Scripts**, e **Page Objects** até a utilização de massas de dados e pré-condições com scripts SQL.

Este exemplo abordou a automação de um cenário fundamental: o login de um usuário na aplicação **Probato Manager**. A aplicação de práticas como a utilização do padrão **Page Object**, que facilita a manutenção e promove a reutilização de código, destacou-se como uma abordagem essencial para a construção de testes robustos e escaláveis.

### Lessons and Benefits Observed

- **Modularidade e organização**: A separação em pacotes e a utilização de annotations simplificam a organização do projeto e aumentam a clareza do fluxo de execução dos testes.
- **Facilidade na configuração inicial**: Com a estrutura bem definida, a criação de novos testes e o gerenciamento de massas de dados tornam-se intuitivos.
- **Integração com dados e banco de dados**: O uso de datasets e scripts SQL facilita a preparação do estado inicial da aplicação, garantindo a consistência dos testes.
- **Padronização**: A adoção de convenções no design de **Suites**, **Scripts**, e **Pages** promove a padronização e reduz a curva de aprendizado para novos integrantes na equipe.

---

## **Final Checklist**

Before finalizing the implementation of a basic script in **Probato**, make sure the following points are correctly configured and functional:

- ✅ Suite and Script classes implemented with the appropriate annotations.
- ✅ Page Object classes created, with elements mapped and methods working as expected.
- ✅ Dataset files created, containing the necessary data for the tests.
- ✅ SQL pre-condition scripts correctly configured, ensuring the application's initial state.

🎉 **Your project is ready to continue test automation with Probato!**

---

## **Ready to Continue?**

Proceed to the next chapter: **Execution and Result Analysis**.