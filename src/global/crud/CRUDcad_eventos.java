package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.Conexao;
import global.dados.Evento;
import global.dados.Pessoa;
import global.util.LogErros;

public class CRUDcad_eventos {
	
private Conexao conexao;
	
	public CRUDcad_eventos(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Evento getDadosSite(Evento evento) {
		try {

			String sql =	"SELECT   e.idevento, \r\n" +
							"         e.idpessoa, \r\n" +
							"		  e.msg, \r\n" +
							"		  e.data, \r\n" +
							"		  e.uf, \r\n" +
							"		  e.logradouro, \r\n" +
							"		  e.numero, \r\n" +
							"		  e.bairro, \r\n" +
							"		  e.cidade, \r\n" +
							"		  e.latitude, \r\n" +
							"		  e.longitude, \r\n" +
							"         p.nome, \r\n" +
							"		  p.fotoperfil, \r\n" +
							"         Array_agg(a.link)  AS album \r\n" +
							"FROM 	  chadl_eventos e \r\n" + 
							"         INNER JOIN chadl_pessoas p \r\n" +
							"       	ON e.idpessoa = p.idpessoa \r\n" +
							"		  INNER JOIN chadl_albuns a \r\n" +
							"           ON a.idevento = e.idevento \r\n" +
							"WHERE 	  e.idevento=? \r\n" +
							"GROUP BY e.idevento, \r\n" +
							"         e.idpessoa, \r\n" +
							"		  e.msg, \r\n" +
							"		  e.data, \r\n" +
							"		  e.uf, \r\n" +
							"		  e.logradouro, \r\n" +
							"		  e.numero, \r\n" +
							"		  e.bairro, \r\n" +
							"		  e.cidade, \r\n" +
							"         p.nome, \r\n" +
							"		  p.fotoperfil \r\n" +
							"ORDER BY e.idevento";
							
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, evento.getIdevento());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				evento.setIdevento(rs.getInt("idevento"));
				evento.setIdpessoa(rs.getInt("idpessoa"));
				evento.setMsg(rs.getString("msg"));
				evento.setData(rs.getString("data"));
				evento.setUf(rs.getString("uf"));
				evento.setLogradouro(rs.getString("logradouro"));
				evento.setNumero(rs.getString("numero"));
				evento.setBairro(rs.getString("bairro"));
				evento.setCidade(rs.getString("cidade"));
				evento.setLatitude(rs.getDouble("latitude"));
				evento.setLongitude(rs.getDouble("longitude"));
				evento.setNome(rs.getString("nome"));
				evento.setFotoperfil(rs.getString("fotoperfil"));
				evento.setAlbum(rs.getString("album"));
								
			}
			
			return evento;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return evento;
		}
	}
	
	public Evento insert(Evento evento) {
		
		conexao.iniciaTransacao();
		try {

				String sql =	"INSERT INTO chadl_eventos (idpessoa, msg, data, hora, cep, \r\n" +
								"							uf, logradouro, numero, \r\n" +
								"							complemento, bairro, cidade, latitude, longitude, datafinal) VALUES \r\n" +
								"                          (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
						
								
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, evento.getIdpessoa());
			pst.setString(2, evento.getMsg());
			pst.setString(3, evento.getData());
			pst.setString(4, evento.getHora());
			pst.setString(5, evento.getCep());
			pst.setString(6, evento.getUf());
			pst.setString(7, evento.getLogradouro());
			pst.setString(8, evento.getNumero());
			pst.setString(9, evento.getComplemento());
			pst.setString(10, evento.getBairro());
			pst.setString(11, evento.getCidade());
			pst.setDouble(12, evento.getLatitude());
			pst.setDouble(13, evento.getLongitude());
			pst.setString(14, evento.getDatafinal());
			
			
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return evento;
			}else {
				conexao.cancelaTransacao();
				return evento;
			}
			
						
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return evento;
		}
	}

	
	public Evento update(Evento evento) {

		conexao.iniciaTransacao();
		try {

				String sql =	"UPDATE chadl_eventos \r\n" + 
								"SET    msg = ?, \r\n" +
								"		data = ?, \r\n" +
								"		hora = ?, \r\n" +
								"		cep = ?, \r\n" +
								"		uf = ?, \r\n" +
								"		logradouro = ?, \r\n" +
								"		numero = ?, \r\n" +
								"		complemento = ?, \r\n" +
								"		bairro = ?, \r\n" +
								"		cidade = ?, \r\n" +
								"		latitude = ?, \r\n" +
								"		longitude = ?, \r\n" +
								"		datafinal = ? \r\n" +
								"WHERE 	idpessoa = ?;"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, evento.getMsg());
			pst.setString(2, evento.getData());
			pst.setString(3, evento.getHora());
			pst.setString(4, evento.getCep());
			pst.setString(5, evento.getUf());
			pst.setString(6, evento.getLogradouro());
			pst.setString(7, evento.getNumero());
			pst.setString(8, evento.getComplemento());
			pst.setString(9, evento.getBairro());
			pst.setString(10, evento.getCidade());
			pst.setDouble(11, evento.getLatitude());
			pst.setDouble(12, evento.getLongitude());
			pst.setString(13, evento.getDatafinal());
			pst.setInt(14, evento.getIdpessoa());
			
			
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return evento;
			}else {
				conexao.cancelaTransacao();
				return evento;
			}
			
						
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return evento;
		}
	}
	
	public Evento getEvento(Evento evento) {

		
		try {

			String sql =	"SELECT e.idevento, \r\n" + 
							"		e.msg, \r\n" +
							"		e.data, \r\n" +
							"		e.hora, \r\n" +
							"		e.cep, \r\n" +
							"		e.uf, \r\n" +
							"		e.logradouro, \r\n" +
							"		e.numero, \r\n" +
							"		e.complemento, \r\n" +
							"		e.bairro, \r\n" +
							"		e.cidade, \r\n" +
							"		e.datafinal \r\n" +
							"FROM 	chadl_eventos e \r\n" + 
							"WHERE 	e.idpessoa=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, evento.getIdpessoa());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				evento.setIdevento(rs.getInt("idevento"));
				evento.setMsg(rs.getString("msg"));	
				evento.setData(rs.getString("data"));
				evento.setHora(rs.getString("hora"));
				evento.setCep(rs.getString("cep"));
				evento.setUf(rs.getString("uf"));
				evento.setLogradouro(rs.getString("logradouro"));
				evento.setNumero(rs.getString("numero"));
				evento.setComplemento(rs.getString("complemento"));
				evento.setBairro(rs.getString("bairro"));
				evento.setCidade(rs.getString("cidade"));
				evento.setDatafinal(rs.getString("datafinal"));
								
			}
			
			return evento;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return evento;
		}

	}
	
	

}
