package server.aplicativo;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_cidades;
import global.crud.CRUDcad_produtos;
import global.dados.Cidade;
import global.dados.Produto;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDcidade {
	
	private String serverName;

	public CRUDcidade(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno getCidades() {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_cidades crud_cidade = new CRUDcad_cidades(conexao);
			LinkedList<Cidade> cidades = crud_cidade.readAll();
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", cidades, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produtos", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getCidade(String estado, String cidade) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_cidades crud_cidade = new CRUDcad_cidades(conexao);
			Cidade cidade1 = crud_cidade.read(estado, cidade);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", cidade1, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

}
