# Criação e Configuração do Projeto

Nesta seção, será detalhado como criar e configurar um projeto Maven para utilizar o **Probato**, garantindo uma estrutura base eficiente para automação de testes. O objetivo é preparar um ambiente com dependências organizadas e configurações essenciais para rodar testes automatizados de forma eficaz.

---

## **Criar um Projeto Maven**

### **Usando o terminal ou prompt de comando**

1. Abra o terminal ou prompt de comando.
2. Navegue até o diretório onde deseja criar o projeto.
3. Execute o comando abaixo:
    ```bash
    mvn archetype:generate -DgroupId=com.example.automation -DartifactId=my-project-automation -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```

    **Parâmetros:**
    
    * **groupId:** Identificador do grupo da sua organização, geralmente relacionado ao domínio (ex.: `com.example.automation`).
    * **artifactId:** Nome do projeto de automação de testes (ex.: `my-project-automation`).

    !!! note
         Caso prefira, a criação do projeto também pode ser feita diretamente pela sua IDE.

4. Após a execução, a seguinte estrutura será criada:
    ```plaintext
    my-project-automation/
    ├── src/
    │   ├── main/
    │   │   └── java/
    │   │       └── com/example/automation/App.java
    │   └── test/
    │       └── java/
    │           └── com/example/automation/AppTest.java
    └── pom.xml
    ```

### **Sobre o Maven Archetype**

O comando `mvn archetype:generate` utiliza o **Maven Archetype** para gerar um projeto com uma estrutura inicial padrão. Ele é útil para criar projetos rapidamente, incluindo diretórios e arquivos básicos.

---

## **Configurar a Estrutura de Pacotes e Pastas**

1. Abra o diretório `src/main/java/`.
2. Remova o pacote `com.*` gerado automaticamente.
3. No pacote `com.example.automation` em `src/test/java/`, remova o arquivo `AppTest.java`.
4. Crie o diretório `src/test/resources/` e adicione um arquivo chamado `configuration.yml`.

A estrutura do projeto ficará assim:

```plaintext
my-project-automation/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/example/automation/
│       │       ├── model/
│       │       ├── page/
│       │       └── usecase/
│       └── resources/
│           ├── dataset/
│           ├── sql/
│           └── configuration.yml
└── pom.xml
```

**Descrição das pastas:**

* `src/test/java/`: Local onde será implementada a automação dos testes.
* `src/test/resources/`: Pasta para armazenar configurações, massas de dados, scripts SQL, entre outros arquivos necessários para os testes.

    !!! note
         O arquivo `configuration.yml` será configurado em detalhes em seções futuras.

---

## **Adicionar Dependências ao Maven**

1. Abra o arquivo `pom.xml` localizado no diretório raiz do projeto.
2. Adicione as seguintes dependências:
    ```xml title="pom.xml"
    <project 
        xmlns="http://maven.apache.org/POM/4.0.0" 
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        
        <modelVersion>4.0.0</modelVersion>

        <groupId>com.example.automation</groupId>
        <artifactId>my-project-automation</artifactId>
        <version>1.0.0</version>

        <properties>
            <probato.version>0.1.0</probato.version>
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
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-record</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>com.probato</groupId>
                <artifactId>probato-manager</artifactId>
                <version>${probato.version}</version>
                <scope>test</scope>
            </dependency>
            <!-- Dependência do bando de dados da aplicação alvo dos testes -->
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

        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>3.0.0-M7</version>
                    <configuration>
                        <includes>
                            <include>**/*.java</include>
                        </includes>
                    </configuration>
                </plugin>
            </plugins>
        </build>

    </project>
    ```

### **Descrição das Dependências**
- **`probato-api`**, **`probato-browser`**, **`probato-dataset-csv`**, **`probato-database-sql`**, **`probato-record`**, **`probato-manager`**  : Oferece acesso às funcionalidades do **Probato**.
- **`postgresql`**: Necessário para conexão com banco de dados, então deve ser adicionada a dependência relativa ao banco de dados da aplicação alvo dos testes.
- **`junit-jupiter`**: Necessário para executar os testes com o JUnit 5.

    !!! note
         Outras dependências serão adicionadas conforme avançamos no tutorial.

---

## Adicionando e configurando o Probato 

1. No arquivo `configuration.yml` adicionar as configurações abaixo
```yaml title="configuration.yml"


execution:

   target:
      url: http://localhost:8099
      version: 0.0.0

   delay:
      waitingTimeout: 5000
      actionInterval: 500
      
   video:
      enabled: true
      frameRate: 1000
      quality: MEDIUM
      
   manager:
      submit: true
      url: http://localhost:8080
      token: [TOKEN]

browsers:

-  type: CHROME
   headless: false
   dimension:
      mode: FULLSCREEN

-  type: FIREFOX
   headless: false
   dimension:
      mode: MAXIMIZED

-  type: EDGE
   headless: false
   dimension:
      mode: CUSTOM
      width: 1256
      height: 1018

datasources:

   probato:
      url: jdbc:postgresql://localhost:5444/testano
      driver: org.postgresql.Driver
      username: root
      password: root

```
  **Propriedades:**
    * **execution.delay.waitingTimeout:**   
    Tempo de espera máximo para execução de ação.
    * **execution.delay.actionInterval:**   
    Intervalo de tempo entre ações a serem executadas.
    * **browsers.[*].type:**   
    Informa qual o navegador será executado.
    * **browsers.[*].headless:**   
    Por padrão é `false`. Se `true` a janela do browser ficará invisível durante a execução.
    * **browsers.[*].dimension.mode:**   
    Por padrão é definido como `MAXIMIZED`, tem valores possíveis `FULLSCREEN`, `MAXIMIZED` e `CUSTOM`. Se informado `CUSTOM`, as propriedades `width` e `height` serão obrigatórias.
    * **browsers.[*].dimension.width:**  
    Informa qual a dimensão de largura o navegador será executado.
    * **browsers.[*].dimension.height:**   
    Informa qual a dimensão de altura o navegador será executado.
    * **datasources.[nome]:**   
    Informa o nome do recurso que será acessado.
    * **datasources.[nome].url:**   
    Informa a URL do recurso que será acessado.
    * **datasources.[nome].driver:**   
    Informa o driver de conexão para o recurso que será acessado.
    * **datasources.[nome].schema:**   
    Informa o schema para o recurso que será acessado.
    * **datasources.[*].dimension.width:**  
    Informa o usuário para o recurso que será acessado.
    * **datasources.[*].dimension.height:**   
    Informa a senha para o recurso que será acessado.

---

## **Checklist Final**

Antes de seguir para a próxima seção, confirme se tudo foi configurado:

- ✅ Projeto Maven criado (`my-project-automation`).
- ✅ Estrutura de pastas ajustada conforme o exemplo.
- ✅ Arquivo `configuration.yml` criado em `src/test/resources/`.
- ✅ Dependências adicionadas ao arquivo `pom.xml`.
- ✅ Configurações adicionadas ao arquivo `configuration.yml`.

🎉 **Seu projeto está pronto para começar a automação de testes com o Probato!**

---

## **Pronto para continuar?**

Siga para o próximo capítulo: **Implementação de Roteiros Básicos**.