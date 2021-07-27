package global.dados;

public class Cupom {
	
	private int idcupom;
	private int idresgate;
	private String codigo;
	private String data;
	private double valor;
	private String empresa;
	private String data_final;
	
		
	public String getData_final() {
		return data_final;
	}
	public void setData_final(String data_final) {
		this.data_final = data_final;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public int getIdcupom() {
		return idcupom;
	}
	public void setIdcupom(int idcupom) {
		this.idcupom = idcupom;
	}
	public int getIdresgate() {
		return idresgate;
	}
	public void setIdresgate(int idresgate) {
		this.idresgate = idresgate;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	

}
