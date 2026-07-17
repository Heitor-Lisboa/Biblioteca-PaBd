# Biblioteca Escolar

Sistema de controle de empréstimos de livros para a biblioteca de uma escola, desenvolvido em Java (Swing) com persistência em banco de dados MySQL.

Trabalho final da disciplina de PaBd.

## Funcionalidades

- Cadastro de livros
- Cadastro de alunos (login por nome + matrícula)
- Registro de empréstimos
- Registro de devoluções
- Relatórios:
  - Livros emprestados no momento
  - Livros disponíveis
  - Empréstimos de um aluno específico
  - Livros mais emprestados (histórico geral)

## Estrutura do projeto

O código segue o padrão MVC:

```
src/main/java/
├── model/
│   ├── entidades/       # Classes de domínio (Livro, Aluno, Emprestimo) + fachada Biblioteca
│   └── dao/              # Acesso ao banco (Conexao, LivroDAO, AlunoDAO, EmprestimoDAO)
└── vision/                # Telas Swing (Login, Principal)
```

- **model.entidades** — as classes de dados (`Livro`, `Aluno`, `Emprestimo`) e a classe `Biblioteca`, que funciona como fachada/controller: as telas chamam métodos da `Biblioteca`, que por sua vez delega para os DAOs.
- **model.dao** — toda a comunicação SQL fica isolada aqui. `Conexao` centraliza a URL/usuário/senha do banco.
- **vision** — interface gráfica (Swing). Não tem lógica de banco, só chama a `Biblioteca`.

## Banco de dados

O script `database/biblioteca_grupo4.sql` cria o banco `biblioteca_escola` com três tabelas:

| Tabela       | Campos principais                                                                 |
|--------------|-------------------------------------------------------------------------------------|
| `livro`      | id, titulo, autor, isbn (único), qtd_total, qtd_disponivel                         |
| `aluno`      | id, nome, matricula (BIGINT, único — comporta matrículas no formato ano/período/campus/curso/registro) |
| `emprestimo` | id, aluno_id (FK), livro_id (FK), data_emprestimo, data_devolucao, devolvido       |

## Como rodar

### 1. Banco de dados

Com o MySQL instalado e rodando, execute o script:

```bash
mysql -u seu_usuario -p < database/biblioteca_grupo4.sql
```

Ou cole o conteúdo do arquivo direto no MySQL Workbench.

### 2. Credenciais de conexão

Abra `src/main/java/model/dao/Conexao.java` e edite com as credenciais do seu banco:

```java
private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_escola?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USUARIO = "SEU_USUARIO";
private static final String SENHA = "SUA_SENHA";
```

> Nunca faça commit desse arquivo com suas credenciais reais preenchidas — deixe sempre um placeholder antes de subir para o repositório.

### 3. Executar

Pelo terminal:

```bash
./gradlew run
```

Ou execute a classe `vision.Login` diretamente pela sua IDE.

## Tecnologias

- Java 17
- Swing (interface gráfica)
- MySQL 8
- Gradle
- Driver JDBC: `mysql-connector-j`

## Autores

- Grupo 4 — PaBd
