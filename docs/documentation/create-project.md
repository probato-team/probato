---
layout: documentation
description: Guia do usuário - Criando projeto
navigation:
    previous:
        name: Configurando ambiente 
        link: /documentation/configure-environment
    next: 
        name: Configurando projeto
        link: /documentation/configure-project
---

Nesta seção vamos criar o projeto base para o desenvolvimento da automação dos testes funcionais com **Probato**. Este Framework é implementado utilizando linguagem de programação Java, então será necessário a instalação do Java Development Kit (JDK), configuração de variáveis de ambiente, e instalação de uma IDE para começar a desenvolver aplicações Java.

## **1. Criar um projeto Maven**

1. Abra o terminal ou prompt de comando.
2. Navegue até o diretório onde deseja criar o projeto.
3. Execute o comando:
    ```bash
    mvn archetype:generate -DgroupId=com.example.automation -DartifactId=my-project -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```
    * **groupId:** Define o identificador do grupo, geralmente relacionado ao domínio (ex.: com.example.automation).
    * **artifactId:** Nome do projeto (ex.: my-project).

    **Obs:** A criação do projeto também pode ser feita pela IDE.
4. Após a execução, será criada a seguinte estrutura:
    ```bash
    my-project/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/automation
    │   │   │       └── App.java
    │   └── test/
    │       └── java/
    │           └── com/example/automation
    │               └── AppTest.java
    └── pom.xml
    ```

## **2. Configurar a estrutura de pacotes e pastas**

1. Abra o diretório `src/main/java/`.
2. Na pasta `src/main/java/` remova o pacote `com.*`
3. Na pasta `src/test/java/` no pacote `com.example.automation` remova o arquivo `AppTest.java`
4. Crie o diretório `src/test/resources/` o arquivo chamado `configuration.yaml`.
5. O projeto deve ficar com estrutura abaixo:
    ```bash
    my-project/
    ├── src/
    │   └──  test/
    │       ├── java/
    │       |   └── com/example/automation
    │       |       ├── model/
    │       |       ├── page/
    │       |       └── usecase/
    |       └── resources/
    │           ├── dataset/
    │           ├── sql/
    |           └── configuration.yaml
    └── pom.xml
    ```
    * **src/test/java/com/example/automation/*:** Pacote que será implementada a automação.
    * **src/test/java/com/resources/automation/*:** Pasta que será armazenada arquivos de configurações, massa de dados e sql.

## **Passo 4: Adicionar dependências ao Maven**

1. Abra o arquivo `pom.xml` localizado no diretório raiz do projeto e adicione as dependências necessárias.
    ```xml
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
            <!-- Dependência do JUnit 5 -->
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter</artifactId>
                <version>5.9.3</version>
                <scope>test</scope>
            </dependency>
        </dependencies>

    </project>
    ````
    **Obs:** Serão adicionada as demais dependências do **Probato** no decorrer do tutorial

