package global.dados;

import java.io.File;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

public class UploadArquivo extends UploadAction {
	
	private static final long serialVersionUID = 1L;
	Hashtable<String, File> arquivosRecebidos = new Hashtable<String, File>();
	Hashtable<String, String> tipoConteudoRecebido = new Hashtable<String, String>();

	public String upload(HttpServletRequest request, FileItem item, String nomeArquivo, String caminhoaux, String tipoArquivo) throws UploadActionException {
		String response = "";
		
		if (item.isFormField() == false) {
			try {
    		
				String tipoarquivo = item.getContentType().split("/")[1].replace("jpeg", "jpg");
    		String caminho = caminhoaux;
    		
    		caminho = "/var/lib/tomcat8/webapps/"+(caminho.isEmpty() ? "agelyArquivos/" : caminho);
    		
    		File txtServidor = new File("/home/agely/servidor.txt"); // /home/agely  /home/sauron/Documentos/projetos/fotos_tws/www/
    		
    		if (!txtServidor.exists()) {
    			if (System.getProperty("os.name").equals("Linux")) {
    				caminho = new File("").getAbsolutePath() + "//" + caminho;
    			} else {
    				caminho = new File("").getAbsolutePath() + "\\" + caminho;
    			}
    		}
		
    		File diretorio = new File(caminho);
				if (!diretorio.exists()) {
					diretorio.mkdirs();
				}
				
				item.getName().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
				
				File arquivo = new File(caminho+nomeArquivo+"."+tipoarquivo);
				while (arquivo.exists()) {
					arquivo.delete();
					arquivo = new File(caminho+nomeArquivo+"."+tipoarquivo);
				}
				
				item.write(arquivo);

				arquivosRecebidos.put(item.getFieldName(), arquivo);
				tipoConteudoRecebido.put(item.getFieldName(), item.getContentType());

				response = tipoArquivo+"?file=" + arquivo.getName();
				
			} catch (Exception e) {
				throw new UploadActionException(e);
			}
		}
		
		return response;
	}
}
