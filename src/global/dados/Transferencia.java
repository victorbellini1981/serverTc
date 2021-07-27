package global.dados;

public class Transferencia {
	
	private int idtransferencia;
	private int idresgate;
	private String codigo_banco;
	private String nome_banco;
	private String tipo_conta;
	private String conta;
	private String agencia;
	private String digito;
	private String tipo_pessoa;
	private String nome;
	private String cpf;
	private String cnpj;
	private String tipo_chave;
	private String chave;
	private double valor;
	private String data;
	private String data_final;
		
	
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
	
	public int getIdtransferencia() {
		return idtransferencia;
	}
	public void setIdtransferencia(int idtransferencia) {
		this.idtransferencia = idtransferencia;
	}
	public int getIdresgate() {
		return idresgate;
	}
	public void setIdresgate(int idresgate) {
		this.idresgate = idresgate;
	}
	public String getCodigo_banco() {
		return codigo_banco;
	}
	public void setCodigo_banco(String codigo_banco) {
		this.codigo_banco = codigo_banco;
	}
	public String getNome_banco() {
		return nome_banco;
	}
	public void setNome_banco(String nome_banco) {
		this.nome_banco = nome_banco;
	}
	public String getTipo_conta() {
		return tipo_conta;
	}
	public void setTipo_conta(String tipo_conta) {
		this.tipo_conta = tipo_conta;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getDigito() {
		return digito;
	}
	public void setDigito(String digito) {
		this.digito = digito;
	}
	public String getTipo_pessoa() {
		return tipo_pessoa;
	}
	public void setTipo_pessoa(String tipo_pessoa) {
		this.tipo_pessoa = tipo_pessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getTipo_chave() {
		return tipo_chave;
	}
	public void setTipo_chave(String tipo_chave) {
		this.tipo_chave = tipo_chave;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	

}
