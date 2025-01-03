# Entendo boas práticas para testes

Nesta seção, antes de colocarmos a mão na massa para implementar scripts de teste, será necessário alinhar o entendimento sobre o que são scripts de teste, como eles são compostos e sua estrutura e seus objetivos.

## **O que um suite de testes**

Uma suite de testes é um conjunto organizado de casos ou scripts de teste que são agrupados com o objetivo de validar um ou mais aspectos de um sistema, aplicação ou funcionalidade. Esses testes podem ser organizados com base em critérios como funcionalidade, prioridade, tipo de teste (funcional, desempenho, segurança, etc.) ou fases do ciclo de vida do software.

### **Objetivo da suite**

O principal objetivo de uma suite de testes é permitir a execução coordenada e estruturada de múltiplos testes, garantindo:

* **Cobertura abrangente:**   
Validação de diferentes cenários de uso e requisitos do sistema.

* **Reutilização:**   
Organização de testes relacionados para facilitar a manutenção e reutilização.
* **Automação:**    
Execução contínua e automatizada de um conjunto de testes como parte do processo de integração ou entrega contínua (CI/CD).
* **Análise de qualidade:**   
Coletar resultados e identificar áreas críticas ou vulnerabilidades no sistema.

### **Composição de uma suite**

Uma suite de testes geralmente inclui:

* **Casos de teste ou scripts:**    
Ações específicas ou cenários que devem ser validados.
* **Configurações gerais:**   
Parâmetros ou ambientes compartilhados pelos testes, como URLs, credenciais e opções de execução.
* **Dados de teste:**   
Massa de dados usada pelos casos de teste, que pode ser compartilhada ou específica para cada teste.
* **Dependências:**   
Ordem de execução ou relação entre os testes, caso algum dependa do estado gerado por outro.
* **Critérios de sucesso ou falha:**    
Regras para determinar se a suite foi executada com sucesso.

### **Tipos de suites**

1. **Funcional:**    
Focada na validação de requisitos funcionais do sistema.
    * **Exemplo:**    
    Testar o fluxo de login, cadastro e recuperação de senha.
2. **Regressão:**     
Verifica se mudanças recentes no código não introduziram novos erros em funcionalidades já existentes.
    * **Exemplo:**    
    Executar todos os testes funcionais após a correção de um bug.
3. **Performance:**   
Avalia o desempenho do sistema sob diferentes condições.
    * **Exemplo:**    
    Medir o tempo de resposta de uma API com alta carga de requisições.
4. **Integração:**    
Valida a comunicação entre diferentes módulos ou sistemas.
    * **Exemplo:**    
    Testar a integração entre uma aplicação web e um serviço de pagamento.
5. **Smoke:**     
Um subconjunto de testes rápidos para verificar se o sistema básico está funcional antes de testes mais detalhados.
    * **Exemplo:**    
    Testar apenas o carregamento da página inicial e o acesso a funções principais.
6. **Exploratória:**    
Testes não estruturados executados para descobrir comportamentos inesperados.

### **Vantagens de usar suites**

* **Organização e modularidade:**     
Agrupa testes relacionados, facilitando a manutenção.
* **Automação eficiente:**    
Permite a execução em lote, otimizando o tempo de validação.
* **Facilidade de análise:**    
Os resultados são centralizados, facilitando a identificação de problemas.
* **Reutilização de scripts:**    
Scripts podem ser usados em várias suites sem duplicação de esforço.

**Exemplo Prático**   
Imagine uma aplicação de e-commerce. Uma suite de testes funcional para validação de fluxo de compra pode incluir:

* **Teste de login**
* **Teste de busca por produto**
* **Teste de adição ao carrinho**
* **Teste de checkout e pagamento**

Essa suite pode ser executada automaticamente sempre que houver uma alteração no código da aplicação, garantindo que o fluxo de compra permaneça funcional.

## **O que compõe um script**

