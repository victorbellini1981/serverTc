package pagarme.servicos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import global.dados.Pessoa;
import me.pagar.BankAccountType;
import me.pagar.model.BankAccount;
import me.pagar.model.BankAccount.DocumentType;
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Recipient;
import me.pagar.model.Recipient.TransferInterval;


public class Recebedores {
	private String APIKEY;
	
	public Recebedores (final String APIKEY) {
		this.APIKEY = APIKEY;
	}
	
	/**
	 * {@link Documentação: https://docs.pagar.me/reference#criando-um-recebedor}
	 * @param pessoa
	 * @return idrecebedor na pagar.me
	 * @throws PagarMeException
	 * @throws Exception
	 */
	public Pessoa cadastraRecebedor (final Pessoa pessoa) throws PagarMeException, Exception {
		PagarMe.init(APIKEY);
		
		final Recipient recipient = new Recipient();
		
		recipient.setTransferInterval(TransferInterval.DAILY);
		//recipient.setTransferDay(transferDay);
		recipient.setTransferEnabled(true);
		//recipient.setBankAccountId(bankAccountId);
		
		final BankAccount bankAccount = new BankAccount();
		/*bankAccount.setBankCode(pessoa.getCb_codigobanco());
		bankAccount.setAgencia(pessoa.getCb_agencia());
		//bankAccount.setAgenciaDv(agenciaDv);
		bankAccount.setConta(pessoa.getCb_numeroconta());
		bankAccount.setType((pessoa.getCb_tipoconta() == 1 ? BankAccountType.CORRENTE : pessoa.getCb_tipoconta() == 2 ? BankAccountType.POUPANCA : pessoa.getCb_tipoconta() == 3 ? BankAccountType.CORRENTE_CONJUNTA : pessoa.getCb_tipoconta() == 4 ? BankAccountType.POUPANCA_CONJUNTA : null));
		bankAccount.setContaDv(pessoa.getCb_numerocontadv());
		bankAccount.setDocumentType((pessoa.getTipo().toUpperCase().equals("PF") ? DocumentType.CPF : DocumentType.CNPJ));
		bankAccount.setDocumentNumber((pessoa.getTipo().toUpperCase().equals("PF") ? pessoa.getCpf() : pessoa.getCnpj()));
		bankAccount.setLegalName(pessoa.getNome_razaosocial_abrev());*/
		
		recipient.setBankAccount(bankAccount);
		//recipient.setAnticipatableVolumePercentage(anticipatableVolumePercentage);
		recipient.setAutomaticAnticipationEnabled(false);
		
		final Recipient ret_Recipient = recipient.save();
		
		//pessoa.setPagarme_idrecebedor(ret_Recipient.getId());
		
		return pessoa;
	}
	
	/**
	 * {@link Documentação: https://docs.pagar.me/reference#retornando-todos-os-recebedores}
	 * @return recebedores
	 * @throws PagarMeException
	 * @throws Exception
	 */
	public List<Recipient> listaRecebedores () throws PagarMeException, Exception {
		PagarMe.init(APIKEY);
		
		int count = 100;
		int page = 1;
		
		List<Recipient> recebedores = new ArrayList<Recipient>();
		
		Collection<Recipient> recipients = new ArrayList<Recipient>();
		do{
			recipients = new Recipient().findCollection(count, page);
			
			for (Recipient recipient : recipients) {
				recebedores.add(recipient);
			}
			
			page++;
		} while (recipients.size() == count);
		
		return recebedores;
	}
}
