package global.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import global.dados.ConsultaCEP;


/**
 * 
 * @author Agely - Caio
 * Classe que faz a consulta de endereço passando um CEP como paramêtro
 * Utilizou a base de dados e serviço da ViaCEP link:https://viacep.com.br/
 */
public class ConsultaCEPViaCEPGson {
	
	public ConsultaCEPViaCEPGson () {
		
	}
	
	/**
	 * 
	 * @param cep
	 * @return Informações do endereço [Classe ConsultaCEP] referente ao cep
	 * @throws Exception
	 */
	public ConsultaCEP consultaCEPViaCEPGson (String cep) throws Exception {
		cep = cep.replace(".", "").replace("-", "").replace("/", "");
		URL url = new URL("http://viacep.com.br/ws/"+cep+"/json/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		//con.setRequestProperty("Content-Type", "application/json");
		//con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			//System.out.println(inputLine);
		    content.append(inputLine);
		}
		in.close();
		con.disconnect();
		
		Gson g = new Gson();
		return g.fromJson(content.toString(), ConsultaCEP.class);
	}
	
}
