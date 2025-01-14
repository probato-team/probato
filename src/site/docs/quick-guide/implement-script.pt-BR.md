# Implementação de roteiro básico

Nessa seção será implementado um script de testes básico. O **Probato** adota uma estrutura modular que inclui scripts, procedures, page objects e test suites. Para exemplificar a automação de uma funcionalidade real, seguiremos com o desenvolvimento da automação da aplicação **Probato Manager**. O **Probato Manager** possui como tela principal a página de login, então partiremos desta funcionalidade para o desenvolvimento dos testes automatizados: **Efetuar Login**

---

## **Estrutura base da aplicação**

Vamos partir do princípio que o projeto possui a seguinte estrutura abaixo:

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

## **Implementando a classe Suite**

1. No pacote `org.probato.manager.usecase` vamos criar o novo pacote `UC01` .
2. No pacote `org.probato.manager.usecase.UC01` vamos criar a classe `UC01_PerformLogin.java`.
3. Na classe `UC01_PerformLogin.java` vamos implementar o código abaixo.
```java title="UC01_PerformLogin.java" linenums="1"
@SQL(
	datasource = "probato", 
	scriptPath = { "sql/init/init.sql" })
@Suite(
	code = "UC01", name = "Perform login", 
	description = "This feature aims to allow the user to login to this application")
class UC01_PerformLogin implements TestSuite {
	
	@TestCase
	private UC01TC01_PerformLoginSuccessfully uc01tc01;

  // Adicioanr outros casos de teste para suite

}
```

---

## **Implementando a classe Script**

1. No pacote `org.probato.manager.usecase.UC01` vamos criar o novo pacote `script` .
2. No pacote `org.probato.manager.usecase.UC01.script` vamos criar a classe `UC01TC01_PerformLoginSuccessfully.java`.
3. Na classe `UC01TC01_PerformLoginSuccessfully.java` vamos implementar o código abaixo.
```java title="UC01TC01_PerformLoginSuccessfully.java" linenums="1"
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

## **Implementando Page Object**

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

## **Criando arquivo dataset**

1. Na pasta `src/test/resources/dataset` vamos criar a nova pasta `UC01`.
2. Na pasta `src/test/resources/dataset/UC01` vamos criar o arquivo chamado `UC01TC01.csv`.
3. No arquivo `UC01TC01.csv` vamos adicionar o conteúdo abaixo.
``` title="UC01TC01.csv" linenums="1"
EMAIL, PASSWORD
user01@probato.org, p@ssword
user02@probato.org, p@ssword123
user02@probato.org, p@ssword321

```

### Implementando classe de mapeamento de entrada

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

## **Criando arquivos SQL**

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

## **Considerações finais**

Conseguimos implementar com sucesso um roteiro básico utilizando o **Probato**. Ao longo desta seção, foi possível demonstrar como o framework proporciona uma estrutura organizada e modular para a automação de testes, abrangendo desde a criação de **Suites**, **Scripts**, e **Page Objects** até a utilização de massas de dados e pré-condições com scripts SQL.

Este exemplo abordou a automação de um cenário fundamental: o login de um usuário na aplicação **Probato Manager**. A aplicação de práticas como a utilização do padrão **Page Object**, que facilita a manutenção e promove a reutilização de código, destacou-se como uma abordagem essencial para a construção de testes robustos e escaláveis.

### Aprendizados e benefícios observados

- **Modularidade e organização**: A separação em pacotes e a utilização de annotations simplificam a organização do projeto e aumentam a clareza do fluxo de execução dos testes.
- **Facilidade na configuração inicial**: Com a estrutura bem definida, a criação de novos testes e o gerenciamento de massas de dados tornam-se intuitivos.
- **Integração com dados e banco de dados**: O uso de datasets e scripts SQL facilita a preparação do estado inicial da aplicação, garantindo a consistência dos testes.
- **Padronização**: A adoção de convenções no design de **Suites**, **Scripts**, e **Pages** promove a padronização e reduz a curva de aprendizado para novos integrantes na equipe.

---

## **Checklist Final**

Antes de finalizar a implementação de um roteiro básico no **Probato**, verifique os seguintes pontos para garantir que tudo está corretamente configurado e funcional:

- ✅ Classes de Suite e Script implementadas com as annotations adequadas.
- ✅ Classes de Page Object criadas, com elementos mapeados e métodos funcionando conforme esperado.
- ✅ Arquivos de dataset criados, contendo os dados necessários para os testes.
- ✅ Scripts SQL de pré-condição configurados corretamente, garantindo o estado inicial da aplicação.

🎉 **Seu projeto está pronto para continuar a automação de testes com o Probato!**

---

## **Pronto para continuar?**

Siga para o próximo capítulo: **Execução e Análise de Resultados**.