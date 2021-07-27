package pagarme;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import conexao.Conexao;
import me.pagar.model.PagarMe;
import me.pagar.model.SplitRule;
import me.pagar.model.Transaction;
import pagarme.servicos.FacadePagarMe;
import pagarme.servicos.Transacao;


/**
 * Servlet implementation class WebHookPagarme
 */
@WebServlet("/webhookpagarme")
public class WebHookPagarme extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FacadePagarMe facadePagarMe = new FacadePagarMe();//Classe padrão para os serviços pagarme
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebHookPagarme() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("WebHook Pagarme");
		
		PrintWriter out = response.getWriter();

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setHeader("Access-Control-Allow-Credentials", "true");


		/* proteje contra SQL Inject */
		HashMap<String, String> data = new HashMap<>();
		Map<String, String[]> allParameters = request.getParameterMap();
		String dados = "";
		for (String par : allParameters.keySet()) {
			data.put(par, request.getParameter(par).replace("'", "`"));
			
			if (dados.length()>0) {
				dados+= "&";
			}
			dados += par + "=" + request.getParameter(par);
		}

		boolean bdTeste = data.get("bdteste") == null ? false : Boolean.parseBoolean(data.get("bdteste"));
		String serverName = bdTeste ? "127.0.0.1" : "oficial";
		
		 //String reader = request.getReader();
		 String requestData = request.getReader().lines().collect(Collectors.joining());
		 requestData+=" | " + dados;
		 
		 System.out.println("requestData = "+requestData);
		
//		serverName = null;

//		serverName = "127.0.0.1";
//		String serverName = request.getServerName();

		String contextPath = getServletContext().getContextPath() + "/";

		//System.out.println("CONTEXTO: " + contextPath);
		//System.out.println("*************************************************");
		//System.out.println(requestData);
		//System.out.println("*************************************************");

