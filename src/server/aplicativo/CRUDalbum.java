package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_albuns;
import global.dados.Album;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDalbum {
	
	private String serverName;

	public CRUDalbum(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postAlbum(Album album) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(album);
				
		try {
			CRUDcad_albuns crud_album = new CRUDcad_albuns(conexao);
			Album albuns = crud_album.insert(album);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", albuns, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

	public Retorno getAlbum(Album album) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_albuns crud_album = new CRUDcad_albuns(conexao);
			LinkedList<Album> albuns = crud_album.getAlbum(album);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", albuns, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno delAlbum(Album album) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_albuns crud_album = new CRUDcad_albuns(conexao);
			Album albuns = crud_album.delete(album);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", albuns, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
