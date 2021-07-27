package global.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.Conexao;
import global.dados.Consultora;
import global.util.LogErros;

public class CRUDcad_consultoras {
	
private Conexao conexao;
	
	public CRUDcad_consultoras(Conexao conexao) {
		this.conexao = conexao;
		
	}
	
	public Consultora select(Consultora consultora) {
		
		try {

			String sql =	"SELECT desce.id, cp.idpessoa, CASE cp.tipopessoa \r\n" +
							"WHEN 'PF' THEN cp.nome ELSE cp.razaosocial END AS nome, \r\n" +
							"ce.logradouro, ce.numero, ce.complemento, ce.bairro, \r\n" +
							"c.cidade, c.estado, cc.contato, cc.email, \r\n" +
							"cc.dddfixo, cc.telefone, cc.dddcelular, cc.celular, \r\n" +
							"empresas.idempresa, empresas.nomefantasia as empresa \r\n" +
							"FROM tab_usuarios as usuario \r\n" +
							"inner join cad_pessoas as cp on cp.idpessoa = usuario.idpessoa \r\n" +
							"inner join cad_empresas as empresas on empresas.idempresa = cp.idempresa \r\n" +
							"INNER JOIN cad_enderecos ce ON ce.idpessoa = cp.idpessoa AND ce.principal = true \r\n" +
							"INNER JOIN cad_cidades c ON c.idcidade = ce.idcidade \r\n" +
							"INNER JOIN cad_contatos cc ON cc.idpessoa = cp.idpessoa AND cc.principal = true \r\n" +
							"INNER JOIN tab_desconto_ecommerce desce ON desce.idpessoa = cp.idpessoa \r\n" +
							"WHERE UPPER(desce.codigo) = UPPER(?) \r\n" +
							"AND usuario.usu_ativo = true AND cp.ativo = true";
							
							
		
			
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setString(1, consultora.getLovecode());
			ResultSet rs = conexao.executaQuery(pst.toString());

			while (rs.next()) {
				consultora.setId(rs.getInt("id"));
				consultora.setIdpessoa(rs.getInt("idpessoa"));
				consultora.setNome(rs.getString("nome"));
				consultora.setLogradouro(rs.getString("logradouro"));
				consultora.setNumero(rs.getString("numero"));
				consultora.setComplemento(rs.getString("complemento"));
				consultora.setBairro(rs.getString("bairro"));
				consultora.setCidade(rs.getString("cidade"));
				consultora.setUf(rs.getString("estado"));
				consultora.setContato(rs.getString("contato"));
				consultora.setEmail(rs.getString("email"));
				consultora.setDddfixo(rs.getString("dddfixo"));
				consultora.setDddcelular(rs.getString("dddcelular"));
				consultora.setCelular(rs.getString("celular"));
				consultora.setIdempresa(rs.getInt("idempresa"));
				consultora.setEmpresa(rs.getString("empresa"));
								
			}
			
			return consultora;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return consultora;
		}

		
	}
	
	public Consultora insert(Consultora consultora) {
	
		conexao.iniciaTransacao();
		
		try {

			String sql =	"INSERT INTO dz_ecommerce_cupons_descontos(idpessoa, data, valor, \r\n" +
							"iddominio, idempresa, idusuario, chadelangerie, idpedidovenda) \r\n" +
							"VALUES (?, now(), ROUND((?-((?*10.0)/100.0))::NUMERIC, 2), \r\n" +
							"72, ?, 752, true, 0)";
							
						
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setInt(1, consultora.getIdpessoa());
			pst.setDouble(2, consultora.getValor());
			pst.setDouble(3, consultora.getValor());
			pst.setInt(4, consultora.getIdempresa());
			
			if (conexao.executaUpdate(pst.toString()).equalsIgnoreCase("ok")) {
				
				conexao.confirmaTransacao();
				return consultora;
			}else {
				conexao.cancelaTransacao();
				return consultora;
			}
			
			
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, "MOBILE", "Erro " + e.getMessage());
			return consultora;
		}

		
	}

}
