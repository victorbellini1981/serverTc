package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_convidados;
import global.dados.Convidado;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDconvidado {
	
	private String serverName;

	public CRUDconvidado(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postConvidado(Convidado convidado) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(convidado);
				
		try {
			CRUDcad_convidados crud_convidado = new CRUDcad_convidados(conexao);
			Convidado convidados = crud_convidado.insert(convidado);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", convidados, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno setConvidado(Convidado convidado) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(convidado);
				
		try {
			CRUDcad_convidados crud_convidado = new CRUDcad_convidados(conexao);
			Convidado convidados = crud_convidado.update(convidado);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", convidados, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getConvidados(Convidado convidado) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_convidados crud_convidado = new CRUDcad_convidados(conexao);
			LinkedList<Convidado> convidados = crud_convidado.getConvidados(convidado);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", convidados, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
