package global.dados;

import java.util.LinkedList;

public class Retorno {
	
	private String situacao;
	private String codErro;
	private String msg;
	private Object obj;
	private LinkedList<Object> arrayObj;
	
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getCodErro() {
		return codErro;
	}
	public void setCodErro(String codErro) {
		this.codErro = codErro;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public LinkedList<Object> getArrayObj() {
		return arrayObj;
	}
	public void setArrayObj(LinkedList<Object> arrayObj) {
		this.arrayObj = arrayObj;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}