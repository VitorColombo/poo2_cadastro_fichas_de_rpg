package view;

import DAO.ContaDAO;
import DAO.ContaPersonagemDAO;
import DAO.PersonagemDAO;
import DAO.PersonagemRacaDAO;
import Model.Conta;
import Model.Personagem;
import Model.Raca;
import utils.ImagensClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class MainMenuGUI {
    private final JFrame frame;
    private final JPanel secondButtonPanel;
    private final JTextArea outputArea;
    private final Conta usuarioLogado;
    private final ContaPersonagemDAO contaPersonagemDAO = new ContaPersonagemDAO();
    private final PersonagemDAO personagemDAO = new PersonagemDAO();
    private final PersonagemRacaDAO personagemRacaDAO = new PersonagemRacaDAO();
    private final ContaDAO contaDAO = new ContaDAO();
    private final JLabel imagemPersonagemLabel;


    public MainMenuGUI(Conta usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        frame = new JFrame("Menu Usuário - " + usuarioLogado.getLogin());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1));
        secondButtonPanel = new JPanel(new GridBagLayout());
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setMaximumSize(new Dimension(300, 500));

        JButton listarPersonagensBtn = new JButton("Listar Personagens");
        JButton atualizarPersonagemBtn = new JButton("Atualizar Personagem");
        JButton deletarContaBtn = new JButton("Deletar Conta");
        JButton alterarContaBtn = new JButton("Alterar Conta");
        JButton adicionarPersonagemBtn = new JButton("Adicionar Personagem");
        JButton buscarPersonagemBtn = new JButton("Buscar Personagem");
        JButton deletarPersonagemBtn = new JButton("Deletar Personagem");
        JButton batalhaBtn = new JButton("Batalhar!");
        JButton sairBtn = new JButton("Sair");

        JButton[] buttons = {listarPersonagensBtn, adicionarPersonagemBtn, buscarPersonagemBtn, deletarPersonagemBtn, atualizarPersonagemBtn,
                alterarContaBtn, deletarContaBtn, batalhaBtn, sairBtn};

        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(200, 40));
        }

        buttonPanel.add(adicionarPersonagemBtn);
        buttonPanel.add(listarPersonagensBtn);
        buttonPanel.add(buscarPersonagemBtn);
        buttonPanel.add(atualizarPersonagemBtn);
        buttonPanel.add(deletarPersonagemBtn);
        buttonPanel.add(alterarContaBtn);
        buttonPanel.add(deletarContaBtn);
        buttonPanel.add(batalhaBtn);
        buttonPanel.add(sairBtn);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        imagemPersonagemLabel = new JLabel();
        imagemPersonagemLabel.setPreferredSize(new Dimension(200, 350));
        imagemPersonagemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        outputPanel.add(imagemPersonagemLabel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(secondButtonPanel, BorderLayout.EAST);
        frame.add(outputPanel, BorderLayout.CENTER);

        listarPersonagensBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPersonagens();
            }
        });
        adicionarPersonagemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for adding a character
            }
        });

        buscarPersonagemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for searching a character
            }
        });

        atualizarPersonagemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for updating a character
            }
        });

        deletarPersonagemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for deleting a character
            }
        });

        alterarContaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("Novo login:"));
                JTextField loginField = new JTextField();
                panel.add(loginField);
                panel.add(new JLabel("Nova password:"));
                JTextField passwordField = new JTextField();
                panel.add(passwordField);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Atualizando Conta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String newLogin = loginField.getText();
                    String newSenha = passwordField.getText();

                    if (newLogin != null && !newLogin.isEmpty()) {
                        usuarioLogado.setLogin(newLogin);
                    }

                    if (newSenha != null && !newSenha.isEmpty()) {
                        usuarioLogado.setSenha(newSenha);
                    }

                    if (contaDAO.update(usuarioLogado) > 0) {
                        JOptionPane.showMessageDialog(frame, "Conta atualizada com sucesso! :)");
                    } else {
                        JOptionPane.showMessageDialog(frame, "A conta não pode ser atualizada", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deletarContaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senha = JOptionPane.showInputDialog(frame, "Insira a senha para prosseguir:", "Deletar Conta", JOptionPane.WARNING_MESSAGE);
                if (usuarioLogado.getSenha().equals(senha)) {
                    if (contaDAO.delete(usuarioLogado.getIdConta()) > 0) {
                        JOptionPane.showMessageDialog(frame, "Você irá fazer falta :(", "Conta deletada com sucesso", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        LoginGUI loginGUI = new LoginGUI();
                        loginGUI.show();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erro ao deletar conta", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Não foi possível deletar a conta", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        batalhaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for starting a battle
            }
        });

        sairBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.show();
            }
        });

        frame.setVisible(true);
    }

    public void listarPersonagens() {
        outputArea.setText("");
        outputArea.append("\nSEUS PERSONAGENS\n\n");
        ArrayList<Personagem> listaPersonagens = contaPersonagemDAO.buscarPersonagemConta(usuarioLogado.getIdConta());
        for (Personagem personagem : listaPersonagens) {
            outputArea.append("id: " + personagem.getIdPersonagem() + "\n");
            outputArea.append("Nome: " + personagem.getNome() + "\n");
            outputArea.append("Nível: " + personagem.getNivel() + "\n");
            outputArea.append("Vida: " + personagem.getVida() + "\n");
            outputArea.append("Força: " + personagem.getForca() + "\n");
            outputArea.append("Defesa: " + personagem.getDefesa() + "\n");
            outputArea.append("\n");
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 2, 5, 2);

        secondButtonPanel.removeAll();
        if (!listaPersonagens.isEmpty()) {
            for (Personagem personagem : listaPersonagens) {
                JButton personagemButton = new JButton(personagem.getNome());
                personagemButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        outputArea.setText("");
                        Raca raca = personagemRacaDAO.getRacaById(personagem.getIdPersonagem());
                        addImagemPersonagem(raca.getClasseNome());
                        outputArea.append("id: " + personagem.getIdPersonagem() + "\n");
                        outputArea.append("Nome: " + personagem.getNome() + "\n");
                        outputArea.append("Nível: " + personagem.getNivel() + "\n");
                        outputArea.append("Vida: " + personagem.getVida() + "\n");
                        outputArea.append("Força: " + personagem.getForca() + "\n");
                        outputArea.append("Defesa: " + personagem.getDefesa() + "\n");
                    }
                });

                personagemButton.setPreferredSize(new Dimension(150, 30));
                secondButtonPanel.add(personagemButton, gbc);
                gbc.gridy++;
            }
        } else {
            outputArea.append("Conta não tem personagem vinculado :(\n");
        }
        secondButtonPanel.revalidate();
        secondButtonPanel.repaint();
    }

    public void show() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void addImagemPersonagem(String className) {
        Map<String, String> paths = ImagensClasses.getPaths();
        String path = paths.get(className);
        if (path != null) {
            ImageIcon imagem = new ImageIcon(path);
            Image image = imagem.getImage().getScaledInstance(250, 350, Image.SCALE_SMOOTH);
            imagemPersonagemLabel.setIcon(new ImageIcon(image));
        }
    }
}