/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package conexao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author ely
 */

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import global.util.LogErros;

public class Conexao {
	// private static Connection conexaoAgely;
	// private static Connection conexaoTeste;

	private Connection conexao;
	private Statement instrucao;
	private PreparedStatement instrucaoPreparada;
	private ResultSet resultado;
	private String serverName;
	private boolean emTransacao = false;
	private boolean bdTeste = false;
	private static List<ResultSetCache> resultSetCache = new LinkedList<ResultSetCache>();

	public Conexao(String serverName) {
		this.serverName = serverName;
		
		try {

			/*bdTeste = true;
			String url = "jdbc:postgresql://agely-homologacao.c4iovzultfsd.sa-east-1.rds.amazonaws.com:5432/agely_homologacao_duzani";
			String username = "agely";
			String password = "4g3lyAWS";*/

			Class.forName("org.mariadb.jdbc.Driver").newInstance();

			conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/tcc", "root", "123456");

		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro de conexao " + e.getMessage());
		}
	}

	public void desconecta() {
		try {
			if (emTransacao) {
				cancelaTransacao();
			}
			conexao.close();
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
		}
	}

	public ResultSet executaQuery(String sql) {
		try {
			instrucao = conexao.createStatement();
			resultado = instrucao.executeQuery(sql);
			return resultado;
		} catch (SQLException ex) {
			LogErros log = new LogErros();
			log.gravarLogErro(ex, serverName, "Erro SQL " + sql + " - " + ex.getMessage());
			return null;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
			return null;
		}

	}

	public ResultSet executaQueryRolavel(String sql) {
		try {
			instrucao = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultado = instrucao.executeQuery(sql);
			// log.gravaLog(sql);
			return resultado;
		} catch (SQLException ex) {
			LogErros log = new LogErros();
			log.gravarLogErro(ex, serverName, "Erro SQL " + sql + " - " + ex.getMessage());
			desconecta();
			return null;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
			desconecta();
			return null;
		}

	}

	public ResultSet executaQueryCache(String sql, int duracaoMilisegundos) {

		// remove os aches invalidos
		for (int i = resultSetCache.size() - 1; i >= 0; i--) {
			ResultSetCache cache = resultSetCache.get(i);
			if (!cache.isValido()) {
				resultSetCache.remove(i);
			}
		}

		// confere se existe cache valido
		ResultSet resultSet = null;
		for (ResultSetCache cache : resultSetCache) {
			if (cache.getSql().equals(sql)) {

				resultSet = cache.getResultSet();
				try {
					resultSet.beforeFirst();
					System.out.print("CACHEBD ");
					break;
				} catch (SQLException ex) {
					resultSet = null;
					// se der erro, ele não faz cache
				}
			}
		}

		if (bdTeste) { // não faz cache quando for banco de teste
			resultSet = null;
		}

		if (resultSet == null) {
			try {
				instrucao = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				resultSet = instrucao.executeQuery(sql);
			} catch (SQLException ex) {
				LogErros log = new LogErros();
				log.gravarLogErro(ex, serverName, "Erro SQL " + sql + " - " + ex.getMessage());
				return null;
			} catch (Exception e) {
				LogErros log = new LogErros();
				log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
				return null;
			}

			ResultSetCache cache = new ResultSetCache(sql, duracaoMilisegundos, resultSet);
			resultSetCache.add(cache);
		}

		return resultSet;
	}

	public String executaUpdate(String Sql) {
		try {
			instrucao = conexao.createStatement();
			instrucao.executeUpdate(Sql);
			return "ok";
		} catch (SQLException ex) {
			LogErros log = new LogErros();
			log.gravarLogErro(ex, serverName, "Erro SQL " + Sql + " - " + ex.getMessage());
			return "erro-" + ex.getMessage();
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
			return "erro-" + e.getMessage();
		}

	}
	
	public String executaBatch(PreparedStatement statement) {
		try {
			instrucao = statement;
			instrucao.executeBatch();
			return "ok";
		} catch (SQLException ex) {
			return "erro-" + ex.getMessage();
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
			return "erro-" + e.getMessage();
		}

	}

	public PreparedStatement sqlPreparada(String sql) {
		try {
			instrucaoPreparada = conexao.prepareStatement(sql);
			return instrucaoPreparada;
		} catch (SQLException ex) {
			LogErros log = new LogErros();
			log.gravarLogErro(ex, serverName, "Erro " + ex.getMessage());
			return null;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
			return null;
		}
	}

	public Connection getConexao() {
		return conexao;
	}

	public void iniciaTransacao() {
		try {
			conexao.setAutoCommit(false);
			emTransacao = true;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
		}
	}

	public void confirmaTransacao() {
		try {
			conexao.commit();
			conexao.setAutoCommit(true);
			emTransacao = false;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
		}
	}

