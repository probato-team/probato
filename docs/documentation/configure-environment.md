---
layout: documentation
description: Guia do usuário - Configurando ambiente
navigation:
    next: 
        name: Criando projeto
        link: /documentation/create-project
---

Nesta seção vamos configurar o ambiente de desenvolvimento para usar o **Probato**. Este Framework utiliza linguagem de programação Java, então será necessário a instalação do Java Development Kit (JDK), configuração de variáveis de ambiente, e instalação de uma IDE para começar a desenvolver aplicações Java.

## **1. Baixar e instalar o Java Development Kit (JDK)**
O JDK é um conjunto de ferramentas para o desenvolvimento e execução de aplicações Java.

### 1.1 Baixar o JDK 11 ou superior

1. Acesse o site oficial da <a href="https://www.oracle.com/java/technologies/javase-downloads.html" target="_blank" >Oracle</a>.
2. Escolha a versão mais recente do JDK (ou a que você deseja utilizar) compatível com o seu sistema operacional.
3. Selecione o instalador para Windows (`.exe`) e faça o download.

### 1.2 Instalar o JDK

1. Após o download, execute o arquivo `.exe`.
2. Aceite os termos de licença.
3. Escolha o local de instalação (ou deixe o padrão, geralmente `C:\Program Files\Java\jdk<versão>`).
4. Finalize a instalação clicando em Next até o final.

## **2. Configurar as Variáveis de Ambiente**
As variáveis de ambiente permitem que o Java seja acessado em qualquer lugar no sistema.

### 2.1. Localize o diretório do JDK
* O diretório padrão é algo como `C:\Program Files\Java\jdk<versão>`.

### 2.2. Configurar JAVA_HOME

1. Pressione `Win + S`, procure por Variáveis de Ambiente e clique em Editar as variáveis de ambiente do sistema.
2. Na aba **Avançado**, clique em **Variáveis de Ambiente**.
3. Na seção **Variáveis de Sistema**, clique em **Novo**
4. Adicione uma variável de ambiente chamada `JAVA_HOME` apontando para o diretório do JDK.
5. Inclua o caminho `%JAVA_HOME%\bin` na variável `Path`.
6. Clique em OK para salvar.

## 2.3. Verificar a instalação

1. Abra o Prompt de Comando (pressione `Win + R`, digite cmd e pressione Enter).
2. Digite:
    ```bash
    java --version
    ```
3. O terminal deverá exibir a versão do Java instalada:
    ```bash
    java version "XX.XX"
    Java(TM) SE Runtime Environment (build XX.XX)
    Java HotSpot(TM) 64-Bit Server VM (build XX.XX)
    ```
Se você vê essa saída, o Java está instalado e configurado corretamente! 🎉

## **3. Instalar o Maven**

### 3.1 Baixar e instalar

1. Acesse o site do <a href="https://maven.apache.org/download.cgi" >Apache Maven</a>.
2. Faça o download do arquivo binário ZIP.
3. Extraia para um local, como `C:\Maven`.

### 3.2 Configurar variáveis de ambiente

1. Pressione `Win + S`, procure por Variáveis de Ambiente e clique em Editar as variáveis de ambiente do sistema.
2. Na aba **Avançado**, clique em **Variáveis de Ambiente**.
3. Na seção **Variáveis de Sistema**, clique em **Novo**
4. Adicione uma variável de ambiente chamada `MAVEN_HOME` apontando para o diretório do Maven.
5. Inclua o caminho `%MAVEN_HOME%\bin` na variável `Path`.
6. Clique em OK para salvar.

### 3.3 Verificar a instalação

1. No prompt de comando, execute:
    ```bash
    mvn -version
    ```
2. Se aparecer:
    ```bash
    Apache Maven X.X.X
    Maven home: C:\path\to\apache-maven-X.X.x
    Java version: X.X.X, vendor: Oracle Corporation, runtime: C:\path\to\jdk-X.X.X
    Default locale: pt_BR, platform encoding: UTF-8
    OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
    ```
    O Maven foi instalado com sucesso! 🎉

## **4. Instalar uma IDE**    

Uma IDE (Ambiente de Desenvolvimento Integrado) facilita a escrita, execução e depuração do código Java.

**Opção 1: Eclipse IDE**
1. Baixe o <a href="https://www.eclipse.org/downloads/" >Eclipse</a>.
2. Instale o Eclipse IDE for Java Developers.
3. Abra o Eclipse, configure um workspace e comece a criar projetos.

**Opção 2: IntelliJ IDEA**
1. Baixe o <a href="https://www.jetbrains.com/idea/download/" >IntelliJ</a>.
2. Escolha a versão Community (gratuita).
3. Durante a primeira execução, configure o caminho do JDK.

**Opção 3: VS Code**
1. Baixe o <a href="https://code.visualstudio.com/" >VS Code</a>.
2. Instale a extensão Language Support for Java™ by Red Hat.
3. Configure o JDK nas preferências.
