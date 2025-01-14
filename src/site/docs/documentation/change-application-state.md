# Alterando estado da aplicação

Nesta seção, exploraremos como alterar o estado da aplicação para garantir que as pré-condições necessárias no banco de dados sejam satisfeitas antes do início da execução do objetivo principal dos testes. Abordaremos técnicas para configurar e manipular dados no banco de forma controlada e consistente, garantindo que os testes sejam executados em um ambiente previsível e alinhado com os cenários desejados.

Além disso, discutiremos boas práticas para realizar essas alterações de maneira automatizada, minimizando interferências externas e otimizando a preparação do ambiente de teste.

## Adicionando dependência para database

1. No arquivo `pom.xml` pode ser adicionada a dependência
```xml title="pom.xml" hl_lines="36-41 43-48"
<project 
    xmlns="http://maven.apache.org/POM/4.0.0" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.exemplo.automation</groupId>
    <artifactId>my-project</artifactId>
    <version>1.0.0</version> <!-- Versão atual do projeto alvo dos testes -->

    <properties>
        <probato.version>0.1.0</testano.version>
    </properties>

    <dependencies>
        <!-- Dependências do Probato -->
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
        <!-- Dependência do banco de dados da aplicação -->
        <dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<version>42.7.3</version>
			<scope>test</scope>
		</dependency>
        <!-- Dependência do JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.3</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

## Configurando datasource

1. No arquivo `configuration.yml` adicionar as configurações abaixo
```yaml title="configuration.yml"

datasources:

   probato:
      url: jdbc:postgresql://localhost:5444/testano
      driver: org.postgresql.Driver
      username: root
      password: root

```
  **Propriedades:**
    * **datasources.[nome]:**   
    Informa o nome do recurso que será acessado.
    * **datasources.[nome].url:**   
    Informa a URL do recurso que será acessado.
    * **datasources.[nome].driver:**   
    Informa o driver de conexão para o recurso que será acessado.
    * **datasources.[nome].schema:**   
    Informa o schema para o recurso que será acessado..
    * **datasources.[*].dimension.width:**  
    Informa o usuário para o recurso que será acessado.
    * **datasources.[*].dimension.height:**   
    Informa a senha para o recurso que será acessado.
2. Caso seja executado o teste no estado atual o teste passará com sucesso. Note que agora na lista de execuções relizadas tem breve descrição do dimensionamento no qual o navegador aberto.
    
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

## **Adicionando SQL ao suite**

1. Na classe `UC01_PerformLogin.java` vamos acionar a implementação abaixo.
```java title="PerformLoginProcedure.java" linenums="1" hl_lines="10-14"
package org.probato.manager.automation.usecase.UC01;

import org.probato.manager.automation.usecase.UC01.script.UC01TC01_PerformLoginSuccessfully;

import org.probato.api.SQL;
import org.probato.api.Suite;
import org.probato.api.TestCase;
import org.probato.api.TestSuite;

@SQL(
	datasource = "probato", 
	scriptPath = { 
        "sql/init/init.sql" 
    })
@Suite(
	code = "UC01", name = "Perform login", 
	description = "This feature aims to allow the user to login to this application")
class UC01_PerformLogin implements TestSuite {
	
	@TestCase
	private UC01TC01_PerformLoginSuccessfully uc01tc01;

}
```

## **Adicionando SQL ao script**

1. Na classe `UC01TC01_PerformLoginSuccessfully.java` vamos acionar a implementação abaixo.
```java title="PerformLoginProcedure.java" linenums="1" hl_lines="13-17"
package org.probato.manager.automation.usecase.UC01.script;

import org.probato.manager.automation.model.LoginModel;
import org.probato.manager.automation.page.DashboardPage;
import org.probato.manager.automation.page.LoginPage;

import org.probato.api.Dataset;
import org.probato.api.Page;
import org.probato.api.Procedure;
import org.probato.api.SQL;
import org.probato.api.Script;

@SQL(
	datasource = "probato", 
	scriptPath = { 
        "sql/user/insert-user.sql" 
    })
@Dataset("dataset/UC01/UC01TC01.csv")
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


7. Agora caso seja executado o teste no estado atual veremos que a pré-condição será satisfeitas e os testes serão executados com sucesso.

    ![execute test based](/documentation/img/sql-execution-database.png)

## **Considerações finais**

Conseguimos implementar com sucesso o uso de SQL para alterar o estado da aplicação, atendendo assim às pré-condições necessárias para a execução dos testes propostos. Essa abordagem garante que cada teste possa começar em um estado controlado e previsível, tornando-os independentes entre si. Além disso, essa independência reduz a interferência entre os testes e melhora a confiabilidade dos resultados, permitindo identificar falhas específicas de forma mais clara.

**Observações:**        

Partiremos do princípio de que a base de dados estará sempre vazia no início de cada execução, para fins de demonstração para teste guia. No entanto, você pode pressupor que haverá uma massa de dados inicial específica para configurar o ambiente de testes. Essa configuração será feita por meio de um arquivo `init.sql`, responsável por adicionar os registros necessários para a execução dos seus testes.

A definição e o conteúdo desse arquivo ficam a seu critério, de acordo com a análise dos requisitos de cada caso de teste. É importante garantir que o `init.sql` inclua todos os dados essenciais para atender às pré-condições dos testes, promovendo um ambiente consistente e adequado para a validação das funcionalidades.

Além disso, recomendamos adotar boas práticas na criação desse arquivo, como:

* Organizar os scripts de maneira clara e modular.
* Incluir comentários que expliquem a finalidade de cada registro.
* Garantir que o script seja idempotente, ou seja, possa ser executado repetidamente sem gerar inconsistências.

Com essa abordagem, você terá maior controle sobre o estado inicial do banco de dados e poderá executar seus testes de maneira confiável e previsível.

