package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_atividades;
import global.dados.Atividade;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDatividade {
	
	private String serverName;

	public CRUDatividade(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postAtividade(Atividade atividade) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		System.out.print(atividade);
				
		try {
			CRUDcad_atividades crud_atividade = new CRUDcad_atividades(conexao);
			Atividade atividades = crud_atividade.insertAtividade(atividade);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", atividades, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno update(Atividade atividade) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		System.out.print(atividade);
				
		try {
			CRUDcad_atividades crud_atividade = new CRUDcad_atividades(conexao);
			Atividade atividades = crud_atividade.update(atividade);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", atividades, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getAtividades(Atividade atividade) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		
		try {
			CRUDcad_atividades crud_atividade = new CRUDcad_atividades(conexao);
			LinkedList<Atividade> atividades = crud_atividade.getAtividades(atividade);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", atividades, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
