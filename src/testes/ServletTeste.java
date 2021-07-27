package testes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import conexao.Conexao;
import global.crud.CRUDcad_recados;
import global.dados.Recado;
import server.aplicativo.CRUDrecado;

/**
 * Servlet implementation class ServletTeste
 */
public class ServletTeste extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTeste() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				/*Conexao		 conexao = new Conexao("duzani", "localhost");
				
				Recado r = new Recado();
				r.setIdevento(1);
				CRUDcad_recados crud = new CRUDcad_recados(conexao);
				
				LinkedList<Recado> lista = crud.getRecados(r);
				String retorno = "";
				for (Recado recado : lista) {
					System.out.println(recado.getRecado());
					retorno+=recado.getRecado();
				}
				
				conexao.desconecta();
				
				response.setContentType("text/html; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				//ServletOutputStream out = response.getOutputStream();
				//out.println(retorno);
				PrintWriter out = response.getWriter();
				out.println(retorno);*/
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		HashMap<String, String> parametros = new HashMap<>();
		Map<String, String[]> allParameters = request.getParameterMap();
		for (String par : allParameters.keySet()) {
			parametros.put(par, request.getParameter(par).replace("'", "`"));
		}

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();

		
		String serverName = "localhost";
		CRUDrecado crud_recado = new CRUDrecado(serverName);
		Recado recado = gson.fromJson(parametros.get("obj"), Recado.class); 
		retornaObj(out , gson.toJsonTree(crud_recado.getRecados(recado)));

	}
	
	public void retornaObj(PrintWriter out, JsonElement jsonElement) {
		JsonObject myObj = new JsonObject();

		JsonElement jsonObj = jsonElement;
		myObj.add("dados", jsonObj);
		out.println(jsonObj.toString());
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
