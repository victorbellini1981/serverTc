package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import conexao.Conexao;
import global.dados.Album;
import global.util.LogErros;

public class CRUDcad_albuns {
	
private Conexao conexao;
	
	public CRUDcad_albuns(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Album insert(Album album) {

		conexao.iniciaTransacao();
		try {
			

			String sql =	"INSERT INTO chadl_albuns(idevento, link) VALUES \r\n" +
							"(?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, album.getIdevento());
			pst.setString(2, album.getLink());
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return album;
			}else {
				conexao.cancelaTransacao();
				return album;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return album;
		}
		
	}

	public LinkedList<Album> getAlbum(Album album) {
		
		LinkedList<Album> lista = new LinkedList<Album>();
				
		try {

			String sql =	"SELECT a.idfoto, \r\n" +
							"		a.idevento, \r\n" +
							"		a.link \r\n" +
							"FROM 	chadl_albuns a \r\n" + 
							"WHERE  a.idevento = ?;";
							
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, album.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Album album1 = new Album();
				album1.setIdfoto(rs.getInt("idfoto"));
				album1.setIdevento(rs.getInt("idevento"));
				album1.setLink(rs.getString("link"));
				lista.add(album1);
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}
	
	public Album delete(Album album) {

		conexao.iniciaTransacao();
		try {
			
			String sql =	"DELETE FROM chadl_albuns a \r\n" +
							"WHERE       a.idfoto = ?"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, album.getIdfoto());
									
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return album;
			}else {
				conexao.cancelaTransacao();
				return album;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return album;
		}
		
	}


}
