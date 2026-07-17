package model.dao;

import model.entidades.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmprestimoDAO {

    public void registrarEmprestimo(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (aluno_id, livro_id, data_emprestimo, devolvido) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getAlunoId());
            stmt.setInt(2, emprestimo.getLivroId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setBoolean(4, false);
            stmt.executeUpdate();
        }
    }

    public boolean registrarDevolucao(int alunoId, int livroId) throws SQLException {
        String sql = "UPDATE emprestimo SET devolvido = TRUE, data_devolucao = ? " +
                     "WHERE aluno_id = ? AND livro_id = ? AND devolvido = FALSE " +
                     "ORDER BY data_emprestimo LIMIT 1";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, alunoId);
            stmt.setInt(3, livroId);
            return stmt.executeUpdate() > 0;
        }
    }

    public ArrayList<String> relatorioLivrosEmprestados() throws SQLException {
        ArrayList<String> linhas = new ArrayList<>();
        String sql = "SELECT a.nome, l.titulo, e.data_emprestimo " +
                     "FROM emprestimo e " +
                     "JOIN aluno a ON a.id = e.aluno_id " +
                     "JOIN livro l ON l.id = e.livro_id " +
                     "WHERE e.devolvido = FALSE " +
                     "ORDER BY e.data_emprestimo";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                linhas.add(rs.getString("nome") + " está com o livro \"" + rs.getString("titulo") +
                        "\" desde " + rs.getDate("data_emprestimo"));
            }
        }
        return linhas;
    }

    public ArrayList<String> relatorioEmprestimosDoAluno(int alunoId) throws SQLException {
        ArrayList<String> linhas = new ArrayList<>();
        String sql = "SELECT l.titulo, e.data_emprestimo, e.data_devolucao, e.devolvido " +
                     "FROM emprestimo e " +
                     "JOIN livro l ON l.id = e.livro_id " +
                     "WHERE e.aluno_id = ? " +
                     "ORDER BY e.data_emprestimo DESC";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String status = rs.getBoolean("devolvido")
                            ? "devolvido em " + rs.getDate("data_devolucao")
                            : "ainda não devolvido";
                    linhas.add(rs.getString("titulo") + " - emprestado em " +
                            rs.getDate("data_emprestimo") + " (" + status + ")");
                }
            }
        }
        return linhas;
    }

    public ArrayList<String> relatorioLivrosMaisEmprestados() throws SQLException {
        ArrayList<String> linhas = new ArrayList<>();
        String sql = "SELECT l.titulo, COUNT(*) AS total " +
                     "FROM emprestimo e " +
                     "JOIN livro l ON l.id = e.livro_id " +
                     "GROUP BY l.id, l.titulo " +
                     "ORDER BY total DESC";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                linhas.add(rs.getString("titulo") + " - " + rs.getInt("total") + " empréstimo(s)");
            }
        }
        return linhas;
    }
}
