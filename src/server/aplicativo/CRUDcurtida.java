package server.aplicativo;

import conexao.Conexao;
import global.crud.CRUDcad_curtidas;
import global.crud.CRUDcad_presentes;
import global.dados.Curtida;
import global.dados.Presente;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDcurtida {
	
	private String serverName;

	public CRUDcurtida(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno insert(Curtida curtida) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(curtida);
				
		try {
			CRUDcad_curtidas crud_curtida = new CRUDcad_curtidas(conexao);
			Curtida curtidas = crud_curtida.insert(curtida);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", curtidas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno contagem(Curtida curtida) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		System.out.print(curtida);
				
		try {
			CRUDcad_curtidas crud_curtida = new CRUDcad_curtidas(conexao);
			Curtida curtidas = crud_curtida.contagem(curtida);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", curtidas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
