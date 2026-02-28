package principal.crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogosDAO extends DAO {
    
    public JogosDAO() {
        super();
        conectar();
    }

    public void finalizar() {
        close();
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public boolean inserir(X jogo) {
        boolean status = false;
       
        String sql = "INSERT INTO Jogos(id_jogo, titulo_jogo, preco) VALUES (?, ?, ?)";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, jogo.getId_jogo());
            st.setString(2, jogo.getTitulo());
            st.setDouble(3, jogo.getPreco());
            
            st.executeUpdate();
            st.close();
            status = true;
            
        } catch (SQLException j) {
            System.err.println("Erro ao inserir: " + j.getMessage());
            return false;
        }
        return status;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public List<X> listar() {
        List<X> lista = new ArrayList<>();
        String sql = "SELECT * FROM Jogos ORDER BY id_jogo";
        
        try {
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                X jogo = new X();
                jogo.setId_jogo(rs.getInt("id_jogo"));
                jogo.setTitulo(rs.getString("titulo_jogo"));
                jogo.setPreco(rs.getDouble("preco"));
                lista.add(jogo);
            }
            st.close();
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public boolean atualizar(X jogo) {
        String sql = "UPDATE Jogos SET titulo_jogo = ?, preco = ? WHERE id_jogo = ?";
        try {
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, jogo.getTitulo());
            st.setDouble(2, jogo.getPreco());
            st.setInt(3, jogo.getId_jogo());
            
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public boolean excluir(int id) {
        String sql = "DELETE FROM Jogos WHERE id_jogo = ?";
        try {
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
    }
}