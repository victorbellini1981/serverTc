package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import conexao.Conexao;
import global.dados.Cupom;
import global.dados.Transferencia;
import global.util.LogErros;

public class CRUDcad_transferencias {
	
	private Conexao conexao;
	
	public CRUDcad_transferencias(Conexao conexao) {
		this.conexao = conexao;
	}
	
public Transferencia insert(Transferencia transferencia) {
	
		conexao.iniciaTransacao();

		
		try {

			String sql =	"INSERT INTO chadl_transferencias(idresgate, codigo_banco, nome_banco, \r\n" +   
							"                            tipo_conta, conta, agencia, digito, \r\n" +
							"                            tipo_pessoa, nome, cpf, cnpj, \r\n" +
							"                            tipo_chave, chave, valor, data) VALUES \r\n" +
							"                           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, transferencia.getIdresgate());
			pst.setString(2, transferencia.getCodigo_banco());
			pst.setString(3, transferencia.getNome_banco());
			pst.setString(4, transferencia.getTipo_conta());
			pst.setString(5, transferencia.getConta());
			pst.setString(6, transferencia.getAgencia());
			pst.setString(7, transferencia.getDigito());
			pst.setString(8, transferencia.getTipo_pessoa());
			pst.setString(9, transferencia.getNome());
			pst.setString(10, transferencia.getCpf());
			pst.setString(11, transferencia.getCnpj());
			pst.setString(12, transferencia.getTipo_chave());
			pst.setString(13, transferencia.getChave());
			pst.setDouble(14, transferencia.getValor());
			pst.setString(15, transferencia.getData());
						
						
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				sql = "select currval('chadl_transferencias_idtransferencia_seq') as id;";
				ResultSet rs = conexao.executaQuery(sql);
				rs.next();
				int idtrans = rs.getInt("id");
				transferencia.setIdtransferencia(idtrans);
				
				conexao.confirmaTransacao();
				return transferencia;
			}else {
				conexao.cancelaTransacao();
				return transferencia;
			}
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return transferencia;
		}
	}

	public LinkedList<Transferencia> getTransferencias() {
		
		LinkedList<Transferencia> lista = new LinkedList<Transferencia>();
				
		try {
	
			String sql =	"SELECT t.idtransferencia, \r\n" +
							"		t.idresgate, \r\n" +
							"		t.codigo_banco, \r\n" +
							"		t.nome_banco, \r\n" +
							"		t.tipo_conta, \r\n" +
							"		t.conta, \r\n" +
							"		t.agencia, \r\n" +
							"		t.digito, \r\n" +
							"		t.tipo_pessoa, \r\n" +
							"		t.nome, \r\n" +
							"		t.cpf, \r\n" +
							"		t.cnpj, \r\n" +
							"		t.tipo_chave, \r\n" +
							"		t.chave, \r\n" +
							"		t.valor, \r\n" +
							"		t.data \r\n" +
							"FROM 	chadl_transferencias t \r\n" ;
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			ResultSet rs = conexao.executaQuery(pst.toString());
	
			while (rs.next()) {
				Transferencia transferencia1 = new Transferencia();
				transferencia1.setIdtransferencia(rs.getInt("idtransferencia"));
				transferencia1.setIdresgate(rs.getInt("idresgate"));
				transferencia1.setCodigo_banco(rs.getString("codigo_banco"));
				transferencia1.setNome_banco(rs.getString("nome_banco"));
				transferencia1.setTipo_conta(rs.getString("tipo_conta"));
				transferencia1.setConta(rs.getString("conta"));
				transferencia1.setAgencia(rs.getString("agencia"));
				transferencia1.setDigito(rs.getString("digito"));
				transferencia1.setTipo_pessoa(rs.getString("tipo_pessoa"));
				transferencia1.setNome(rs.getString("nome"));
				transferencia1.setCpf(rs.getString("cpf"));
				transferencia1.setCnpj(rs.getString("cnpj"));
				transferencia1.setTipo_chave(rs.getString("tipo_chave"));
				transferencia1.setChave(rs.getString("chave"));
				transferencia1.setValor(rs.getDouble("valor"));
				transferencia1.setData(rs.getString("data"));
				
												
				lista.add(transferencia1);							
			}
			
			return lista;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return lista;
		}
	
	}
	
	public Transferencia getDetTransferencia(Transferencia transferencia) {
		
		
				
		try {
	
			String sql =	"SELECT t.idtransferencia, \r\n" +
							"		t.idresgate, \r\n" +
							"		t.codigo_banco, \r\n" +
							"		t.nome_banco, \r\n" +
							"		t.tipo_conta, \r\n" +
							"		t.conta, \r\n" +
							"		t.agencia, \r\n" +
							"		t.digito, \r\n" +
							"		t.tipo_pessoa, \r\n" +
							"		t.nome, \r\n" +
							"		t.cpf, \r\n" +
							"		t.cnpj, \r\n" +
							"		t.tipo_chave, \r\n" +
							"		t.chave, \r\n" +
							"		t.valor, \r\n" +
							"		t.data, \r\n" +
							"		t.data_final \r\n" +
							"FROM 	chadl_transferencias t \r\n" +
							"WHERE	t.idresgate=?";
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, transferencia.getIdresgate());
			ResultSet rs = conexao.executaQuery(pst.toString());
	
			while (rs.next()) {
				
				transferencia.setIdtransferencia(rs.getInt("idtransferencia"));
				transferencia.setIdresgate(rs.getInt("idresgate"));
				transferencia.setCodigo_banco(rs.getString("codigo_banco"));
				transferencia.setNome_banco(rs.getString("nome_banco"));
				transferencia.setTipo_conta(rs.getString("tipo_conta"));
				transferencia.setConta(rs.getString("conta"));
				transferencia.setAgencia(rs.getString("agencia"));
				transferencia.setDigito(rs.getString("digito"));
				transferencia.setTipo_pessoa(rs.getString("tipo_pessoa"));
				transferencia.setNome(rs.getString("nome"));
				transferencia.setCpf(rs.getString("cpf"));
				transferencia.setCnpj(rs.getString("cnpj"));
				transferencia.setTipo_chave(rs.getString("tipo_chave"));
				transferencia.setChave(rs.getString("chave"));
				transferencia.setValor(rs.getDouble("valor"));
				transferencia.setData(rs.getString("data"));
				transferencia.setData_final(rs.getString("data_final"));
												
											
			}
			
			return transferencia;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return transferencia;
		}
	
	}
	
	public Transferencia updateTransferencia(Transferencia transferencia) {
		
		conexao.iniciaTransacao();
		
		try {
			
				String sql = "UPDATE chadl_transferencias SET data_final = ? WHERE idresgate = ?";
				
				PreparedStatement pst4 = conexao.sqlPreparada(sql);
				pst4.setString(1, transferencia.getData_final());
				pst4.setInt(2, transferencia.getIdresgate());
				
				if (conexao.executaUpdate(pst4.toString()).equalsIgnoreCase("ok")) {
				conexao.confirmaTransacao();
				return transferencia;
				} else {
					conexao.cancelaTransacao();
					return transferencia;
				}
			
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return transferencia;			
		}
		
	}




}
