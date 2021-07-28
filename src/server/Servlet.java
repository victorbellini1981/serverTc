package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import global.dados.Pessoa;
import global.dados.UploadArquivo;
import global.dados.Usuario;
import global.util.UploadServlet;
import global.util.Util;
import gwtupload.server.exceptions.UploadActionException;
import server.aplicativo.*;



public class Servlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {



		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();

		String serverName = request.getServerName();
				
		
//		String serverName = "127.0.0.1";

		// Proteje contra SQL Inject
		HashMap<String, String> parametros = new HashMap<>();
		Map<String, String[]> allParameters = request.getParameterMap();
		for (String par : allParameters.keySet()) {
			parametros.put(par, request.getParameter(par).replace("'", "`"));
		}
		
		

		// Lê os parametros passados
		String metodo = parametros.get("metodo");
		metodo = metodo == null ? "index" : metodo;

		System.out.println("METODO -> " + metodo + " | Banco -> " + serverName);

		// APLICATIVO -------------------------------------
		if (metodo.equals("GetVersaoMobile")) {
			retornaObj(out, gson.toJsonTree("teste"));
		}
		else if (metodo.equals("PostUsuario")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			retornaObj(out , gson.toJsonTree(crud_usuario.postUsuario(usuario)));
		}
		else if (metodo.equals("GetLogin")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			retornaObj(out , gson.toJsonTree(crud_usuario.getLogin(usuario)));
		}		
		else if (metodo.equals("PostPessoa")) {
			CRUDpessoa crud_pessoa = new CRUDpessoa(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Pessoa pessoa = gson.fromJson(parametros.get("obj"), Pessoa.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.postPessoa(pessoa)));
		}
		else if (metodo.equals("GetPessoa")) {
			CRUDpessoa crud_pessoa = new CRUDpessoa(serverName);
			Pessoa pessoa = gson.fromJson(parametros.get("obj"), Pessoa.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.getPessoa(pessoa)));
		}
		else if (metodo.equals("GetPessoaNome")) {
			CRUDpessoa crud_pessoa = new CRUDpessoa(serverName);
			Pessoa pessoa = gson.fromJson(parametros.get("obj"), Pessoa.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.getPessoaNome(pessoa)));
		}
		
		else if(metodo.equals("Upload")) {
			String retorno = uploadArquivos(request, parametros);
			out.println(retorno);
		}
		else if (metodo.equals("GetSenha")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			retornaObj(out, gson.toJsonTree(crud_usuario.redefinirSenha(usuario)));
		}
		else if (metodo.equals("SetSenha")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			String md5 = parametros.get("codigo");
			md5 = md5.replace("\"", "");
			retornaObj(out, gson.toJsonTree(crud_usuario.updateSenha(usuario, md5)));
		}	
		else if(metodo.equals("SetFotoEvento")) {
        	JsonElement jsonObj = null;
			try {
				DiskFileItemFactory diskFactory = new DiskFileItemFactory(); 
				ServletFileUpload upload = new ServletFileUpload(diskFactory); 
				List<FileItem> items;
				boolean temArquivo = false; //Flagp para upload
				items = upload.parseRequest(request);
				for(FileItem item : items) {
					//Verifica se Ã© campo de formulÃ¡rio
					if (item.isFormField()) {
						
						switch (item.getFieldName()) {
						case "idevento":
							//evento.setIdevento(Integer.parseInt(item.getString()));
							break;
						}
	                } else {
	                	//Verifica se tem foto para fazer upload
	                    if(item.getSize()!=0) {
	                    	temArquivo = true;
	                    }
	                }         		            
				}
				String arquivos = "";
				if(temArquivo) {
					UploadServlet up = new UploadServlet();
					
					arquivos = up.executeAction(request, items);
					
					for (String arq : arquivos.split(";")) {
						//evento.getFotos().add(arq);
					}
				}								
				out.print(arquivos);
			} catch (FileUploadException e) {
				out.println("Erro ao carregar arquivo");
				e.printStackTrace();
				jsonObj = gson.toJsonTree("erro - " + e.getMessage());
			} catch (Exception e) {
				out.println("Erro ao carregar arquivo");
				e.printStackTrace();
				jsonObj = gson.toJsonTree("erro - " + e.getMessage());
			}	
			
			
		}

		// -------------------------------------------------
		else {
			retornaObj(out, gson.toJsonTree("Parâmetros Inválidos!"));
		}

		out.close();

	}


	
	public void retornaObj(PrintWriter out, JsonElement jsonElement) {
		JsonObject myObj = new JsonObject();

		JsonElement jsonObj = jsonElement;
		myObj.add("dados", jsonObj);
		out.println(jsonObj.toString());
	}
	
	private String uploadArquivos(HttpServletRequest request, HashMap<String, String> data) {
		String retorno = "";
		UploadArquivo uploadArquivo = new UploadArquivo();
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			List<FileItem> items;
			items = upload.parseRequest(request);

			for (FileItem item : items) {
				if (item.isFormField()) {
				} else {
					if (item.getSize() != 0) {
						retorno = uploadArquivo.upload(request, item, data.get("nomeArquivo"), data.get("caminho"), data.get("tipo"));
					}
				}
			}
		} catch (FileUploadException e) {
			retorno = "FileUploadException: "+e.getMessage();
			e.printStackTrace();
		} catch (UploadActionException e) {
			retorno = "UploadActionException: "+e.getMessage();
			e.printStackTrace();
		}
		return retorno;
	}
	
		
	}
