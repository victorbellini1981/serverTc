package global.dados;

public class Batimento {
	private int idbatimento;
	private int idusuario;
	private String batimento;
	private String data_bat;
	private String dtinicial;
	private String dtfinal;
	
	public String getDtinicial() {
		return dtinicial;
	}
	public void setDtinicial(String dtinicial) {
		this.dtinicial = dtinicial;
	}
	public String getDtfinal() {
		return dtfinal;
	}
	public void setDtfinal(String dtfinal) {
		this.dtfinal = dtfinal;
	}
	public int getIdbatimento() {
		return idbatimento;
	}
	public void setIdbatimento(int idbatimento) {
		this.idbatimento = idbatimento;
	}
	public int getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public String getBatimento() {
		return batimento;
	}
	public void setBatimento(String batimento) {
		this.batimento = batimento;
	}
	public String getData_bat() {
		return data_bat;
	}
	public void setData_bat(String data_bat) {
		this.data_bat = data_bat;
	}
	
	
}
