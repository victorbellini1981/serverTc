package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Atividade;
import global.dados.Batimento;
import global.util.LogErros;

public class CRUDcad_batimentos {
	
private Conexao conexao;
	
	public CRUDcad_batimentos(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public LinkedList<Batimento> getBatimentos(Batimento batimento) {
		
		LinkedList<Batimento> lista = new LinkedList<Batimento>();
				
		try {
			
			String dtfinal = batimento.getDtfinal();
			String filtro = "";
			if(dtfinal != "sem data") {
				filtro = "data_bat > ? and data_bat < ?";
			} else {
				filtro = "data_bat > ?";
			}

			String sql =	"SELECT a.data_bat, \r\n" +
							"		a.batimento \r\n" +
							"FROM 	cad_batimentos a \r\n" + 
							"WHERE " +filtro+";";
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			if(dtfinal != "sem data") {
				pst.setString(1, batimento.getDtinicial());
				pst.setString(2, batimento.getDtfinal());
			} else {
				pst.setString(1, batimento.getDtinicial());
			}
			ResultSet rs = conexao.executaQuery(pst);

			while (rs.next()) {
				Batimento batimento1 = new Batimento();
				batimento1.setData_bat(rs.getString("data_bat"));
				batimento1.setBatimento(rs.getString("batimento"));
				
				
				lista.add(batimento1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}

}
