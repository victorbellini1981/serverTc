package global.dados;

import org.joda.time.DateTime;

public class Atividade {
	
	private int idatividade;
	private String data_atv;
	private String atividade;
	private int idusuario;
	
	public int getIdatividade() {
		return idatividade;
	}
	public void setIdatividade(int idatividade) {
		this.idatividade = idatividade;
	}
	public String getData_atv() {
		return data_atv;
	}
	public void setData_atv(String data_atv) {
		this.data_atv = data_atv;
	}
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public int getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	

}
