package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_presentes;
import global.crud.CRUDcad_recados;
import global.dados.Presente;
import global.dados.Recado;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDrecado {
	
	private String serverName;

	public CRUDrecado(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postRecado(Recado recado) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(recado);
				
		try {
			CRUDcad_recados crud_recado = new CRUDcad_recados(conexao);
			Recado recados = crud_recado.insert(recado);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", recados, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno updateRecado(Recado recado) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(recado);
				
		try {
			CRUDcad_recados crud_recado = new CRUDcad_recados(conexao);
			Recado recados = crud_recado.update(recado);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", recados, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getRecados(Recado recado) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_recados crud_recado = new CRUDcad_recados(conexao);
			LinkedList<Recado> recados = crud_recado.getRecados(recado);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", recados, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
}
