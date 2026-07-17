package model.entidades;

import model.dao.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Biblioteca {

    private final LivroDAO livroDAO = new LivroDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    public boolean addLivro(Livro livro) {
        try {
            livroDAO.inserir(livro);
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar livro: " + e.getMessage());
            return false;
        }
    }

    public boolean addAluno(Aluno aluno) {
        try {
            alunoDAO.inserir(aluno);
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
            return false;
        }
    }

    public Livro pesquisarLivro(String titulo) {
        try {
            return livroDAO.buscarPorTitulo(titulo);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
            return null;
        }
    }

    public Aluno pesquisarAluno(long matricula) {
        try {
            return alunoDAO.buscarPorMatricula(matricula);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Livro> getLivros() {
        try {
            return livroDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Livro> getLivrosDisponiveis() {
        try {
            return livroDAO.listarDisponiveis();
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros disponíveis: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean emprestarLivro(Aluno aluno, Livro livro) {
        try {
            boolean estoqueOk = livroDAO.alterarQtdDisponivel(livro.getId(), -1);
            if (!estoqueOk) {
                return false;
            }
            emprestimoDAO.registrarEmprestimo(new Emprestimo(aluno.getId(), livro.getId()));
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao registrar empréstimo: " + e.getMessage());
            return false;
        }
    }

    public boolean devolverLivro(Aluno aluno, Livro livro) {
        try {
            boolean devolveu = emprestimoDAO.registrarDevolucao(aluno.getId(), livro.getId());
            if (devolveu) {
                livroDAO.alterarQtdDisponivel(livro.getId(), 1);
            }
            return devolveu;
        } catch (SQLException e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
            return false;
        }
    }

    public void gerarRelatorioLivrosEmprestados() {
        try {
            System.out.println("Livros emprestados:");
            for (String linha : emprestimoDAO.relatorioLivrosEmprestados()) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void gerarRelatorioLivrosDisponiveis() {
        System.out.println("Livros disponíveis:");
        for (Livro l : getLivrosDisponiveis()) {
            System.out.println(l);
        }
    }

    public void gerarRelatorioEmprestimosDoAluno(Aluno aluno) {
        try {
            System.out.println("Empréstimos de " + aluno.getNome() + ":");
            for (String linha : emprestimoDAO.relatorioEmprestimosDoAluno(aluno.getId())) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void gerarRelatorioLivrosMaisEmprestados() {
        try {
            System.out.println("Livros mais emprestados:");
            for (String linha : emprestimoDAO.relatorioLivrosMaisEmprestados()) {
                System.out.println(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
