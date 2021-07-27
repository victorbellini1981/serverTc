package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.Conexao;
import global.dados.Pessoa;
import global.util.LogErros;

public class CRUDcad_pessoas {
	
private Conexao conexao;
	
	public CRUDcad_pessoas(Conexao conexao) {
		this.conexao = conexao;
		this.conexao.iniciaTransacao();
	}
	
	public Pessoa update(Pessoa pessoa) {

		
		try {

				String sql =	"UPDATE chadl_pessoas \r\n" + 
								"SET    nome = ?, \r\n" +
								"		email = ?, \r\n" +
								"		cpf = ?, \r\n" +
								"		fotoperfil = ?, \r\n" +
								"		telefone = ?, \r\n" +
								"		tipofoto = ? \r\n" +
								"WHERE 	idpessoa = ?;"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, pessoa.getNome());
			pst.setString(2, pessoa.getEmail());
			pst.setString(3, pessoa.getCpf());
			pst.setString(4, pessoa.getFotoperfil());
			pst.setString(5, pessoa.getTelefone());
			pst.setString(6, pessoa.getTipofoto());
			pst.setInt(7, pessoa.getIdpessoa());
			
			
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return pessoa;
			}else {
				conexao.cancelaTransacao();
				return pessoa;
			}
			
						
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return pessoa;
		}
	}
	
public Pessoa insertFaceGoogle(Pessoa pessoa) {

		
		try {

				String sql =	"INSERT INTO chadl_pessoas(nome, email, fotoperfil, tipofoto) VALUES \r\n" +
								"(?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, pessoa.getNome());
			pst.setString(2, pessoa.getEmail());
			pst.setString(3, pessoa.getFotoperfil());
			pst.setString(4, pessoa.getTipofoto());
						
			
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				sql = "select currval('chadl_pessoas_idpessoa_seq') as id;";
				ResultSet rs = conexao.executaQuery(sql);
				rs.next();
				int idpes = rs.getInt("id");
				pessoa.setIdpessoa(idpes);
				
				
				conexao.confirmaTransacao();
				return pessoa;
			}else {
				conexao.cancelaTransacao();
				return pessoa;
			}
			
						
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return pessoa;
		}
	}

	
	public Pessoa getPessoa(Pessoa pessoa) {

		
		try {

			String sql =	"SELECT p.idusuario, \r\n" + 
							"		p.nome, \r\n" + 
							"		p.email, \r\n" +
							"		p.cpf, \r\n" +
							"		p.fotoperfil, \r\n" +
							"		p.telefone, \r\n" +
							"		p.tipofoto \r\n" +
							"FROM 	chadl_pessoas p \r\n" + 
							"WHERE 	p.idpessoa=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, pessoa.getIdpessoa());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				pessoa.setIdusuario(rs.getInt("idusuario"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setFotoperfil(rs.getString("fotoperfil"));
				pessoa.setTelefone(rs.getString("telefone"));
				pessoa.setTipofoto(rs.getString("tipofoto"));
								
			}
			
			return pessoa;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return pessoa;
		}

	}
	
	public Pessoa verPessoaFaceGoogle(Pessoa pessoa) {

		
		try {

			String sql =	"SELECT p.idpessoa, \r\n" + 
							"		p.nome, \r\n" +
							"		p.email, \r\n" +
							"		p.cpf, \r\n" +
							"		p.fotoperfil, \r\n" +
							"		p.tipofoto \r\n" +
							"FROM 	chadl_pessoas p \r\n" + 
							"WHERE 	p.nome=?";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, pessoa.getNome());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				pessoa.setIdpessoa(rs.getInt("idpessoa"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setFotoperfil(rs.getString("fotoperfil"));
				pessoa.setTipofoto(rs.getString("tipofoto"));
												
			}
			
			return pessoa;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return pessoa;
		}

	}

	public Pessoa getPessoaEmail(Pessoa pessoa) {
	
		
		try {
	
			String sql =	"SELECT p.idusuario, \r\n" + 
							"		p.idpessoa, \r\n" +
							"		p.nome, \r\n" + 
							"		p.cpf, \r\n" +
							"		p.fotoperfil, \r\n" +
							"		p.telefone, \r\n" +
							"		p.tipofoto \r\n" +
							"FROM 	chadl_pessoas p \r\n" + 
							"WHERE 	p.email=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, pessoa.getEmail());
			ResultSet rs = conexao.executaQuery(pst.toString());
	
			while (rs.next()) {
				pessoa.setIdusuario(rs.getInt("idusuario"));
				pessoa.setIdpessoa(rs.getInt("idpessoa"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setFotoperfil(rs.getString("fotoperfil"));
				pessoa.setTelefone(rs.getString("telefone"));
				pessoa.setTipofoto(rs.getString("tipofoto"));
								
			}
			
			return pessoa;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return pessoa;
		}
	
	}
	
}