Em testes de software, pré-condições, procedimentos, e pós-condições são conceitos fundamentais que estruturam um roteiro de teste. Eles ajudam a garantir que os testes sejam executados de forma consistente, organizada e com resultados verificáveis. Veja os detalhes de cada um:

### **Pré-Condições**

As pré-condições são as condições ou estados que precisam ser estabelecidos antes que o teste possa ser executado. Elas garantem que o ambiente de teste, os dados ou a aplicação estejam em um estado inicial adequado para o sucesso do teste.

#### Exemplos:

* **Ambiente configurado:**   
Certificar-se de que o servidor de aplicação está em execução e acessível.
* **Usuário autenticado:**    
O usuário deve estar logado no sistema com um perfil específico.
* **Banco de dados:**   
Dados de teste previamente carregados ou tabelas limpas.
* **Recursos externos:**    
Verificar se APIs externas necessárias estão disponíveis.

#### Boas práticas:

* Automatizar a configuração das pré-condições para evitar erros humanos.
* Definir claramente as dependências e garantir que o estado inicial seja reproduzível.

#### Exemplo no contexto do Probato:   
  Um script de teste pode incluir uma pré-condição para que o estado do banco de dados seja alterado usando o executor SQL integrado antes de iniciar o teste principal.

### **Procedimentos**

Os procedimentos descrevem os passos reais que compõem e que são o objeto alvo do teste. Eles incluem todas as ações que simulam o comportamento do usuário ou a interação com o sistema. Esses passos devem ser claros e detalhados para que possam ser reproduzidos de forma consistente.

#### Exemplos:

1. Navegar para a página de login.
2. Preencher campo 'E-mail'.
2. Preencher campo 'Senha'.
3. Acionar o botão de "Acessar".
4. Validar que o usuário foi redirecionado para a página inicial.

#### Boas práticas:

* Cada passo deve ser atômico, ou seja, simples e com uma única responsabilidade.
* Documentar as ações de maneira que qualquer pessoa possa seguir e entender, mesmo que não seja técnica.
* Incluir verificações ou checkpoints durante os procedimentos para validar o estado do sistema.

#### Exemplo no Probato:
O framework organiza os procedimentos em etapas bem definidas, permitindo capturar logs detalhados de cada ação, como cliques e entradas de dados, para facilitar o rastreamento de erros.

### **Pós-Condições**

As pós-condições são os resultados esperados ou estados finais que devem ser verificados após a execução do teste. Elas confirmam que o teste atingiu seus objetivos e que o sistema respondeu conforme o esperado.

#### Exemplos:

* **Mudança de estado:**    
Verificar que o usuário foi autenticado e está na página inicial.
* **Persistência de dados:**    
Garantir que um novo registro foi adicionado ao banco de dados.
* **Saída visível:**    
Validar que uma mensagem de sucesso foi exibida na tela.
* **Restauração do ambiente:**    
Retornar o sistema ao estado inicial para que outros testes possam ser executados.

#### Boas práticas:

* Verificar o maior número de condições relevantes sem introduzir redundância.
* Automatizar as validações para garantir precisão e reduzir esforço humano.
* Rastrear erros capturando logs, vídeos e screenshots.

#### Exemplo no Probato:
Após executar um teste, o framework pode captura de tela, vídeos e logs detalhados de execução, além de validar as condições de sucesso ou falha com base nos dados coletados.

### **Relação entre pré-condições, procedimentos e pós-condições**

Esses elementos formam uma estrutura lógica para qualquer teste:

* As pré-condições configuram o estado inicial do sistema.
* Os procedimentos realizam a simulação ou execução do comportamento esperado.
* As pós-condições validam se o comportamento resultante foi conforme o esperado.

Essa abordagem garante clareza e rastreabilidade nos testes, permitindo que os resultados sejam analisados de forma objetiva, além disso ajuda na análide do resultados a da classificação em Sucesso, Erro, Falha e Impeditivo, nesse último ocorren quando um teste não consegue checar até a execução do procedimento.

