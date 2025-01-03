# Criação do projeto

Nesta seção vamos criar o projeto base para o desenvolvimento da automação dos testes funcionais com **Probato**. O **Probato** foi implementado utilizando linguagem de programação Java, então será necessário a instalação do Java Development Kit (JDK), configuração de variáveis de ambiente, e instalação de uma IDE para começar a desenvolver aplicações Java.

## **Criar um projeto Maven**

1. Abra o terminal ou prompt de comando.
2. Navegue até o diretório onde deseja criar o projeto.
3. Execute o comando abaixo
    ```bash
    mvn archetype:generate -DgroupId=com.example.automation -DartifactId=my-project-automation -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```
    * **groupId:** Defina o identificador do grupo da sua organização, que é geralmente relacionado ao domínio (ex.: com.example.automation).
    * **artifactId:** Defina o nome do projetode automação do testes (ex.: my-project-automation).

    **Obs:** A criação do projeto também pode ser feita pela IDE.
4. Após a execução, será criada a seguinte estrutura:
    ```bash title="Estutura"
    my-project-automation/
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

## **Configurar a estrutura de pacotes e pastas**

1. Abra o diretório `src/main/java/`.
2. Na pasta `src/main/java/` remova o pacote `com.*`
3. Na pasta `src/test/java/` no pacote `com.example.automation` remova o arquivo `AppTest.java`
4. Crie o diretório `src/test/resources/` o arquivo chamado `configuration.yml`.
5. O projeto deve ficar com estrutura abaixo:
    ```bash title="Estrutura"
    my-project-automation/
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
    |           └── configuration.yml
    └── pom.xml
    ```
    * **src/test/java/com/example/automation/*:** No pacote que será implementada a automação.
    * **src/test/java/com/resources/automation/*:** Na pasta que será armazenada arquivos de configurações, massa de dados, sql, etc.

## **Adicionar dependências ao Maven**

1. Abra o arquivo `pom.xml` localizado no diretório raiz do projeto e adicione as dependências necessárias.     
    ```xml title="pom.xml"
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
    ```
    **Obs:** Serão adicionada as demais dependências do **Probato** no decorrer desse tutorial

