package global.util;

import java.sql.PreparedStatement;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import global.dados.Email;
import global.dados.Json;
import global.dados.Retorno;
import global.util.Util;

public class Util {
	
	// Generic return
	public Retorno retorno(String situacao, String codErro, String msg, Object obj, LinkedList<Object> arrayObj) {
		Retorno retorno = new Retorno();

		retorno.setSituacao(situacao);
		retorno.setCodErro(codErro);
		retorno.setMsg(msg);
		retorno.setObj(obj);
		retorno.setArrayObj(arrayObj);

		return retorno;
	}

	

	public static void setIntOrNull(PreparedStatement pstmt, int indice, int valor) {
		try {
			if (valor != 0) {
				pstmt.setInt(indice, valor);
			} else {
				pstmt.setNull(indice, java.sql.Types.INTEGER);
			}
		} catch (Exception e) {

		}
	}
	
	
}
