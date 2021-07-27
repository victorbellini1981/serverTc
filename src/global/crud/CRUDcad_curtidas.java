package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.Conexao;
import global.dados.Curtida;
import global.dados.Presente;
import global.util.LogErros;

public class CRUDcad_curtidas {
	
private Conexao conexao;
	
	public CRUDcad_curtidas(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Curtida insert(Curtida curtida) {
		conexao.iniciaTransacao();
		try {

			String sql =	"INSERT INTO chadl_curtidas(idevento) VALUES(?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, curtida.getIdevento());
			
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return curtida;
			}else {
				conexao.cancelaTransacao();
				return curtida;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return curtida;
		}
	}
	
	public Curtida contagem(Curtida curtida) {
		
		try {

			String sql =	"SELECT count(*) AS curtidas \r\n" +
							"FROM 	chadl_curtidas c\r\n" + 
							"WHERE 	c.idevento=? \r\n";
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, curtida.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				
				curtida.setCurtidas(rs.getInt("curtidas"));
											
			}
			
			return curtida;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return curtida;
		}
	}

}
