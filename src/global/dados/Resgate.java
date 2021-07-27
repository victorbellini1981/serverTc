package global.dados;

public class Resgate {
	
	private int idresgate;
	private int idevento;
	private int idtipo_resgate;
	private String codigo;
	private double valor;
	private double valorrecebido;
	private String data;
	private String status;
	private String data_final;
	private String nome;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getData_final() {
		return data_final;
	}
	public void setData_final(String data_final) {
		this.data_final = data_final;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public double getValorrecebido() {
		return valorrecebido;
	}
	public void setValorrecebido(double valorrecebido) {
		this.valorrecebido = valorrecebido;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getIdresgate() {
		return idresgate;
	}
	public void setIdresgate(int idresgate) {
		this.idresgate = idresgate;
	}
	public int getIdevento() {
		return idevento;
	}
	public void setIdevento(int idevento) {
		this.idevento = idevento;
	}
	public int getIdtipo_resgate() {
		return idtipo_resgate;
	}
	public void setIdtipo_resgate(int idtipo_resgate) {
		this.idtipo_resgate = idtipo_resgate;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	

}
