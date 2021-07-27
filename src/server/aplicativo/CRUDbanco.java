package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_bancos;
import global.dados.Banco;
import global.dados.Cidade;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDbanco {
	
	private String serverName;

	public CRUDbanco(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno getBancos() {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_bancos crud_banco = new CRUDcad_bancos(conexao);
			LinkedList<Banco> bancos = crud_banco.getBancos();
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", bancos, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produtos", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
