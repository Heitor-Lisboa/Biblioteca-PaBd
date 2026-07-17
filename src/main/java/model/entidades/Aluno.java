package model.entidades;

public class Aluno {

    private int id;
    private String nome;
    private long matricula;

    public Aluno(String nome, long matricula) {
        this(0, nome, matricula);
    }

    public Aluno(int id, String nome, long matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public long getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return "Aluno: " + nome + " (matrícula: " + matricula + ")";
    }
}
