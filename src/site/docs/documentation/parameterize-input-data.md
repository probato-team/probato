# Parametrizando dados de entrada

Nesta seção, abordaremos a parametrização de massa de dados utilizando _datasets_ para conjuntos de dados de entrada. Exploraremos como configurar e estruturar esses arquivos, além de demonstrar o processo de injeção dos dados diretamente nos scripts de teste. Esse recurso é essencial para criar testes dinâmicos e flexíveis, permitindo validar a funcionalidade com diversos cenários e melhorar a abrangência da automação.

## Adicionando dependência para dataset

1. No arquivo `pom.xml` pode ser adicionada a dependência
```xml title="pom.xml" hl_lines="30-35"
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

## Implementando classe de mapeamento de entrada

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

## Adicionando dataset ao script

1. Na classe `UC01TC01_PerformLoginSuccessfully.java` vamos acionar a implementação abaixo.
```java title="PerformLoginProcedure.java" linenums="1" hl_lines="12 26 29-30"
package org.probato.manager.automation.usecase.UC01.script;

import org.probato.manager.automation.model.LoginModel;
import org.probato.manager.automation.page.DashboardPage;
import org.probato.manager.automation.page.LoginPage;

import org.probato.api.Dataset;
import org.probato.api.Page;
import org.probato.api.Procedure;
import org.probato.api.Script;

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
2. Agora caso seja executado o teste no estado atual veremos que será realizada um execução para cada uma das linhas de dados acrescentados no arquivo CSV para cada um dos browsers configurados.

    ![execute test based](/documentation/img/browser-execution-dataset.png)

## **Considerações finais**

Conseguimos implementar com sucesso a utilização de _datasets_ no nosso primeiro script. Apesar de todas as execuções terem apresentado falhas, foi possível observar que o comportamento esperado foi alcançado: os testes foram executados de forma iterativa, passando cada linha do arquivo CSV como entrada para o script e executando para cada um dos browsers configurados.

Nosso próximo objetivo é garantir que as pré-condições associadas a cada conjunto de dados sejam verificadas e atendidas antes do início da execução de cada teste. Isso assegurará que o ambiente de teste esteja devidamente preparado, minimizando falhas relacionadas a estados inadequados da aplicação ou inconsistências nos dados de entrada.