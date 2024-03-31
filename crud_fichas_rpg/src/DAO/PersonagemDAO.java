
package DAO;

import Main.ConexaoMySQL;
import Model.Personagem;
import Model.Raca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PersonagemDAO {

    public int insert(Personagem personagem){
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConexaoMySQL.getConexaoMySQL();
            ps = conn.prepareStatement("INSERT INTO personagem(nome, nivel, vida, forca) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, personagem.getNome());
            ps.setInt(2, personagem.getNivel());
            ps.setDouble(3, personagem.getVida());
            ps.setInt(4, personagem.getForca());

            int rowCount = ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                int generatedId = generatedKeys.getInt(1);
                personagem.setIdPersonagem(generatedId);
            }else{
                System.out.println("Nenhuma chave foi gerada");
            }
            generatedKeys.close();
            return rowCount;
        }catch(SQLException ex){
            System.out.println(ex);
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public Personagem read(int idPersonagem){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = ConexaoMySQL.getConexaoMySQL();
            ps = conn.prepareStatement("SELECT * FROM personagem WHERE idPersonagem=?");
            ps.setInt(1, idPersonagem);
            rs = ps.executeQuery();

            if(rs.next()){
                String nome = rs.getString(2);
                int nivel = rs.getInt(3);
                int forca = rs.getInt(4);
                double vida = rs.getDouble(5);
                double defesa = rs.getDouble(6);
                Personagem personagem = new Personagem (idPersonagem, nome, nivel, forca, vida, defesa);
                return personagem;
            }
        }catch(SQLException ex){
            System.out.println(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return null;
    }

    public ArrayList<Personagem> list(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Personagem> personagens = new ArrayList<Personagem>();
        try{
            conn = ConexaoMySQL.getConexaoMySQL();
            ps = conn.prepareStatement("SELECT * FROM personagem");
            rs = ps.executeQuery();

            while(rs.next()){
                int idPersonagem = rs.getInt(1);
                String nome = rs.getString(2);
                int nivel = rs.getInt(3);
                int forca = rs.getInt(4);
                double vida = rs.getDouble(5);
                Personagem personagem = new Personagem(idPersonagem, nome, nivel, forca, vida);
                personagens.add(personagem);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return personagens;
    }

    public int update(Personagem personagem){
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConexaoMySQL.getConexaoMySQL();
            ps = conn.prepareStatement("UPDATE personagem SET nome=?, nivel=?, vida=?, forca=?, defesa=? WHERE idPersonagem=?");
            ps.setString(1, personagem.getNome());
            ps.setInt(2, personagem.getNivel());
            ps.setDouble(3, personagem.getVida());
            ps.setInt(4, personagem.getForca());
            ps.setDouble(5, personagem.getDefesa());
            ps.setInt(6, personagem.getIdPersonagem());

            int rowCount = ps.executeUpdate();
            return rowCount;
        }catch(SQLException ex){
            System.out.println(ex);
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
    public int delete(int idPersonagem){
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = ConexaoMySQL.getConexaoMySQL();
            ps = conn.prepareStatement("DELETE FROM personagem WHERE idPersonagem=?");
            ps.setInt(1, idPersonagem);

            int rowCount = ps.executeUpdate();
            return rowCount;
        }catch(SQLException ex){
            System.out.println(ex);
            return 0;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
