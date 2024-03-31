package utils;

import DAO.PersonagemDAO;
import DAO.PersonagemRacaDAO;
import Model.Personagem;
import Model.Raca;
import view.BatalhaGUI;

public class AtualizarTelaThread extends Thread {
    private PersonagemDAO personagemDAO = new PersonagemDAO();
    private PersonagemRacaDAO personagemRacaDAO = new PersonagemRacaDAO();
    private Personagem p1;
    private Personagem p2;
    private Raca racaP1;
    private Raca racaP2;
    private BatalhaGUI gui;

    public AtualizarTelaThread(Personagem p1, Personagem p2, BatalhaGUI gui) {
        this.p1 = p1;
        this.p2 = p2;
        this.gui = gui;
        racaP1 = personagemRacaDAO.getRacaById(p1.getIdPersonagem());
        racaP2 = personagemRacaDAO.getRacaById(p2.getIdPersonagem());
    }
    @Override
    public void run() {
        gui.show();
        gui.updateImagemPersonagem(racaP1.getClasseNome(), racaP2.getClasseNome());

        while (true) {
            try {
                p1 = personagemDAO.read(p1.getIdPersonagem());
                p2 = personagemDAO.read(p2.getIdPersonagem());
                gui.updatePersonagem1Info(p1.toString());
                gui.updatePersonagem2Info(p2.toString());
                gui.updateNomePersonagem(p1.getNome(), p2.getNome());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
