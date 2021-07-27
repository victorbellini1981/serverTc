package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.Conexao;
import global.dados.Pessoa;
import global.dados.Transacao;
import global.util.LogErros;

public class CRUDcad_transacoes {
	
private Conexao conexao;
	
	public CRUDcad_transacoes(Conexao conexao) {
		this.conexao = conexao;
		this.conexao.iniciaTransacao();
	}
	
	public Transacao insert(Transacao transacao) {

		
		try {

			String sql =	"INSERT INTO chadl_transacao(idevento, data, nomecliente, valor, idtransaction_pagarme, qtdparcelas) VALUES \r\n" +
							"(?,?,?,?,?,?);"; 

			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, transacao.getIdevento());
			pst.setString(2, transacao.getData());
			pst.setString(3, transacao.getNomecliente());
			pst.setDouble(4, transacao.getValor());
			pst.setInt(5, 0);
			pst.setInt(6, transacao.getQtdparcelas());
						
			/*
			 * ResultSet rs = conexao.executaQuery(pst.toString());
			 * 
			 * while (rs.next()) { usuario.setEmail(rs.getString("email"));
			 * usuario.setSenha(rs.getString("senha"));
			 * 
			 * }
			 */
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				sql = "select currval('chadl_transacao_idtransacao_seq') as id;";
				ResultSet rs = conexao.executaQuery(sql);
				rs.next();
				int idtrans = rs.getInt("id");
				transacao.setIdtransacao(idtrans);
				
				conexao.confirmaTransacao();
				System.out.print(transacao);
				return transacao;
				
			} else {
				conexao.cancelaTransacao();
				return transacao;
			}
			
			
			//conexao.executaUpdate(pst.toString());
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return transacao;
		}
		
	}

	public Transacao finalizacao(Transacao transacao) {

		
		try {

			String sql =	"SELECT t.idtransaction_pagarme, \r\n" +
							"t.valor, \r\n" +
							"t.qtdparcelas \r\n" +
							"FROM 	chadl_transacao t \r\n" + 
							"WHERE 	t.idtransacao=?;";
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, transacao.getIdtransacao());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				transacao.setIdtransaction_pagarme(rs.getInt("idtransaction_pagarme"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setQtdparcelas(rs.getInt("qtdparcelas"));
			}
			
			return transacao;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return transacao;
		}

	}


}
