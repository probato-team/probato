# Configuração de ambiente

Nesta seção vamos configurar o ambiente de desenvolvimento para usar o **Probato**. Este Framework utiliza linguagem de programação Java, então será necessário a instalação do Java Development Kit (JDK), configuração de variáveis de ambiente, e instalação de uma IDE para começar a desenvolver aplicações Java.

## **Baixar e instalar o Java Development Kit (JDK)**

O JDK é um conjunto de ferramentas para o desenvolvimento e execução de aplicações Java.

### Baixar o JDK

1. Acesse o site oficial
    - Acesse a página de downloads do <a href="https://www.oracle.com/java/technologies/javase-downloads.html" target="_blank" >Oracle</a> ou <a href="https://jdk.java.net/" target="_blank" >OpenJDK</a> ou uma versão confiável como o <a href="https://adoptium.net/" target="_blank" >Adoptium</a> ou <a href="https://aws.amazon.com/pt/corretto/?filtered-posts.sort-by=item.additionalFields.createdDate&filtered-posts.sort-order=desc" target="_blank" >Amazon Corretto</a>.


2. Escolha a versão desejada
    - Identifique a versão do JDK que você deseja instalar, é mandatório que seja instalada versão 11 ou superior.
    - Clique no link correspondente à versão.

3. Baixe o arquivo
    - Escolha a distribuição e baixe o arquivo no formato `.zip`.
    - Após o download, extraia o arquivo `.zip` para uma pasta de sua escolha, como exemplo `C:dev\java\jdk<versão>`

### Configurar variável de ambiente

1. Pressione `Win + S`, procure por Variáveis de Ambiente e clique em Editar as variáveis de ambiente do sistema.
2. Na aba **Avançado**, clique em **Variáveis de Ambiente**.
3. Na seção **Variáveis de Sistema**, clique em **Novo**
4. Adicione uma variável de ambiente chamada `JAVA_HOME` apontando para o diretório do JDK, como exemplo `C:dev\java\jdk<versão>`.
5. Clique em OK para salvar.
6. Busque na lista de **Variáveis de Sistema** a variável `Path` e clique em **Editar**. 
7. Inclua na lista o caminho `%JAVA_HOME%\bin` na variável `Path`.
8. Clique em OK para salvar.

### Verificar a instalação

1. Abra o Prompt de Comando (pressione `Win + R`, digite cmd e pressione Enter).
2. Digite o comando abaixo
    ```bash
    java --version
    ```
3. O terminal deverá exibir a versão do Java instalado
    ```bash
    java version "XX.XX"
    Java(TM) SE Runtime Environment (build XX.XX)
    Java HotSpot(TM) 64-Bit Server VM (build XX.XX)
    ```
Se você vê essa saída, o Java está instalado e configurado corretamente! 🎉

## **Instalar o Maven**

### Baixar e instalar

1. Acesse o site do <a href="https://maven.apache.org/download.cgi" target="_blank" >Apache Maven</a>.
2. Faça o download do arquivo binário ZIP.
3. Extraia para um local, como `C:\dev\maven`.

### Configurar variáveis de ambiente

1. Pressione `Win + S`, procure por Variáveis de Ambiente e clique em Editar as variáveis de ambiente do sistema.
2. Na aba **Avançado**, clique em **Variáveis de Ambiente**.
3. Na seção **Variáveis de Sistema**, clique em **Novo**
4. Adicione uma variável de ambiente chamada `MAVEN_HOME` apontando para o diretório do Maven.
5. Inclua o caminho `%MAVEN_HOME%\bin` na variável `Path`.
6. Clique em OK para salvar.

### Verificar a instalação

1. No prompt de comando, execute   
    ```bash
    mvn -version
    ```
2. Após a execução deve exibir saída semelhante a abaixo     
    ```bash
    Apache Maven X.X.X
    Maven home: C:\path\to\apache-maven-X.X.x
    Java version: X.X.X, vendor: Oracle Corporation, runtime: C:\path\to\jdk-X.X.X
    Default locale: pt_BR, platform encoding: UTF-8
    OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
    ```
    O Maven foi instalado com sucesso! 🎉

## **Instalar uma IDE**    

Uma IDE (Ambiente de Desenvolvimento Integrado) facilita a escrita, execução e depuração do código Java.

### **Eclipse IDE**    

1. Baixe o <a href="https://www.eclipse.org/downloads/" >Eclipse</a>.
2. Instale o Eclipse IDE for Java Developers.
3. Abra o Eclipse, configure um workspace e comece a criar projetos.

### **IntelliJ IDEA**      

1. Baixe o <a href="https://www.jetbrains.com/idea/download/" >IntelliJ</a>.
2. Escolha a versão Community (gratuita).
3. Durante a primeira execução, configure o caminho do JDK.

### **VS Code**    

1. Baixe o <a href="https://code.visualstudio.com/" >VS Code</a>.
2. Instale a extensão Language Support for Java™ by Red Hat.
3. Configure o JDK nas preferências.
