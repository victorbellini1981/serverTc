package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Cidade;
import global.dados.Produto;
import global.util.LogErros;

public class CRUDcad_cidades {
private Conexao conexao;
	
	public CRUDcad_cidades(Conexao conexao) {
		this.conexao = conexao;
	}
	
	public LinkedList<Cidade> readAll() {

		LinkedList<Cidade> lista = new LinkedList<Cidade>();

		try {

			String sql =	"select DISTINCT c.estado as estado, array_agg(c.cidade) as cidades from cad_cidades c group by estado order by estado;";
							
			  
		        
		    PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Cidade cidades = new Cidade();
				cidades.setEstado(rs.getString("estado"));
				cidades.setCidade(rs.getString("cidades"));
				lista.add(cidades);
				
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}
	
	public Cidade read(String estado, String cidade) {

		Cidade cidade1 = new Cidade();

		try {

			String sql =	"SELECT 	c.idcidade \r\n" +
							"FROM		cad_cidades c \r\n" +
							"WHERE		c.estado=? \r\n" +
							"AND		c.cidade=?;";	
			
			  
		        
		    PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, estado);
			pst.setString(2, cidade);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				cidade1.setIdcidade(rs.getInt("idcidade"));
				
			}
			
			return cidade1;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return cidade1;
		}

	}

}
