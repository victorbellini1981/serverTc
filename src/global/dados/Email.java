package global.dados;

import java.util.LinkedList;

public class Email{
	private String servidor;
	private String enderecoEmailDestinatario;
	private String enderecoEmailRemetente;
	private String senha;
	private String assunto;
	private String mensagem;
	private LinkedList<String> anexos;
	
	public Email() {
		this.anexos = new LinkedList<String>();
	}
	
	public String getServidor() {
		return servidor;
	}
	public void setServidor(String servidor) {
		this.servidor = servidor;
	}
	public String getEnderecoEmailDestinatario() {
		return enderecoEmailDestinatario;
	}
	public void setEnderecoEmailDestinatario(String enderecoEmailDestinatario) {
		this.enderecoEmailDestinatario = enderecoEmailDestinatario;
	}
	public String getEnderecoEmailRemetente() {
		return enderecoEmailRemetente;
	}
	public void setEnderecoEmailRemetente(String enderecoEmailRemetente) {
		this.enderecoEmailRemetente = enderecoEmailRemetente;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public LinkedList<String> getAnexos() {
		return anexos;
	}
	public void setAnexos(LinkedList<String> anexos) {
		this.anexos = anexos;
	}
}
