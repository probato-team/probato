# Implementando execução por browser

Nesta seção, será implementada a execução das ações de teste nos navegadores. Essa etapa é essencial para que o framework interaja com a interface gráfica da aplicação em teste, simulando o comportamento do usuário final. Será demonstrado como configurar e utilizar os métodos disponíveis para realizar ações como clicar em elementos, preencher campos, navegar entre páginas, e validar informações exibidas na interface.

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
3. Na classe `UC01TC01_PerformLoginSuccessfully.java` vamos acionar a implementação abaixo.
```java title="PerformLoginProcedure.java" linenums="1" hl_lines="18-19 21-22 24-31"
package org.probato.manager.automation.usecase.UC01.script;

import org.probato.manager.automation.page.DashboardPage;
import org.probato.manager.automation.page.LoginPage;

import org.probato.api.Page;
import org.probato.api.Postcondition;
import org.probato.api.Precondition;
import org.probato.api.Procedure;
import org.probato.api.Script;

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
	private void procedure() {
		loginPage.selectEnglishTranslate();
		loginPage.checkPage();
		loginPage.fillEmail("feliperudolfe@probato.org");
		loginPage.fillPassword("p@ssword");
		loginPage.pressAccessButton();
		dashboardPage.checkPage();
	}

}
```
4. Agora caso seja executado o teste no estado atual veremos que cada um dos browser será aberto e veremos que os passos implementados para o teste serão executados.

## **Considerações finais**

Conseguimos implementar com sucesso nosso primeiro caso de uso, alcançando os objetivos iniciais. Contudo, identificamos alguns pontos de atenção e oportunidades de melhoria que abordaremos nas próximas seções desse guia para tornar nossa solução mais robusta e eficiente.

**Observação:**   
Com base no estado atual da implementação, destacamos os seguintes aspectos que podem ser aprimorados:

### **Dados de entrada estáticos:**
Os dados utilizados no teste estão fixos no código, o que limita a capacidade de explorar a funcionalidade com diferentes conjuntos de dados. Adicionar suporte para parametrização de dados de entrada, como arquivos CSV ou JSON, permitiria validar a robustez e flexibilidade do sistema com cenários variados.

### **Ausência de pré-condições e pós-condições:**
Atualmente, todos os procedimentos estão sendo validados diretamente no método procedure. Isso dificulta a distinção entre erros de pré-condição e falhas no escopo da funcionalidade principal do teste. A implementação de pré-condições e pós-condições ajudaria a isolar possíveis problemas, facilitando a análise e o diagnóstico de falhas.

### **Dependência de dados na base da aplicação:**
O script de teste atual exige que um e-mail e senha estejam previamente cadastrados no banco de dados da aplicação para ser executado com sucesso. Essa dependência pode ser problemática e tornar o teste menos confiável. É recomendável implementar um mecanismo de setup automatizado para criar os dados necessários antes da execução do teste, eliminando essa dependência manual ou até de outros cenários de testes.

Ao abordar essas melhorias, estaremos fortalecendo a estrutura dos testes, garantindo maior clareza na análise de falhas e aumentando a cobertura e a confiabilidade dos casos de teste.

