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

				String sql =	"UPDATE cad_pessoas \r\n" + 
								"SET    nome = ?, \r\n" +
								"		email = ?, \r\n" +
								"		cpf = ?, \r\n" +
								"		telefone = ?, \r\n" +
								"	    cep = ?, \r\n" +
								"		uf = ?, \r\n" +
								"		logradouro = ?, \r\n" +
								"		numero = ?, \r\n" +
								"		complemento = ?, \r\n" +
								"		bairro = ?, \r\n" +
								"		cidade = ? \r\n" +
								"WHERE 	idpessoa = ?;"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, pessoa.getNome());
			pst.setString(2, pessoa.getEmail());
			pst.setString(3, pessoa.getCpf());
			pst.setString(4, pessoa.getTelefone());
			pst.setString(5, pessoa.getCep());
			pst.setString(6, pessoa.getUf());
			pst.setString(7, pessoa.getLogradouro());
			pst.setString(8, pessoa.getNumero());
			pst.setString(9, pessoa.getComplemento());
			pst.setString(10, pessoa.getBairro());
			pst.setString(11, pessoa.getCidade());
			pst.setInt(12, pessoa.getIdpessoa());
			
			
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
	
	/*public Pessoa insertFaceGoogle(Pessoa pessoa) {

		
		try {

				String sql =	"INSERT INTO cad_pessoas(nome, email, fotoperfil, tipofoto) VALUES \r\n" +
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
	}*/

	
	public Pessoa getPessoa(Pessoa pessoa) {

		
		try {

			String sql =	"SELECT p.idusuario, \r\n" + 
							"		p.nome, \r\n" + 
							"		p.email, \r\n" +
							"		p.cpf, \r\n" +
							"		p.telefone, \r\n" +
							"		p.cep, \r\n" +
							"		p.uf, \r\n" +
							"		p.logradouro, \r\n" +
							"		p.numero, \r\n" +
							"		p.complemento, \r\n" +
							"		p.bairro, \r\n" +
							"		p.cidade \r\n" +
							"FROM 	cad_pessoas p \r\n" + 
							"WHERE 	p.idpessoa=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, pessoa.getIdpessoa());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				pessoa.setIdusuario(rs.getInt("idusuario"));
				pessoa.setNome(rs.getString("nome"));
				pessoa.setEmail(rs.getString("email"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setTelefone(rs.getString("telefone"));
				pessoa.setCep(rs.getString("cep"));
				pessoa.setUf(rs.getString("uf"));
				pessoa.setLogradouro(rs.getString("logradouro"));
				pessoa.setNumero(rs.getString("numero"));
				pessoa.setComplemento(rs.getString("complemento"));
				pessoa.setBairro(rs.getString("bairro"));
				pessoa.setCidade(rs.getString("cidade"));
												
			}
			
			return pessoa;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return pessoa;
		}

	}
	
	/*public Pessoa verPessoaFaceGoogle(Pessoa pessoa) {

		
		try {

			String sql =	"SELECT p.idpessoa, \r\n" + 
							"		p.nome, \r\n" +
							"		p.email, \r\n" +
							"		p.cpf, \r\n" +
							"		p.fotoperfil, \r\n" +
							"		p.tipofoto \r\n" +
							"FROM 	cad_pessoas p \r\n" + 
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

	}*/

	public Pessoa getPessoaNome(Pessoa pessoa) {
	
		
		try {
	
			String sql =	"SELECT p.idusuario, \r\n" + 
							"		p.idpessoa, \r\n" +
							"		p.email, \r\n" + 
							"		p.cpf, \r\n" +
							"		p.telefone, \r\n" +
							"		p.cep, \r\n" +
							"		p.uf, \r\n" +
							"		p.logradouro, \r\n" +
							"		p.numero, \r\n" +
							"		p.complemento, \r\n" +
							"		p.bairro, \r\n" +
							"		p.cidade \r\n" +
							"FROM 	cad_pessoas p \r\n" + 
							"WHERE 	p.nome=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, pessoa.getNome());
			ResultSet rs = conexao.executaQuery(pst.toString());
	
			while (rs.next()) {
				pessoa.setIdusuario(rs.getInt("idusuario"));
				pessoa.setIdpessoa(rs.getInt("idpessoa"));
				pessoa.setNome(rs.getString("email"));
				pessoa.setCpf(rs.getString("cpf"));
				pessoa.setTelefone(rs.getString("telefone"));
				pessoa.setCep(rs.getString("cep"));
				pessoa.setUf(rs.getString("uf"));
				pessoa.setLogradouro(rs.getString("logradouro"));
				pessoa.setNumero(rs.getString("numero"));
				pessoa.setComplemento(rs.getString("complemento"));
				pessoa.setBairro(rs.getString("bairro"));
				pessoa.setCidade(rs.getString("cidade"));
												
			}
			
			return pessoa;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return pessoa;
		}
	
	}
	
}
