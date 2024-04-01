package view;

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
        contaDAO = new ContaDAO();
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));
        Image image = logoIcon.getImage();
        Image resizedImage = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(resizedImage);
        JLabel logoLabel = new JLabel(resizedLogoIcon);
        mainPanel.add(logoLabel, gbc);
        gbc.gridy++;

        loginField = new JTextField();
        loginField.setPreferredSize(new Dimension(200, 20));
        loginField.setMinimumSize(new Dimension(100, 20));
        loginField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));
        passwordField.setMinimumSize(new Dimension(100, 20));
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });

        mainPanel.add(new JLabel("Login:"), gbc);
        gbc.gridy++;
        mainPanel.add(loginField, gbc);
        gbc.gridy++;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        mainPanel.add(passwordField, gbc);
        gbc.gridy++;
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        mainPanel.add(loginButton, gbc);

        gbc.gridy++;
        registerButton = new JButton("Registrar");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroGUI registroGUI = new RegistroGUI(LoginGUI.this);
                registroGUI.show();
                frame.setVisible(false);
            }
        });
        mainPanel.add(registerButton, gbc);

        frame.add(mainPanel);
    }

    public void show() {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void fazerLogin() {
        String login = loginField.getText();
        String senha = new String(passwordField.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos os campos são obrigatórios");
            return;
        }
        Conta conta = contaDAO.readLogin(login);

        if (conta != null) {
            if (conta.getSenha().equals(senha)) {
                usuarioLogado = conta;
                JOptionPane.showMessageDialog(frame, "Bem vindo/a " + conta.getLogin() + "!");
                MainMenuGUI mainMenuGUI = new MainMenuGUI(usuarioLogado);
                mainMenuGUI.show();
                frame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(frame, "Login inválido. Tente novamente");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Usuário não cadastrado. Tente novamente");
        }
    }
}