## Exemplo prático de caso de uso

 Implementado um script de testes básico. O **Probato** adota uma estrutura modular que inclui scripts, procedures, page objects e suites, vamos iniciar pelo nosso test suite. Para exemplificar a automação de uma funcionalidade real, seguiremos com o desenvolvimento da automação da aplicação **Probato Manager**. O **Probato Manager** possui como tela principal a página de login, então partiremos desta funcionalidade para o desenvolvimento dos testes automatizados: **Realizar Login**

### **Caso de uso: Realizar Login**

O caso de uso de realizar login na aplicação tem como objetivo permitir que o usuário, utilizando e-mail e senha previamente cadastrados, acesse as demais funcionalidades da aplicação. Essa funcionalidade inclui os seguintes fluxos:

#### [Fluxo Básico] Realizar login com sucesso:   
  - Pré-condições:  
    - Deve ter solicitada a página de login;
    - Deve possuir usuário com e-mail '{{email}}' e respectiva senha '{{senha}}' cadastrado;
    - Usuário com e-mail '{{email}}' deve está ativo;
  - Procedimento:
    - Usuário preenche campo 'E-mail' com valor '{{email}}';
    - Usuário preenche campo 'Senha' com valor '{{senha}}';
    - Usuário aciona botão 'Acessar';
  - Pós-Condições:
    - Sistema exibe página 'Painel Principal' com sucesso;

#### [Fluxo de Exceção] Usuário não encontrado:
  - Pré-condições:  
    - Deve ter solicitada a página de login;
    - Não deve possuir usuário com e-mail '{{email}}';
  - Procedimento:
    - Usuário preenche campo 'E-mail' com valor '{{email}}';
    - Usuário preenche campo 'Senha' com valor '{{senha}}';
    - Usuário aciona botão 'Acessar';
  - Pós-Condições:
    - Sistema exibe mensagem de aviso com texto '{{usuario_nao_encontrado}}'

#### [Fluxo de Exceção] Senha não confere:
  - Pré-condições:  
    - Deve ter solicitada a página de login;
    - Deve possuir usuário com e-mail '{{email}}' e senha senha diferente de '{{senha}}' cadastrado;
  - Procedimento:
    - Usuário preenche campo 'E-mail' com valor '{{email}}';
    - Usuário preenche campo 'Senha' com valor '{{senha}}';
    - Usuário aciona botão 'Acessar';
  - Pós-Condições:
    - Sistema exibe mensagem de aviso '{{senha_nao_confere}}'

#### [Fluxo de Exceção] Usuário inativo:
  - Pré-condições:  
    - Deve ter solicitada a página de login;
    - Deve possuir usuário com e-mail '{{email}}' e senha senha diferente de '{{senha}}' cadastrado;
    - Usuário com e-mail '{{email}}' deve está inativo;
  - Procedimento:
    - Usuário preenche campo 'E-mail' com valor '{{email}}';
    - Usuário preenche campo 'Senha' com valor '{{senha}}';
    - Usuário aciona botão 'Acessar';
  - Pós-Condições:
    - Sistema exibe mensagem de aviso com texto '{{usuario_inativo}}'

#### [Fluxo de Exceção] Campo obrigatório não informado:
  - Pré-condições:  
    - Deve ter solicitada a página de login;
  - Procedimento:
    - Usuário aciona botão 'Acessar';
  - Pós-Condições:
    - Sistema exibe mensagem de aviso com texto '{{email_obrigatoria}}'
    - Sistema exibe mensagem de aviso com texto '{{senha_obrigatoria}}'

**Obs:** Essa estrutura sistemática não apenas facilita a execução dos testes, mas também ajuda a documentar e comunicar claramente os cenários de teste.

## **Massa de dados**

