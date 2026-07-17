package model.entidades;

public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private long isbn;
    private int qtdTotal;
    private int qtdDisponivel;

    public Livro(String titulo, String autor, long isbn, int qtdTotal) {
        this(0, titulo, autor, isbn, qtdTotal, qtdTotal);
    }

    public Livro(int id, String titulo, String autor, long isbn, int qtdTotal, int qtdDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.qtdTotal = qtdTotal;
        this.qtdDisponivel = qtdDisponivel;
    }

    public boolean isDisponivel() {
        return qtdDisponivel > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(int qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public int getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(int qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    @Override
    public String toString() {
        return qtdDisponivel + "/" + qtdTotal + " unidade(s) disponíveis do livro: " + titulo + " de " + autor;
    }
}
