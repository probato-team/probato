# Criação do Projeto

Nesta seção, vamos criar o projeto base para o desenvolvimento da automação de testes funcionais com **Probato**. Configuraremos um projeto Maven, ajustaremos a estrutura de pacotes e pastas, e adicionaremos as dependências iniciais necessárias para começar a utilizar o framework.

---

## **1. Criar um Projeto Maven**

### **Usando o terminal ou prompt de comando**

1. Abra o terminal ou prompt de comando.
2. Navegue até o diretório onde deseja criar o projeto.
3. Execute o comando abaixo:
    ```bash
    mvn archetype:generate -DgroupId=com.example.automation -DartifactId=my-project-automation -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```

    **Parâmetros:**
    - **groupId:** Identificador do grupo da sua organização, geralmente relacionado ao domínio (ex.: `com.example.automation`).
    - **artifactId:** Nome do projeto de automação de testes (ex.: `my-project-automation`).

    > **Nota:** Caso prefira, a criação do projeto também pode ser feita diretamente pela sua IDE.

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

## **2. Configurar a Estrutura de Pacotes e Pastas**

1. Abra o diretório `src/main/java/`.
2. Remova o pacote `com.*` gerado automaticamente.
3. No diretório `src/test/java/com/example/automation/`, remova o arquivo `AppTest.java`.
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

* `src/test/java/com/example/automation/`: Local onde será implementada a automação dos testes.
* `src/test/resources/`: Pasta para armazenar configurações, massas de dados, scripts SQL, entre outros arquivos necessários para os testes.
> Nota: O arquivo configuration.yml será configurado em detalhes em seções futuras.

---

## **3. Adicionar Dependências ao Maven**

1. Abra o arquivo `pom.xml` localizado no diretório raiz do projeto.
2. Adicione as seguintes dependências:
    ```xml
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

### **Descrição das Dependências**
- **`probato-api`**: Oferece acesso às funcionalidades do **Probato**.
- **`junit-jupiter`**: Necessário para executar os testes com o JUnit 5.

> **Nota:** Outras dependências serão adicionadas conforme avançamos no tutorial.

---

## **Checklist Final**

Antes de seguir para a próxima seção, confirme se tudo foi configurado:

- ✅ Projeto Maven criado (`my-project-automation`).
- ✅ Estrutura de pastas ajustada conforme o exemplo.
- ✅ Dependências adicionadas ao arquivo `pom.xml`.
- ✅ Arquivo `configuration.yml` criado em `src/test/resources/`.

🎉 **Seu projeto está pronto para começar a automação de testes com o Probato!**