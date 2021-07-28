package server.aplicativo;


import conexao.Conexao;
import global.crud.CRUDcad_usuarios;
import global.crud.CRUDlinks_temporarios;
import global.dados.Usuario;

import global.dados.LinkTemporario;
import global.dados.Retorno;
import global.util.LogErros;
import global.util.Util;

public class CRUDusuario {
	
	private String serverName;

	public CRUDusuario(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postUsuario(Usuario usuario) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		System.out.print(usuario);
				
		try {
			CRUDcad_usuarios crud_usuario = new CRUDcad_usuarios(conexao);
			Usuario usuarios = crud_usuario.insert(usuario);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", usuarios, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getLogin(Usuario usuario) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		
		try {
			CRUDcad_usuarios crud_usuario = new CRUDcad_usuarios(conexao);
			Usuario usuarios = crud_usuario.getLogin(usuario);
				return u.retorno("sucesso", "", "Sucesso ao retornar dados", usuarios, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}

	public Retorno redefinirSenha(Usuario c) {
		
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		System.out.println(c.getEmail());
		
		try {
			boolean ok;
			CRUDlinks_temporarios crud_links = new CRUDlinks_temporarios(conexao);
			CRUDcad_usuarios crud_usuarios = new CRUDcad_usuarios(conexao);
			
			/* VERIFICA SE O EMAIL JÁ EXIXTE */
			ok = crud_usuarios.verificaEmailExistente(c.getEmail());
			if(!ok){ return u.retorno("erro", "0", "E-mail não cadastrado.", c, null); }
			
			c = crud_usuarios.readPorEmail(c.getEmail());
			
			int codigo = crud_usuarios.gerarNumeroRandonico(1000, 9999);
			
			LinkTemporario lt = new LinkTemporario();
			lt.setIdusuario(c.getIdusuario());
			lt.setMd5(Integer.toString(codigo));
			lt.setTipo_link("ChaDeLingerie-redefinicao-senha");
			System.out.println(lt.getIdusuario());
			
			ok = crud_links.insert(lt);
			if(!ok) { return u.retorno("erro", "0", "Não foi possível armazenar o código.", c, null); }
			
			ok = crud_usuarios.enviaEmailRedefinirSenha(c, codigo);
			if(!ok) { return u.retorno("erro", "0", "Não foi possível enviar o e-mail.", c, null); }
			
			return u.retorno("sucesso", "", "Email enviado com sucesso!", c, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
            log.gravarLogErro(e,"MOBILE", "Erro " + e.getMessage());
            return u.retorno("erro", "1", "Cód. Erro: 1 - Erro ao atualizar dados", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno updateSenha(Usuario c, String md5) {
		
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		
		try {
			boolean ok;
			CRUDlinks_temporarios crud_links = new CRUDlinks_temporarios(conexao);
			CRUDcad_usuarios crud_usuarios = new CRUDcad_usuarios(conexao);
			
			conexao.iniciaTransacao();

			LinkTemporario lt = crud_links.read(md5);
			if(md5 != null) {				
				
				if(lt != null) {
					//c = crud_usuarios.read(lt.getIdpessoa());
					crud_links.update(lt.getIdlink());
				} else {
					return u.retorno("erro", "66", "Link invalido, solicite um novo!", c, null);		
				}
			}
			
			ok = crud_usuarios.updataSenha(c.getSenha(), lt.getIdusuario());
			if(!ok) { return u.retorno("erro", "66", "Não foi possível atualizar.", c, null); }
			
			conexao.confirmaTransacao();
			return u.retorno("sucesso", "", "Atualizado com sucesso", c, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
            log.gravarLogErro(e,"MOBILE", "Erro " + e.getMessage());
            return u.retorno("erro", "1", "Cód. Erro: 1 - Erro ao atualizar dados", c, null);
		} finally {
			conexao.desconecta();
		}
	}


}
