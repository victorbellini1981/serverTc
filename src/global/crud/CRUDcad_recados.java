package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Recado;
import global.util.LogErros;

public class CRUDcad_recados {

	private Conexao conexao;
	
	public CRUDcad_recados(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
public Recado insert(Recado recado) {

	conexao.iniciaTransacao();
		try {

			String sql =	"INSERT INTO chadl_recados(idevento, foto, \r\n" +   
							"                            nome, recado, status) VALUES \r\n" +
							"                           (?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, recado.getIdevento());
			pst.setString(2, recado.getFoto());
			pst.setString(3, recado.getNome());
			pst.setString(4, recado.getRecado());
			pst.setString(5, recado.getStatus());
			
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return recado;
			}else {
				conexao.cancelaTransacao();
				return recado;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return recado;
		}
	}

public Recado update(Recado recado) {

	conexao.iniciaTransacao();
	try {

			String sql =	"UPDATE chadl_recados \r\n" + 
							"SET    status = ? \r\n" +
							"WHERE 	idevento = ?;"; 

		PreparedStatement pst = conexao.sqlPreparada(sql);
		pst.setString(1, recado.getStatus());
		pst.setInt(2, recado.getIdevento());
		
		
		if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
			
			conexao.confirmaTransacao();
			return recado;
		}else {
			conexao.cancelaTransacao();
			return recado;
		}
		
					
	} catch (Exception e) {
		LogErros log = new LogErros();
		log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
		return recado;
	}
}


public LinkedList<Recado> getRecados(Recado recado) {
	
	LinkedList<Recado> lista = new LinkedList<Recado>();
			
	try {

		String sql =	"SELECT r.idrecado, \r\n" +
						"		r.idevento, \r\n" +
						"		r.foto, \r\n" +
						"		r.nome, \r\n" +
						"		r.recado, \r\n" +
						"		r.status \r\n" +
						"FROM 	chadl_recados r \r\n" + 
						"WHERE 	r.idevento=? ORDER BY idrecado DESC \r\n";
								
		PreparedStatement pst = conexao.sqlPreparada(sql);
		pst.setInt(1, recado.getIdevento());
		ResultSet rs = conexao.executaQuery(pst.toString());

		while (rs.next()) {
			
			Recado recado1 = new Recado();
			recado1.setIdrecado(rs.getInt("idrecado"));
			recado1.setIdevento(rs.getInt("idevento"));
			recado1.setFoto(rs.getString("foto"));
			recado1.setNome(rs.getString("nome"));
			recado1.setRecado(rs.getString("recado"));
			recado1.setStatus(rs.getString("status"));
			
			lista.add(recado1);		
			System.out.println(rs.getString("recado"));
			System.out.println("Sejam bem vindos ao meu Chá de Lingerie! Conto com a ajuda de vocês para realizar esse sonho.😄‫");
		}
		
		return lista;
	} catch (Exception e) {
		LogErros log = new LogErros();
		log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
		return lista;
	}

}
}
