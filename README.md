# TasksManagement

Backend acadêmico em Kotlin e Spring Boot, baseado nos estudos do canal Devaria. O estado atual implementa autenticação demonstrativa por JWT e validação de um cadastro de usuário. O gerenciamento persistente de tarefas ainda não está implementado.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.6.0-blue)](https://kotlinlang.org/) [![Spring_Boot](https://img.shields.io/badge/Spring_Boot-2.5.0-blue)](https://docs.spring.io/spring-boot/) [![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

## 📋 Table of contents

- [✨ Features](#features)
- [🧪 Technologies](#technologies)
- [🛠️ Environment setup](#environment-setup)
- [🚀 Getting started](#getting-started)
- [🔌 API usage](#api-usage)
- [🗂️ Project structure](#project-structure)
- [✅ Testing and quality](#testing-and-quality)
- [🔎 Troubleshooting](#troubleshooting)
- [🤝 Contributing](#contributing)
- [📜 License](#license)

<a id="features"></a>

## ✨ Features

- `POST /api/login` compara os dados com uma conta de demonstração fixa e emite JWT.
- `POST /api/usuario` valida nome, e-mail e senha e retorna o objeto recebido; não persiste dados.
- Filtro JWT com header `Authorization: Bearer <token>` para rotas protegidas.
- Configuração Swagger/Springfox; o acesso está sujeito à configuração de segurança.

As dependências JPA e MySQL estão comentadas em `build.gradle.kts`. Não há repository, conexão MySQL ativa ou CRUD de tarefas nesta revisão. As credenciais e chave demonstrativas embutidas no código precisam de revisão antes de qualquer uso real.

<a id="technologies"></a>

## 🧪 Technologies

| Tecnologia | Versão declarada | Papel |
| --- | --- | --- |
| [Kotlin](https://kotlinlang.org/docs/home.html) | `1.6.0` | Linguagem e plugins Spring/JPA |
| [Spring Boot](https://docs.spring.io/spring-boot/) | `2.5.0` | API e segurança |
| [Gradle](https://docs.gradle.org/) | `7.3` no wrapper | Build |
| [JJWT](https://github.com/jwtk/jjwt) | `0.6.0` | Tokens JWT |
| [Springfox](https://springfox.github.io/springfox/) | `2.7.0` | Swagger |
| Java/JVM | alvo `1.8` | Bytecode configurado |

Para executar o Gradle 7.3, selecione **JDK 17 já instalado**. O alvo JVM 1.8 não obriga usar Java 8 como runtime do wrapper. O único Java disponível nesta validação foi 21.0.13; esse conjunto legado não foi validado nele.

<a id="environment-setup"></a>

## 🛠️ Environment setup

```bash
git clone https://github.com/paulodaluz/TasksManagement.git
cd TasksManagement
```

Defina `JAVA_HOME` para seu JDK 17 já instalado e adicione `$JAVA_HOME/bin` ao `PATH`. Confirme `java -version` e use o wrapper, sem instalar Gradle globalmente:

```bash
bash ./gradlew --version
bash ./gradlew --no-daemon build
```

No Windows, use `gradlew.bat`. Não há `.env`, variáveis próprias obrigatórias, hooks de Git ou etapa `prepare`. `application.properties` está vazio. O projeto atual não requer MySQL porque o acesso a dados está comentado.

A configuração de login e assinatura JWT é fixa no código e não lê variáveis de ambiente. Não copie essas credenciais para a documentação, não use esse mecanismo com dados reais e trate a parametrização como trabalho separado.

<a id="getting-started"></a>

## 🚀 Getting started

```bash
bash ./gradlew --no-daemon bootRun
```

O Spring usa `http://localhost:8080` por padrão. `SERVER_PORT=4500 bash ./gradlew --no-daemon bootRun` é uma opção para escolher uma porta local distinta, via configuração padrão do Spring Boot; não é o padrão do projeto.

Após build, o JAR executável fica em `build/libs/` (nome derivado do projeto/versão `0.0.1-SNAPSHOT`). Confirme o nome gerado antes de usar `java -jar`. A aplicação não disponibiliza endpoint de saúde. Não foi confirmado um servidor iniciado nesta tarefa.

<a id="api-usage"></a>

## 🔌 API usage

| Método | Rota | Acesso e comportamento |
| --- | --- | --- |
| `POST` | `/api/login` | Pública; corpo `login`, `senha`; valida conta demonstrativa fixa |
| `POST` | `/api/usuario` | Pública; corpo `id`, `name`, `email`, `password`; valida e devolve dados |

Exemplo fictício de validação de cadastro, sem persistência:

```bash
curl -X POST http://localhost:8080/api/usuario \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"name":"Pessoa Exemplo","email":"pessoa@example.com","password":"senha-ficticia"}'
```

`SecurityConfiguration` libera apenas os dois `POST` e exige autenticação nas demais requisições. O filtro espera `Authorization: Bearer TOKEN_DE_TESTE`. O Swagger é configurado para `/swagger-ui.html`, mas essa rota não foi liberada explicitamente na segurança; o antigo README não deve ser interpretado como garantia de acesso anônimo. Nenhuma chamada foi realizada contra uma implantação externa.

<a id="project-structure"></a>

## 🗂️ Project structure

Todos os caminhos abaixo estão em `src/main/kotlin/br/com/paulo/projetos/tasksmanagement/`:

| Caminho | Responsabilidade |
| --- | --- |
| `TasksManagementApplication.kt` | Entrada Spring Boot |
| `controllers/` | Login e validação de usuário |
| `configurations/` | Segurança e Swagger |
| `filters/`, `utils/` | Autorização e operações JWT |
| `models/`, `dtos/`, `impl/` | Dados de usuário, respostas e UserDetails |

`src/test/kotlin/` contém um teste de carregamento do contexto. `gradle/wrapper/` fixa o Gradle utilizado.

<a id="testing-and-quality"></a>

## ✅ Testing and quality

```bash
bash ./gradlew --no-daemon test
bash ./gradlew --no-daemon build
```

O Gradle usa JUnit Platform. Não há tarefas próprias de lint ou formatação. Foi tentado `bash ./gradlew --no-daemon build -x test` com Java 21.0.13. O wrapper Gradle 7.3 foi obtido, mas a execução não passou da inicialização do processo de build no limite de 180 segundos e foi encerrada. Não há build, testes ou API em execução confirmados. A captura real do VS Code depende do editor e da verificação do tema/extensões atuais, indisponíveis nesta nuvem.

<a id="troubleshooting"></a>

## 🔎 Troubleshooting

- **Wrapper/build não conclui com Java 21:** o wrapper é Gradle 7.3; reproduza com JDK 17 já instalado. Nesta tarefa a tentativa foi encerrada por timeout antes de compilar.
- **401 ao abrir Swagger:** a configuração permite apenas os dois POST públicos; as demais rotas são protegidas.
- **Login fictício falha:** o controller compara com uma conta demonstrativa fixa. Não há cadastro persistente que crie uma nova credencial de login.
- **Usuário não aparece em banco:** o endpoint apenas valida e devolve os dados; JPA/MySQL estão desativados no build.

<a id="contributing"></a>

## 🤝 Contributing

Crie uma branch a partir de `main`, descreva a mudança e suas verificações em um pull request. Não inclua dependências instaladas, arquivos de ambiente, credenciais ou artefatos de build. Revise o diff antes do commit. Referência do estudo: [Devaria](https://www.youtube.com/c/Devaria/).

<a id="license"></a>

## 📜 License

Licença [MIT](LICENSE). Os avisos de autoria e o ano do arquivo existente foram preservados.

---

<p align="center">Made with 💜 by <a href="https://github.com/paulodaluz">Paulo da Luz</a></p>
