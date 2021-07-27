package pagarme;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conexao.Conexao;
import pagarme.servicos.FacadePagarMe;
import java.text.DecimalFormat;
/**
 * Servlet implementation class ServletPagarme
 */
@WebServlet("/pagarme")
public class ServletPagarme extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPagarme() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idtransacao = 0;
		String hash = "";
		try {
			idtransacao = Integer.parseInt(request.getParameter("id"));
			hash = request.getParameter("hash");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//if (idtransacao==0 || ! hash.equals(gerarHashPagamento(idtransacao))) {
		if (idtransacao==0) {
			//formatar esta página
			response.getWriter().print("<html><body><h5>Dados inválidos para pagamento</h5></body></html>");
			return;
		}
		
		String serverName = request.getServerName(); //sempre na base oficial
		Conexao conexao = new Conexao("duzani", serverName);
		
		try {
			//Pega informações do cliente que vai pagar a transação
			/*String sql = "SELECT COALESCE(t.idtransaction_pagarme, 0) AS idtransaction_pagarme, cpc.idpessoacliente, retira_acentos(cpc.nome) as nome, retira_acentos(cpc.email) AS email, cpc.cpf_cnpj, "
					+ "cpc.telefone, cpc.datanascimento::date AS datanascimento, " +
					"retira_acentos(cpc.rua) AS rua, cpc.numero, \r\n" + 
					"retira_acentos(cpc.bairro) AS bairro, retira_acentos(cpc.complemento) AS complemento, cpc.cep, cpc.uf, retira_acentos(cpc.cidade) AS cidade,"
					+ " (t.valorfinal*100) AS valor, t.qtdparcelas, cc.cor_botao \r\n" + 
					"FROM transacoes t \r\n" + 
					"INNER JOIN cad_pessoas_clientes cpc ON cpc.idpessoacliente = t.idpessoacliente \r\n" + 
					"INNER JOIN cad_contratos cc ON cc.idcontrato = t.idcontrato \r\n" + 
					"WHERE t.idtransacao = "+idtransacao+";";*/
			/*String sql = "SELECT COALESCE(t.idtransaction_pagarme, 0) AS idtransaction_pagarme, cpc.idpessoacliente, retira_acentos(cpc.nome) as nome, retira_acentos(cpc.email) AS email, cpc.cpf_cnpj, "
					+ "cpc.telefone, cpc.datanascimento::date AS datanascimento, " +
					"retira_acentos(cpc.rua) AS rua, cpc.numero, \r\n" + 
					"retira_acentos(cpc.bairro) AS bairro, retira_acentos(cpc.complemento) AS complemento, cpc.cep, cpc.uf, retira_acentos(cpc.cidade) AS cidade,"
					+ " (t.valorfinal*100) AS valor, t.qtdparcelas, cc.cor_botao \r\n" + 
					"FROM transacoes t \r\n" + 
					"INNER JOIN cad_pessoas_clientes cpc ON cpc.idpessoacliente = t.idpessoacliente \r\n" + 
					"INNER JOIN cad_contratos cc ON cc.idcontrato = t.idcontrato \r\n" + 
					"WHERE t.idtransacao = "+idtransacao+";";*/
			String sql = "SELECT COALESCE(t.idtransaction_pagarme, 0) AS idtransaction_pagarme, t.idevento, retira_acentos(t.nomecliente) as nome, "
					+ " (t.valor*100) AS valor, t.qtdparcelas FROM chadl_transacao t \r\n" + 
					"WHERE t.idtransacao = "+idtransacao+";"; 
			ResultSet res = conexao.executaQueryRolavel(sql);
			
			if (!res.next()) {
				response.getWriter().print("<html><body><h5>Falha ao buscar dados da transação</h5></body></html>");
				return;
			}
			res.beforeFirst();
		
			String revisao = "true";
			
			//TODO puxar informações da tabela cad_pessoas_cliente (se for nulo deixe vazio)
			int idevento = 0;
			String nome = "";
			String email = "";
			String cpf = "cpf";
			String telefone = "telefone";
			String datanascto = "";
			String logradouro = "";
			String endereco_numero = "";
			String cep = "";
			String estado = "";
			String cidade = "";
			String bairro = "";
			String complemento = "";
			String idtransaction_pagarme = "0";
			String cor_botao = "#fa226b";
			
			//Informações de valores da parcela
			int valor = 0;
			int qtdparcelas = 0;
						
			//pega as informações necessárias
			while (res.next()) {
				idevento = res.getInt("idevento");
				nome = (res.getString("nome") != null ? res.getString("nome") : "");
				idtransaction_pagarme = res.getString("idtransaction_pagarme");
				valor = res.getInt("valor");//valor * 100
				qtdparcelas = res.getInt("qtdparcelas");
				cor_botao = "#ff0000";
				//if(res.getString("cor_botao") != null) cor_botao = res.getString("cor_botao");
				
				
								
			}
			
			res.close();
			
			System.out.println("idtransaction_pagarme: "+idtransaction_pagarme);
			if (!idtransaction_pagarme.equals("0")) {
				System.out.println("Transação já se encontra em processamento!");
				response.getWriter().print("<html><body><h5>Transação em processo, verifique o status no painel!</h5></body></html>");
				
				return;
			}
			
			boolean temEndereco = false;
			if (logradouro.length() > 0 && endereco_numero.length() > 0 && cep.length() > 0 && estado.length() > 0 && cidade.length() > 0 
					&& bairro.length() > 0) {
				temEndereco = true;
			}
			
			String customer = "  customer: {\n" + 
					"      external_id: '"+idevento+"',\n" + 
					"      name: '"+nome+"',\n" + 
					"      type: 'individual',\n" + 
					"      country: 'br',\n" + 
					"      email: '"+email+"',\n" + 
					"      documents: [\n" + 
					"        {\n" + 
					"          type: 'cpf',\n" + 
					"          number: '"+cpf+"',\n" + 
					"        },\n" + 
					"      ],\n" + 
					"      phone_numbers: ['"+telefone+"'],\n" + 
					"      birthday: '"+datanascto+"',\n" + 
					"    },";
			if (temEndereco) {
				customer += "shipping: {\n" + 
				"      name: '"+nome+"',\n" + 
				"      fee: 0,\n" + 
				"      delivery_date: '2020-12-25',\n" + 
				"      expedited: true,\n" + 
				"      address: {\n" + 
				"        country: 'br',\n" + 
				"          state: '"+estado+"',\n" +
				"          city: '"+cidade+"',\n" + 
				"          neighborhood: '"+bairro+"',\n" + 
				"          street: '"+logradouro+"',\n" + 
				"          street_number: '"+endereco_numero+"',\n" +
				"          zipcode: '"+cep+"',\n" + 
				"      }\n" + 
				"    }," +
				"  billing: {\n" +  
				"      name: '"+nome+"',\n" + 
				"      address: {\n" + 
				"          street: '"+logradouro+"',\n" +
				"          street_number: '"+endereco_numero+"',\n" +
				"          zipcode: '"+cep+"',\n" +
				"          country: 'br',\n" +
				"          state: '"+estado+"',\n" +
				"          city: '"+cidade+"',\n" +
				"          neighborhood: '"+bairro+"',\n";
				customer += (complemento.length() > 0 ? "          complementary: '"+complemento+"',\n" : "");
				customer += "      },\n" + 
				"    },";				
			}
			

			response.getWriter().print("<html>\n" + 
					"  <head>\n" + 
					"    <!-- SCRIPT PAGAR.ME -->\n" + 
					"    <script src=\"https://assets.pagar.me/checkout/1.1.0/checkout.js\"></script>\n" + 
					"    <style>\n" + 
					"        body{\n" + 
					"          background-color: #282828;\n" + 
					"        }\n" + 
					"\n" + 
					"        .estiloBotaoPagto{\n" + 
					"          background-color: "+cor_botao+";\n" + 
					"          border-color: white;\n" + 
					"          border-style: solid;\n" + 
					"          border-width: 1px;\n" + 
					"        }\n" + 
					"    </style>\n" + 
					"  </head>\n" + 
					"  <body>\n" + 
					"    <script>\n" + 
					"      \n" +  
					"      setTimeout(function() {\n" + 
					"            \n" + 
					"        // inicia a instância do checkout\n" + 
					"        var checkout = new PagarMeCheckout.Checkout({\n" + 
					"          encryption_key: '"+(conexao.isBdTeste() ? FacadePagarMe.APIKEY_CRIP_TEST : FacadePagarMe.APIKEY_CRIP)+"',\n" +
					//"          encryption_key: '"+FacadePagarMe.APIKEY_CRIP_TEST+"',\n" +
					"          success: function(data) {\n" + 
					"            console.log(data);\n" + 
					"          },\n" + 
					"          error: function(err) {\n" + 
					"            console.log(err);\n" + 
					"          },\n" + 
					"          close: function() {\n" + 
					"            console.log('The modal has been closed.');\n" + 
					"          }\n" + 
					"        });             \n" + 
					"\n" + 
					"        checkout.open({\n" + 
					"          amount: "+valor+",\n" + 
					"          maxInstallments: "+qtdparcelas+", \n" + 
					"          defaultInstallments: "+qtdparcelas+", \n" +
					"          minInstallments: "+qtdparcelas+", \n" +
					customer + 
					"          buttonClass: 'estiloBotaoPagto',\n" + 
					"          uiColor: '"+cor_botao+"',\n" + 
					//"		   postbackUrl: \"http://10.0.0.233:8080/ChaDeLingerie/webhookpagarme?idtransacao="+idtransacao+"\",\n" +
					"          postbackUrl: \"https://sistemaagely.com.br:8245/chadelingerie24052021/webhookpagarme?idtransacao="+idtransacao+"\",\n" + 
					"          paymentMethods: 'credit_card',\n" + 
					"          customerData: 'true',\n" +
					"          reviewInformations: '"+revisao+"',\n" +
					"          createToken: 'true',\n" + 
					"                items: [{\n" + 
					"            id: '1',\n" + 
					"            title: 'UP VENDAS'  ,\n" + 
					"            unit_price: "+valor+",\n" + 
					"            quantity: 1,\n" + 
					"            tangible: 'false'\n" + 
					"          }]\n" + 
					"        })\n" + 
					"      }, 500);\n" + 
					"    </script>\n" + 
					"  </body>\n" + 
					"</html>");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("<html><body><h5>Falha ao processar pagamento!</h5></body></html>");
			
		} finally {
			conexao.desconecta();
			conexao = null;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String gerarHashPagamento(int idtransacao) {
		try {
			String plaintext = "ID" + idtransacao + "#chadelingerie";
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			e.printStackTrace();
			return "ERRO";
		}
	}

}
