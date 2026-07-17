CREATE DATABASE IF NOT EXISTS biblioteca_escola;
USE biblioteca_escola;

CREATE TABLE IF NOT EXISTS livro (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    titulo         VARCHAR(200) NOT NULL,
    autor          VARCHAR(150) NOT NULL,
    isbn           BIGINT NOT NULL UNIQUE,
    qtd_total      INT NOT NULL,
    qtd_disponivel INT NOT NULL
);

CREATE TABLE IF NOT EXISTS aluno (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nome      VARCHAR(150) NOT NULL,
    matricula INT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS emprestimo (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    aluno_id         INT NOT NULL,
    livro_id         INT NOT NULL,
    data_emprestimo  DATE NOT NULL,
    data_devolucao   DATE NULL,
    devolvido        BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id),
    FOREIGN KEY (livro_id) REFERENCES livro(id)
);
