package global.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

import conexao.Conexao;

public class LogErros {
	
	public void gravarLog(String log) {
		FileWriter arquivo;  
        
		System.out.println(log);
		
		String pasta = "";
		File f = new File("");
		
		//pega a data atual
		Calendar cal = Calendar.getInstance();  
        String day = String.valueOf(cal.get(Calendar.DATE));  
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);  
        String year = String.valueOf(cal.get(Calendar.YEAR));  
		if (day.length()==1) {
			day += "0" + day;
		}
		if (month.length()==1) {
			month += "0" + month;
		}
        
        try {  
        	String nomeArquivo = pasta + "log/Log"+year+month+day+".txt";
        	

        	
            arquivo = new FileWriter(new File(nomeArquivo),true);  
            arquivo.write( new Date() + " - ");
            arquivo.write(log);
            arquivo.write(13);
            arquivo.write(10);
            arquivo.close();  
        } catch (Exception e) {  
        	System.out.println("Pasta Atual: " + f.getAbsolutePath());
            e.printStackTrace();  
        }  
	}

	public void gravarLogErro(Exception ex, String serverName,  String erro) {
		FileWriter arquivo;  
        
		System.out.println(erro);
		if (ex!=null) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		String pasta = "";
		File f = new File("");
		//System.out.println("pasta: " + f.getAbsolutePath());
		//pasta: /var/www/vhosts/agely.com.br/appservers/apache-tomcat-7x/bin
		//if (f.getAbsolutePath().startsWith("/var/www/vhosts/agely.com.br")) {
		//	pasta = "/var/lib/tomcat7/webapps/agely/";
		//}

		//pega a data atual
		Calendar cal = Calendar.getInstance();  
        String day = String.valueOf(cal.get(Calendar.DATE));  
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);  
        String year = String.valueOf(cal.get(Calendar.YEAR));  
		if (day.length()==1) {
			day += "0" + day;
		}
		if (month.length()==1) {
			month += "0" + month;
		}
        
        try {  
        	String nomeArquivo = pasta + "logerros/LogErros"+year+month+day+".txt";
        	
        	if (ArrayUtils.indexOf(new String[] {"127.0.0.1", "teste.sistema.agely.com.br"}, serverName.toLowerCase()) != -1) {
        		nomeArquivo = pasta + "logerros/TesteLogErros"+year+month+day+".txt";
        	}
        	
            arquivo = new FileWriter(new File(nomeArquivo),true);  
            arquivo.write( new Date() + " - ");
            arquivo.write(erro);
            arquivo.write(13);
            arquivo.write(10);
            arquivo.close();  
        } catch (Exception e) {  
        	System.out.println("Pasta Atual: " + f.getAbsolutePath());
            e.printStackTrace();  
        }  
	}
}
