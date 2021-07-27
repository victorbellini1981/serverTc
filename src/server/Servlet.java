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

import global.dados.Album;
import global.dados.Consultora;
import global.dados.Convidado;
import global.dados.Cupom;
import global.dados.Curtida;
import global.dados.Email;
import global.dados.Evento;
import global.dados.Pessoa;
import global.dados.Presente;
import global.dados.Recado;
import global.dados.Resgate;
import global.dados.TipoResgate;
import global.dados.Transacao;
import global.dados.Transferencia;
import global.dados.Lingerie;
import global.dados.UploadArquivo;
import global.dados.Usuario;
import global.util.EnviaEmail;
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
		else if (metodo.equals("GetProdutos")) {
			CRUDproduto produtos = new CRUDproduto(serverName);
			retornaObj(out, gson.toJsonTree(produtos.getProdutos()));
		}
		else if (metodo.equals("GetBancos")) {
			CRUDbanco bancos = new CRUDbanco(serverName);
			retornaObj(out, gson.toJsonTree(bancos.getBancos()));
		}
		else if (metodo.equals("GetEmpresas")) {
			CRUDempresa empresas = new CRUDempresa(serverName);
			retornaObj(out, gson.toJsonTree(empresas.getEmpresas()));
		}
		else if (metodo.equals("GetProduto")) {
			CRUDproduto produtos = new CRUDproduto(serverName);
			String referencia = parametros.get("referencia");
			retornaObj(out, gson.toJsonTree(produtos.getProduto(referencia)));
		}
		else if (metodo.equals("GetCidades")) {
			CRUDcidade cidades = new CRUDcidade(serverName);
			retornaObj(out, gson.toJsonTree(cidades.getCidades()));
		}
		else if (metodo.equals("GetCidade")) {
			CRUDcidade cidades = new CRUDcidade(serverName);
			String estado = parametros.get("estado");
			String cidade = parametros.get("cidade");
			retornaObj(out, gson.toJsonTree(cidades.getCidade(estado, cidade)));
		}
		else if (metodo.equals("PostUsuario")) {
			CRUDusuario crud_usuario = new CRUDusuario(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Usuario usuario = gson.fromJson(parametros.get("obj"), Usuario.class); 
			retornaObj(out , gson.toJsonTree(crud_usuario.postUsuario(usuario)));
		}
		else if (metodo.equals("PostTransacao")) {
			CRUDtransacao crud_transacao = new CRUDtransacao(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Transacao transacao = gson.fromJson(parametros.get("obj"), Transacao.class); 
			retornaObj(out , gson.toJsonTree(crud_transacao.postTransacao(transacao)));
		}
		else if (metodo.equals("Finalizacao")) {
			CRUDtransacao crud_transacao = new CRUDtransacao(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Transacao transacao = gson.fromJson(parametros.get("obj"), Transacao.class); 
			retornaObj(out , gson.toJsonTree(crud_transacao.finalizacao(transacao)));
		}
		else if (metodo.equals("CarregaMeta")) {
			CRUDlingerie crud_lingerie = new CRUDlingerie(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Lingerie lingerie = gson.fromJson(parametros.get("obj"), Lingerie.class); 
			retornaObj(out , gson.toJsonTree(crud_lingerie.carregaMeta(lingerie)));
		}
		else if (metodo.equals("CarregaMetaAtingida")) {
			CRUDpresente crud_presente = new CRUDpresente(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Presente presente = gson.fromJson(parametros.get("obj"), Presente.class); 
			retornaObj(out , gson.toJsonTree(crud_presente.carregaMetaAtingida(presente)));
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
		else if (metodo.equals("GetPessoaEmail")) {
			CRUDpessoa crud_pessoa = new CRUDpessoa(serverName);
			Pessoa pessoa = gson.fromJson(parametros.get("obj"), Pessoa.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.getPessoaEmail(pessoa)));
		}
		else if (metodo.equals("VerPessoaFaceGoogle")) {
			CRUDpessoa crud_pessoa = new CRUDpessoa(serverName);
			Pessoa pessoa = gson.fromJson(parametros.get("obj"), Pessoa.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.verPessoaFaceGoogle(pessoa)));
		}
		else if (metodo.equals("InsertFaceGoogle")) {
			CRUDpessoa crud_pessoa = new CRUDpessoa(serverName);
			Pessoa pessoa = gson.fromJson(parametros.get("obj"), Pessoa.class); 
			retornaObj(out , gson.toJsonTree(crud_pessoa.insertFaceGoogle(pessoa)));
		}
		else if (metodo.equals("PostAlbum")) {
			CRUDalbum crud_album = new CRUDalbum(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Album album = gson.fromJson(parametros.get("obj"), Album.class); 
			retornaObj(out , gson.toJsonTree(crud_album.postAlbum(album)));
		}
		else if (metodo.equals("GetAlbum")) {
			CRUDalbum albuns = new CRUDalbum(serverName);
			Album album = gson.fromJson(parametros.get("obj"), Album.class);
			retornaObj(out , gson.toJsonTree(albuns.getAlbum(album)));
		}
		else if (metodo.equals("DelAlbum")) {
			CRUDalbum albuns = new CRUDalbum(serverName);
			Album album = gson.fromJson(parametros.get("obj"), Album.class);
			retornaObj(out , gson.toJsonTree(albuns.delAlbum(album)));
		}
		else if (metodo.equals("PostLingerie")) {
			CRUDlingerie crud_lingerie = new CRUDlingerie(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Lingerie lingerie = gson.fromJson(parametros.get("obj"), Lingerie.class); 
			retornaObj(out , gson.toJsonTree(crud_lingerie.postLingerie(lingerie)));
		}
		else if (metodo.equals("DelLingerie")) {
			CRUDlingerie lingeries = new CRUDlingerie(serverName);
			Lingerie lingerie = gson.fromJson(parametros.get("obj"), Lingerie.class);
			retornaObj(out , gson.toJsonTree(lingeries.delLingerie(lingerie)));
		}
		else if (metodo.equals("PostPresente")) {
			CRUDpresente crud_presente = new CRUDpresente(serverName);
			Presente presente = gson.fromJson(parametros.get("obj"), Presente.class); 
			retornaObj(out , gson.toJsonTree(crud_presente.postPresente(presente)));
		}
		else if (metodo.equals("PostResgate")) {
			CRUDresgate crud_resgate = new CRUDresgate(serverName);
			Resgate resgate = gson.fromJson(parametros.get("obj"), Resgate.class); 
			retornaObj(out , gson.toJsonTree(crud_resgate.postResgate(resgate)));
		}
		else if (metodo.equals("PostCupom")) {
			CRUDcupom crud_cupom = new CRUDcupom(serverName);
			Cupom cupom = gson.fromJson(parametros.get("obj"), Cupom.class); 
			retornaObj(out , gson.toJsonTree(crud_cupom.postCupom(cupom)));
		}
		else if (metodo.equals("UpdateCodigo")) {
			CRUDcupom crud_cupom = new CRUDcupom(serverName);
			Cupom cupom = gson.fromJson(parametros.get("obj"), Cupom.class); 
			retornaObj(out , gson.toJsonTree(crud_cupom.updateCodigo(cupom)));
		}
		else if (metodo.equals("UpdateTransferencia")) {
			CRUDtransferencia crud_transferencia = new CRUDtransferencia(serverName);
			Transferencia transferencia = gson.fromJson(parametros.get("obj"), Transferencia.class); 
			retornaObj(out , gson.toJsonTree(crud_transferencia.updateTransferencia(transferencia)));
		}
		else if (metodo.equals("GetCupom")) {
			CRUDcupom crud_cupom = new CRUDcupom(serverName);
			retornaObj(out , gson.toJsonTree(crud_cupom.getCupom()));
		}
		else if (metodo.equals("PostTransferencia")) {
			CRUDtransferencia crud_transferencia = new CRUDtransferencia(serverName);
			Transferencia transferencia = gson.fromJson(parametros.get("obj"), Transferencia.class); 
			retornaObj(out , gson.toJsonTree(crud_transferencia.postTransferencia(transferencia)));
		}
		else if (metodo.equals("GetDetTransferencia")) {
			CRUDtransferencia crud_transferencia = new CRUDtransferencia(serverName);
			Transferencia transferencia = gson.fromJson(parametros.get("obj"), Transferencia.class); 
			retornaObj(out , gson.toJsonTree(crud_transferencia.getDetTransferencia(transferencia)));
		}
		else if (metodo.equals("VerSaldo")) {
			CRUDresgate crud_resgate = new CRUDresgate(serverName);
			Resgate resgate = gson.fromJson(parametros.get("obj"), Resgate.class); 
			retornaObj(out , gson.toJsonTree(crud_resgate.saldo(resgate)));
		}
		else if (metodo.equals("PostRecado")) {
			CRUDrecado crud_recado = new CRUDrecado(serverName);
			Recado recado = gson.fromJson(parametros.get("obj"), Recado.class); 
			retornaObj(out , gson.toJsonTree(crud_recado.postRecado(recado)));
		}
		else if (metodo.equals("UpdateRecado")) {
			CRUDrecado crud_recado = new CRUDrecado(serverName);
			Recado recado = gson.fromJson(parametros.get("obj"), Recado.class); 
			retornaObj(out , gson.toJsonTree(crud_recado.updateRecado(recado)));
		}
		else if (metodo.equals("GetRecados")) {
			CRUDrecado crud_recado = new CRUDrecado(serverName);
			Recado recado = gson.fromJson(parametros.get("obj"), Recado.class); 
			retornaObj(out , gson.toJsonTree(crud_recado.getRecados(recado)));
		}
		else if (metodo.equals("PostConvidado")) {
			CRUDconvidado crud_convidado = new CRUDconvidado(serverName);
			Convidado convidado = gson.fromJson(parametros.get("obj"), Convidado.class); 
			retornaObj(out , gson.toJsonTree(crud_convidado.postConvidado(convidado)));
		}
		else if (metodo.equals("PostCurtida")) {
			CRUDcurtida crud_curtida = new CRUDcurtida(serverName);
			Curtida curtida = gson.fromJson(parametros.get("obj"), Curtida.class); 
			retornaObj(out , gson.toJsonTree(crud_curtida.insert(curtida)));
		}
		else if (metodo.equals("GetDetCupom")) {
			CRUDcupom crud_cupom = new CRUDcupom(serverName);
			Cupom cupom = gson.fromJson(parametros.get("obj"), Cupom.class); 
			retornaObj(out , gson.toJsonTree(crud_cupom.getDetCupom(cupom)));
		}
		else if (metodo.equals("GetCurtidas")) {
			CRUDcurtida crud_curtida = new CRUDcurtida(serverName);
			Curtida curtida = gson.fromJson(parametros.get("obj"), Curtida.class); 
			retornaObj(out , gson.toJsonTree(crud_curtida.contagem(curtida)));
		}
		else if (metodo.equals("SetConvidado")) {
			CRUDconvidado crud_convidado = new CRUDconvidado(serverName);
			Convidado convidado = gson.fromJson(parametros.get("obj"), Convidado.class); 
			retornaObj(out , gson.toJsonTree(crud_convidado.setConvidado(convidado)));
		}
		else if (metodo.equals("GetConvidados")) {
			CRUDconvidado crud_convidado = new CRUDconvidado(serverName);
			Convidado convidado = gson.fromJson(parametros.get("obj"), Convidado.class); 
			retornaObj(out , gson.toJsonTree(crud_convidado.getConvidados(convidado)));
		}
		else if (metodo.equals("GetPresentes")) {
			CRUDpresente crud_presente = new CRUDpresente(serverName);
			Presente presente = gson.fromJson(parametros.get("obj"), Presente.class); 
			retornaObj(out , gson.toJsonTree(crud_presente.getPresentes(presente)));
		}
		else if (metodo.equals("GetResgates")) {
			CRUDresgate crud_resgate = new CRUDresgate(serverName);
			Resgate resgate = gson.fromJson(parametros.get("obj"), Resgate.class); 
			retornaObj(out , gson.toJsonTree(crud_resgate.getResgates(resgate)));
		}
		else if (metodo.equals("GetResgatesTotal")) {
			CRUDresgate crud_resgate = new CRUDresgate(serverName);
			retornaObj(out , gson.toJsonTree(crud_resgate.getResgatesTotal()));
		}
		else if (metodo.equals("GetTransferencias")) {
			CRUDtransferencia crud_transferencia = new CRUDtransferencia(serverName);
			retornaObj(out , gson.toJsonTree(crud_transferencia.getTransferencias()));
		}
		else if (metodo.equals("GetResgatesConcluidos")) {
			CRUDresgate crud_resgate = new CRUDresgate(serverName);
			Resgate resgate = gson.fromJson(parametros.get("obj"), Resgate.class); 
			retornaObj(out , gson.toJsonTree(crud_resgate.getResgatesConcluidos(resgate)));
		}
		else if (metodo.equals("UpdateVerificado")) {
			CRUDresgate crud_resgate = new CRUDresgate(serverName);
			Resgate resgate = gson.fromJson(parametros.get("obj"), Resgate.class); 
			retornaObj(out , gson.toJsonTree(crud_resgate.updateVerificado(resgate)));
		}
		else if (metodo.equals("UpdateConcluido")) {
			CRUDresgate crud_resgate = new CRUDresgate(serverName);
			Resgate resgate = gson.fromJson(parametros.get("obj"), Resgate.class); 
			retornaObj(out , gson.toJsonTree(crud_resgate.updateConcluido(resgate)));
		}
		else if (metodo.equals("GetTipoResgate")) {
			CRUDtipo_resgate crud_resgate = new CRUDtipo_resgate(serverName);
			TipoResgate resgate = gson.fromJson(parametros.get("obj"), TipoResgate.class); 
			retornaObj(out , gson.toJsonTree(crud_resgate.getTipoResgate(resgate)));
		}
		else if (metodo.equals("GetPresentesConvidado")) {
			CRUDpresente crud_presente = new CRUDpresente(serverName);
			Presente presente = gson.fromJson(parametros.get("obj"), Presente.class); 
			retornaObj(out , gson.toJsonTree(crud_presente.getPresentesConvidado(presente)));
		}
		else if (metodo.equals("GetLingerie")) {
			CRUDlingerie lingeries = new CRUDlingerie(serverName);
			String referencia = parametros.get("referencia");
			int idevento = Integer.parseInt(parametros.get("idevento"));
			retornaObj(out, gson.toJsonTree(lingeries.getLingerie(referencia, idevento)));
		}
		else if (metodo.equals("GetLingeries")) {
			CRUDlingerie crud_lingerie = new CRUDlingerie(serverName);
			Lingerie lingerie = gson.fromJson(parametros.get("obj"), Lingerie.class); 
			retornaObj(out , gson.toJsonTree(crud_lingerie.getLingeries(lingerie)));
		}
		else if (metodo.equals("UpdateEvento")) {
			CRUDevento crud_evento = new CRUDevento(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Evento evento = gson.fromJson(parametros.get("obj"), Evento.class); 
			retornaObj(out , gson.toJsonTree(crud_evento.updateEvento(evento)));
		}
		else if (metodo.equals("PostEvento")) {
			CRUDevento crud_evento = new CRUDevento(serverName);
			/*String email = parametros.get("email");
			String senha = parametros.get("senha");*/
			Evento evento = gson.fromJson(parametros.get("obj"), Evento.class); 
			retornaObj(out , gson.toJsonTree(crud_evento.insertEvento(evento)));
		}
		else if (metodo.equals("GetEvento")) {
			CRUDevento crud_evento = new CRUDevento(serverName);
			Evento evento = gson.fromJson(parametros.get("obj"), Evento.class); 
			retornaObj(out , gson.toJsonTree(crud_evento.getEvento(evento)));
		}
		else if (metodo.equals("GetDadosSite")) {
			CRUDevento crud_evento = new CRUDevento(serverName);
			Evento evento = gson.fromJson(parametros.get("obj"), Evento.class); 
			retornaObj(out , gson.toJsonTree(crud_evento.getDadosSite(evento)));
		}
		else if (metodo.equals("GetLink")) {
			CRUDevento crud_evento = new CRUDevento(serverName);
			Evento evento = gson.fromJson(parametros.get("obj"), Evento.class); 
			retornaObj(out , gson.toJsonTree(crud_evento.gerarLink(evento)));
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
		else if (metodo.equals("PostLoveCode")) {
			CRUDconsultora crud_consultora = new CRUDconsultora(serverName);
			Consultora consultora = gson.fromJson(parametros.get("obj"), Consultora.class); 
			retornaObj(out, gson.toJsonTree(crud_consultora.postLoveCode(consultora)));
		}
		else if (metodo.equals("GetConsultora")) {
			CRUDconsultora crud_consultora = new CRUDconsultora(serverName);
			Consultora consultora = gson.fromJson(parametros.get("obj"), Consultora.class); 
			retornaObj(out, gson.toJsonTree(crud_consultora.getConsultora(consultora)));
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
