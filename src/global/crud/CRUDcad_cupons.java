package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Cupom;
import global.dados.Resgate;
import global.util.LogErros;

public class CRUDcad_cupons {
	
private Conexao conexao;
	
	public CRUDcad_cupons(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Cupom insert(Cupom cupom) {
		
		conexao.iniciaTransacao();
		
		try {

			String sql =	"INSERT INTO chadl_cupons(idresgate, codigo, data, valor, empresa) \r\n" +
							"                           VALUES (?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, cupom.getIdresgate());
			pst.setString(2, cupom.getCodigo());
			pst.setString(3, cupom.getData());
			pst.setDouble(4, cupom.getValor());
			pst.setString(5, cupom.getEmpresa());			
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				sql = "select currval('chadl_cupons_idcupom_seq') as id;";
				ResultSet rs = conexao.executaQuery(sql);
				rs.next();
				int idcup = rs.getInt("id");
				cupom.setIdcupom(idcup);
				
				
				conexao.confirmaTransacao();
				return cupom;
			}else {
				conexao.cancelaTransacao();
				return cupom;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return cupom;
		}
	}
	
	public Cupom updateCodigo(Cupom cupom) {
		
		conexao.iniciaTransacao();
		
		try {
			
			String sql = "UPDATE chadl_cupons SET codigo = ? WHERE idresgate = ?";
			
			PreparedStatement pst3 = conexao.sqlPreparada(sql);
			pst3.setString(1, cupom.getCodigo());
			pst3.setInt(2, cupom.getIdresgate());
			
			if (conexao.executaUpdate(pst3.toString()).equalsIgnoreCase("ok")) {
			
				sql = "UPDATE chadl_cupons SET data_final = ? WHERE idresgate = ?";
				
				PreparedStatement pst4 = conexao.sqlPreparada(sql);
				pst4.setString(1, cupom.getData_final());
				pst4.setInt(2, cupom.getIdresgate());
				
				if (conexao.executaUpdate(pst4.toString()).equalsIgnoreCase("ok")) {
				conexao.confirmaTransacao();
				return cupom;
				} else {
					conexao.cancelaTransacao();
					return cupom;
				}
			} else {
				conexao.cancelaTransacao();
				return cupom;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return cupom;			
		}
		
	}

	
	public LinkedList<Cupom> getCupom() {
		
		LinkedList<Cupom> lista = new LinkedList<Cupom>();
				
		try {

			String sql =	"SELECT r.idcupom, \r\n" +
							"		r.codigo, \r\n" +
							"		r.valor, \r\n" +
							"		r.data, \r\n" +
							"		r.idresgate, \r\n" +
							"		r.empresa \r\n" +
							"FROM 	chadl_cupons r \r\n" ;
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				Cupom cupom1 = new Cupom();
				cupom1.setIdcupom(rs.getInt("idcupom"));
				cupom1.setCodigo(rs.getString("codigo"));
				cupom1.setValor(rs.getDouble("valor"));
				cupom1.setData(rs.getString("data"));
				cupom1.setIdresgate(rs.getInt("idresgate"));
				cupom1.setEmpresa(rs.getString("empresa"));				
								
								
				lista.add(cupom1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}
		
	}
	
	public Cupom getDetCupom(Cupom cupom) {
		
						
		try {

			String sql =	"SELECT r.idcupom, \r\n" +
							"		r.codigo, \r\n" +
							"		r.valor, \r\n" +
							"		r.data, \r\n" +
							"		r.idresgate, \r\n" +
							"		r.empresa, \r\n" +
							"		r.data_final \r\n" +
							"FROM 	chadl_cupons r \r\n" +
							"WHERE  r.idresgate=?" ;
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, cupom.getIdresgate());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				
				cupom.setIdcupom(rs.getInt("idcupom"));
				cupom.setCodigo(rs.getString("codigo"));
				cupom.setValor(rs.getDouble("valor"));
				cupom.setData(rs.getString("data"));
				cupom.setIdresgate(rs.getInt("idresgate"));
				cupom.setEmpresa(rs.getString("empresa"));				
				cupom.setData_final(rs.getString("data_final"));
				
								
											
			}
			
			return cupom;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return cupom;
		}
	}



}
