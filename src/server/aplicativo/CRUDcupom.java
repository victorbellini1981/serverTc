package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_cupons;
import global.dados.Cupom;
import global.dados.Evento;
import global.dados.Resgate;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDcupom {
	
	private String serverName;

	public CRUDcupom(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postCupom(Cupom cupom) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(cupom);
				
		try {
			CRUDcad_cupons crud_cupom = new CRUDcad_cupons(conexao);
			Cupom cupons = crud_cupom.insert(cupom);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", cupons, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno updateCodigo(Cupom cupom) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(cupom);
				
		try {
			CRUDcad_cupons crud_cupom = new CRUDcad_cupons(conexao);
			Cupom cupons = crud_cupom.updateCodigo(cupom);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", cupons, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getCupom() {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
				
		try {
			CRUDcad_cupons crud_cupom = new CRUDcad_cupons(conexao);
			LinkedList<Cupom> cupons = crud_cupom.getCupom();
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", cupons, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getDetCupom(Cupom cupom) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
				
		try {
			CRUDcad_cupons crud_cupom = new CRUDcad_cupons(conexao);
			Cupom cupons = crud_cupom.getDetCupom(cupom);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", cupons, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}


}
