package pagarme.servicos;

import java.util.List;

import global.dados.Pessoa;
import me.pagar.model.PagarMeException;
import me.pagar.model.Payable;
import me.pagar.model.Recipient;
//import src.pagarme.servicos.String;

public class FacadePagarMe {
	
	/*public static void main(String[] args) {
		try {
			FacadePagarMe facadePagarMe = new FacadePagarMe();
			
			//Cadastra recebedor na pagar.me, exemplo para cadastro de nivel 1
			
			//Maneira de preencher um usuário
			Gson gson = new Gson();
			String stringJson = "{\"usuario\":\"69339034000101\",\"senha\":\"f77d7fe7e2444252f8abc9e2716ebeb9\",\"tiponivel\":\"pagarme\"}";
			Usuario usuario = gson.fromJson(stringJson, Usuario.class);
			CRUDUsuario CRUDUsuario = new CRUDUsuario(new Conexao("atelie", ""));
			String stringUsuario = CRUDUsuario.getUsuario(usuario);
			usuario = gson.fromJson(stringUsuario, Usuario.class);
			usuario.getPessoa().setNome_razaosocial_abrev("FRELITH LTDA");
			facadePagarMe.cadastraRecebedor(usuario.getPessoa());
		} catch (PagarMeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	//Chaves API
	//public static final String APIKEY = "ak_test_zBBMKrWvqGpsnVaSyzwWswOo0uzjgM";
	//public static final String APIKEY_CRIP = "ek_test_IkHZM1n0vKdNDtSX2wLkU9a81IU19b";
	
	public static final String APIKEY_TEST = "ak_test_zBBMKrWvqGpsnVaSyzwWswOo0uzjgM";//Teste
	public static final String APIKEY_CRIP_TEST = "ek_test_IkHZM1n0vKdNDtSX2wLkU9a81IU19b";//Teste
	
	public static final String APIKEY = "ak_live_6UqmV2CKOvLmGlbHioPcPY4FHoH5VN";//Oficial
	public static final String APIKEY_CRIP = "ek_live_PpeVZPsnJVzYcLWN3I9LllS9QRsLPa";//Oficial
	/*
	private final Transacao transacao;
	private final Recebedores recebedores;
	
	public FacadePagarMe () {
		this.transacao = new Transacao(APIKEY);
		this.recebedores = new Recebedores(APIKEY);
	}
	
	/**
	 * {@link https://docs.pagar.me/reference#capturando-uma-transacao-posteriormente}
	 * @param idtransacao_pagarme
	 * @param transacaoReteio
	 * @throws PagarMeException
	 *
	public void enviaSplitTransacao (final int idtransacao_pagarme, final Transacao transacaoReteio) throws PagarMeException, Exception {
		this.transacao.enviaSplitTransacao(idtransacao_pagarme, transacaoReteio);
	}
	
	/**
	 * {@link https://docs.pagar.me/reference#retornando-receb%C3%ADveis}
	 * @param pagarme_idrecebedor
	 * @return
	 * @throws PagarMeException
	 * @throws Exception
	 *
	public List<Payable> retornaRecebiveisPorRecebedor (final String pagarme_idrecebedor) throws PagarMeException, Exception {
		return this.transacao.retornaRecebiveisPorRecebedor(pagarme_idrecebedor);
	}
	
	/**
	 * {@link Documentação: https://docs.pagar.me/reference#criando-um-recebedor}
	 * @param pessoa
	 * @return
	 * @throws PagarMeException
	 * @throws Exception
	 *
	public Pessoa cadastraRecebedor (final Pessoa pessoa) throws PagarMeException, Exception {
		return this.recebedores.cadastraRecebedor(pessoa);
	}
	
	/**
	 * {@link Documentação: https://docs.pagar.me/reference#retornando-todos-os-recebedores}
	 * @return
	 * @throws PagarMeException
	 * @throws Exception
	 *
	public List<Recipient> listaRecebedores () throws PagarMeException, Exception {
		return this.recebedores.listaRecebedores();
	}*/
}
