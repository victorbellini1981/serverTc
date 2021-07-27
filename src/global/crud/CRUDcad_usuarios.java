package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import global.util.EnviaEmail;
import global.util.LogErros;

import global.dados.Email;
import global.dados.Usuario;

import conexao.Conexao;

public class CRUDcad_usuarios {
	
private Conexao conexao;
	
	public CRUDcad_usuarios(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Usuario insert(Usuario usuario) {
		this.conexao.iniciaTransacao();
		
		try {

			String sql =	"INSERT INTO chadl_usuarios(email, senha) VALUES \r\n" +
							"(?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, usuario.getEmail());
			pst.setString(2, usuario.getSenha());
						
			/*
			 * ResultSet rs = conexao.executaQuery(pst.toString());
			 * 
			 * while (rs.next()) { usuario.setEmail(rs.getString("email"));
			 * usuario.setSenha(rs.getString("senha"));
			 * 
			 * }
			 */
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				sql = "select currval('chadl_usuarios_idusuario_seq') as id;";
				ResultSet rs = conexao.executaQuery(sql);
				rs.next();
				int iduser = 0;
				iduser = rs.getInt("id");
				sql = "INSERT INTO chadl_pessoas(idusuario) VALUES (" + iduser + ");";
				//conexao.executaUpdate(sql);
				if (conexao.executaUpdate(sql).equalsIgnoreCase("ok")) {
					sql = "select currval('chadl_pessoas_idpessoa_seq') as idp;";
					ResultSet rst = conexao.executaQuery(sql);
					rst.next();
					int idpessoa = 0;
					idpessoa = rst.getInt("idp");
					usuario.setIdpessoa(idpessoa);
				}
				
				conexao.confirmaTransacao();
				return usuario;
				
			} else {
				conexao.cancelaTransacao();
				return usuario;
			}
			
			
			//conexao.executaUpdate(pst.toString());
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return usuario;
		}
		
	}

	public Usuario getLogin(Usuario usuario) {

				
		try {

			String sql =	"SELECT u.idusuario, \r\n" + 
							"		u.email, \r\n" +
							"		u.senha, \r\n" +
							"		p.idpessoa \r\n" +
							"FROM 	chadl_usuarios u \r\n" + 
							"		INNER JOIN chadl_pessoas p \r\n" +
							"		ON u.idusuario = p.idusuario \r\n" +
							"WHERE 	u.email=? \r\n" +
							"AND 	u.senha=?; ";
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, usuario.getEmail());
			pst.setString(2, usuario.getSenha());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				usuario.setIdusuario(rs.getInt("idusuario"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setIdpessoa(rs.getInt("idpessoa"));
								
			}
			
			return usuario;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return usuario;
		}

	}
	
	public boolean verificaEmailExistente(String email) {
		try {	
			System.out.println("entrou");
			String sql = "SELECT email FROM chadl_usuarios WHERE email = ?";
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, email.toLowerCase());
			
			ResultSet rs = conexao.executaQuery(pst.toString());
			
			return rs.next();
		} catch (Exception e) {
			LogErros log = new LogErros();
            log.gravarLogErro(e,"MOBILE", "Erro " + e.getMessage());
            return false;
		}
	}
	
	public Usuario readPorEmail(String email) {
		Usuario c = new Usuario();
		try {
			String sql = "SELECT email, idusuario " + 
					" FROM chadl_usuarios " + 
					" WHERE email = '" + email + "' ";
			
			ResultSet res = conexao.executaQuery(sql);
			if(res.next()) {
				c.setIdusuario(res.getInt("idusuario"));
				c.setEmail(res.getString("email"));
			}
			
			return c;
		} catch (Exception e) {
			LogErros log = new LogErros();
            log.gravarLogErro(e,"MOBILE", "Erro " + e.getMessage());
            return c;
		}
	}
	
	public int gerarNumeroRandonico(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("maximo precisa ser maior que o minimo");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public boolean enviaEmailRedefinirSenha(Usuario c, int codigo) {		
		try {
			Email email = new Email();
			email.setEnderecoEmailDestinatario(c.getEmail());
			email.setAssunto("ChaDeLingerie - Redefinição de Senha");
			
			email.setMensagem("Olá!<br/><br/>"
					+ "Seu código para redefinição de senha é: <strong>" + codigo + "</strong><br/><br/>"
					+ "Se você não solicitou uma redefinição de senha, ignore esta mensagem.");
			
			EnviaEmail enviaEmail = new EnviaEmail(email);
			enviaEmail.run();
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean updataSenha(String senha, int idusuario) {
		//this.conexao.iniciaTransacao();
		boolean ok;
		try {
			String sql = "UPDATE chadl_usuarios SET senha = ? WHERE idusuario = ?";
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, senha);
			pst.setInt(2, idusuario);
			
			//conexao.confirmaTransacao();			
			return conexao.executaUpdate(pst.toString()).equals("ok");
		} catch (Exception e) {
			LogErros log = new LogErros();
            log.gravarLogErro(e,"MOBILE", "Erro " + e.getMessage());
            return false;
		}
	}
	
}
