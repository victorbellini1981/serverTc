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

import global.dados.Atividade;
import global.dados.Batimento;
import global.dados.Paciente;
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
		else if (metodo.equals("PostUsuarioP")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			retornaObj(out , gson.toJsonTree(crud_usuario.postUsuarioP(usuario)));
		}
		else if (metodo.equals("GetLoginP")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			retornaObj(out , gson.toJsonTree(crud_usuario.getLoginP(usuario)));
		}
		else if (metodo.equals("GetDadosPaciente")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			retornaObj(out , gson.toJsonTree(crud_usuario.getDadosPaciente(usuario)));
		}	
		else if (metodo.equals("PostPaciente")) {
			CRUDpaciente crud_pessoa = new CRUDpaciente(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Paciente pessoa = gson.fromJson(parametros.get("obj"), Paciente.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.postPaciente(pessoa)));
		}
		else if (metodo.equals("GetPaciente")) {
			CRUDpaciente crud_pessoa = new CRUDpaciente(serverName);
			Paciente pessoa = gson.fromJson(parametros.get("obj"), Paciente.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.getPaciente(pessoa)));
		}
		else if (metodo.equals("GetPacienteNome")) {
			CRUDpaciente crud_pessoa = new CRUDpaciente(serverName);
			Paciente pessoa = gson.fromJson(parametros.get("obj"), Paciente.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.getPacienteNome(pessoa)));
		}
		else if (metodo.equals("PostAtividade")) {
			CRUDatividade crud_atividade = new CRUDatividade(serverName);
			Atividade atividade = gson.fromJson(parametros.get("obj"), Atividade.class); 
			retornaObj(out , gson.toJsonTree(crud_atividade.postAtividade(atividade)));
		}
		else if (metodo.equals("UpAtividade")) {
			CRUDatividade crud_atividade = new CRUDatividade(serverName);
			Atividade atividade = gson.fromJson(parametros.get("obj"), Atividade.class); 
			retornaObj(out , gson.toJsonTree(crud_atividade.update(atividade)));
		}		
		else if (metodo.equals("GetBatimentos")) {
			CRUDbatimento crud_batimento = new CRUDbatimento(serverName);
			Batimento batimento = gson.fromJson(parametros.get("obj"), Batimento.class);
			retornaObj(out , gson.toJsonTree(crud_batimento.getBatimentos(batimento)));
		}
		else if (metodo.equals("GetAtividades")) {
			CRUDatividade crud_atividade = new CRUDatividade(serverName);
			Atividade atividade = gson.fromJson(parametros.get("obj"), Atividade.class); 
			retornaObj(out , gson.toJsonTree(crud_atividade.getAtividades(atividade)));
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
