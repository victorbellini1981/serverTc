package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Resgate;
import global.util.LogErros;

public class CRUDcad_resgates {
	
private Conexao conexao;
	
	public CRUDcad_resgates(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Resgate insert(Resgate resgate) {
		
		conexao.iniciaTransacao();
		
		try {

			String sql =	"INSERT INTO chadl_resgates(idevento, \r\n" +
							"       idtipo_resgate, codigo, valor, \r\n" +
							"		valorrecebido, data, status, nome) \r\n" +
							"       VALUES (?,?,?,?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, resgate.getIdevento());
			pst.setInt(2, resgate.getIdtipo_resgate());
			pst.setString(3, resgate.getCodigo());
			pst.setDouble(4, resgate.getValor());
			pst.setDouble(5, resgate.getValorrecebido());
			pst.setString(6, resgate.getData());
			pst.setString(7, resgate.getStatus());
			pst.setString(8, resgate.getNome());
			
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				sql = "select currval('chadl_resgates_idresgate_seq') as id;";
				ResultSet rs = conexao.executaQuery(sql);
				rs.next();
				int idres = rs.getInt("id");
				resgate.setIdresgate(idres);
				
				
				conexao.confirmaTransacao();
				return resgate;
			}else {
				conexao.cancelaTransacao();
				return resgate;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return resgate;
		}
	}
	
	public Resgate updateVerificado(Resgate resgate) {
		
		conexao.iniciaTransacao();
		
		try {
			
			String sql = 		"UPDATE		chadl_resgates \r\n" +
								"SET		status = 'verificado' \r\n" +
								"WHERE		idresgate = ? \r\n";
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, resgate.getIdresgate());
			
			
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return resgate;
			}else {
				conexao.cancelaTransacao();
				return resgate;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return resgate;			
		}
	}
	
public Resgate updateConcluido(Resgate resgate) {
		
		conexao.iniciaTransacao();
		
		try {
			
			String sql = "SELECT r.status FROM chadl_resgates r WHERE r.idresgate=?";
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, resgate.getIdresgate());
			ResultSet rs = conexao.executaQuery(pst.toString());
			
			String estatus = "";

			while (rs.next()) {
				Resgate resgate1 = new Resgate();
				
				resgate1.setStatus(rs.getString("status"));
				estatus = resgate1.getStatus();
			}
			
			if (estatus.equals("pendente")) {
				
				sql = 		"UPDATE		chadl_resgates \r\n" +
							"SET		status = 'concluido' \r\n" +
							"WHERE		idresgate = ? \r\n";
		
				PreparedStatement pst1 = conexao.sqlPreparada(sql);
				pst1.setInt(1, resgate.getIdresgate());
				
				
				if (conexao.executaUpdate(pst1.toString()).equalsIgnoreCase("ok")) {
					sql = "UPDATE chadl_resgates SET data_final = ? WHERE idresgate = ?";
					
					PreparedStatement pst2 = conexao.sqlPreparada(sql);
					pst2.setString(1, resgate.getData_final());
					pst2.setInt(2, resgate.getIdresgate());
					
					if (conexao.executaUpdate(pst2.toString()).equalsIgnoreCase("ok")) {
						sql = "UPDATE chadl_resgates SET codigo = ? WHERE idresgate = ?";
						
						PreparedStatement pst3 = conexao.sqlPreparada(sql);
						pst3.setString(1, resgate.getCodigo());
						pst3.setInt(2, resgate.getIdresgate());
						
						if (conexao.executaUpdate(pst3.toString()).equalsIgnoreCase("ok")) {
						conexao.confirmaTransacao();
						return resgate;
						} else {
							conexao.cancelaTransacao();
							return resgate;
						}
					} else {
						conexao.cancelaTransacao();
						return resgate;
					}
					
				}else {
					conexao.cancelaTransacao();
					return resgate;
				}
			} else {
				conexao.cancelaTransacao();
				return resgate;
			}
			
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return resgate;			
		}
		
	}
	
	public LinkedList<Resgate> getResgates(Resgate resgate) {
		
		LinkedList<Resgate> lista = new LinkedList<Resgate>();
				
		try {

			String sql =	"SELECT r.idresgate, \r\n" +
							"		r.idevento, \r\n" +
							"		r.idtipo_resgate, \r\n" +
							"		r.codigo, \r\n" +
							"		r.valor, \r\n" +
							"		r.valorrecebido, \r\n" +
							"		r.data, \r\n" +
							"		r.status \r\n" +
							"FROM 	chadl_resgates r \r\n" + 
							"WHERE 	r.idevento=? \r\n" +
							"GROUP BY r.idresgate, \r\n" +
							"		  r.idevento, \r\n" +
							"		  r.idtipo_resgate, \r\n" +
							"		  r.codigo, \r\n" +
							"		  r.valor, \r\n" +
							"		  r.valorrecebido, \r\n" +
							"		  r.data, \r\n" +
							"		  r.status \r\n" +
							"ORDER BY r.data";
							
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, resgate.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Resgate resgate1 = new Resgate();
				resgate1.setIdresgate(rs.getInt("idresgate"));
				resgate1.setIdevento(rs.getInt("idevento"));
				resgate1.setIdtipo_resgate(rs.getInt("idtipo_resgate"));				
				resgate1.setCodigo(rs.getString("codigo"));
				resgate1.setValor(rs.getDouble("valor"));
				resgate1.setValorrecebido(rs.getDouble("valorrecebido"));
				resgate1.setData(rs.getString("data"));
				resgate1.setStatus(rs.getString("status"));
								
				lista.add(resgate1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}
	
public LinkedList<Resgate> getResgatesTotal() {
		
		LinkedList<Resgate> lista = new LinkedList<Resgate>();
				
		try {

			String sql =	"SELECT r.idresgate, \r\n" +
							"		r.idevento, \r\n" +
							"		r.idtipo_resgate, \r\n" +
							"		r.codigo, \r\n" +
							"		r.valor, \r\n" +
							"		r.valorrecebido, \r\n" +
							"		r.data, \r\n" +
							"		r.status, \r\n" +
							"		r.data_final, \r\n" +
							"		r.nome \r\n" +
							"FROM 	chadl_resgates r \r\n" + 
							"GROUP BY r.idresgate, \r\n" +
							"		  r.idevento, \r\n" +
							"		  r.idtipo_resgate, \r\n" +
							"		  r.codigo, \r\n" +
							"		  r.valor, \r\n" +
							"		  r.valorrecebido, \r\n" +
							"		  r.data, \r\n" +
							"		  r.status, \r\n" +
							"		  r.data_final, \r\n" +
							"		  r.nome \r\n" +
							"ORDER BY r.idresgate DESC";
							
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Resgate resgate1 = new Resgate();
				resgate1.setIdresgate(rs.getInt("idresgate"));
				resgate1.setIdevento(rs.getInt("idevento"));
				resgate1.setIdtipo_resgate(rs.getInt("idtipo_resgate"));				
				resgate1.setCodigo(rs.getString("codigo"));
				resgate1.setValor(rs.getDouble("valor"));
				resgate1.setValorrecebido(rs.getDouble("valorrecebido"));
				resgate1.setData(rs.getString("data"));
				resgate1.setStatus(rs.getString("status"));
				resgate1.setData_final(rs.getString("data_final"));
				resgate1.setNome(rs.getString("nome"));
								
				lista.add(resgate1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}

	
public LinkedList<Resgate> getResgatesConcluidos(Resgate resgate) {
		
		LinkedList<Resgate> lista = new LinkedList<Resgate>();
				
		try {

			String sql =	"SELECT r.idresgate, \r\n" +
							"		r.idevento, \r\n" +
							"		r.idtipo_resgate, \r\n" +
							"		r.codigo, \r\n" +
							"		r.valor, \r\n" +
							"		r.valorrecebido, \r\n" +
							"		r.data, \r\n" +
							"		r.status \r\n" +
							"FROM 	chadl_resgates r \r\n" + 
							"WHERE 	r.idevento=? AND r.status=? \r\n" +
							"GROUP BY r.idresgate, \r\n" +
							"		  r.idevento, \r\n" +
							"		  r.idtipo_resgate, \r\n" +
							"		  r.codigo, \r\n" +
							"		  r.valor, \r\n" +
							"		  r.valorrecebido, \r\n" +
							"		  r.data, \r\n" +
							"		  r.status \r\n" +
							"ORDER BY r.data";
							
									
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, resgate.getIdevento());
			pst.setString(2, resgate.getStatus());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Resgate resgate1 = new Resgate();
				resgate1.setIdresgate(rs.getInt("idresgate"));
				resgate1.setIdevento(rs.getInt("idevento"));
				resgate1.setIdtipo_resgate(rs.getInt("idtipo_resgate"));				
				resgate1.setCodigo(rs.getString("codigo"));
				resgate1.setValor(rs.getDouble("valor"));
				resgate1.setValorrecebido(rs.getDouble("valorrecebido"));
				resgate1.setData(rs.getString("data"));
				resgate1.setStatus(rs.getString("status"));
								
				lista.add(resgate1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}

	}

	
	public double saldo(Resgate resgate) {
		double valorResgate = 0.0;
		double valorPresente = 0.0;
		double saldo = 0.0;
		
		try {

			String sql =	"select sum(r.valor) as vlrresgates from chadl_resgates r \r\n" +
							"where r.idevento = ?;";
					
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, resgate.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				
				valorResgate = rs.getDouble("vlrresgates");
																	
			}

			sql = "select sum(p.valor) as vlrpresentes from chadl_presentes p \r\n" +
					"where p.idevento = ?;";
			
			PreparedStatement pstt = conexao.sqlPreparada(sql);
			pstt.setInt(1, resgate.getIdevento());
			ResultSet rss = conexao.executaQuery(pstt.toString());

			while (rss.next()) {
				
				valorPresente = rss.getDouble("vlrpresentes");
																	
			}
			
			if (valorPresente > 0) {
				
				saldo = valorPresente - valorResgate;				
				
				return saldo;
			}else {
				saldo = 0.0;
				
				return saldo;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return saldo;
		}
	}


}
