package testes;

import java.util.LinkedList;

import conexao.Conexao;
import global.crud.CRUDcad_recados;
import global.dados.Recado;

public class MainTesteEmoji {
	public static void main(String[] args) {
		Conexao conexao = new Conexao("duzani", "localhost");
			
		Recado r = new Recado();
		r.setIdevento(1);
		CRUDcad_recados crud = new CRUDcad_recados(conexao);
		
		LinkedList<Recado> lista = crud.getRecados(r);
		for (Recado recado : lista) {
			System.out.println(recado.getRecado());
		}
		
		conexao.desconecta();
	}
}