	public static boolean verificaBancoTeste(String serverName) {
		String ServerTeste[] = { "teste.sistema.agely.com.br", "127.0.0.1", "54.233.217.61" };

		try {
			return ArrayUtils.indexOf(ServerTeste, serverName) >= 0;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public void cancelaTransacao() {
		try {
			conexao.rollback();
			conexao.setAutoCommit(true);
			emTransacao = false;
		} catch (Exception e) {
			LogErros log = new LogErros();
			log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
		}
	}

	public String executaInsert(String sql, String chave_seq) {
		// TesteAws testeaws = new TesteAws();
		String resp = "";
		try {
			instrucao = conexao.createStatement();
			instrucao.executeUpdate(sql);
			// testeaws.executaTesteAws(sql);

			ResultSet resultado = executaQuery("select currval('" + chave_seq + "') as cod");
			// objeto p/ retorna nomes das colunas
			if (resultado.next()) {
				resp = String.valueOf(resultado.getInt(1));
			}

			return resp;
		} catch (SQLException ex) {
			if (conexao != null) {
				LogErros log = new LogErros();
				log.gravarLogErro(ex, serverName, "Erro SQL " + sql + " - " + ex.getMessage());
				desconecta();
			}
			return "erro-" + ex.getMessage();
		} catch (Exception e) {
			if (conexao != null) {
				LogErros log = new LogErros();
				log.gravarLogErro(e, serverName, "Erro " + e.getMessage());
				desconecta();
			}
			return "erro-" + e.getMessage();
		}

	}

	public String executaInsert(String sql, String tabela, String chave) {

		return executaInsert(sql, tabela + "_" + chave + "_seq");
	}

	public Object transformResultSetToObject(Class classe, ResultSet resultSet, boolean colection) throws Exception {
		if (colection) {
			return resultSetToObjectList(classe, resultSet);
		} else {
			return resultSetToObject(classe, resultSet);
		}
	}

	private Object resultSetToObjectList(Class classe, ResultSet resultSet) throws Exception {
		LinkedList<Object> objectRetorno_list = new LinkedList<Object>();

		while (resultSet.next()) {
			ResultSetMetaData columns = resultSet.getMetaData();
			int i = 0;

			Object object = classe.newInstance();

			while (i < columns.getColumnCount()) {
				i++;

				Method[] methods = object.getClass().getDeclaredMethods();
				for (int j = 0; j < methods.length; j++) {
					if (methods[j].getName().startsWith("set")) {

						methods[j].setAccessible(true);

						if ((methods[j].getName().replace("set", "").substring(0, 1).toLowerCase()
								+ methods[j].getName().replace("set", "").substring(1))
										.equals(columns.getColumnName(i))) {
							// System.out.println(columns.getColumnName(i)+" -
							// "+resultSet.getString(columns.getColumnName(i)));

							Field declaredField = object.getClass().getDeclaredField(columns.getColumnName(i));
							boolean accessible = declaredField.isAccessible();

							declaredField.setAccessible(true);

							declaredField.set(object,
									retornaObjectPrimarioFormatado(resultSet.getString(columns.getColumnName(i)),
											declaredField.getType().getSimpleName()));
							declaredField.setAccessible(accessible);
						}
					}
				}
			}

			objectRetorno_list.add(object);
		}

		return objectRetorno_list;// gson.toJson(objectRetorno_list);
	}

	private Object resultSetToObject(Class classe, ResultSet resultSet) throws Exception {
		Object objectRetorno = classe.newInstance();

		while (resultSet.next()) {
			ResultSetMetaData columns = resultSet.getMetaData();
			int i = 0;

			while (i < columns.getColumnCount()) {
				i++;

				Method[] methods = objectRetorno.getClass().getDeclaredMethods();
				for (int j = 0; j < methods.length; j++) {
					if (methods[j].getName().startsWith("set")) {
						// System.out.println(methods[j].getName());
						methods[j].setAccessible(true);

						if ((methods[j].getName().replace("set", "").substring(0, 1).toLowerCase()
								+ methods[j].getName().replace("set", "").substring(1))
										.equals(columns.getColumnName(i))) {
							// System.out.println(columns.getColumnName(i)+" -
							// "+resultSet.getString(columns.getColumnName(i)));

							Field declaredField = objectRetorno.getClass().getDeclaredField(columns.getColumnName(i));
							boolean accessible = declaredField.isAccessible();

							declaredField.setAccessible(true);

							declaredField.set(objectRetorno,
									retornaObjectPrimarioFormatado(resultSet.getString(columns.getColumnName(i)),
											declaredField.getType().getSimpleName()));

							declaredField.setAccessible(accessible);
						}
					}
				}
			}
		}

		return objectRetorno;
	}

	private Object retornaObjectPrimarioFormatado(String valor, String typeSimpleName) {
		switch (typeSimpleName) {
		case "int":
			return (valor == null ? 0 : Integer.parseInt(valor));
		case "String":
			return (valor == null ? "null" : valor);
		case "double":
			return Double.parseDouble(valor);
		case "boolean":
			return Boolean.parseBoolean(
					(valor.equalsIgnoreCase("t") ? "true" : (valor.equalsIgnoreCase("f") ? "false" : valor)));
		default:
			return null;
		}
	}

	public boolean isBdTeste() {
		return bdTeste;
	}

	

}
