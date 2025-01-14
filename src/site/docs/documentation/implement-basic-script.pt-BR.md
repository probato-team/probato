# Implementação de roteiro básico

Nessa seção será implementado um script de testes básico. O **Probato** adota uma estrutura modular que inclui scripts, procedures, page objects e test suites, vamos iniciar pelo nosso test suite. Para exemplificar a automação de uma funcionalidade real, seguiremos com o desenvolvimento da automação da aplicação **Probato Manager**. O **Probato Manager** possui como tela principal a página de login, então partiremos desta funcionalidade para o desenvolvimento dos testes automatizados: **Efetuar Login**

## Estrutura base da aplicação

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

## Implementado classe Suite

A _Suite_ consiste em um agrupamento organizado de scripts de teste destinados a validar funcionalidades relacionadas ou específicas de um sistema. A _Suite_ é essencial para estruturar e gerenciar a execução de múltiplos testes, garantindo que eles sejam executados de forma eficiente e em uma ordem lógica, quando necessário. Vamos criar nossa suite de testes.

1. No pacote `org.probato.manager.usecase` vamos criar o novo pacote `UC01` .
2. No pacote `org.probato.manager.usecase.UC01` vamos criar a classe `UC01_PerformLogin.java`.
3. Na classe `UC01_PerformLogin.java` vamos implementar o código abaixo.
```java title="UC01_PerformLogin.java" linenums="1"
package org.probato.manager.usecase.UC01;

import org.probato.api.Suite;
import org.probato.api.TestSuite;

@Suite(
  code = "UC01", 
  name = "Perform login", 
  description = "This feature aims to allow the user to login to this application")
class UC01_PerformLogin implements TestSuite {
  
}
```
4. Caso seja executado o teste no estado atual, será recebido a mensagem informando que a suite deve possuir pelo menos 1 caso de teste.

## Implementado classe Script

1. No pacote `org.probato.manager.usecase.UC01` vamos criar o novo pacote `script` .
2. No pacote `org.probato.manager.usecase.UC01.script` vamos criar a classe `UC01TC01_PerformLoginSuccessfully.java`.
3. Na classe `UC01TC01_PerformLoginSuccessfully.java` vamos implementar o código abaixo.
```java title="UC01TC01_PerformLoginSuccessfully.java" linenums="1"
package org.probato.manager.automation.usecase.UC01.script;

import org.probato.api.Script;

@Script(
  code = "UC01TC01", 
  name = "Perform login successfully", 
  description = "This script aims to validate the user's login in the application successfully")
public class UC01TC01_PerformLoginSuccessfully {

} 
```
4. Na classe `UC01_PerformLogin.java` vamos adicionar a implementação abaixo.
```java title="UC01_PerformLogin.java" linenums="1" hl_lines="13-14"
package org.probato.manager.usecase.UC01;

import org.probato.api.Suite;
import org.probato.api.TestSuite;
import org.probato.manager.automation.usecase.UC01.script.UC01TC01_PerformLoginSuccessfully;

@Suite(
  code = "UC01", 
  name = "Perform login", 
  description = "This feature aims to allow the user to login to this application")
class UC01_PerformLogin implements TestSuite {

  @TestCase
  private UC01TC01_PerformLoginSuccessfully uc01tc01;

}
```
5. Caso seja executado o teste no estado atual, será recebido a mensagem informando que o script deve possuir pelo menos 1 procedure.

## Implementado método procedure 

1. Na classe `UC01TC01_PerformLoginSuccessfully.java` vamos implementar o método procedure abaixo.
```java title="UC01TC01_PerformLoginSuccessfully.java" linenums="1" hl_lines="12-15"

package org.probato.manager.automation.usecase.UC01.script;

import org.probato.api.Procedure;
import org.probato.api.Script;

@Script(
  code = "UC01TC01", 
  name = "Perform login successfully", 
  description = "This script aims to validate the user's login in the application successfully")
public class UC01TC01_PerformLoginSuccessfully {

  @Procedure
  private void procedure() {
    System.out.println("Run method procedure");
  }
  
}
```

**Obs:**    

- Mesmo que adicionado conteúdo aos métodos de procedure não será executado, pois precisamos de algum executor instalado na nossa aplicação de automações.
- Tanto a implementação de procedure como método ou como atributo, caso seja executado o teste no estado atual o teste não terá resultado.    
  ![execute test based](/documentation/img/execute-test-based.png)
