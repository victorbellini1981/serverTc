package conexao;

import java.sql.ResultSet;

public class ResultSetCache {
	private String sql;
	private int duracaoMilisegundos;
	private ResultSet resultSet;
	private long milisegundosAtual;
	
	
	
	public ResultSetCache(String sql, int duracaoMilisegundos, ResultSet resultSet) {
		this.sql = sql;
		this.duracaoMilisegundos = duracaoMilisegundos;
		this.resultSet = resultSet;
		this.milisegundosAtual = System.currentTimeMillis();
	}
	
	public boolean isValido() {
		long agora = System.currentTimeMillis();
		if (agora>milisegundosAtual && agora<=milisegundosAtual+duracaoMilisegundos) {
			return true;
		} else { 
			return false;
		}
	}

	public int getDuracaoMilisegundos() {
		return duracaoMilisegundos;
	}
	
	public void setDuracaoMilisegundos(int duracaoMilisegundos) {
		this.duracaoMilisegundos = duracaoMilisegundos;
	}
	
	public long getMilisegundosAtual() {
		return milisegundosAtual;
	}
	
	public void setMilisegundosAtual(long milisegundosAtual) {
		this.milisegundosAtual = milisegundosAtual;
	}
	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}
	
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	
	
}