Massa de dados para teste de software refere-se ao conjunto de dados usado durante a execução de testes, com o objetivo de validar a funcionalidade, desempenho, segurança e outros aspectos de um sistema ou aplicação. Esses dados representam entradas, condições ou cenários reais e simulados que ajudam a avaliar o comportamento do sistema em diferentes situações.

### **Objetivo da massa de dados**

A massa de dados é fundamental para:

* **Simular cenários reais:**   
Verificar se o sistema funciona corretamente com dados semelhantes aos que serão usados em produção.
* **Garantir cobertura de teste:**  
Testar todos os possíveis caminhos, incluindo entradas válidas, inválidas e extremas.
* **Identificar falhas:**   
Detectar erros que ocorrem devido a dados inesperados, formatos incorretos ou limites ultrapassados.
* **Validar integrações:**  
Garantir que a comunicação entre diferentes componentes do sistema, como bancos de dados e APIs, funciona conforme o esperado.

### **Tipos de massa de dados**

1. **Dados válidos**  
São os dados que seguem os requisitos esperados pelo sistema. Eles são usados para verificar o funcionamento correto do sistema.
    * **Exemplo:** Um CPF válido, um nome de usuário dentro do limite permitido, ou valores numéricos esperados.

2. **Dados inválidos**  
São dados que deliberadamente não atendem aos requisitos do sistema, usados para testar a robustez e a capacidade de tratamento de erros.
    * **Exemplo:** Um CPF com formato incorreto, campos vazios ou um número fora do intervalo permitido.

3. **Dados nulos ou vazios**    
Representam a ausência de entrada, ajudando a validar como o sistema lida com a falta de dados.
    * **Exemplo:** Testar um campo obrigatório sem preenchê-lo.

4. **Dados de limite**    
São usados para validar o comportamento do sistema nas extremidades das condições aceitáveis.
    * **Exemplo:** Inserir o menor e o maior valor permitido para um campo numérico ou texto com o limite máximo de caracteres.

5. **Dados redundantes**    
Dados repetidos ou duplicados são usados para verificar se o sistema lida corretamente com entradas duplicadas.
    * **Exemplo:** Inserir o mesmo registro de usuário duas vezes para testar restrições de unicidade.

6. **Dados massivos**   
Um grande volume de dados usado para testar a escalabilidade e desempenho do sistema.
    * **Exemplo:** Um teste de carga com milhões de registros no banco de dados.

### **Origem da massa de dados**
A massa de dados pode ser obtida de várias formas, dependendo do contexto e da necessidade do teste:

1. **Dados reais**    
Extraídos de ambientes de produção (com os devidos cuidados para anonimização e segurança) para representar situações reais.

2. **Dados parametrizados**   
Criados com base em arquivos como CSV, JSON ou XML, ou até mesmo em bancos de dados específicos, permitindo reutilização e variação controlada.

3. **Dados simulados**   
São gerados artificialmente para simular entradas específicas, geralmente usando ferramentas ou scripts automatizados.

4. **Dados aleatórios**   
Gerados dinamicamente durante os testes para explorar situações inesperadas.

### **Desafios na utilização de massa de dados**

1. **Manutenção**   
Garantir que a massa de dados esteja sempre atualizada e alinhada aos requisitos do sistema.

2. **Segurança e privacidade**  
Se forem usados dados reais, é necessário garantir a anonimização para evitar vazamento de informações sensíveis.

3. **Cobertura**  
Criar uma massa de dados que cubra adequadamente todos os cenários de teste, sem deixar lacunas importantes.

4. **Volume e desempenho**  
Garantir que os testes sejam executados dentro de um tempo aceitável mesmo ao lidar com grandes volumes de dados.

### **Boas práticas**  

1. **Automatizar a geração de dados**   
Use ferramentas ou scripts para criar massa de dados variada e dinâmica, reduzindo erros manuais.

