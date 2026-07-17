package vision;

import javax.swing.*;
import java.awt.*;
import model.entidades.*;

public class Login extends JFrame {
    private JTextField txtNome;
    private JTextField txtMatricula;
    private JButton btnLogin;
    private Biblioteca biblioteca;

    public Login() {
        super("Login da Biblioteca");

        biblioteca = new Biblioteca();

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(15);

        JLabel lblMatricula = new JLabel("Matrícula:");
        txtMatricula = new JTextField(15);

        btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(e -> fazerLogin());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblMatricula);
        panel.add(txtMatricula);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fazerLogin() {
        String nome = txtNome.getText().trim();
        String matriculaText = txtMatricula.getText().trim();

        if (nome.isEmpty() || matriculaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        long matricula;
        try {
            matricula = Long.parseLong(matriculaText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Matrícula deve ser um número.");
            return;
        }

        Aluno aluno = biblioteca.pesquisarAluno(matricula);
        if (aluno == null) {
            aluno = new Aluno(nome, matricula);
            if (!biblioteca.addAluno(aluno)) {
                JOptionPane.showMessageDialog(this,
                        "Não foi possível cadastrar o aluno. Verifique a conexão com o banco.");
                return;
            }
        }

        dispose();
        new Principal(biblioteca, aluno);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
