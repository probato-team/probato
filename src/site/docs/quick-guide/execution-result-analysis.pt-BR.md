# **Execução e Análise de Resultados**

## **Executando os Testes Automatizados**

Após a implementação do roteiro de testes utilizando o **Probato**, o próximo passo é executar os testes automatizados para validar as funcionalidades da aplicação. A execução pode ser realizada de forma eficiente seguindo as etapas abaixo:

1. **Execução via IDE**:
    * Utilize a IDE para iniciar a execução dos testes como JUnit.
    * Caso necessário, ajuste os arquivos de configuração (`configuration.yml`) para personalizar a execução, como tempos de espera, navegador utilizado, ou ambiente alvo (desenvolvimento, homologação, produção).

    ![execute IDE test](/assets/images/quick-guide/execution-result-ide.png)

2. **Execução via Terminal**:
    * Utilize comandos como `mvn test` (Maven) ou `gradle test` (Gradle) para iniciar a execução dos testes.
    * Monitore o log gerado durante a execução para identificar erros ou comportamentos inesperados.

    ![execute CMD test](/assets/images/quick-guide/execution-result-cmd.png)

3. **Execução em Pipelines CI/CD**:
    * Integre os testes em pipelines de integração contínua, como Jenkins, GitHub Actions ou GitLab CI, para garantir a validação automatizada a cada alteração no código.
    * Configure notificações para alertar a equipe sobre falhas nos testes.

    !!! note
         A execução é semelhante ao procedimento pelo CMD.

    !!! warning
         O ambiente de execução precisa ter todos os recursos necessários instalados.
---

## **Coleta de Resultados**

Durante a execução dos testes, o **Probato** coleta dados relevantes para análise, como:

- **Passos executados**:      
    Cada ação realizada pelo script é registrada, proporcionando um histórico detalhado da execução.
- **Capturas de tela e vídeos**:    
    Em caso de falha, capturas de tela e vídeos do momento da execução são gerados automaticamente para facilitar a análise.
- **Resultados de validação**:      
    Informações sobre quais testes foram aprovados ou falharam, com detalhes sobre os erros encontrados.
- **Tempo de execução**:      
    Relatórios indicam o tempo total para cada teste, auxiliando na identificação de gargalos de desempenho.
- **Logs SQL**:   
    Todos os comandos SQL executados durante o teste são registrados, permitindo a auditoria das alterações feitas no banco de dados.

    !!! note
         Esses dados são armazenados em disco temporariamente durante a execução dos testes. Ao finalizar cada um dos roteiros, os dados serão submetidos para o **Probato Manager**.

---

## **Análise de Resultados**

Após a execução, é essencial realizar uma análise detalhada para identificar falhas e melhorar a qualidade do sistema. O **Probato** possui uma aplicação dedicada com essa finalidade, o **Probato Manager**. É através dela que os dados coletados durante a execução dos testes são armazenados e podem ser observados e analisados. As informações de qualidade aferidas também estão disponíveis nessa ferramenta.

1. **Métricas de qualidade**:
    * O **Probato Manager** fornece, de forma gráfica, métricas de qualidade das aplicações alvo dos testes ao longo de sua evolução. Também é possível visualizar essas mesmas métricas para uma aplicação específica.
    * O **Probato Manager** fornece métricas diversas, como quantidade de projetos analisados, colaboradores, suítes, scripts, execuções, complexidade média dos produtos e histórico de qualidade ao longo do tempo.

    ![Probato Manager](/assets/images/quick-guide/probato-manager-dashboard.png)

1. **Relatórios Detalhados**:
    * Utilize relatórios gerados pelo **Probato Manager** para visualizar o status de cada teste, erros encontrados e evidências coletadas.
    * Verifique a correlação entre os erros reportados e os logs da aplicação para identificar a causa raiz.

    ![Probato Manager](/assets/images/quick-guide/probato-manager-results.png)

2. **Validação de Requisitos**:
    * Certifique-se de que todos os cenários definidos nos requisitos foram validados.
    * Atualize os casos de teste conforme novos requisitos ou mudanças na aplicação.

    ![Probato Manager](/assets/images/quick-guide/probato-manager-report.png)

3. **Acompanhamento de Falhas**:
    * Para cada falha identificada, crie tickets em ferramentas de gerenciamento de projetos, como Jira, Trello ou GitLab.
    * Priorize a correção das falhas mais críticas, garantindo que os erros sejam resolvidos antes de prosseguir com novas implementações.

    ![Probato Manager](/assets/images/quick-guide/probato-manager-result-detail.png)

---

## **Checklist Final**

Antes de concluir a análise de resultados no Probato Manager, verifique se:

* ✅ Todos os logs relevantes foram analisados e correlacionados com os erros reportados.
* ✅ Todos os casos de teste falhos foram documentados em ferramentas de gerenciamento de projetos.
* ✅ Relatórios foram gerados e enviados para as partes interessadas.
* ✅ O ambiente de execução foi revisado para garantir consistência nas próximas execuções.

🎉 Pronto para aprimorar ainda mais a qualidade do seu sistema!
