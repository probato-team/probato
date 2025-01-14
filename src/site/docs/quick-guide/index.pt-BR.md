# Guia do Usuário

Bem-vindo ao guia rápido do **Probato**! Este documento foi criado para ajudá-lo a configurar, utilizar e personalizar este framework de automação de testes. Aqui, você encontrará exemplos práticos e orientações claras para começar sua jornada com o **Probato**.

---

## **Como usar este guia**

Este guia está dividido em quatro capítulos, cada um cobrindo um aspecto essencial do uso do Probato:

<div class="grid cards" markdown>

-   :octicons-gear-24:{ .md .middle } __Configuração de Ambiente__

    ---

    Aprenda a instalar e configurar as ferramentas necessárias para começar a usar o Probato, incluindo JDK, Maven e IDEs.

    [:octicons-arrow-right-24: Ir](/pt-BR/quick-guide/configure-environment/)

-   :octicons-file-code-24:{ .md .middle } __Criação e Configuração do Projeto__

    ---

    Descubra como criar e configurar um projeto base, estruturando diretórios, adicionando dependências e configurando o `configuration.yml`.

    [:octicons-arrow-right-24: Ir](/pt-BR/quick-guide/configure-project/)

-   :material-code-json:{ .md .middle } __Implementação de Roteiros Básicos__

    ---

    Entenda como criar suítes, scripts e procedimentos para automatizar casos de uso simples no Probato.

    [:octicons-arrow-right-24: Ir](/pt-BR/quick-guide/implement-script/)

-   :simple-cachet:{ .md .middle } __Execução e Análise de Resultados__

    ---

    Veja como executar o projeto, interpretar logs e validar resultados para garantir a qualidade do seu software.

    [:octicons-arrow-right-24: Ir](/pt-BR/quick-guide/execution-result-analysis/)

</div>

---

## **Fluxo de Teste no Probato**

Abaixo está o fluxo típico de execução de testes com o Probato:

**Testing Workflow**

``` mermaid
graph LR
  A[Implementar Roteiros] --> B[Executar Suites];
  B --> C{Integrou?};
  C --> |Yes| D[Probato Manager];
  C --> |No| B;
  D --> E[Analisar Resultados];
```

**Descrição do Fluxo**:

1. **Implementar Roteiros**: Desenvolva suítes, scripts e procedimentos para cobrir os casos de teste.
2. **Executar Suites**: Execute os testes automatizados e colete os resultados.
3. **Analisar Resultados**: Verifique logs, relatórios e evidências geradas para identificar falhas ou validar o comportamento esperado.

--- 

## **Pronto para começar?**

Siga para o primeiro capítulo: **Configuração de Ambiente**.