package global.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import global.dados.Email;


public class EnviaEmail {
	
	private Email email;
	
	public EnviaEmail(Email email) {
		this.email = email;		
	}
	
	public void run() {
		
		
		/*email.setServidor("smtp.gmail.com");
		email.setEnderecoEmailRemetente("andreprieto.agely@gmail.com");
		email.setSenha("A&mxyz18");
		
		email.setServidor("smtp.gmail.com");
		email.setEnderecoEmailRemetente("joaovictorbellini33@gmail.com");
		email.setSenha("Jvmb1981");*/
		
		email.setServidor("mail.agely.com.br");
		email.setEnderecoEmailRemetente("contato@agely.com.br");
		email.setSenha("a1g2e3l4y5");
		
		HtmlEmail simpleEmail = new HtmlEmail();

		try {
        	simpleEmail.setDebug(true);  
        	simpleEmail.setHostName(email.getServidor());  
        	simpleEmail.setAuthentication(email.getEnderecoEmailRemetente(), email.getSenha());  
        	simpleEmail.setSSL(false);  
        	simpleEmail.setSmtpPort(587);
        	/*simpleEmail.setSSL(true);  
        	simpleEmail.setSmtpPort(465);*/
        	simpleEmail.addTo(email.getEnderecoEmailDestinatario()); 
        	simpleEmail.setFrom(email.getEnderecoEmailRemetente()); 
        	simpleEmail.setSubject(email.getAssunto());  
        	simpleEmail.setMsg(email.getMensagem());
        	simpleEmail.setCharset("utf-8");
        	simpleEmail.send();  
	        
        } catch (EmailException e) {  
        	LogErros log = new LogErros();
			log.gravarLogErro(e, "Prefeitura", "Erro " + e.getMessage());
        }
	}
	
}