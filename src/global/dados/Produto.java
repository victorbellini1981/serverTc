package global.dados;

public class Produto {
	
	private String idproduto;
	private String referencia;
	private String descricao;
	private String cor;
	private String categoria;
	private String tamanhos;
	private Double preco_tabela;
	private String marca;
	private String link;
	
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco_tabela() {
		return preco_tabela;
	}
	public void setPreco_tabela(Double preco_tabela) {
		this.preco_tabela = preco_tabela;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getTamanhos() {
		return tamanhos;
	}
	public void setTamanhos(String tamanhos) {
		this.tamanhos = tamanhos;
	}
	public String getIdproduto() {
		return idproduto;
	}
	public void setIdproduto(String idproduto) {
		this.idproduto = idproduto;
	}
		
}