2. **Organização e reutilização**     
Estruture a massa de dados em arquivos ou bancos de dados bem organizados para facilitar sua reutilização em diferentes testes.

3. **Controle de versões**    
Versione os conjuntos de dados para rastrear alterações e manter a consistência ao longo do tempo.

4. **Validação antecipada**   
Antes de usar os dados, verifique se eles atendem aos requisitos e estão consistentes com o cenário de teste.

## **Page Object**

_Page Object_ é um padrão de design utilizado na automação de testes que visa organizar e abstrair a interação com elementos das interfaces de usuário (UI) de uma aplicação. Ele encapsula a lógica de interação com uma página ou componente específico da interface, promovendo a reutilização de código, facilitando a manutenção e melhorando a legibilidade dos testes. Para a implementação do script de teste para o caso de uso de login, classes _LoginPage_ e _DashboardPage_ são onde serão mapeada todas as ações para a página a qual ela representa, onde veremos usa implementação abaixo:

### **Estrutura do Page Object**

1. **Classe representativa da página**    
Cada página ou componente da interface é representado por uma classe. Por exemplo, uma página de login seria representada pela classe `LoginPage`.

2. **Elementos da interface**   
Os elementos interativos da página, como botões, campos de texto e links, são representados como atributos dessa classe. Em frameworks como Selenium, esses elementos são frequentemente localizados por seletores (como XPath ou CSS selectors).

3. **Métodos para interação**    
A lógica para interagir com os elementos da página é encapsulada em métodos. Por exemplo, preencher o campo de usuário ou clicar no botão de login seria implementado em métodos da classe `LoginPage`.

4. **Abstração de Comportamento**
Além de métodos individuais para cada ação, é comum criar métodos que encapsulam comportamentos completos. Por exemplo, um método loginAs que combina o preenchimento de campos e o clique no botão. Para o **Probato** este emcapsulamento de comportamente ocorre na classe `procedure`.

### **Vantagens do Page Object**

1. **Reutilização de Código**   
A lógica de interação com a UI está centralizada no _Page Object_, permitindo reutilizar o mesmo código em diferentes scripts de teste.

2. **Facilidade de Manutenção**   
Caso o layout ou a identificação de elementos na página mudem, é necessário alterar apenas o _Page Object_ correspondente, sem impactar diretamente os scripts de teste.

3. **Legibilidade**   
Os scripts de teste se tornam mais legíveis e focados na lógica de negócio, pois utilizam métodos abstratos em vez de comandos detalhados do Selenium.

4. **Separação de Responsabilidades**   
O _Page Object_ separa a lógica de interação com a UI da lógica dos testes, promovendo um design mais modular.

### **Boas Práticas**

1. **Evitar lógica de negócio nos Page Objects**    
A lógica de negócio deve ficar nos scripts de teste. O Page Object deve focar apenas na abstração da UI.

2. **Nomeação clara**   
Os métodos devem ter nomes intuitivos que representem ações de usuário.

3. **Organização Modular**    
Para aplicações complexas, organize os Page Objects em pacotes, agrupando-os por funcionalidade ou seção da aplicação.

4. **Manter a Simplicidade**    
Não inclua lógica desnecessária no Page Object. Ele deve ser um espelho da interface e não lidar com validações ou fluxos complexos.

O padrão _Page Object_, quando bem implementado, torna os testes automatizados mais robustos, flexíveis e fáceis de manter, facilitando a escalabilidade do projeto de automação.

## Considerações finais

Nesta seção, abordamos de forma resumida e introdutória alguns dos principais aspectos que devem ser considerados ao criar suítes, scripts de teste e as demais estruturas que compõem os testes automatizados. Esses pontos são fundamentais para garantir que os testes sejam bem estruturados, eficientes e alinhados com os objetivos da automação. No decorrer do material, exploraremos cada um desses elementos com maior profundidade, oferecendo diretrizes práticas e exemplos para facilitar a implementação no contexto do **Probato**.