package server.aplicativo;

import conexao.Conexao;
import global.crud.CRUDcad_transacoes;
import global.crud.CRUDcad_usuarios;
import global.dados.Transacao;
import global.dados.Usuario;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDtransacao {
	
	private String serverName;
	
	public CRUDtransacao(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postTransacao(Transacao transacao) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(transacao);
				
		try {
			CRUDcad_transacoes crud_transacao = new CRUDcad_transacoes(conexao);
			Transacao transacoes = crud_transacao.insert(transacao);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", transacoes, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno finalizacao(Transacao transacao) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(transacao);
				
		try {
			CRUDcad_transacoes crud_transacao = new CRUDcad_transacoes(conexao);
			Transacao transacoes = crud_transacao.finalizacao(transacao);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", transacoes, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
