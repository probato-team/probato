# Funcionalidades

## **Estrutura simples e intuitiva**

O **Probato** oferece uma estrutura organizada e modularizada para implementação de testes, facilitando a reutilização de componentes e a manutenção de scripts de teste. Isso permite que equipes de desenvolvimento se concentrem mais na lógica de testes do que na estrutura de implementação.
    
## **Injeção de objetos através de anotações**  
    
Com a utilização de anotações, o **Probato** simplifica a injeção de objetos, permitindo que desenvolvedores configurem testes de forma clara e concisa, sem a necessidade de  implementações adicionais.
    
## **Padrão Page Object Model (POM)**  
    
O **Probato** segue o padrão **Page Object Model**, que ajuda na separação de camadas e na organização do código, tornando a manutenção e a leitura dos scripts de teste mais simples e intuitiva. Isso é especialmente útil ao trabalhar com frameworks de automação como o Selenium.
    
## **Organização de procedimentos de teste**  
    
Os testes poder ser organizados em três etapas distintas: pré-condições, procedimentos e pós-condições. Essa abordagem ajuda a identificar rapidamente a origem de falhas, permitindo que os desenvolvedores entendam se um erro ocorreu devido à funcionalidade alvo do teste ou a etapas anteriores.
    
## **Carregamento e injeção implícita de massa de dados**  
    
O **Probato** permite a execução de scripts com diferentes conjuntos de dados, facilitando o teste de várias condições sem a necessidade de duplicar código. Essa funcionalidade torna a execução de testes mais eficiente e abrangente.
    
## **Executor de arquivos SQL**  
    
O **Probato** inclui um executor SQL que se conecta a múltiplas bases de dados, permitindo a alteração do estado da aplicação de acordo com as pré-condições dos testes. Isso oferece uma flexibilidade adicional para a configuração de testes.
    
## **Criação de roteiros de testes intuitivos**  
    
Os roteiros de testes podem ser criados de maneira simples e intuitiva, com a possibilidade de atribuir código, descrição e pesos a cada roteiro, com base na relevância e complexidade da funcionalidade. Essa avaliação é fundamental para a análise de qualidade do software alvo dos testes.
    
## **Configurações de timeout e intervalos entre ações**  
    
O **Probato** permite a configuração de _timeouts_ para o tempo de espera durante a execução dos testes, bem como intervalos entre ações, garantindo que o desempenho dos testes possam ser otimizados.
    
## **Execução em diversos browsers**  
    
O **Probato** suporta a execução de testes em vários navegadores, com opções de execução maximizada, normal ou customizada. A execução customizada possibilita especificar dimensões do browser para  execução. Pode ser configurada também em qual monitor deseja executar o teste, como primário ou secundário.
    
## **Aplicação para gerenciamento de dados coletados**  
    
O **Probato** oferece uma aplicação dedicada de interface web para o gerenciamento de dados de coletados das execuções, permitindo a análise da qualidade do software, criação de bugs a partir dos resultados dos testes, criação de plano de execução de testes. A aplicação também suporta a visualização por versionamento da aplicação. Gera relatórios automatizados com estatísticas detalhadas sobre a execução dos testes, incluindo logs e gráficos de cobertura.

## **Captura de dados durante a execução**  
    
O **Probato** coleta uma variedade de dados durante a execução dos testes, como a suíte e roteiros de testes, passos executados, dados aplicados, scripts SQL, vídeos e capturas de tela em casos de falha. Esses dados são essenciais para análise posterior. O usuário pode ajustar as configurações para determinar a qualidade das imagens capturadas durante os testes, permitindo uma análise mais detalhada das falhas visualmente.

## **Notificações de execução**  
    
O **Probato** notifica os colaboradores do projeto quando novas execuções ocorrem, garantindo que todos estejam atualizados sobre o status dos testes o mas breve o possível.
    
## **Extensibilidade**  
    
O **Probato** permite a extensão em diversas partes, possibilitando a implementação de novos plugins, como para novos browsers, novas validações, para entrada de massa de dados, executores SQL ou NoSQL. Isso garante que o framework possa evoluir de acordo com as necessidades do projeto alvo dos testes.
    
## **Integração com Ferramentas de CI/CD**  
	
Integra-se facilmente com sistemas de integração contínua (CI), como Jenkins, Travis CI e GitLab CI, permitindo que os testes sejam executados automaticamente em cada commit.
