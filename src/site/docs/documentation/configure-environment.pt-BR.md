# Configuração de Ambiente

Nesta seção, vamos configurar o ambiente de desenvolvimento para usar o **Probato**. Este framework utiliza a linguagem de programação Java, então será necessário instalar o Java Development Kit (JDK), configurar variáveis de ambiente e instalar uma IDE para começar a desenvolver aplicações Java.

---

## **Componentes Necessários**

Antes de começar, certifique-se de que terá os seguintes itens configurados:

- **Java Development Kit (JDK)**: Para compilar e executar código Java.
- **Maven**: Para gerenciamento de dependências e automação de builds.
- **IDE (Ambiente de Desenvolvimento Integrado)**: Para facilitar a escrita e depuração do código.

> ⚠️ **Nota:** É necessário usar o JDK versão 11 ou superior.

No final desta configuração, você terá todos os componentes prontos para desenvolver e executar testes no **Probato**.

---

## **1. Baixar e Instalar o Java Development Kit (JDK)**

O JDK é um conjunto de ferramentas para desenvolvimento e execução de aplicações Java.

### **Baixar o JDK**

1. Acesse um dos links abaixo para baixar o JDK:
    - [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
    - [OpenJDK](https://jdk.java.net/)
    - [Adoptium](https://adoptium.net/)
    - [Amazon Corretto](https://aws.amazon.com/pt/corretto/)

2. Escolha a versão desejada (obrigatoriamente 11 ou superior).
3. Baixe o arquivo no formato `.zip` e extraia para um diretório, como `C:\\dev\\java\\jdk<versão>`.

### **Configurar Variável de Ambiente**

1. Pressione `Win + S`, procure por **Variáveis de Ambiente** e clique em "Editar as variáveis de ambiente do sistema".
2. Na aba **Avançado**, clique em **Variáveis de Ambiente**.
3. Em **Variáveis de Sistema**, clique em **Novo** e adicione:
    - Nome: `JAVA_HOME`
    - Valor: `C:\\dev\\java\\jdk<versão>`
4. Edite a variável `Path` e adicione `%JAVA_HOME%\\bin` à lista.
5. Clique em OK para salvar.

### **Verificar a Instalação**

1. Abra o Prompt de Comando (pressione `Win + R`, digite `cmd` e pressione Enter).
2. Execute o comando:
    ```bash
    java --version
    ```
3. A saída deverá mostrar a versão do Java instalado, como:
    ```bash
    java version "11.0.X"
    Java(TM) SE Runtime Environment (build 11.0.X)
    Java HotSpot(TM) 64-Bit Server VM (build 11.0.X)
    ```
    ✅ **Se você vir essa saída, o Java está instalado e configurado corretamente!**

---

## **2. Instalar o Maven**

### **Baixar e Instalar**

1. Acesse o site oficial do [Apache Maven](https://maven.apache.org/download.cgi).
2. Baixe o arquivo binário ZIP e extraia para `C:\\dev\\maven`.

### **Configurar Variável de Ambiente**

1. Pressione `Win + S`, procure por **Variáveis de Ambiente** e clique em "Editar as variáveis de ambiente do sistema".
2. Em **Variáveis de Sistema**, clique em **Novo** e adicione:
    - Nome: `MAVEN_HOME`
    - Valor: `C:\\dev\\maven`
3. Edite a variável `Path` e adicione `%MAVEN_HOME%\\bin` à lista.
4. Clique em OK para salvar.

### **Verificar a Instalação**

1. Abra o Prompt de Comando e execute:
    ```bash
    mvn -version
    ```
2. A saída deverá ser semelhante a:
    ```bash
    Apache Maven 3.X.X
    Maven home: C:\\dev\\maven
    Java version: 11.0.X, vendor: Oracle Corporation
    OS name: "windows 11", version: "10.0", arch: "amd64"
    ```
    ✅ **Se você vir essa saída, o Maven está instalado e configurado corretamente!**

---

## **3. Instalar uma IDE**

Uma IDE facilita a escrita, execução e depuração do código Java. Aqui estão algumas opções:

### **Eclipse IDE**

1. Baixe o [Eclipse](https://www.eclipse.org/downloads/).
2. Instale o "Eclipse IDE for Java Developers".
3. Configure um workspace e comece a criar projetos.

### **IntelliJ IDEA**

1. Baixe o [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
2. Escolha a versão Community (gratuita).
3. Durante a primeira execução, configure o caminho do JDK.

### **VS Code**

1. Baixe o [VS Code](https://code.visualstudio.com/).
2. Instale a extensão "Language Support for Java™ by Red Hat".
3. Configure o JDK nas preferências do editor.

---

## **Checklist Final**

Antes de seguir para a próxima seção, confirme se tudo foi configurado:

- ✅ JDK instalado e configurado (`java --version` funcionando).
- ✅ Maven instalado e configurado (`mvn -version` funcionando).
- ✅ IDE instalada e configurada.

🎉 **Seu ambiente está pronto para usar o Probato!**
