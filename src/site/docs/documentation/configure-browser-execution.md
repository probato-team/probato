# Configurando execução por browser

Nesta seção, será configurado um plugin para permitir a execução de testes em diferentes browsers. Essa funcionalidade é essencial para garantir que os testes automatizados sejam executados em ambientes variados, simulando as condições reais de uso por diferentes usuários e garantindo a compatibilidade da aplicação com os principais navegadores do mercado. Será abordado como configurar os browsers suportados pelo framework, como Chrome, Firefox e Edge, que são suportados nativamente.

## Adicionando dependência para execução por browser

1. No arquivo `pom.xml` pode ser adicionada a dependência
```xml title="pom.xml" hl_lines="24-29"
<project 
    xmlns="http://maven.apache.org/POM/4.0.0" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.exemplo.automation</groupId>
    <artifactId>my-project</artifactId>
    <version>1.0.0</version> <!-- Versão atual do projeto alvo dos testes -->

    <properties>
        <probato.version>0.1.0</testano.version>
    </properties>

    <dependencies>
        <!-- Dependências do Probato -->
        <dependency>
            <groupId>com.probato</groupId>
            <artifactId>probato-api</artifactId>
            <version>${probato.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.probato</groupId>
            <artifactId>probato-browser</artifactId>
            <version>${probato.version}</version>
            <scope>test</scope>
        </dependency>
        <!-- Dependência do JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.3</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```
2. Caso seja executado o teste no estado atual, será recebido a mensagem informando que devem ser configuradas propriedades no arquivo `configuration.yml`.

## Configurando execução por browser

1. No arquivo `configuration.yml` adicionar as configurações abaixo
```yaml title="configuration.yml"

execution:

  target:
    url: http://localhost:8099    # Obrigatório
    version: 0.0.0                # Obrigatório

browsers:

- type: CHROME                    # Obrigatório pelo menos 1 browser

```
2. Caso seja executado o teste no estado atual o teste passará com sucesso. Note que durante a execução foi iniciado e fechado o browser rapidamente.
3. Também é possível adicionar configurações adicionais para os navegadores. Para cada configuração de navegador adicionada, será realizada uma nova execução dos testes, garantindo que todos os cenários sejam validados em diferentes navegadores.
```yaml title="configuration.yml" hl_lines="7-11"

execution:

  target:
    url: http://localhost:8099
    version: 0.0.0

browsers:

- type: CHROME                    # Execução 1
- type: FIREFOX                   # Execução 2
- type: EDGE                      # Execução 3

```
  ![execute test based](/documentation/img/browser-multi-execution.png)   
  **Obs:** Para que o teste seja executado com sucesso deve ter o browser informados instalados na máquina host de execuções.

4. Por padrão, os browser são executados com janela maximizada, porém podem ser aplicadas diversas configurações para o dimensionamento do browser    
```yaml title="configuration.yml" hl_lines="9-12 14-17 19-23"

execution:

  target:
    url: http://localhost:8099
    version: 0.0.0

browsers:

- type: CHROME
  headless: false
  dimension:
    mode: FULLSCREEN

- type: FIREFOX
  headless: true
  dimension:
    mode: MAXIMIZED

- type: EDGE
  dimension:
    mode: CUSTOM
    width: 1256
    height: 1018

```
  **Propriedades:**
    * **browsers.[*].type:**   
    Informa qual o navegador será executado.
    * **browsers.[*].headless:**   
    Por padrão é `false`. Se `true` a janela do browser ficará invisível durante a execução.
    * **browsers.[*].dimension.mode:**   
    Por padrão é definido como `MAXIMIZED`, tem valores possíveis `FULLSCREEN`, `MAXIMIZED` e `CUSTOM`. Se informado `CUSTOM`, as propriedades `width` e `height` serão obrigatórias.
    * **browsers.[*].dimension.width:**  
    Informa qual a dimensão de largura o navegador será executado.
    * **browsers.[*].dimension.height:**   
    Informa qual a dimensão de altura o navegador será executado.
5. Caso seja executado o teste no estado atual o teste passará com sucesso. Note que agora na lista de execuções relizadas tem breve descrição do dimensionamento no qual o navegador aberto.
    
    ![execute test based](/documentation/img/browser-custom-execution.png)

7. Pode ser configurada também _timeouts_ para execução dos testes
```yaml title="configuration.yml" hl_lines="7-9"

execution:

  target:
    url: http://localhost:8099
    version: 0.0.0
  
  delay:
    waitingTimeout: 5000    # Padrão
    actionInterval: 300     # Padrão

```
    * **execution.delay.waitingTimeout:**   
    Tempo de espera máximo para execução de ação.
    * **execution.delay.actionInterval:**   
    Intervalo de tempo entre ações a serem executadas.