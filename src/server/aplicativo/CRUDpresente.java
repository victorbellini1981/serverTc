package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_lingeries;
import global.crud.CRUDcad_presentes;
import global.dados.Lingerie;
import global.dados.Presente;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDpresente {
	
	private String serverName;

	public CRUDpresente(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postPresente(Presente presente) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(presente);
				
		try {
			CRUDcad_presentes crud_presente = new CRUDcad_presentes(conexao);
			Presente presentes = crud_presente.insert(presente);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", presentes, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getPresentes(Presente presente) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_presentes crud_presente = new CRUDcad_presentes(conexao);
			LinkedList<Presente> presentes = crud_presente.getPresentes(presente);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", presentes, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getPresentesConvidado(Presente presente) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_presentes crud_presente = new CRUDcad_presentes(conexao);
			LinkedList<Presente> presentes = crud_presente.getPresentesConvidado(presente);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", presentes, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno carregaMetaAtingida(Presente presente) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(presente);
				
		try {
			CRUDcad_presentes crud_presente = new CRUDcad_presentes(conexao);
			Presente presentes = crud_presente.carregaMetaAtingida(presente);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", presentes, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
