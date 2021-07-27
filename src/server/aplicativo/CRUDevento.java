package server.aplicativo;

import java.util.Base64;

import conexao.Conexao;
import global.crud.CRUDcad_eventos;
import global.crud.CRUDcad_pessoas;
import global.dados.Evento;
import global.dados.Pessoa;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDevento {
	
	private String serverName;

	public CRUDevento(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno insertEvento(Evento evento) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(evento);
				
		try {
			CRUDcad_eventos crud_evento = new CRUDcad_eventos(conexao);
			Evento eventos = crud_evento.insert(evento);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", eventos, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno updateEvento(Evento evento) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(evento);
				
		try {
			CRUDcad_eventos crud_evento = new CRUDcad_eventos(conexao);
			Evento eventos = crud_evento.update(evento);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", eventos, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

	public Retorno getEvento(Evento evento) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_eventos crud_evento = new CRUDcad_eventos(conexao);
			Evento eventos = crud_evento.getEvento(evento);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", eventos, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno gerarLink(Evento evento) {
		String hash = "idevento="+evento.getIdevento();
		String link = "https://c.chadelingerievirtual.com.br/?hash="+Base64.getEncoder().encodeToString(hash.getBytes());
		evento.setLink(link);
		Util u = new Util();
		return u.retorno("sucesso", "", "Sucesso ao retornar dados", evento, null);
	}
	
	public Retorno getDadosSite(Evento evento) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(evento);
				
		try {
			CRUDcad_eventos crud_evento = new CRUDcad_eventos(conexao);
			Evento eventos = crud_evento.getDadosSite(evento);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", eventos, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}


}
