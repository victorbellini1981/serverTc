package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_empresas;
import global.dados.Empresa;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDempresa {
	
	private String serverName;

	public CRUDempresa(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno getEmpresas() {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_empresas crud_empresa = new CRUDcad_empresas(conexao);
			LinkedList<Empresa> empresas = crud_empresa.getEmpresas();
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", empresas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produtos", null, null);
		} finally {
			conexao.desconecta();
		}
	}


}
