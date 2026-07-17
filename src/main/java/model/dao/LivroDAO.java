package model.dao;

import model.entidades.*;
import java.sql.*;
import java.util.ArrayList;

public class LivroDAO {

    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, autor, isbn, qtd_total, qtd_disponivel) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setLong(3, livro.getIsbn());
            stmt.setInt(4, livro.getQtdTotal());
            stmt.setInt(5, livro.getQtdDisponivel());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    livro.setId(rs.getInt(1));
                }
            }
        }
    }

    public Livro buscarPorTitulo(String titulo) throws SQLException {
        String sql = "SELECT * FROM livro WHERE titulo LIKE ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public ArrayList<Livro> listarTodos() throws SQLException {
        ArrayList<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro ORDER BY titulo";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                livros.add(mapear(rs));
            }
        }
        return livros;
    }

    public ArrayList<Livro> listarDisponiveis() throws SQLException {
        ArrayList<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro WHERE qtd_disponivel > 0 ORDER BY titulo";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                livros.add(mapear(rs));
            }
        }
        return livros;
    }

    // decremento/incremento seguro (nunca deixa qtd_disponivel < 0 nem > qtd_total)
    public boolean alterarQtdDisponivel(int livroId, int delta) throws SQLException {
        String sql = "UPDATE livro SET qtd_disponivel = qtd_disponivel + ? " +
                     "WHERE id = ? AND qtd_disponivel + ? BETWEEN 0 AND qtd_total";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, delta);
            stmt.setInt(2, livroId);
            stmt.setInt(3, delta);
            return stmt.executeUpdate() > 0;
        }
    }

    private Livro mapear(ResultSet rs) throws SQLException {
        return new Livro(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getLong("isbn"),
                rs.getInt("qtd_total"),
                rs.getInt("qtd_disponivel")
        );
    }
}
