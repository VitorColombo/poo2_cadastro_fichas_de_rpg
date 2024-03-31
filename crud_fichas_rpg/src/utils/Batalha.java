package utils;

import DAO.PersonagemDAO;
import Model.Personagem;
import view.BatalhaGUI;

import javax.swing.*;

public class Batalha {
    private Personagem personagem1;
    private Personagem personagem2;
    private PersonagemDAO personagemDAO = new PersonagemDAO();
    private AtualizarTelaThread atualizarTelaThread;
    private BatalhaGUI gui;
    private int turno;
    private boolean personagem1Turno = true;

    public Batalha(Personagem personagem1, Personagem personagem2) {
        this.personagem1 = personagem1;
        this.personagem2 = personagem2;
        this.turno = 0;
        this.gui = new BatalhaGUI(this);
        this.atualizarTelaThread = new AtualizarTelaThread(personagem1, personagem2, gui);
    }

    public void batalhaAteAMorte() {
        this.gui.desativarBotoes();
        System.out.println("Batalha entre " + personagem1.getNome() + " e " + personagem2.getNome());
        System.out.println("Iniciando batalha...");
        atualizarTelaThread.start();
        gui.updateTurnoJogador(isPersonagem1Turno() ? getPersonagem1().getNome() : getPersonagem2().getNome(), this.turno);
        while (personagem1.getVida() > 0 && personagem2.getVida() > 0) {
            gui.checarVida();
            tomarPocao(personagem1);
            tomarPocao(personagem2);
            embate(personagem1, personagem2);
            embate(personagem2, personagem1);
            mudancaDeTurno();
        }
        if (personagem1.getVida() <= 0) {
            gui.addAcao(personagem1.getNome() + " foi derrotado em " + this.turno + " turnos!");
        } else {
            gui.addAcao(personagem2.getNome() + " foi derrotado em " + this.turno + " turnos!");
        }
    }
    public void batalhaManual() {
        atualizarTelaThread.start();
        gui.updateTurnoJogador(isPersonagem1Turno() ? getPersonagem1().getNome() : getPersonagem2().getNome(), this.turno);
    }
    public void tomarPocao(Personagem personagem) {
        double randomizador = Math.random();
        double chanceDeCura = randomizador + Math.pow(personagem.getNivel(), 2) * 0.01;
        double curaTotal = 0;
        if (chanceDeCura > 0.8) {
            curaTotal = 10;
            personagem.setVida(personagem.getVida() + curaTotal);
        } else if (chanceDeCura > 0.5) {
            curaTotal = 5;
            personagem.setVida(personagem.getVida() + curaTotal);
        } else {
            curaTotal = 2;
            personagem.setVida(personagem.getVida() + curaTotal);
        }
        gui.addAcao(personagem.getNome() + " tomou uma poção e recuperou " + curaTotal +  " de vida!");
        personagemDAO.update(personagem);
    }

    public void defenderAtaque(Personagem personagemDefendendo, double dano) {
        double randomizador = Math.random();
        double chanceDeDefesa = randomizador + (personagemDefendendo.getNivel() * 0.1);
        if (chanceDeDefesa > 0.9) {
            personagemDefendendo.setVida(personagemDefendendo.getVida() - (dano / 3));
        } else if (chanceDeDefesa > 0.5) {
            personagemDefendendo.setVida(personagemDefendendo.getVida() - (dano / 2));
        } else {
            personagemDefendendo.setVida(personagemDefendendo.getVida() - dano);
        }
        personagemDAO.update(personagemDefendendo);
    }

    public void embate(Personagem personagemAtacante, Personagem personagemDefendendo) {
        int dano = personagemAtacante.getForca() * personagemAtacante.getNivel();
        defenderAtaque(personagemDefendendo, dano);
        gui.addAcao(personagemAtacante.getNome() + " atacou " + personagemDefendendo.getNome() + " causando " + dano + " de dano!");
        personagemDAO.update(personagemDefendendo);
    }

    public double atacar(Personagem personagemAtacante, Personagem personagemDefendendo) {
        int dano = personagemAtacante.getForca() * personagemAtacante.getNivel();
        personagemDefendendo.setVida((personagemDefendendo.getVida() - dano) * personagemDefendendo.getDefesa());
        if (personagemDefendendo.getDefesa() < 1) {
            personagemDefendendo.setDefesa(1);
        }
        gui.addAcao(personagemAtacante.getNome() + " atacou " + personagemDefendendo.getNome() + " causando " + dano + " de dano!");
        personagemDAO.update(personagemDefendendo);
        return dano;
    }

    public void defender(Personagem personagemDefendendo) {
        double randomizador = Math.random();
        double chanceDeDefesa = randomizador + Math.pow(personagemDefendendo.getNivel(), 2) * 0.01;
        if (chanceDeDefesa > 1) {
            chanceDeDefesa = 1;
        }
        if (chanceDeDefesa > 0.9) {
            personagemDefendendo.setDefesa(0.4);
        } else if (chanceDeDefesa > 0.5) {
            personagemDefendendo.setDefesa(0.7);
        } else {
            personagemDefendendo.setDefesa(1);
        }
        gui.addAcao(personagemDefendendo.getNome() + " está se defendendo com " + personagemDefendendo.getDefesa() + "!");
        personagemDAO.update(personagemDefendendo);
    }
    public int getTurno() {
        return turno;
    }
    public Personagem getPersonagem1() {
        return personagem1;
    }
    public Personagem getPersonagem2() {
        return personagem2;
    }
    public boolean isPersonagem1Turno() {
        return personagem1Turno;
    }
    public void mudancaDeTurno() {
        this.personagem1Turno = !this.personagem1Turno;
        this.turno++;
        gui.updateTurnoJogador(isPersonagem1Turno() ? getPersonagem1().getNome() : getPersonagem2().getNome(), this.turno);
    }
    public void redirecionarMenu() {
        atualizarTelaThread.interrupt();
//        return;
    }
}
//as batalhas devem possuir alguns randomizadores que podem ser implementados em um método separado
//para que a batalha seja mais dinâmica e menos previsível
//o método de batalha deve ser chamado em algum lugar do código, como no main
//os metodos com randomização sao de poção e defesa
//o metodo de ataque é fixo, baseado na força e no nivel de cada personagem
//o metodo de fuga é feito com base em um randomizador multiplicado pela vida do personagem
//deve existir uma batalha manual onde o usuário pode escolher as ações de cada personagem
//deve existir uma batalha automática onde as ações são feitas de forma aleatória
//deve existir um método para verificar se a batalha acabou
//o sistema deve ter uma thread que atualiza a tela a cada 2 segundos
//mostrando os personagens e seus atributos atuais