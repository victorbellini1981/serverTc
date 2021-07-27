package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Convidado;
import global.util.LogErros;

public class CRUDcad_convidados {
	
	private Conexao conexao;
	
	public CRUDcad_convidados(Conexao conexao) {
		this.conexao = conexao;
		
		
	}
	
	public Convidado insert(Convidado convidado) {
		
		conexao.iniciaTransacao();
		try {

			String sql =	"INSERT INTO chadl_convidados(idevento, foto, \r\n" +   
							"                            nome, email, status) VALUES \r\n" +
							"                           (?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, convidado.getIdevento());
			pst.setString(2, convidado.getFoto());
			pst.setString(3, convidado.getNome());
			pst.setString(4, convidado.getEmail());
			pst.setString(5, convidado.getStatus());
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return convidado;
			}else {
				conexao.cancelaTransacao();
				return convidado;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return convidado;
		}
	}
	
public Convidado update(Convidado convidado) {
		
	conexao.iniciaTransacao();
		try {

			String sql =	"UPDATE chadl_convidados \r\n" + 
							"SET    status = ? \r\n" +
							"WHERE 	nome = ? \r\n" +
							"AND	idevento = ?;";

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, convidado.getStatus());
			pst.setString(2, convidado.getNome());
			pst.setInt(3, convidado.getIdevento());
												
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return convidado;
			}else {
				conexao.cancelaTransacao();
				return convidado;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return convidado;
		}
	}


	public LinkedList<Convidado> getConvidados(Convidado convidado) {
	
	LinkedList<Convidado> lista = new LinkedList<Convidado>();
			
	try {

		String sql =	"SELECT c.idconvidado, \r\n" +
						"		c.idevento, \r\n" +
						"		c.foto, \r\n" +
						"		c.nome, \r\n" +
						"		c.email, \r\n" +
						"		c.status \r\n" +
						"FROM 	chadl_convidados c \r\n" + 
						"WHERE 	c.idevento=? \r\n";
								
		PreparedStatement pst = conexao.sqlPreparada(sql);
		pst.setInt(1, convidado.getIdevento());
		ResultSet rs = conexao.executaQuery(pst.toString());

		while (rs.next()) {
			Convidado convidado1 = new Convidado();
			convidado1.setIdconvidado(rs.getInt("idconvidado"));
			convidado1.setIdevento(rs.getInt("idevento"));
			convidado1.setFoto(rs.getString("foto"));
			convidado1.setNome(rs.getString("nome"));
			convidado1.setEmail(rs.getString("email"));
			convidado1.setStatus(rs.getString("status"));
						
			lista.add(convidado1);							
		}
		
		return lista;
	} catch (Exception e) {
		LogErros log = new LogErros();
		log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
		return lista;
	}

}

}
