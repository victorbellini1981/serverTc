package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Atividade;
import global.util.LogErros;

public class CRUDcad_atividades {
	
private Conexao conexao;
	
	public CRUDcad_atividades(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Atividade insertAtividade(Atividade atividade) {
		this.conexao.iniciaTransacao();
		
		try {

			String sql =	"INSERT INTO cad_atividades(data_atv, atividade, idusuario) \r\n"
						  + "VALUES(ADDDATE(NOW(), INTERVAL ? HOUR),?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, atividade.getData_atv());
			pst.setString(2, atividade.getAtividade());
			pst.setInt(3, atividade.getIdusuario());
			
			if (conexao.executaUpdate(pst).equalsIgnoreCase("ok")) {
				sql = "select last_insert_id() as id from cad_atividades;";
				PreparedStatement pst1 = conexao.sqlPreparada(sql);
				ResultSet rs = conexao.executaQuery(pst1);
				rs.next();
				int idatv = 0;
				idatv = rs.getInt("id");
				atividade.setIdatividade(idatv);
				
				conexao.confirmaTransacao();
				return atividade;
				
			} else {
				conexao.cancelaTransacao();
				return atividade;
			}
			
			//conexao.executaUpdate(pst.toString());
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return atividade;
		}
		
	}
	
	public LinkedList<Atividade> getAtividades(Atividade atividade) {
		
		LinkedList<Atividade> lista = new LinkedList<Atividade>();
				
		try {

			String sql =	"SELECT a.data_atv, \r\n" +
							"		a.atividade \r\n" +
							"FROM 	cad_atividades a \r\n" + 
							"WHERE 	a.idusuario=?";
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, atividade.getIdusuario());
			ResultSet rs = conexao.executaQuery(pst);

			while (rs.next()) {
				Atividade atividade1 = new Atividade();
				atividade1.setData_atv(rs.getString("data_atv"));
				atividade1.setAtividade(rs.getString("atividade"));
				
				
				lista.add(atividade1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}

	public Atividade update(Atividade atividade) {

		
		try {

				String sql =	"UPDATE cad_atividades \r\n" + 
								"SET    atividade = ? \r\n" +
								"WHERE 	idusuario = ? \r\n" +
								"AND	data_atv = ?;";

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, atividade.getAtividade());
			pst.setInt(2, atividade.getIdusuario());
			pst.setString(3, atividade.getData_atv());
			
			
			
			if (conexao.executaUpdate(pst).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return atividade;
			}else {
				conexao.cancelaTransacao();
				return atividade;
			}
			
						
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return atividade;
		}
	}

}
