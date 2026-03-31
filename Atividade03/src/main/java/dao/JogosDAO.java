package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Jogo;

public class JogosDAO extends DAO {
    
    public JogosDAO() {
        super();
        conectar();
    }

    public void finalizar() {
        close();
    }

    public boolean insert(Jogo jogo) {
        boolean status = false;
        
        String sql = "INSERT INTO jogo (titulo, preco) VALUES (?, ?)";

        try {
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, jogo.getTitulo());
            st.setDouble(2, jogo.getPreco());
            
            st.executeUpdate();
            st.close();
            status = true;
            
        } catch (SQLException j) {
            System.err.println("Erro ao inserir: " + j.getMessage());
            return false;
        }
        return status;
    }
    
	public Jogo get(int id) {
		Jogo jogo = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM jogo WHERE id=" + id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            

	        	jogo = new Jogo(rs.getInt("id"), rs.getString("titulo"), rs.getFloat("preco"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println("Erro no get(id): " + e.getMessage());
		}
		return jogo;
	}
	
	public List<Jogo> get() {
		return get("");
	}
	
	public List<Jogo> getOrderByID() {
		return get("id");		
	}
	
	public List<Jogo> getOrderByTitulo() {
		return get("titulo");		
	}
	
	public List<Jogo> getOrderByPreco() {
		return get("preco");		
	}
	
	private List<Jogo> get(String orderBy) {
		List<Jogo> lista = new ArrayList<Jogo>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM jogo" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Jogo j = new Jogo(rs.getInt("id"), rs.getString("titulo"), rs.getFloat("preco"));
	            lista.add(j);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println("Erro no get(orderBy): " + e.getMessage());
		}
		return lista;
	}
	
    public boolean update(Jogo jogo) {
        String sql = "UPDATE jogo SET titulo = ?, preco = ? WHERE id = ?";
        try {
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, jogo.getTitulo());
            st.setDouble(2, jogo.getPreco());
            st.setInt(3, jogo.getId());
            
            st.executeUpdate();
            st.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM jogo WHERE id = ?";
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