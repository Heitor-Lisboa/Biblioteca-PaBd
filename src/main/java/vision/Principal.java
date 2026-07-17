package vision;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import model.entidades.*;


public class Principal extends JFrame {
    private Biblioteca biblioteca;
    private Aluno aluno;

    public Principal(Biblioteca biblioteca, Aluno aluno) {
        super("Biblioteca Escolar");

        this.biblioteca = biblioteca;
        this.aluno = aluno;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 150, 100, 150));

        JButton btnAlugar = new JButton("Alugar Livro");
        JButton btnDevolver = new JButton("Devolver Livro");
        JButton btnCatalogo = new JButton("Mostrar Catálogo");
        JButton btnRelatorios = new JButton("Relatórios");
        JButton btnCadastrar = new JButton("Cadastrar Livro");

        Dimension buttonSize = new Dimension(400, 40);
        for (JButton b : new JButton[]{btnAlugar, btnDevolver, btnCatalogo, btnRelatorios, btnCadastrar}) {
            b.setMaximumSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        btnAlugar.addActionListener(e -> {
            String titulo = JOptionPane.showInputDialog(this, "Título do livro:");
            if (titulo != null && !titulo.trim().isEmpty()) {
                Livro livro = biblioteca.pesquisarLivro(titulo.trim());
                if (livro == null) {
                    JOptionPane.showMessageDialog(this, "Livro não encontrado.");
                } else if (biblioteca.emprestarLivro(aluno, livro)) {
                    JOptionPane.showMessageDialog(this, "Livro alugado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Livro indisponível.");
                }
            }
        });

        btnDevolver.addActionListener(e -> {
            String titulo = JOptionPane.showInputDialog(this, "Título do livro a devolver:");
            if (titulo != null && !titulo.trim().isEmpty()) {
                Livro livro = biblioteca.pesquisarLivro(titulo.trim());
                if (livro == null) {
                    JOptionPane.showMessageDialog(this, "Livro não encontrado.");
                } else if (biblioteca.devolverLivro(aluno, livro)) {
                    JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Você não tem esse livro emprestado.");
                }
            }
        });

        btnCatalogo.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Catálogo de Livros:\n");
            ArrayList<Livro> livros = biblioteca.getLivros();
            for (Livro l : livros) {
                sb.append(l.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnRelatorios.addActionListener(e -> {
            // os 4 relatórios pedidos na atividade (saem no console)
            biblioteca.gerarRelatorioLivrosEmprestados();
            biblioteca.gerarRelatorioLivrosDisponiveis();
            biblioteca.gerarRelatorioEmprestimosDoAluno(aluno);
            biblioteca.gerarRelatorioLivrosMaisEmprestados();
            JOptionPane.showMessageDialog(this, "Relatórios gerados no console.");
        });

        btnCadastrar.addActionListener(e -> cadastrarLivro());

        panel.add(btnAlugar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnDevolver);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnCatalogo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRelatorios);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnCadastrar);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void cadastrarLivro() {
        String titulo = JOptionPane.showInputDialog(this, "Digite o título do livro:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título é obrigatório.");
            return;
        }

        String autor = JOptionPane.showInputDialog(this, "Digite o autor do livro:");
        if (autor == null || autor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Autor é obrigatório.");
            return;
        }

        String isbnStr = JOptionPane.showInputDialog(this, "Digite o ISBN (número) do livro:");
        long isbn;
        try {
            isbn = Long.parseLong(isbnStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ISBN deve ser um número válido.");
            return;
        }

        String qtdStr = JOptionPane.showInputDialog(this, "Digite a quantidade disponível:");
        int qtd;
        try {
            qtd = Integer.parseInt(qtdStr);
            if (qtd < 1) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro válido.");
            return;
        }

        boolean ok = biblioteca.addLivro(new Livro(titulo.trim(), autor.trim(), isbn, qtd));
        if (ok) {
            JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível cadastrar o livro. Verifique a conexão com o banco (ISBN duplicado?).");
        }
    }
}
