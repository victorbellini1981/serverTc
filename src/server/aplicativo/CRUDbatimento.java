package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_batimentos;
import global.dados.Batimento;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDbatimento {
	
	private String serverName;

	public CRUDbatimento(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno getBatimentos(Batimento batimento) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		
		try {
			CRUDcad_batimentos crud_batimento = new CRUDcad_batimentos(conexao);
			LinkedList<Batimento> batimentos = crud_batimento.getBatimentos(batimento);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", batimentos, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
