package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_cupons;
import global.crud.CRUDcad_transferencias;
import global.dados.Cupom;
import global.dados.Retorno;
import global.dados.Transferencia;
import global.util.LogErros;
import global.util.Util;

public class CRUDtransferencia {
	
	private String serverName;

	public CRUDtransferencia(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postTransferencia(Transferencia transferencia) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(transferencia);
				
		try {
			CRUDcad_transferencias crud_transferencia = new CRUDcad_transferencias(conexao);
			Transferencia transferencias = crud_transferencia.insert(transferencia);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", transferencias, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getDetTransferencia(Transferencia transferencia) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(transferencia);
				
		try {
			CRUDcad_transferencias crud_transferencia = new CRUDcad_transferencias(conexao);
			Transferencia transferencias = crud_transferencia.getDetTransferencia(transferencia);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", transferencias, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getTransferencias() {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
				
		try {
			CRUDcad_transferencias crud_transferencia = new CRUDcad_transferencias(conexao);
			LinkedList<Transferencia> transferencias = crud_transferencia.getTransferencias();
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", transferencias, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno updateTransferencia(Transferencia transferencia) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(transferencia);
				
		try {
			CRUDcad_transferencias crud_transferencia = new CRUDcad_transferencias(conexao);
			Transferencia transferencias = crud_transferencia.updateTransferencia(transferencia);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", transferencias, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}


}