//		contextPath = getServletContext().getRealPath("/")+"/";   

		Conexao conexao = new Conexao("duzani", serverName);
		
		try {

			String sql = "INSERT INTO log_acessos(data, idpessoa, tela, parametros) "
					+ "VALUES (now(),?,?,?)";
			PreparedStatement pst = conexao.sqlPreparada(sql);
			pst.setObject(1, 2210);
			pst.setString(2, "webhook");
			pst.setString(3, requestData);
			conexao.executaUpdate(pst.toString()).equals("ok");
			pst.close();
			
			//pega os dados transação
			int idtransacao = Integer.parseInt(data.get("idtransacao")); //id da transação do BD Atelie
			int idtransacao_pagarme = Integer.parseInt(data.get("id")); //id da transação da pagarme
			int valor = Integer.parseInt(data.get("transaction[amount]"));
			String status = data.get("transaction[status]"); //authorized
			
			//Informações para atualização de dados
			String nome = new String(data.get("transaction[customer][name]").getBytes("ISO-8859-1"));
			String email = data.get("transaction[customer][email]");
			//String tipo = data.get("transaction[customer][documents][0][type]");
			String cpf = data.get("transaction[customer][documents][0][number]");
			String logradouro = new String(data.get("transaction[billing][address][street]").getBytes("ISO-8859-1"));
			String complemento = new String(data.get("transaction[billing][address][complementary]").getBytes("ISO-8859-1"));
			String numero = data.get("transaction[billing][address][street_number]");
			String bairro = new String(data.get("transaction[billing][address][neighborhood]").getBytes("ISO-8859-1"));
			String cidade = new String(data.get("transaction[billing][address][city]").getBytes("ISO-8859-1"));
			String estado = data.get("transaction[billing][address][state]");
			String cep = data.get("transaction[billing][address][zipcode]");
			String datanascimento = data.get("transaction[customer][birthday]");
			
			System.out.println("idtransacao: " + idtransacao);
			System.out.println("idtransaction: " + idtransacao_pagarme);
			System.out.println("nome: " + nome);
			System.out.println("valor: " + valor);
			System.out.println("status: " + status);
			
			//se a transação estiver autorizada, atualiza as informações do cliente pagante e faz a captura
			if (status.toLowerCase().equals("authorized")) {
				//atualizar cadastro do cad_pessoa_cliente
				/*sql = "UPDATE public.cad_pessoas_clientes SET "
						+ "nome = '"+nome+"', "
						+ "cpf_cnpj = '"+cpf.replace(" ", "")+"', "
						//+ "datanascimento = '"+datanascimento+"', "
						+ "email = '"+email+"', "
						+ "cep = '"+cep+"', "
						+ "rua = '"+logradouro+"', "
						+ "numero = '"+numero+"', "
						+ "complemento = '"+complemento+"', "
						+ "bairro = '"+bairro+"', "
						+ "cidade = '"+cidade+"', "
						+ "uf = '"+estado+"' \r\n" + 
						"WHERE idpessoacliente = (SELECT idpessoacliente FROM transacoes WHERE idtransacao = "+idtransacao+");";
				conexao.executaUpdate(sql);*/
				
				//deve se fazer a captura quando receber essa chamada com o status da condição
				//verifica se já foi capturado
				sql =  "select idtransaction_pagarme "
						+ "from chadl_transacao WHERE idtransacao= "+idtransacao+""; 
				ResultSet res = conexao.executaQuery(sql);
				boolean capturar = true;
				if (res.next()) {
					if (res.getString("idtransaction_pagarme")==null) {
						capturar = true;
					}
				}
				res.close(); 
				if (capturar) {
					//Maneira simples de gerar o objeto com as chamada já existentes
				/*	objetos.Transacao transacao = null;
					sql = "SELECT * FROM transacoes WHERE idtransacao = "+idtransacao+";";
					res = conexao.executaQuery(sql);
					transacao = (objetos.Transacao) conexao.transformResultSetToObject(objetos.Transacao.class, res, false, true);
					CRUDTransacao cudTransacao = new CRUDTransacao(new Conexao("atelie", serverName));
					Retorno ret = cudTransacao.getTransacaoReateio(transacao);
					
					if (ret.getSituacao().equals(RetornoStaus.SUCCESS.getDescricao())) {//caso consiga gerar o rateio corretamente faz a capture
						facadePagarMe.enviaSplitTransacao(idtransacao_pagarme, (objetos.Transacao) ret.getObj());
					} else {
						throw new Exception("Falha ao gerar dados para captura idtransacao ["+idtransacao+"]");
					}
				} else {
					throw new Exception("Falha ao gerar captura idtransacao ["+idtransacao+"]");
				}
			} else if (status.toLowerCase().equals("paid")) {//após a authorized se faz a captura e o webhook é chamado novamente passando a situação paid -> pago
				//Com o pagamento feito se faz o rateio correto separando a parte do cliente "UpVendas" cliente gestor "Agely"
			/*	CRUDTransacao cudTransacao = new CRUDTransacao(conexao);
				Retorno ret = cudTransacao.salvaTransacaoReteio(idtransacao);
				if (!ret.getSituacao().equals(RetornoStaus.SUCCESS.getDescricao())) {
					throw new Exception("Falha ao salvar transacao rateio idtransacao ["+idtransacao+"]");
				}*/
				}
			}
			//Atualiza o status da transação
			//pega o status atual
			/*int idstatus = 1;
			sql =  "select idstatus from transacoes_status WHERE idpagarme= '"+status+"'"; 
			ResultSet res = conexao.executaQuery(sql);
			if (res.next()) {
				idstatus = res.getInt("idstatus");
			}
			res.close();*/
			
			sql = "UPDATE chadl_transacao "
					+ " SET idtransaction_pagarme = " + idtransacao_pagarme
					//+ " idstatus = " + idstatus + " "
					+ " WHERE idtransacao = " + idtransacao;
			conexao.executaUpdate(sql);
			
			PagarMe.init(FacadePagarMe.APIKEY);
			
			Transaction tx = new Transaction().find(idtransacao_pagarme);
			
			tx.setSplitRules(new ArrayList<SplitRule>());
			
			SplitRule split = new SplitRule();
			
			split.setAmount(valor);
			//split.setRecipientId("re_ckffaxi0n0hd3sr6egzcl9e7g");//teste re_ckffaxi0n0hd3sr6egzcl9e7g Agely
			split.setRecipientId("re_ckffasfj608zwsr7c09u0gg1l");  //oficial re_ckffasfj608zwsr7c09u0gg1l                
			split.setLiable(true);
			split.setChargeProcessingFee(true);
			split.setChargeRemainder(true);
			
			tx.getSplitRules().add(split);
			tx.capture(valor);
			
			
			out.println("ok");
		} catch (Exception e) {
			e.printStackTrace();
			
		//	log.LogErros log = new log.LogErros();
		//	log.gravarLogErro(e, "Ateliê", "Erro " + e.getMessage());
		//	
			out.println("ok");
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

}
