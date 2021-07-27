package server.aplicativo;

import conexao.Conexao;
import global.crud.CRUDcad_consultoras;
import global.crud.CRUDcad_eventos;
import global.dados.Consultora;
import global.dados.Evento;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDconsultora {
	
	private String serverName;

	public CRUDconsultora(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno getConsultora(Consultora consultora) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_consultoras crud_consultora = new CRUDcad_consultoras(conexao);
			Consultora consultoras = crud_consultora.select(consultora);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", consultoras, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno postLoveCode(Consultora consultora) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_consultoras crud_consultora = new CRUDcad_consultoras(conexao);
			Consultora consultoras = crud_consultora.insert(consultora);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", consultoras, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
