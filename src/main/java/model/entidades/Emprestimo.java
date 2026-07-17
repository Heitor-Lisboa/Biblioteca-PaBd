package model.entidades;

import java.time.LocalDate;

public class Emprestimo {

    private int id;
    private int alunoId;
    private int livroId;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    public Emprestimo(int alunoId, int livroId) {
        this.alunoId = alunoId;
        this.livroId = livroId;
        this.dataEmprestimo = LocalDate.now();
        this.devolvido = false;
    }

    public Emprestimo(int id, int alunoId, int livroId, LocalDate dataEmprestimo,
                       LocalDate dataDevolucao, boolean devolvido) {
        this.id = id;
        this.alunoId = alunoId;
        this.livroId = livroId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = devolvido;
    }

    public int getId() {
        return id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public int getLivroId() {
        return livroId;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean isDevolvido() {
        return devolvido;
    }
}
