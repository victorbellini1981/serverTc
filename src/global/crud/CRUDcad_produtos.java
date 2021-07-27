package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import global.util.LogErros;
import global.dados.Produto;

import conexao.Conexao;

public class CRUDcad_produtos {
	private Conexao conexao;
	
	public CRUDcad_produtos(Conexao conexao) {
		this.conexao = conexao;
	}

	public LinkedList<Produto> readAll() {

		LinkedList<Produto> lista = new LinkedList<Produto>();

		try {
			
			
			
			String sql = 	"SELECT p.idproduto, \r\n" + 
							"       p.referencia, \r\n" + 
							"       p.descricaoproduto, \r\n" + 
							"       p.preco_tabela, \r\n" + 
							"       p.marca, \r\n" + 
							"       cor.descricao              cor, \r\n" +
							"		cvc.descricao as categoria, \r\n" +
							"       Array_agg(DISTINCT tam.descricao)   AS tamanhos, \r\n" + 
							"       Array_agg(DISTINCT f.link) AS link \r\n" +  
							"FROM   cad_vitrine_produto v \r\n" + 
							"       INNER JOIN produtos_compartilhados_parceira_tabela(72, 52, 209) p \r\n" + 
							"               ON v.idproduto = p.idproduto \r\n" + 
							"                  AND Coalesce(v.idprodutograde, 0) = Coalesce(p.idgrade, 0) \r\n" + 
							"                  AND Coalesce(v.idprodutosubsecao, 0) = \r\n" + 
							"                      Coalesce(p.idprodutosubsecao, 0) \r\n" + 
							"		LEFT JOIN cad_vitrine_categoria cvc on cvc.idvitrine_categoria = v.idvitrine_categoria \r\n" +
							"		AND v.idproduto = p.idproduto \r\n" +
							"       LEFT JOIN cad_produtos_foto_marketing f \r\n" + 
							"              ON p.idproduto = f.idproduto \r\n" +
							"       LEFT JOIN itens_grade igcor \r\n" + 
							"              ON igcor.idprodutograde = p.idgrade \r\n" + 
							"                 AND igcor.idatributo = 53 \r\n" + 
							"       LEFT JOIN cad_atributos_grade_valores cor \r\n" + 
							"              ON igcor.idatributograde = cor.idatributograde \r\n" + 
							"       LEFT JOIN itens_grade igtam \r\n" + 
							"              ON igtam.idprodutograde = p.idgrade \r\n" + 
							"                 AND igtam.idatributo = 54 \r\n" + 
							"       LEFT JOIN cad_atributos_grade_valores tam \r\n" + 
							"              ON igtam.idatributograde = tam.idatributograde \r\n" + 
							"WHERE  v.idvitrinetipo = 5 \r\n" + 
							"GROUP  BY p.idproduto, \r\n" + 
							"          p.referencia, \r\n" + 
							"          p.descricaoproduto, \r\n" + 
							"          p.preco_tabela, \r\n" + 
							"          p.marca, \r\n" + 
							"		   cvc.descricao, \r\n" +
							"          cor.descricao \r\n" +
							"ORDER  BY p.referencia :: INT DESC, \r\n" + 
							"          p.descricaoproduto ";
							
							
			
		/*	String sql =	"SELECT p.idproduto, \r\n" + 
					"       p.referencia, \r\n" + 
					"       p.descricaoproduto, \r\n" + 
					"       p.preco_tabela, \r\n" + 
					"       p.marca, \r\n" + 
					"       cor.descricao              cor, \r\n" +
					"		cvc.descricao as categoria, \r\n" +
					"       Array_agg(DISTINCT tam.descricao)   AS tamanhos, \r\n" + 
					"       Array_agg(DISTINCT f.link) AS link \r\n" + 
					"FROM   cad_vitrine_produto v \r\n" + 
					"       INNER JOIN produtos_compartilhados_parceira_tabela(72, 52, 209) p \r\n" + 
					"               ON v.idproduto = p.idproduto \r\n" + 
					"                  AND Coalesce(v.idprodutograde, 0) = Coalesce(p.idgrade, 0) \r\n" + 
					"                  AND Coalesce(v.idprodutosubsecao, 0) = \r\n" + 
					"                      Coalesce(p.idprodutosubsecao, 0) \r\n" + 
					"		LEFT JOIN cad_vitrine_categoria cvc on cvc.idvitrine_categoria = v.idvitrine_categoria \r\n" +
					"		AND v.idproduto = p.idproduto \r\n" +
					"       LEFT JOIN cad_produtos_foto_marketing f \r\n" + 
					"              ON p.idproduto = f.idproduto \r\n" + 
					"       LEFT JOIN itens_grade igcor \r\n" + 
					"              ON igcor.idprodutograde = p.idgrade \r\n" + 
					"                 AND igcor.idatributo = 53 \r\n" + 
					"       LEFT JOIN cad_atributos_grade_valores cor \r\n" + 
					"              ON igcor.idatributograde = cor.idatributograde \r\n" + 
					"       LEFT JOIN itens_grade igtam \r\n" + 
					"              ON igtam.idprodutograde = p.idgrade \r\n" + 
					"                 AND igtam.idatributo = 54 \r\n" + 
					"       LEFT JOIN cad_atributos_grade_valores tam \r\n" + 
					"              ON igtam.idatributograde = tam.idatributograde \r\n" + 
					"		WHERE p.marca not ilike '%lageli%' and p.marca not ilike '%vigathi%' \r\n" +
					"		and p.marca not ilike '%plus size%' and p.marca not ilike '%merchandising%' \r\n" +
					"GROUP  BY p.idproduto, \r\n" + 
					"          p.referencia, \r\n" + 
					"          p.descricaoproduto, \r\n" + 
					"          p.preco_tabela, \r\n" + 
					"          p.marca, \r\n" + 
					"		   cvc.descricao, \r\n" +
					"          cor.descricao \r\n" + 
					"ORDER  BY p.referencia :: INT, \r\n" + 
					"          p.descricaoproduto "; */
			
										
			PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdproduto(rs.getString("idproduto"));
				produto.setReferencia(rs.getString("referencia"));
				produto.setDescricao(rs.getString("descricaoproduto"));
				produto.setCor(rs.getString("cor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setTamanhos(rs.getString("tamanhos"));
				produto.setPreco_tabela(rs.getDouble("preco_tabela"));
				produto.setMarca(rs.getString("marca"));
				produto.setLink(rs.getString("link"));
				lista.add(produto);
			}

			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}
	
	public Produto read(String referencia) {

		Produto produto = new Produto();

		try {

			String sql =	"SELECT p.idproduto, \r\n" + 
							"       p.referencia, \r\n" + 
							"       p.descricaoproduto, \r\n" + 
							"       p.preco_tabela, \r\n" + 
							"       p.marca, \r\n" + 
							"       cor.descricao              cor, \r\n" +
							"		cvc.descricao as categoria, \r\n" +
							"       Array_agg(DISTINCT tam.descricao)   AS tamanhos, \r\n" + 
							"       Array_agg(DISTINCT f.link) AS link \r\n" + 
							"FROM   cad_vitrine_produto v \r\n" +
							"		INNER JOIN cad_produtos cp on v.idproduto = cp.idproduto \r\n" +
							"LEFT JOIN cad_produtos_grade cpg ON v.idprodutograde = cpg.idprodutograde \r\n" +
							"       INNER JOIN produtos_compartilhados_parceira_tabela(72, 52, 209, (v.idproduto::text||';'||coalesce(v.idprodutograde::text,'null')||';'||coalesce(v.idprodutosubsecao::text,'null'))::character varying, (COALESCE(cpg.codbarras, cp.codbarras)||COALESCE(cpg.codbarrasdigito, cp.codbarrasdigito))::character varying) p \r\n" + 
							"               ON v.idproduto = p.idproduto \r\n" + 
							"                  AND Coalesce(v.idprodutograde, 0) = Coalesce(p.idgrade, 0) \r\n" + 
							"                  AND Coalesce(v.idprodutosubsecao, 0) = \r\n" + 
							"                      Coalesce(p.idprodutosubsecao, 0) \r\n" +
							"		LEFT JOIN cad_vitrine_categoria cvc on cvc.idvitrine_categoria = v.idvitrine_categoria \r\n" +
							"		AND v.idproduto = p.idproduto \r\n" +
							"       LEFT JOIN cad_produtos_foto_marketing f \r\n" + 
							"              ON p.idproduto = f.idproduto \r\n" + 
							"       LEFT JOIN itens_grade igcor \r\n" + 
							"              ON igcor.idprodutograde = p.idgrade \r\n" + 
							"                 AND igcor.idatributo = 53 \r\n" + 
							"       LEFT JOIN cad_atributos_grade_valores cor \r\n" + 
							"              ON igcor.idatributograde = cor.idatributograde \r\n" + 
							"       LEFT JOIN itens_grade igtam \r\n" + 
							"              ON igtam.idprodutograde = p.idgrade \r\n" + 
							"                 AND igtam.idatributo = 54 \r\n" + 
							"       LEFT JOIN cad_atributos_grade_valores tam \r\n" + 
							"              ON igtam.idatributograde = tam.idatributograde \r\n" + 
							"WHERE  cp.referencia=? \r\n" +
							"GROUP  BY p.idproduto, \r\n" + 
							"          p.referencia, \r\n" + 
							"          p.descricaoproduto, \r\n" + 
							"          p.preco_tabela, \r\n" + 
							"          p.marca, \r\n" + 
							"          cor.descricao, \r\n" +
							"		   cvc.descricao \r\n" +
							"ORDER  BY p.referencia :: INT, \r\n" + 
							"          p.descricaoproduto ";
			
			  
		        
		    PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, referencia);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				produto.setIdproduto(rs.getString("idproduto"));
				produto.setReferencia(rs.getString("referencia"));
				produto.setDescricao(rs.getString("descricaoproduto"));
				produto.setCor(rs.getString("cor"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setTamanhos(rs.getString("tamanhos"));
				produto.setPreco_tabela(rs.getDouble("preco_tabela"));
				produto.setMarca(rs.getString("marca"));
				produto.setLink(rs.getString("link"));
			}
			
			return produto;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return produto;
		}

	}
	
}
