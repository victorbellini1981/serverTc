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
	
	public Usuario insertUserP(Usuario usuario) {
		this.conexao.iniciaTransacao();
		
		try {

			String sql =	"INSERT INTO cad_usuarios(email, senha) VALUES(?,?);"; 

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
			if (conexao.executaUpdate(pst).equalsIgnoreCase("ok")) {
				sql = "select last_insert_id() as id from cad_usuarios;";
				PreparedStatement pst1 = conexao.sqlPreparada(sql);
				ResultSet rs = conexao.executaQuery(pst1);
				rs.next();
				int iduser = 0;
				iduser = rs.getInt("id");
				sql = "INSERT INTO cad_pacientes(idusuario) VALUES (" + iduser + ");";
				PreparedStatement pst2 = conexao.sqlPreparada(sql);
				//conexao.executaUpdate(sql);
				if (conexao.executaUpdate(pst2).equalsIgnoreCase("ok")) {
					sql = "select last_insert_id() as idp from cad_pacientes;";
					PreparedStatement pst3 = conexao.sqlPreparada(sql);
					ResultSet rst = conexao.executaQuery(pst3);
					rst.next();
					int idpessoa = 0;
					idpessoa = rst.getInt("idp");
					usuario.setIdpaciente(idpessoa);
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

	public Usuario getLoginP(Usuario usuario) {

				
		try {

			String sql =	"SELECT u.idusuario, \r\n" + 
							"		u.email, \r\n" +
							"		u.senha, \r\n" +
							"		p.idpaciente \r\n" +
							"FROM 	cad_usuarios u \r\n" + 
							"		INNER JOIN cad_pacientes p \r\n" +
							"		ON u.idusuario = p.idusuario \r\n" +
							"WHERE 	u.email=? \r\n" +
							"AND 	u.senha=?; ";
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, usuario.getEmail());
			pst.setString(2, usuario.getSenha());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				usuario.setIdusuario(rs.getInt("idusuario"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setIdpaciente(rs.getInt("idpaciente"));
								
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
			String sql = "SELECT email FROM cad_usuarios WHERE email = ?";
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, email.toLowerCase());
			
			ResultSet rs = conexao.executaQuery(pst);
			
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
					" FROM cad_usuarios " + 
					" WHERE email = '" + email + "' ";
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			
			ResultSet res = conexao.executaQuery(pst);
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
			email.setAssunto("Heart's Health - Redefinição de Senha");
			
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
			String sql = "UPDATE cad_usuarios SET senha = ? WHERE idusuario = ?";
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, senha);
			pst.setInt(2, idusuario);
			
			//conexao.confirmaTransacao();			
			if (conexao.executaUpdate(pst).equalsIgnoreCase("ok")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			LogErros log = new LogErros();
            log.gravarLogErro(e,"MOBILE", "Erro " + e.getMessage());
            return false;
		}
	}
	
}
