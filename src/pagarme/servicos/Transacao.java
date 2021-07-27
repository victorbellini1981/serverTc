package pagarme.servicos;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import conexao.Conexao;
import global.crud.CRUDcad_transacoes;
import global.dados.Retorno;
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Payable;
import me.pagar.model.SplitRule;
import me.pagar.model.Transaction;
import me.pagar.model.filter.PayableQueriableFields;


public class Transacao {
	/*public static void main(String[] args) {
		try {
			Transacao transacaoPagarme = new Transacao(FacadePagarMe.APIKEY);
			
			String sql = "SELECT * FROM transacoes WHERE idtransacao = "+142+";";
			
			Conexao con = new Conexao("atelie", "");
			ResultSet res = con .executaQuery(sql);
			Transacao transacao = (Transacao) con.transformResultSetToObject(Transacao.class, res, false, true);

			CRUDcad_transacoes cudTransacao = new CRUDcad_transacoes(new Conexao("atelie", ""));
			Retorno ret = cudTransacao.getTransacao(transacao);
			System.out.println(ret.getSituacao());
			
			transacaoPagarme.enviaSplitTransacao(10474819, transacao);
			
			/*Transacao transacaoPagarme = new Transacao(FacadePagarMe.APIKEY);
			transacaoPagarme.retornaRecebiveisPorRecebedor("re_ckfqxob3807rx0g9t17bk9tt9");*/
		/*} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String APIKEY;
	
	public Transacao (final String APIKEY) {
		this.APIKEY = APIKEY;
	}
	
	/**
	 * {@link https://docs.pagar.me/reference#capturando-uma-transacao-posteriormente}
	 * @param idtransacao_pagarme
	 * @param transacaoReteio
	 * @throws PagarMeException
	 *
	public void enviaSplitTransacao(final int idtransacao_pagarme, final Transacao transacaoReteio) throws PagarMeException, Exception {
		System.out.println("Enviar Split Transação: " + idtransacao_pagarme);
		System.out.println("Valor: " + (transacaoReteio.getValorfinalCentavos()/100.0));
		PagarMe.init(APIKEY);
		int saldo = 0;

		System.out.println("find" + idtransacao_pagarme);
		Transaction tx = new Transaction().find(idtransacao_pagarme);

		tx.setSplitRules(new ArrayList<SplitRule>());
		
		for (int i = 0; i < transacaoReteio.getTransacaoRateio().size(); i++) {
			SplitRule split = new SplitRule();
			int amount = transacaoReteio.getTransacaoRateio().get(i).getValorCentavos();
			
			/*
			 * Último registro
			 * Sendo esse cliente "UpVendas", então deve se jogar o valor a receber 
			 * para a pessoa que mantem a conta "AGELY", está responsável por pagar 
			 * o cliente "UpVendas" com d+1.
			 *
			if ((i+1) == transacaoReteio.getTransacaoRateio().size()) {
				amount = transacaoReteio.getValorfinalCentavos() - saldo;
				split.setAmount(amount);
				split.setRecipientId(getRecebedorContaAgely());//chave da conta da agely
				split.setLiable(transacaoReteio.getTransacaoRateio().get(i).isResponsavel_pelo_chargeback());
				split.setChargeProcessingFee(true);
				split.setChargeRemainder(true);
			} else {			
				split.setAmount(amount);
				saldo += amount;
				split.setRecipientId(transacaoReteio.getTransacaoRateio().get(i).getPagarme_idrecebedor());
				split.setLiable(transacaoReteio.getTransacaoRateio().get(i).isResponsavel_pelo_chargeback());
				split.setChargeProcessingFee(false);
				split.setChargeRemainder(false);
			}
			
			tx.getSplitRules().add(split);
			
			System.out.println("Split ["+transacaoReteio.getTransacaoRateio().get(i).getNivel()+"]");
			System.out.println("Valor: "+(split.getAmount()/100.0));
			System.out.println("np_repaassa_valor_para_nivel: "+transacaoReteio.getTransacaoRateio().get(i).isRepaassa_valor_para_nivel());
		}
		
		//envia captura 
		System.out.println("captura");
		tx.capture(transacaoReteio.getValorfinalCentavos());
		System.out.println("Fim da captura");
	}
	
	private String getRecebedorContaAgely() {
		Conexao con = new Conexao("atelie", "");
		try {
			String sql = "select pagarme_idrecebedor from cad_pessoas WHERE idpessoa = 1;";
			ResultSet resultSet = con.executaQuery(sql);
			String pagarme_idrecebedor = null;
			while (resultSet.next()) {
				pagarme_idrecebedor = resultSet.getString("pagarme_idrecebedor");
			}
			resultSet.close();
			
			return pagarme_idrecebedor;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		} finally {
			con.desconecta();
			con = null;
		}
	}

	/**
	 * {@link https://docs.pagar.me/reference#retornando-receb%C3%ADveis}
	 * @param pagarme_idrecebedor
	 * @return
	 * @throws PagarMeException
	 * @throws Exception
	 *
	public List<Payable> retornaRecebiveisPorRecebedor (final String pagarme_idrecebedor) throws PagarMeException, Exception {
		PagarMe.init(APIKEY);
		
		Integer count = 1000;
		Integer page = 0;
		PayableQueriableFields payableFilter = new PayableQueriableFields();
		payableFilter.recipientIdEquals(pagarme_idrecebedor);
		List<Payable> recebiveis = new ArrayList<Payable>();
		Collection<Payable> payables = null;
		do{
			page++;//pagina atual
			
			payables = new Payable().findCollection(count, page, payableFilter);
			
			for (Payable payable : payables) {
				recebiveis.add(payable);
			}
			
		} while (payables.size() == count);
		
		return recebiveis;
	}*/
	
}
