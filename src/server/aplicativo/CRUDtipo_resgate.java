package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_resgates;
import global.crud.CRUDcad_tipo_resgate;
import global.dados.Resgate;
import global.dados.Retorno;
import global.dados.TipoResgate;
import global.util.LogErros;
import global.util.Util;

public class CRUDtipo_resgate {
	
	private String serverName;

	public CRUDtipo_resgate(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno getTipoResgate(TipoResgate resgate) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_tipo_resgate crud_resgate = new CRUDcad_tipo_resgate(conexao);
			LinkedList<TipoResgate> resgates = crud_resgate.getTipoResgate(resgate);			
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
