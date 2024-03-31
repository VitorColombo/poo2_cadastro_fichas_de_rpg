import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.ContaDAO;
import Model.Conta;

public class LoginGUI {
    private JFrame frame;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private ContaDAO contaDAO;
    private Conta usuarioLogado;

    public LoginGUI() {
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        loginField = new JTextField();
        passwordField = new JPasswordField();

        contaDAO = new ContaDAO();

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String senha = new String(passwordField.getPassword());

                Conta conta = contaDAO.readLogin(login);

                if (conta != null) {
                    if (conta.getSenha().equals(senha)) {
                        usuarioLogado = conta;
                        JOptionPane.showMessageDialog(frame, "Bem vindo/a " + conta.getLogin() + "!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Senha incorreta. Tente novamente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Conta nao encontrada. Tente novamente ou crie uma nova conta.");
                }
            }
        });

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action when the register button is clicked
            }
        });

        frame.setLayout(new GridLayout(4, 1));
        frame.add(new JLabel("Login:"));
        frame.add(loginField);
        frame.add(new JLabel("Password:"));
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(registerButton);
    }

    public void show() {
        frame.setVisible(true);
    }
}