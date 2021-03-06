package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.text.StyledEditorKit.BoldAction;

import conexao.Conexao;
import global.dados.LinkTemporario;
import global.util.LogErros;

public class CRUDlinks_temporarios {
	private Conexao conexao;	
	public CRUDlinks_temporarios (Conexao conexao) {
		this.conexao = conexao;
	}
	
	public boolean insert (LinkTemporario lt) {
		boolean ok = false;
		try {
			System.out.println("entrou insert");
			String sql = "INSERT INTO links_temporarios (idusuario, md5, tipo_link, data_expiracao) "
					+ "VALUES ( ?, ?, ?, ADDDATE(NOW(), INTERVAL 4 HOUR))";
			System.out.println(sql);
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, lt.getIdusuario());
			pst.setString(2, lt.getMd5());
			pst.setString(3, lt.getTipo_link());
			
			if (conexao.executaUpdate(pst).equalsIgnoreCase("ok")) {
				ok = true; }
			return ok;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "Duzani", "Erro " + e.getMessage());
			return false;
		}
	}
	
	public LinkTemporario read(String md5) {
		LinkTemporario lt = null;
		try {
			
			String sql = "SELECT idlink, idusuario "
					+ "FROM chadl_links_temporarios "
					+ "WHERE md5 = '" + md5 + "'";
					//+ " AND data_expiracao >= now()";
			
			PreparedStatement pst = conexao.sqlPreparada(sql);

			
			ResultSet rs = conexao.executaQuery(pst);
			
			if(rs.next()) {
				lt = new LinkTemporario();
				lt.setIdlink(rs.getInt("idlink"));
				lt.setIdusuario(rs.getInt("idusuario"));
			}
			
			pst.close();
			rs.close();	
			
			return lt;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "Duzani", "Erro " + e.getMessage());
			return null;
		}
	}
	
	public boolean update (int idlink) {
		boolean ok;
		try {
			
			String sql = "UPDATE chadl_links_temporarios SET expirado = true WHERE idlink = " + idlink;
			PreparedStatement pst = conexao.sqlPreparada(sql);
			
			if (conexao.executaUpdate(pst).equalsIgnoreCase("ok")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "Duzani", "Erro " + e.getMessage());
			return false;
		}
	}
	
}
