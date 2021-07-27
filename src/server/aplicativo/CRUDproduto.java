package server.aplicativo;

import java.util.LinkedList;
import global.util.LogErros;
import global.util.Util;

import global.dados.Retorno;
import global.dados.Produto;

import global.crud.CRUDcad_produtos;

import conexao.Conexao;

public class CRUDproduto {
	private String serverName;

	public CRUDproduto(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno getProdutos() {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_produtos crud_produto = new CRUDcad_produtos(conexao);
			LinkedList<Produto> produtos = crud_produto.readAll();
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", produtos, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produtos", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getProduto(String referencia) {
		Conexao conexao = new Conexao("duzani", serverName);
		Util u = new Util();
		
		try {
			CRUDcad_produtos crud_produto = new CRUDcad_produtos(conexao);
			Produto produto = crud_produto.read(referencia);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", produto, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
}
