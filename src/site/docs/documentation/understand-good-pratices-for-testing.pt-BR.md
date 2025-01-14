# Entendendo Boas Práticas para Testes

Nesta seção, antes de implementarmos scripts de teste, alinharemos o entendimento sobre o que são scripts de teste, sua composição, estrutura e objetivos. Compreender e aplicar boas práticas em testes melhora a eficiência, a confiabilidade e reduz custos no desenvolvimento de software.

---

## **O que é uma suite de testes?**

Uma suite de testes é um conjunto organizado de casos ou scripts de teste agrupados para validar um ou mais aspectos de um sistema, aplicação ou funcionalidade. Esses testes podem ser organizados com base em critérios como funcionalidade, prioridade, tipo de teste (funcional, desempenho, segurança, etc.) ou fases do ciclo de vida do software.

### **Objetivo da suite**

O principal objetivo de uma suite de testes é permitir a execução coordenada e estruturada de múltiplos testes, garantindo:

- **Cobertura abrangente:** Validação de diferentes cenários de uso e requisitos do sistema.
- **Reutilização:** Organização de testes relacionados para facilitar manutenção e reutilização.
- **Automação:** Execução contínua de testes no processo de integração ou entrega contínua (CI/CD).
- **Análise de qualidade:** Coletar resultados e identificar áreas críticas no sistema.

### **Composição de uma suite**

Uma suite de testes geralmente inclui:

- **Casos de teste ou scripts:** Cenários específicos que devem ser validados.
- **Configurações gerais:** Parâmetros compartilhados, como URLs e credenciais.
- **Dados de teste:** Massa de dados compartilhada ou específica para cada teste.
- **Dependências:** Relações entre testes, caso algum dependa de outro.
- **Critérios de sucesso ou falha:** Regras para determinar o resultado da suite.

### **Tipos de suites**

| Tipo             | Objetivo                                 | Exemplo                                    |
|------------------|-----------------------------------------|-------------------------------------------|
| Funcional        | Validar requisitos funcionais           | Testar login e cadastro                   |
| Regressão        | Garantir que alterações não introduzam erros | Executar testes após correções           |
| Performance      | Avaliar desempenho sob diferentes condições | Testar tempo de resposta da API           |
| Integração       | Validar comunicação entre módulos        | Testar integração com serviço de pagamento|
| Smoke            | Verificar funcionalidade básica          | Testar carregamento da página inicial     |
| Exploratória     | Descobrir comportamentos inesperados     | Testar novos fluxos sem roteiro fixo      |

---

## **Estrutura de um Script de Teste**

Os scripts de teste são compostos por pré-condições, procedimentos e pós-condições. Essa estrutura garante testes organizados e resultados verificáveis.

### **Pré-Condições**

Condições ou estados que precisam ser estabelecidos antes que o teste seja executado.

**Exemplos:**       

* Ambiente configurado (servidor em execução).
* Dados de teste carregados.
* APIs externas disponíveis.

**Boas práticas:**

* Automatizar a configuração das pré-condições.
* Definir dependências e garantir estados reproduzíveis.

**Exemplo no Probato:**     
Pré-condição para alterar o estado do banco de dados usando o executor SQL antes do teste.

### **Procedimentos**

Passos que compõem o teste, simulando ações do usuário ou interações com o sistema.

**Exemplos:**

1. Navegar para a página de login.
2. Preencher campos 'E-mail' com `user@probato.org`.
3. Preencher campos 'Senha' com `p@ssword`.
4. Clicar no botão 'Acessar'.

**Boas práticas:**

* Cada passo deve ser atômico e focar em uma única ação.
* Documentar ações de forma clara e acessível.
* Incluir checkpoints para validar estados intermediários.

**Exemplo no Probato:**     
Procedimentos organizados em etapas bem definidas, com logs detalhados de cada ação.

### **Pós-Condições**

Resultados esperados ou estados finais após a execução do teste.

**Exemplos:**

* Usuário autenticado.
* Redirecionado para a página inicial.
* Dados persistidos no banco de dados.

**Boas práticas:**

* Verificar condições relevantes sem redundância.
* Automatizar validações para maior precisão.
* Rastrear erros com logs, vídeos e screenshots.
* Valida se estado da aplicação está em conformidade após a execução do procedimento alvo.

**Exemplo no Probato:**     
Após o teste, capturar logs e validar condições de sucesso ou falha.

---

## **Caso de Uso: Realizar Login**

O caso de uso de realizar login permite que o usuário acesse a aplicação usando e-mail e senha cadastrados. Inclui os seguintes fluxos:

| Fluxo                  | Pré-condições                        | Procedimento                              | Pós-condições                           |
|------------------------|--------------------------------------|------------------------------------------|----------------------------------------|
| Login com sucesso      | Usuário ativo                       | Preencher e-mail e senha, clicar em acessar | Painel principal exibido               |
| Usuário não encontrado        | Usuário não cadastrado                     | Preencher e-mail e senha, clicar em acessar | Mensagem de usuário não encontrado    |
| Usuário inativo        | Usuário inativo                     | Preencher e-mail e senha, clicar em acessar | Mensagem de usuário inativo exibida    |
| Senha incorreta        | Senha diferente                     | Preencher e-mail e senha errada, clicar em acessar | Mensagem de senha incorreta exibida    |
| Campos obrigatórios    | Campos de e-mail e senha vazios     | Clicar em acessar                        | Mensagens de erro exibidas             |

---

## **Massa de Dados**

Massa de dados refere-se ao conjunto de dados usados nos testes para validar diferentes aspectos do sistema.

### **Objetivo:**
- Simular cenários reais.
- Garantir cobertura de testes com entradas válidas, inválidas e extremas.
- Identificar falhas causadas por dados inesperados.

### **Tipos:**
- **Dados válidos:** Entradas que atendem aos requisitos.
- **Dados inválidos:** Entradas que não atendem aos requisitos.
- **Dados extremos:** Valores nos limites aceitáveis.
- **Dados massivos:** Grandes volumes para avaliar desempenho.

**Exemplo de Dados:**
```plaintext
Dados válidos:
- Email: user@example.com
- Senha: Password123!

Dados inválidos:
- Email: user@example
- Senha: 123

Dados extremos:
- Email: [256 caracteres]
- Senha: [0 caracteres]
```

---

## **Page Object**

_Page Object_ é um padrão de design que organiza e abstrai interações com a interface do usuário (UI). Ele encapsula a lógica de interação, promovendo reutilização, manutenção e legibilidade.

### **Estrutura:**
- **Classe da página:** Representa a página ou componente da interface.
- **Elementos da interface:** Botões, campos e links como atributos.
- **Métodos para interação:** Lógica encapsulada para interagir com os elementos.

**Exemplo:**
```java  title="LoginPage" linenums="1"
public class LoginPage {
    
    @FindBy(id = "email") 
    private WebElement emailField;
    @FindBy(id = "password") 
    private WebElement passwordField;
    @FindBy(id = "loginButton") 
    private WebElement loginButton;

    public void loginAs(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
```

**Boas práticas:**

* Evitar lógica de negócio nos Page Objects.
* Nomear métodos de forma intuitiva.
* Manter o código modular e simples.

## **Considerações Finais**

Nesta seção, discutimos os principais elementos que garantem testes bem estruturados e alinhados aos objetivos da automação. Aplicar boas práticas e usar ferramentas como o Probato facilita a implementação de testes robustos, escaláveis e de fácil manutenção. Nos próximos capítulos, exploraremos cada conceito com maior profundidade e exemplos práticos.