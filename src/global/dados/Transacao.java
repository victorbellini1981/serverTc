package global.dados;

public class Transacao {
	
	private int idtransacao;
	private int idevento;
	private String data;
	private String nomecliente;
	private double valor;
	private int idtransaction_pagarme;
	private int qtdparcelas;
	
			
	public int getQtdparcelas() {
		return qtdparcelas;
	}
	public void setQtdparcelas(int qtdparcelas) {
		this.qtdparcelas = qtdparcelas;
	}
	public int getIdtransaction_pagarme() {
		return idtransaction_pagarme;
	}
	public void setIdtransaction_pagarme(int idtransaction_pagarme) {
		this.idtransaction_pagarme = idtransaction_pagarme;
	}
	public int getIdtransacao() {
		return idtransacao;
	}
	public void setIdtransacao(int idtransacao) {
		this.idtransacao = idtransacao;
	}
	public int getIdevento() {
		return idevento;
	}
	public void setIdevento(int idevento) {
		this.idevento = idevento;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNomecliente() {
		return nomecliente;
	}
	public void setNomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	

}
