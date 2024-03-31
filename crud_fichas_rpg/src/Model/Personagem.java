
package Model;

public class Personagem {
    private int idPersonagem;
    private String nome;
    private int nivel;
    private int forca;
    private double vida;
    private double defesa;

    public Personagem(int idPersonagem, String nome, int nivel, int forca, double vida) {
        this.idPersonagem = idPersonagem;
        this.nome = nome;
        this.nivel = nivel;
        this.forca = forca;
        this.vida = vida;
        this.defesa = 1;
    }
    public Personagem(int idPersonagem, String nome, int nivel, int forca, double vida, double defesa) {
        this.idPersonagem = idPersonagem;
        this.nome = nome;
        this.nivel = nivel;
        this.forca = forca;
        this.vida = vida;
        this.defesa = defesa;
    }

    public Personagem(String nome, int nivel) {
        this.idPersonagem = idPersonagem;
        this.nome = nome;
        this.nivel = nivel;
    } 
    

    public int getIdPersonagem() {
        return idPersonagem;
    }

    public void setIdPersonagem(int idPersonagem) {
        this.idPersonagem = idPersonagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public int getForca() {
        return forca;
    }
    public void setForca(int forca) {
        this.forca = forca;
    }
    public double getVida() {
        return vida;
    }
    public void setVida(double vida) {
        this.vida = vida;
    }
    public double getDefesa() {
        return defesa;
    }
    public void setDefesa(double defesa) {
        this.defesa = defesa;
    }
    @Override
    public String toString() {
        return "Nível: " + nivel + "\nForca:" + forca
                + "\nVida: " + vida + "\nDefesa: " + defesa ;
    }
}

//Criar uma aplicação em JAVA com interface gráfica com usuário contendo uma base de
//dados. O sistema realizará operações CRUD (Create, Read, Update, Delete) sobre a base
//de dados.
//A listagem dos dados armazenados na base de dados será obtida pela(s) operação(ões)
//READ, sendo feita de forma constante e regular. Ou seja, o usuário não precisará pressionar
//qualquer botão para listar e atualizar os dados existentes. A listagem dos dados existentes
//deverá permanecer visível o tempo tod o, não pode limpar de forma que o usuário veja a
//atualização completa.
//Portanto, o sistema deverá contar com thread(s) para realizar a busca (read) dos dados na
//SGBD e atualização da visualização na tela.
//Deixe um campo de configuração para que seja definido a frequência com que será
//realizada a busca (READ) dos dados do SGBD. Exemplo: buscar os dados a cada 10
//segundos de forma automática.
//A realização das demais operações (Create, Update, Delete) devem acontecer de forma
//assíncrona, ou seja, a operação não trava o sistema.
//Você deve fornecer uma forma de inserir automaticamente um conjunto grande de dados
//na base de dados. Assim poderemos verificar se a atualização automática está
//funcionando corretamente. Essa inserção pode ser feita num outro aplicativo a ser
//entregue.
