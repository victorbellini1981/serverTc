package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Lingerie;
import global.dados.Presente;
import global.util.LogErros;

public class CRUDcad_presentes {
	
private Conexao conexao;
	
	public CRUDcad_presentes(Conexao conexao) {
		this.conexao = conexao;
		this.conexao.iniciaTransacao();
	}
	
	public Presente insert(Presente presente) {

		
		try {

			String sql =	"INSERT INTO chadl_presentes(referencia, valor, \r\n" +   
							"                            idevento, convidado, \r\n" +
							"                            mensagem, qtd, foto) VALUES \r\n" +
							"                           (?,?,?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, presente.getReferencia());
			pst.setDouble(2, presente.getValor());
			pst.setInt(3, presente.getIdevento());
			pst.setString(4, presente.getConvidado());
			pst.setString(5, presente.getMensagem());
			pst.setInt(6, presente.getQtd());
			pst.setString(7, presente.getFoto());
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return presente;
			}else {
				conexao.cancelaTransacao();
				return presente;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return presente;
		}
	}
	
	public LinkedList<Presente> getPresentes(Presente presente) {
		
		LinkedList<Presente> lista = new LinkedList<Presente>();
				
		try {

			String sql =	"SELECT p.idpresente, \r\n" +
							"		p.referencia, \r\n" +
							"		p.valor, \r\n" +
							"		p.idevento, \r\n" +
							"		p.convidado, \r\n" +
							"		p.qtd, \r\n" +
							"		p.mensagem \r\n" +
							"FROM 	chadl_presentes p \r\n" + 
							"WHERE 	p.idevento=? AND p.convidado=? \r\n";
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, presente.getIdevento());
			pst.setString(2, presente.getConvidado());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Presente presente1 = new Presente();
				presente1.setIdpresente(rs.getInt("idpresente"));
				presente1.setReferencia(rs.getString("referencia"));
				presente1.setValor(rs.getDouble("valor"));
				presente1.setIdevento(rs.getInt("idevento"));
				presente1.setConvidado(rs.getString("convidado"));
				presente1.setQtd(rs.getInt("qtd"));
				presente1.setMensagem(rs.getString("mensagem"));
				
				lista.add(presente1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}
	
public LinkedList<Presente> getPresentesConvidado(Presente presente) {
		
		LinkedList<Presente> lista = new LinkedList<Presente>();
				
		try {

			//String sql =	"select distinct p.convidado as convidado, p.mensagem as mensagem, SUM(p.valor) as valores \r\n"
					//+ "from chadl_presentes p where p.idevento = ? group by convidado, mensagem order by convidado;";
			String sql = "select distinct dados.convidado as convidados, Sum(dados.quantidades) as quantidade, Sum(dados.valores) as valortotal, array_agg(dados.mensagem) as lista, dados.ftperfil as fts from (select distinct p.convidado as convidado, p.mensagem as mensagem, SUM(p.qtd) as quantidades, SUM(p.valor) as valores, p.foto as ftperfil \r\n" 
					+ "from chadl_presentes p where p.idevento = ? group by convidado, mensagem, ftperfil order by convidado) as dados group by convidados, fts order by convidados;";

									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, presente.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Presente presente1 = new Presente();
				presente1.setValor(rs.getDouble("valortotal"));
				presente1.setConvidado(rs.getString("convidados"));
				presente1.setMensagem(rs.getString("lista"));
				presente1.setQtd(rs.getInt("quantidade"));
				presente1.setFoto(rs.getString("fts"));
				
				lista.add(presente1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}

	public Presente carregaMetaAtingida(Presente presente) {
	
		
		try {
	
			String sql =	"SELECT sum(p.valor) AS metaAtingida \r\n" + 
							"FROM 	chadl_presentes p \r\n" + 
							"WHERE 	p.idevento=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, presente.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());
	
			while (rs.next()) {
				presente.setMetaAtingida(rs.getDouble("metaAtingida"));
				
								
			}
			
			return presente;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return presente;
		}
	
	}


}
