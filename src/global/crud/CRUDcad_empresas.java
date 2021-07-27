package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Empresa;
import global.util.LogErros;

public class CRUDcad_empresas {
	
	private Conexao conexao;
	
	public CRUDcad_empresas(Conexao conexao) {
		this.conexao = conexao;
	}
	
	public LinkedList<Empresa> getEmpresas() {

		LinkedList<Empresa> lista = new LinkedList<Empresa>();

		try {

			String sql =	"select e.nome_empresa as empresa, e.site as site \r\n " +
					 		"from chadl_empresas e group by e.nome_empresa, e.site \r\n" +
					 		"order by e.nome_empresa;";
							
			  
		        
		    PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Empresa empresas = new Empresa();
				empresas.setNome_empresa(rs.getString("empresa"));
				empresas.setSite(rs.getString("site"));
				lista.add(empresas);
				
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}


}
