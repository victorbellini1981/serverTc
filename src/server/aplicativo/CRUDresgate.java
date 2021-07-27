package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_resgates;
import global.dados.Resgate;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDresgate {
	
	private String serverName;

	public CRUDresgate(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno saldo(Resgate resgate) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(resgate);
				
		try {
			CRUDcad_resgates crud_resgate = new CRUDcad_resgates(conexao);
			double saldo = crud_resgate.saldo(resgate);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", saldo, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno postResgate(Resgate resgate) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(resgate);
				
		try {
			CRUDcad_resgates crud_resgate = new CRUDcad_resgates(conexao);
			Resgate resgates = crud_resgate.insert(resgate);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", resgates, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno updateVerificado(Resgate resgate) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(resgate);
				
		try {
			CRUDcad_resgates crud_resgate = new CRUDcad_resgates(conexao);
			Resgate resgates = crud_resgate.updateVerificado(resgate);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", resgates, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno updateConcluido(Resgate resgate) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(resgate);
				
		try {
			CRUDcad_resgates crud_resgate = new CRUDcad_resgates(conexao);
			Resgate resgates = crud_resgate.updateConcluido(resgate);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", resgates, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getResgates(Resgate resgate) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_resgates crud_resgate = new CRUDcad_resgates(conexao);
			LinkedList<Resgate> resgates = crud_resgate.getResgates(resgate);			
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", resgates, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getResgatesTotal() {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_resgates crud_resgate = new CRUDcad_resgates(conexao);
			LinkedList<Resgate> resgates = crud_resgate.getResgatesTotal();			
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", resgates, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

	public Retorno getResgatesConcluidos(Resgate resgate) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_resgates crud_resgate = new CRUDcad_resgates(conexao);
			LinkedList<Resgate> resgates = crud_resgate.getResgatesConcluidos(resgate);			
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", resgates, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
