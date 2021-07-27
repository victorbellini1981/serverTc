package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Resgate;
import global.dados.TipoResgate;
import global.util.LogErros;

public class CRUDcad_tipo_resgate {
	
private Conexao conexao;
	
	public CRUDcad_tipo_resgate(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public LinkedList<TipoResgate> getTipoResgate(TipoResgate resgate) {
		
		LinkedList<TipoResgate> lista = new LinkedList<TipoResgate>();
				
		try {

			String sql =	"SELECT r.idtipo_resgate, \r\n" +
							"		r.descricao, \r\n" +
							"		r.taxa \r\n" +
							"FROM 	chadl_tipo_resgate r";
							
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				TipoResgate resgate1 = new TipoResgate();
				resgate1.setIdtipo_resgate(rs.getInt("idtipo_resgate"));
				resgate1.setDescricao(rs.getString("descricao"));
				resgate1.setTaxa(rs.getDouble("taxa"));
				
								
				lista.add(resgate1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}


}
