package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import conexao.Conexao;
import global.dados.Lingerie;
import global.util.LogErros;

public class CRUDcad_lingeries {
	
private Conexao conexao;
	
	public CRUDcad_lingeries(Conexao conexao) {
		this.conexao = conexao;
		this.conexao.iniciaTransacao();
	}
	
	public Lingerie insert(Lingerie lingerie) {

		
		try {

			String sql =	"INSERT INTO chadl_lingeries(idevento, idproduto, \r\n" +   
							"                            referencia, descricao, \r\n" +
							"                            cor, tamanho, preco_tabela, \r\n" +
							"                            marca, link, categoria) VALUES \r\n" +
							"                           (?,?,?,?,?,?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, lingerie.getIdevento());
			pst.setString(2, (lingerie.getIdproduto().equalsIgnoreCase("null")? "0" : lingerie.getIdproduto()));
			pst.setString(3, lingerie.getReferencia());
			pst.setString(4, lingerie.getDescricao());
			pst.setString(5, lingerie.getCor());
			pst.setString(6, lingerie.getTamanho());
			pst.setDouble(7, lingerie.getPreco_tabela());
			pst.setString(8, lingerie.getMarca());
			pst.setString(9, lingerie.getLink());
			pst.setString(10, lingerie.getCategoria());
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return lingerie;
			}else {
				conexao.cancelaTransacao();
				return lingerie;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lingerie;
		}
		
	}
	
	public Lingerie read(String referencia, int idevento) {

		Lingerie presente1 = new Lingerie();
		
		try {

			String sql =	"SELECT p.idlingerie, \r\n" +
							"		p.idevento, \r\n" +
							"		p.idproduto, \r\n" +
							"		p.referencia, \r\n" +
							"		p.descricao, \r\n" +
							"		p.cor, \r\n" +
							"		p.tamanho, \r\n" +
							"		p.preco_tabela, \r\n" +
							"		p.marca, \r\n" +
							"		p.link,  \r\n" +
							"		p.categoria  \r\n" +
							"FROM 	chadl_lingeries p \r\n" +
							/*"       Array_agg(DISTINCT p.link) AS link \r\n" +
							"FROM 	chadl_lingeries p \r\n" + 
							"       LEFT JOIN cad_produtos_foto_marketing f \r\n" + 
							"              ON p.idproduto ::INT = f.idproduto \r\n" + */
							"WHERE 	p.referencia=? AND p.idevento=? \r\n"+
							"GROUP  BY p.idlingerie, \r\n" + 
							"		   p.idproduto, \r\n" +
							"          p.referencia, \r\n" + 
							"          p.descricao, \r\n" + 
							"          p.preco_tabela, \r\n" + 
							"          p.marca, \r\n" + 
							"          p.categoria \r\n" +
							"ORDER  BY p.referencia :: INT, \r\n" + 
							"          p.descricao ";
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, referencia);
			pst.setInt(2, idevento);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				presente1.setIdlingerie(rs.getInt("idlingerie"));
				presente1.setIdevento(rs.getInt("idevento"));
				presente1.setIdproduto(rs.getString("idproduto"));
				presente1.setReferencia(rs.getString("referencia"));
				presente1.setDescricao(rs.getString("descricao"));
				presente1.setCor(rs.getString("cor"));
				presente1.setTamanho(rs.getString("tamanho"));
				presente1.setPreco_tabela(rs.getDouble("preco_tabela"));
				presente1.setMarca(rs.getString("marca"));
				presente1.setLink(rs.getString("link"));
				presente1.setCategoria(rs.getString("categoria"));
				
			}
			
			return presente1;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return presente1;
		}
		
	}

	public LinkedList<Lingerie> getLingeries(Lingerie lingerie) {
		
		LinkedList<Lingerie> lista = new LinkedList<Lingerie>();
				
		try {

			String sql =	"SELECT p.idlingerie, \r\n" +
							"		p.idevento, \r\n" +
							"		p.idproduto, \r\n" +
							"		p.referencia, \r\n" +
							"		p.descricao, \r\n" +
							"		p.cor, \r\n" +
							"		p.tamanho, \r\n" +
							"		p.preco_tabela, \r\n" +
							"		p.marca, \r\n" +
							"		p.link, \r\n" +
							"		p.categoria \r\n" +
							"FROM 	chadl_lingeries p \r\n" + 
							"WHERE 	p.idevento=?";
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, lingerie.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Lingerie presente1 = new Lingerie();
				presente1.setIdlingerie(rs.getInt("idlingerie"));
				presente1.setIdevento(rs.getInt("idevento"));
				presente1.setIdproduto(rs.getString("idproduto"));
				presente1.setReferencia(rs.getString("referencia"));
				presente1.setDescricao(rs.getString("descricao"));
				presente1.setCor(rs.getString("cor"));
				presente1.setTamanho(rs.getString("tamanho"));
				presente1.setPreco_tabela(rs.getDouble("preco_tabela"));
				presente1.setMarca(rs.getString("marca"));
				presente1.setLink(rs.getString("link"));
				presente1.setCategoria(rs.getString("categoria"));
				
				lista.add(presente1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}
	
	public Lingerie delete(Lingerie lingerie) {

		conexao.iniciaTransacao();
			try {
				
				String sql =	"DELETE FROM chadl_lingeries l \r\n" +
								"WHERE       l.idlingerie = ?"; 

				PreparedStatement pst = conexao.sqlPreparada(sql);
				pst.setInt(1, lingerie.getIdlingerie());
										
				if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
					
					conexao.confirmaTransacao();
					return lingerie;
				}else {
					conexao.cancelaTransacao();
					return lingerie;
				}
				
			} catch (Exception e) {
				LogErros log = new LogErros();
				log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
				return lingerie;
			}
			
		}

	
	public Lingerie carregaMeta(Lingerie lingerie) {

		
		try {

			String sql =	"SELECT sum(l.preco_tabela) AS meta \r\n" + 
							"FROM 	chadl_lingeries l \r\n" + 
							"WHERE 	l.idevento=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, lingerie.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				lingerie.setMeta(rs.getDouble("meta"));
				
								
			}
			
			return lingerie;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lingerie;
		}

	}

}

