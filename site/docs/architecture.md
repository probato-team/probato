# Arquitetura

O **Probato** foi projetado com uma arquitetura modular e altamente extensível para suportar a automação de testes a nível funcional de forma eficiente e escalável. Ele integra conceitos modernos de design de software, utilizando padrões amplamente aceitos, como o **Page Object Model (POM)** e a **injeção de dependências via anotações**, além de permitir a criação de scripts de teste reutilizáveis, de fácil manutenção e expansíveis.

## **Camadas modulares e isolamento de responsabilidades**

A arquitetura do **Probato** é composta por múltiplas camadas bem definidas, cada uma com responsabilidades específicas, o que facilita a manutenção e a evolução do framework.

### **Camada de interação (Page Object Model)**

Implementa o padrão **POM**, que encapsula a lógica de interação com a interface do usuário. Cada página, tela ou componente da aplicação a ser testada é representado como um objeto, contendo métodos que descrevem as interações possíveis (cliques, inserções de dados, verificações, etc.). Isso promove a reutilização de código e facilita a manutenção quando a interface da aplicação é alterada.
    
### **Camada de testes (Scripts e Procedimentos)**   

Nesta camada, os testes são organizados em scripts, que por sua vez é composto por ações, nas quais podem ser subdivididas em pré-condições, procedimentos e pós-condições, e nas quais podem ser reutilizáveis em outros scripts. Essa separação de responsabilidades ajuda a isolar falhas em etapas específicas, possibilitando que testes sejam mais previsíveis e que o diagnóstico de erros seja facilitado.
    
### **Camada de injeção de massa de dados**     

O **Probato** permite o uso de dados de entrada para testes de forma flexível e dinâmica. Atualmente, ele suporta injeção de dados via arquivos CSV, com a visão de incluir outros formatos, como JSON, YAML e Banco de dados, por meio de plugins customizados. A injeção de massa de dados é configurada de maneira implícita, integrando facilmente com os scripts de teste.
    
### **Camada de persistência e conectores SQL**   

Para permitir testes que envolvem manipulação de bases de dados, o **Probato** dispõe de um executor SQL capaz de se conectar a múltiplas bases de dados. Essa funcionalidade permite que pré-condições de banco de dados sejam definidas e alteradas dinamicamente antes da execução do teste, além de garantir que os estados sejam restaurados corretamente após os testes.

## **Injeção de dependências com anotações**

O **Probato** adota um modelo simplificado de **injeção de dependências** através de anotações Java. Isso reduz a complexidade da configuração manual, permitindo que objetos necessários aos testes sejam injetados automaticamente pelo framework, com base nas declarações de anotação. Esse padrão é amplamente utilizado para promover a **inversão de controle (IoC)** e facilitar a modularidade, onde componentes são facilmente substituíveis e reutilizáveis.

## **Executor de testes baseado no JUnit 5**

O **Probato** se integra ao ciclo de vida do **JUnit 5** por meio de testes dinâmicos, utilizando a anotação `@TestFactory` para gerar casos de teste em tempo de execução. Em vez de definir métodos de teste convencionais com a anotação `@Test`, o **Probato** adota uma abordagem mais flexível e modular. As classes de script, que representam os cenários de teste, são marcadas com anotações como `@TestCase`, permitindo a criação de testes automaticamente com base na lógica definida nessas classes.

#### Ciclo de vida e estrutura

- **@BeforeEach**:  
    O **Probato** utiliza essa fase do ciclo de vida do JUnit para garantir que as pré-condições do teste sejam aplicadas. Scripts SQL podem ser executados ou estados de aplicação configurados para preparar o ambiente de teste.
    
- **@TestFactory**:     
    Ao invés de métodos `@Test`, os testes são gerados dinamicamente através do `@TestFactory`, com base nas classes que compõem os scripts, procedimentos e Page Objects. Cada cenário é tratado como um teste individual, permitindo flexibilidade para múltiplas execuções com diferentes conjuntos de dados (data-driven testing).
    
- **@AfterEach**:   
    Após a execução de cada teste, o **Probato** aplica as pós-condições, como limpeza de dados, restauração de estados e captura de evidências (capturas de tela, vídeos e logs). Esse estágio é essencial para garantir que o ambiente de teste retorne a um estado consistente.

## **Suporte para execuções multiplos browser**

A camada de interação com browsers no **Probato** é construída sobre a API do Selenium (que pode ser extentido em novos plugins para interação com outras ferramentas), permitindo a automação de testes em múltiplos navegadores e ambientes. A arquitetura do framework permite adicionar suporte a novos browsers de forma extensível, garantindo que testes possam ser escalados para diferentes contextos de execução, seja em diferentes sistemas operacionais ou em diferentes versões de navegadores.

## **Extensibilidade e plugins**

A arquitetura do **Probato** foi projetada para ser **extensível**, permitindo que novas funcionalidades sejam adicionadas sem a necessidade de modificar o núcleo do framework. Isso é feito por meio de um sistema de plugins, que pode ser utilizado para integrar novos drivers de browser, formatos de dados de entrada, ou até mesmo novos tipos de validação e manipulação de dados. Essa modularidade permite que o framework se adapte rapidamente às necessidades específicas de diferentes projetos.

## **Gerenciamento de execuções e coleta de dados**

Durante a execução dos testes, o **Probato** captura uma vasta gama de dados, como logs de execução, capturas de tela, vídeos e passos executados. Esses dados são processados e enviados para uma aplicação web integrada que centraliza o gerenciamento das execuções. Essa aplicação web permite o acompanhamento das execuções de testes por parte das equipes de desenvolvimento, análise de qualidade e a geração de relatórios, possibilitando inclusive o rastreamento de bugs e análise de versionamento de testes e resultados. O probato também permite a criação de plugins para integração como outras ferramentas, como Testlink, Mantis Bug Tracker, entre outras.

## **Configurações e personalizações avançadas**

O **Probato** oferece várias opções de personalização, incluindo configurações para:

- **Timeouts** entre ações e espera de respostas de interfaces ou APIs.
- **Qualidade de imagem e captura de vídeo** para análise visual detalhada.
- **Configuração de execução em múltiplas telas**, permitindo a execução de testes em monitores primários ou secundários, ideal para equipes que utilizam setups de múltiplos monitores.

## **Notificações e integração contínua**

O **Probato** também possui suporte para notificações automáticas, que podem ser enviadas para os colaboradores do projeto após cada execução de testes. Além disso, ele pode ser facilmente integrado com ferramentas de **integração contínua (CI)**, como Jenkins, facilitando a automação completa dos processos de teste no ciclo de desenvolvimento.
