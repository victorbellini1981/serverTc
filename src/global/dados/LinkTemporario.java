package global.dados;

public class LinkTemporario {
	private int idlink;
	private int idusuario;
	private String md5;
	private String tipo_link;
	private String data_expiracao;

	public LinkTemporario() { }

	public int getIdlink() {
		return idlink;
	}
	public void setIdlink(int idlink) {
		this.idlink = idlink;
	}
	public int getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getTipo_link() {
		return tipo_link;
	}
	public void setTipo_link(String tipo_link) {
		this.tipo_link = tipo_link;
	}
	public String getData_expiracao() {
		return data_expiracao;
	}
	public void setData_expiracao(String data_expiracao) {
		this.data_expiracao = data_expiracao;
	}	
}
