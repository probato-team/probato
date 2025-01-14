# Arquitetura

O **Probato** foi projetado com uma arquitetura modular e altamente extensível para suportar a automação de testes a nível funcional de forma eficiente e escalável. Ele utiliza conceitos modernos de design de software, como o **Page Object Model (POM)** e a **injeção de dependências via anotações**, permitindo a criação de scripts reutilizáveis, fáceis de manter e expandir.

---

## **Camadas Modulares e Isolamento de Responsabilidades**

A arquitetura do **Probato** é composta por múltiplas camadas, cada uma com responsabilidades bem definidas, o que facilita a manutenção e evolução do framework.

### **Camada de Interação (Page Object Model)**

* Implementa o padrão **POM**, encapsulando a lógica de interação com a interface do usuário.
* Cada página, tela ou componente é representado como um objeto, contendo métodos para interações possíveis (cliques, inserção de dados, verificações, etc.).
* Promove a reutilização de código e facilita a manutenção quando a interface da aplicação é alterada.

### **Camada de Testes (Scripts e Procedimentos)**

* Os testes são organizados em scripts compostos por ações subdivididas em:
    * **Pré-condições**
    * **Procedimentos**
    * **Pós-condições**
* A separação de responsabilidades ajuda a isolar falhas e facilita o diagnóstico de erros.

### **Camada de Injeção de Massa de Dados**

* Permite o uso flexível e dinâmico de dados de entrada para testes.
* Suporta injeção de dados via arquivos CSV, com previsão de suporte futuro para JSON, YAML e bancos de dados por meio de plugins personalizados.

### **Camada de Persistência e Conectores SQL**

* Disponibiliza um executor SQL integrado que se conecta a múltiplas bases de dados.
* Permite definir pré-condições de banco de dados, alterar estados dinamicamente antes dos testes e restaurar os estados após a execução.

---

## **Injeção de Dependências com Anotações**

* Adota um modelo de **injeção de dependências** via anotações Java, promovendo a **inversão de controle (IoC)**.
* Simplifica a configuração manual, permitindo que objetos necessários sejam injetados automaticamente com base nas declarações de anotação.
* Promove modularidade e reutilização de componentes.

---

## **Executor de Testes Baseado no JUnit 5**

O **Probato** integra-se ao ciclo de vida do **JUnit 5**, utilizando testes dinâmicos e a anotação `@TestFactory` para gerar casos de teste em tempo de execução.

### **Ciclo de Vida e Estrutura**

![Probato Life Cycle](/assets/images/introduction/probato-life-cycle.png)

* **BeforeAll**:    
  Carrega pontos de extensão, configurações e executa validações de código e configurações. Também cria _Dynamic tests_.

* **BeforeEach**:   
  Carrega conjuntos de dados e scripts necessários e inicia a execução dos cenários de teste.

* **TestFactory**:    
  Gera testes dinamicamente com base em classes de script, procedimentos e Page Objects. Suporta data-driven testing, permitindo múltiplas execuções com diferentes conjuntos de dados.

* **AfterEach**:    
  Submete os dados coletados durante a execução dos testes ao **Probato Manager** e armazena as imagens e vídeos no armazenamento.

* **AfterAll**:   
  Calcula a qualidade do software com base em métricas e dados de execução e notifica os colaboradores sobre a conclusão da execução.

---

## **Suporte para Execução Multibrowser**

* Construído sobre a API do Selenium, permitindo automação em múltiplos navegadores.
* Suporte extensível para adicionar novos browsers e contextos de execução (diferentes sistemas operacionais ou versões).

---

## **Extensibilidade e Plugins**

* Projetado para ser **extensível**, permitindo a adição de novas funcionalidades sem modificar o núcleo do framework.
* Suporte a plugins para:
    * Novos drivers de browser.
    * Formatos de dados de entrada adicionais.
    * Novos tipos de validação e manipulação de dados.

---

## **Gerenciamento de Execuções e Coleta de Dados**

* Durante os testes, captura dados como:
    * Logs de execução.
    * Capturas de tela.
    * Vídeos e passos executados.
* Processa e envia os dados para uma aplicação web integrada, que oferece:
    * Monitoramento centralizado das execuções.
    * Geração de relatórios detalhados.
    * Rastreamento de bugs e análise de versionamento.
* Suporta integração com ferramentas como **TestLink** e **Mantis Bug Tracker**.

---

## **Configurações e Personalizações Avançadas**

* Oferece opções para:
    * Configurar **timeouts** e intervalos entre ações.
    * Ajustar a qualidade de imagens e vídeos capturados.
    * Definir execução em tela (monitores primários ou secundários).

---

## **Notificações e Integração Contínua**

* Envia notificações automáticas para colaboradores após cada execução de testes.
* Integra-se facilmente com ferramentas de **CI/CD**, como **Jenkins**, permitindo a automação total dos processos de teste no ciclo de desenvolvimento.
