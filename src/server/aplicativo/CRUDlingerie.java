package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_lingeries;
import global.dados.Lingerie;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDlingerie {
	private String serverName;

	public CRUDlingerie(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postLingerie(Lingerie lingerie) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(lingerie);
				
		try {
			CRUDcad_lingeries crud_presente = new CRUDcad_lingeries(conexao);
			Lingerie lingeries = crud_presente.insert(lingerie);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", lingeries, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getLingerie(String referencia, int idevento) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_lingeries crud_lingerie = new CRUDcad_lingeries(conexao);
			Lingerie lingerie = crud_lingerie.read(referencia, idevento);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", lingerie, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

	public Retorno getLingeries(Lingerie lingerie) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_lingeries crud_lingerie = new CRUDcad_lingeries(conexao);
			LinkedList<Lingerie> lingeries = crud_lingerie.getLingeries(lingerie);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", lingeries, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno delLingerie(Lingerie lingerie) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_lingeries crud_lingerie = new CRUDcad_lingeries(conexao);
			Lingerie lingeries = crud_lingerie.delete(lingerie);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", lingeries, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno carregaMeta(Lingerie lingerie) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(lingerie);
				
		try {
			CRUDcad_lingeries crud_lingerie = new CRUDcad_lingeries(conexao);
			Lingerie lingeries = crud_lingerie.carregaMeta(lingerie);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", lingeries, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}


}
