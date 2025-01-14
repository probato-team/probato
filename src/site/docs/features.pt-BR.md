# Funcionalidades

## **Estrutura Simples e Intuitiva**

O **Probato** oferece uma estrutura organizada e modular para implementação de testes, facilitando a reutilização de componentes e a manutenção de scripts. Isso permite que equipes se concentrem mais na lógica de testes do que na estrutura de implementação.

---

## **Injeção de Objetos com Anotações**

A utilização de anotações simplifica a configuração de testes, permitindo uma injeção de objetos clara e concisa, sem necessidade de implementações adicionais.

---

## **Padrão Page Object Model (POM)**

O **Probato** segue o padrão **Page Object Model**, ajudando na separação de camadas e organização do código. Essa abordagem facilita a manutenção e leitura dos scripts, especialmente em frameworks como Selenium.

---

## **Organização de Procedimentos de Teste**

Os testes são organizados em três etapas:

1. **Pré-condições**
2. **Procedimentos**
3. **Pós-condições**

Essa estrutura permite identificar rapidamente a origem de falhas e entender se os erros estão nas funcionalidades alvo ou nas etapas preparatórias.

---

## **Carregamento e Injeção Implícita de Massa de Dados**

Scripts podem ser executados com diferentes conjuntos de dados, permitindo testes abrangentes sem duplicar código. Essa funcionalidade melhora a eficiência e cobertura dos testes.

---

## **Executor de Arquivos SQL**

O executor SQL integrado conecta-se a múltiplas bases de dados para alterar o estado da aplicação conforme as pré-condições dos testes. Isso oferece flexibilidade na configuração de cenários de teste.

---

## **Criação de Roteiros de Teste Intuitivos**

Os roteiros podem ser criados com código, descrição e pesos baseados na relevância e complexidade da funcionalidade. Isso ajuda na priorização e na análise da qualidade do software testado.

---

## **Configurações de Timeout e Intervalos**

O **Probato** permite:

- Configurar _timeouts_ para o tempo de espera durante a execução dos testes.
- Ajustar intervalos entre ações, otimizando o desempenho dos testes.

---

## **Execução em Diversos Browsers**

Suporte para execução de testes em múltiplos navegadores, com opções como:

- Modo maximizado, normal ou customizado (dimensões específicas).
- Escolha do monitor para execução (primário ou secundário).

---

## **Gerenciamento de Dados Coletados**

O **Probato** inclui uma aplicação web para:

- Gerenciamento de dados de execução.
- Análise da qualidade do software.
- Criação de bugs a partir dos resultados.
- Visualização de históricos e versionamento.
- Geração de relatórios detalhados com logs e gráficos de cobertura.

---

## **Captura de Dados Durante a Execução**

Coleta e armazena informações como:

- Suíte e roteiros de testes.
- Passos executados.
- Dados aplicados.
- Scripts SQL.
- Vídeos e capturas de tela (em casos de falha).

A qualidade das imagens pode ser ajustada para análises detalhadas das falhas.

---

## **Notificações de Execução**

Envio de notificações para os colaboradores quando novas execuções ocorrem, mantendo todos atualizados sobre o status dos testes.

---

## **Extensibilidade**

O **Probato** permite personalizações por meio de plugins, incluindo:

- Suporte a novos browsers.
- Validações adicionais.
- Entrada de massa de dados em novos formatos.
- Executores SQL ou NoSQL personalizados.

---

## **Integração com Ferramentas de CI/CD**

Integra-se facilmente com sistemas de integração contínua, como:

- **Jenkins**
- **Travis CI**
- **GitLab CI**

Isso permite a execução automática de testes em cada commit, garantindo validações contínuas e ágeis.
