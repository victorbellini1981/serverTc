package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Banco;
import global.util.LogErros;

public class CRUDcad_bancos {
	private Conexao conexao;
	
	public CRUDcad_bancos(Conexao conexao) {
		this.conexao = conexao;
	}
	
	public LinkedList<Banco> getBancos() {

		LinkedList<Banco> lista = new LinkedList<Banco>();

		try {

			String sql =	"select b.codbanco as codigo, b.nome as banco from cad_bancos b group by b.nome, b.codbanco order by b.nome;";
							
			  
		        
		    PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Banco bancos = new Banco();
				bancos.setCodbanco(rs.getString("codigo"));
				bancos.setNomebanco(rs.getString("banco"));
				lista.add(bancos);
				
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}


}
