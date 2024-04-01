package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.ContaDAO;
import Model.Conta;

public class RegistroGUI {
    private JFrame frame;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;
    private ContaDAO contaDAO;
    private LoginGUI loginGUI;
    private Conta usuarioLogado;


    public RegistroGUI(LoginGUI loginGUI) {
        this.loginGUI = loginGUI;
        contaDAO = new ContaDAO();
        frame = new JFrame("Cadastro");
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
        loginField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerCadastro();
            }
        });

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerCadastro();
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
        registerButton = new JButton("Registrar");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerCadastro();
            }
        });
        mainPanel.add(registerButton, gbc);

        gbc.gridy++;
        backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                loginGUI.show();
            }
        });
        mainPanel.add(backButton, gbc);

        frame.add(mainPanel);
    }

    public void show() {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void fazerCadastro() {
        String login = loginField.getText();
        String senha = new String(passwordField.getPassword());

        Conta conta = new Conta(login, senha);

        if (contaDAO.insert(conta) > 0) {
            usuarioLogado = conta;
            JOptionPane.showMessageDialog(frame, "Bem vindo(a) ao sistema, " + login + "!");
            MainMenuGUI mainMenuGUI = new MainMenuGUI(usuarioLogado);
            mainMenuGUI.show();
            frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(frame, "Erro! Nao foi possivel criar conta");
        }
    }
}