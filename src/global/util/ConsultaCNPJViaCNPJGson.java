package global.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import global.dados.ConsultaCNPJ;


public class ConsultaCNPJViaCNPJGson {
	public ConsultaCNPJViaCNPJGson() {
		
	}
	
	public ConsultaCNPJ consultar(String cnpj) throws Exception{
		//Substitui qualquer tipo de pontuação
		cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "");
		
		if(!cnpj.equals("")) {
			URL url = new URL("http://receitaws.com.br/v1/cnpj/"+cnpj+"/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			con.disconnect();
			
			Gson g = new Gson();
			return g.fromJson(content.toString(), ConsultaCNPJ.class);
		} else {
			return null;
		}
	}
}
