package view;
import utils.Batalha;
import utils.ImagensClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class BatalhaGUI {
    private JFrame frame;
    private JLabel nomePersonagemLabel;
    private JLabel nomePersonagemLabel1;
    private JLabel imagemPersonagemLabel;
    private JLabel imagemPersonagemLabel1;
    private JTextArea atributosPersonagemArea;
    private JLabel turnoLabel;
    private JLabel turnoJogadorLabel;
    private JTextArea acoesTextArea1;
    private JTextArea acoesTextArea2;
    private JTextArea personagem1Area;
    private JTextArea personagem2Area;
    private JButton atacarButton;
    private JButton defenderButton;
    private JButton pocaoButton;
    private Batalha batalha;

    public BatalhaGUI(Batalha batalha) {
        this.batalha = batalha;

        frame = new JFrame("Batalha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);

        nomePersonagemLabel = new JLabel();
        nomePersonagemLabel.setFont(new Font("Arial", Font.BOLD, 30));
        nomePersonagemLabel1 = new JLabel();
        nomePersonagemLabel1.setFont(new Font("Arial", Font.BOLD, 30));
        imagemPersonagemLabel = new JLabel();
        imagemPersonagemLabel1 = new JLabel();
        atributosPersonagemArea = new JTextArea();
        atributosPersonagemArea.setEditable(false);
        turnoLabel = new JLabel();

        personagem1Area = new JTextArea();
        personagem1Area.setEditable(false);
        personagem1Area.setFont(new Font("Arial", Font.PLAIN, 20));

        personagem2Area = new JTextArea();
        personagem2Area.setEditable(false);
        personagem2Area.setFont(new Font("Arial", Font.PLAIN, 20));

        atacarButton = new JButton("Atacar");
        atacarButton.setFont(new Font("Arial", Font.BOLD, 20));
        atacarButton.setBackground(Color.RED);
        atacarButton.setForeground(Color.WHITE);
        atacarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atacarAction();
            }
        });

        defenderButton = new JButton("Defender");
        defenderButton.setFont(new Font("Arial", Font.BOLD, 20));
        defenderButton.setBackground(Color.BLUE);
        defenderButton.setForeground(Color.WHITE);
        defenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defenderAction();
            }
        });

        pocaoButton = new JButton("Tomar Poção");
        pocaoButton.setFont(new Font("Arial", Font.BOLD, 20));
        pocaoButton.setBackground(Color.GREEN);
        pocaoButton.setForeground(Color.WHITE);
        pocaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarPocaoAction();
            }
        });

        turnoJogadorLabel = new JLabel();
        turnoJogadorLabel.setFont(new Font("Arial", Font.BOLD, 20));

        acoesTextArea1 = new JTextArea();
        acoesTextArea1.setEditable(true);
        acoesTextArea1.setFont(new Font("Arial", Font.PLAIN, 20));
        acoesTextArea2 = new JTextArea();
        acoesTextArea2.setEditable(false);
        acoesTextArea2.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel acoesPanel = new JPanel(new GridLayout(2, 1));
        acoesPanel.add(acoesTextArea1, BorderLayout.CENTER);
        acoesPanel.add(new JScrollPane(acoesTextArea2));

        JPanel personagem1Panel = new JPanel(new BorderLayout());
        nomePersonagemLabel.setHorizontalAlignment(JLabel.CENTER);
        personagem1Panel.add(nomePersonagemLabel, BorderLayout.NORTH);
        imagemPersonagemLabel.setHorizontalAlignment(JLabel.CENTER);
        personagem1Panel.add(imagemPersonagemLabel, BorderLayout.CENTER);
        personagem1Panel.add(new JScrollPane(personagem1Area), BorderLayout.SOUTH);

        JPanel personagem2Panel = new JPanel(new BorderLayout());
        nomePersonagemLabel1.setHorizontalAlignment(JLabel.CENTER);
        personagem2Panel.add(nomePersonagemLabel1, BorderLayout.NORTH);
        imagemPersonagemLabel1.setHorizontalAlignment(JLabel.CENTER);
        personagem2Panel.add(imagemPersonagemLabel1, BorderLayout.CENTER);
        personagem2Panel.add(new JScrollPane(personagem2Area), BorderLayout.SOUTH);

        JPanel personagemPanel = new JPanel(new GridLayout(1, 3));
        personagemPanel.add(personagem1Panel);
        personagemPanel.add(acoesPanel);
        personagemPanel.add(personagem2Panel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(atacarButton);
        buttonPanel.add(defenderButton);
        buttonPanel.add(pocaoButton);

        frame.getContentPane().add(atributosPersonagemArea, BorderLayout.EAST);
        frame.getContentPane().add(turnoJogadorLabel, BorderLayout.NORTH);
        frame.getContentPane().add(personagemPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    public void show() {
        frame.setVisible(true);
    }

    public void updatePersonagem1Info(String info) {
        personagem1Area.setText(info);
    }

    public void updatePersonagem2Info(String info) {
        personagem2Area.setText(info);
    }

    public void updateNomePersonagem(String nome, String nome2) {
        nomePersonagemLabel.setText(nome);
        nomePersonagemLabel1.setText(nome2);
    }

    public void updateImagemPersonagem(String classNameP1, String classNameP2) {
        Map<String, String> paths = ImagensClasses.getPaths();
        String path = paths.get(classNameP1);
        if (path != null) {
            ImageIcon imagem = new ImageIcon(path);
            Image image = imagem.getImage().getScaledInstance(350, 500, Image.SCALE_SMOOTH);
            imagemPersonagemLabel.setIcon(new ImageIcon(image));
        }
        String path2 = paths.get(classNameP2);
        if (path2 != null) {
            ImageIcon imagem = new ImageIcon(path2);
            Image image = imagem.getImage().getScaledInstance(350, 500, Image.SCALE_SMOOTH);
            imagemPersonagemLabel1.setIcon(new ImageIcon(image));
        }
    }
    private void atacarAction() {
        if (batalha.isPersonagem1Turno()) {
            batalha.atacar(batalha.getPersonagem1(), batalha.getPersonagem2());
        } else {
            batalha.atacar(batalha.getPersonagem2(), batalha.getPersonagem1());
        }
        checarVida();
        batalha.mudancaDeTurno();
    }

    private void defenderAction() {
        if (batalha.isPersonagem1Turno()) {
            batalha.defender(batalha.getPersonagem1());
        } else {
            batalha.defender(batalha.getPersonagem2());
        }
        checarVida();
        batalha.mudancaDeTurno();
    }

    private void tomarPocaoAction() {
        checarVida();
        if (batalha.isPersonagem1Turno()) {
            batalha.tomarPocao(batalha.getPersonagem1());
        } else {
            batalha.tomarPocao(batalha.getPersonagem2());
        }
        batalha.mudancaDeTurno();
    }
    public void checarVida() {
        if (batalha.getPersonagem1().getVida() <= 0) {
            JOptionPane.showMessageDialog(null, batalha.getPersonagem1().getNome() + " foi derrotado em " + batalha.getTurno() + " turnos!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            batalha.redirecionarMenu();
        }
        if (batalha.getPersonagem2().getVida() <= 0) {
            JOptionPane.showMessageDialog(null, batalha.getPersonagem2().getNome() + " foi derrotado em " + batalha.getTurno() + " turnos!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            batalha.redirecionarMenu();
        }
    }
    public void desativarBotoes() {
        atacarButton.setEnabled(false);
        defenderButton.setEnabled(false);
        pocaoButton.setEnabled(false);
    }
    public void updateTurnoJogador(String nomeJogador, int turno) {
        acoesTextArea1.setText("\n\n\n\n\n\n\n\nTurno: " + turno + "\n" + "Vez de: " + nomeJogador);
    }

    public void addAcao(String acao) {
        acoesTextArea2.append(acao + "\n\n");
    }
}