package server.aplicativo;


import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import conexao.Conexao;
import global.crud.CRUDcad_pacientes;
import global.dados.Paciente;
import global.dados.Retorno;
import global.dados.Usuario;
import global.util.LogErros;
import global.util.Util;

public class CRUDpaciente {
	
	private String serverName;

	public CRUDpaciente(String serverName) {
		this.serverName = serverName;
	}
	
	public Retorno postPaciente(Paciente pessoa) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		System.out.print(pessoa);
				
		try {
			CRUDcad_pacientes crud_pessoa = new CRUDcad_pacientes(conexao);
			Paciente pessoas = crud_pessoa.update(pessoa);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", pessoas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getPaciente(Paciente pessoa) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		
		try {
			CRUDcad_pacientes crud_pessoa = new CRUDcad_pacientes(conexao);
			Paciente pessoas = crud_pessoa.getPaciente(pessoa);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", pessoas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno getPacienteNome(Paciente pessoa) {
		Conexao conexao = new Conexao(serverName);
		Util u = new Util();
		
		try {
			CRUDcad_pacientes crud_pessoa = new CRUDcad_pacientes(conexao);
			Paciente pessoas = crud_pessoa.getPacienteNome(pessoa);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", pessoas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	/*public Retorno insertFaceGoogle(Pessoa pessoa) {
		Conexao conexao = new Conexao();
		Util u = new Util();
		
		try {
			CRUDcad_pessoas crud_pessoa = new CRUDcad_pessoas(conexao);
			Pessoa pessoas = crud_pessoa.insertFaceGoogle(pessoa);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", pessoas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}
	
	public Retorno verPessoaFaceGoogle(Pessoa pessoa) {
		Conexao conexao = new Conexao();
		Util u = new Util();
		
		try {
			CRUDcad_pessoas crud_pessoa = new CRUDcad_pessoas(conexao);
			Pessoa pessoas = crud_pessoa.verPessoaFaceGoogle(pessoa);
			return u.retorno("sucesso", "", "Sucesso ao retornar dados", pessoas, null);
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return u.retorno("erro", "", "Erro ao retornar produto", null, null);
		} finally {
			conexao.desconecta();
		}
	}*/

}
