# Probato

**Probato** é uma **proposta Open Source** para automação de testes funcionais end-to-end (E2E), focada em tornar a automação **mais viável, sustentável e acessível** para equipes e empresas de diferentes portes e níveis de maturidade.

O projeto não se propõe a ser uma solução definitiva ou completa. Ele nasce como uma **base inicial estruturada**, construída a partir de necessidades reais, e evolui de forma colaborativa com a participação da comunidade.

---

## Visão geral

Na prática, a automação de testes funcionais costuma sofrer com:

* Alto custo de manutenção
* Falta de padronização
* Uso disperso de bibliotecas e utilitários
* Resultados e evidências fragmentados
* Pouca visibilidade do valor da automação para além do time técnico

O **Probato** surge como uma iniciativa para organizar esse cenário, oferecendo uma abordagem integrada e pragmática para automação E2E.

---

## O que é o Probato

O Probato é composto por dois componentes independentes e complementares:

### 📦 Biblioteca Java

A biblioteca Java é o núcleo da automação. Ela centraliza o uso de soluções consolidadas (como Selenium), adicionando:

* Padrões de projeto e boas práticas
* Estrutura baseada em Page Objects
* API simples, orientada a anotações
* Reutilização de código
* Configuração mínima para execução em múltiplos navegadores

A biblioteca pode ser utilizada de forma independente, sem a necessidade da aplicação Web.

➡ Mais detalhes: [Biblioteca Java](https://probato.org/library)

---

### 🌐 Aplicação Web

A aplicação Web tem como objetivo centralizar os dados gerados durante as execuções dos testes, oferecendo:

* Armazenamento estruturado de resultados e evidências
* Histórico de execuções
* Visualização de métricas e indicadores de qualidade
* Apoio à análise técnica e tomada de decisão

A aplicação é distribuída via Docker e sua adoção é **opcional**.

➡ Mais detalhes: [Aplicação Web](https://probato.org/web-app)

---

## Para quem é

O Probato foi pensado para atender diferentes contextos:

* Equipes pequenas que desejam iniciar automação de forma organizada
* Times maduros que buscam padronização e redução de esforço operacional
* Empresas com múltiplos projetos e pipelines de CI/CD
* Ambientes corporativos que precisam de visibilidade e histórico de qualidade

A adoção pode ser feita de forma **progressiva**, respeitando o nível de maturidade de cada projeto.

---

## O que o Probato não é

Para alinhar expectativas, é importante destacar que o Probato:

* Não é um framework definitivo ou fechado
* Não substitui todo o ecossistema existente de automação
* Não cobre todos os cenários e contextos possíveis

Trata-se de uma **proposta em evolução**, aberta a ajustes e extensões conforme o uso prático e as contribuições da comunidade.

---

## Começando

Para um primeiro contato com o projeto, recomenda-se seguir a documentação inicial:

➡ [Getting Started](https://probato.org/getting-started)

Ela apresenta um caminho simples para:

* Utilizar a biblioteca Java
* Executar os primeiros testes
* Entender a integração com a aplicação Web

---

## Documentação

A documentação completa do projeto está disponível no site:

➡ [https://probato.org](https://probato.org)

Principais seções:

* [Introdução](https://probato.org)
* [Getting Started](docs/getting-started.md)
* [Biblioteca Java](docs/library.md)
* [Aplicação Web](docs/web-app.md)
* [Sobre o projeto](https://probato.org/about)

---

## Open Source e colaboração

O Probato é um projeto **Open Source** e assume desde o início que suas decisões técnicas **não são definitivas**.

A participação da comunidade é considerada essencial para:

* Evoluir a arquitetura
* Refinar APIs
* Discutir métricas e indicadores
* Ajustar a proposta a diferentes realidades

Sugestões, issues, discussões e contribuições são muito bem-vindas.

---

## Licença

Este projeto é distribuído sob a licença **MIT**.

---

## Status do projeto

🚧 Projeto em evolução ativa

O Probato está em fase de consolidação da proposta, validação de conceitos e amadurecimento com base no uso real.
