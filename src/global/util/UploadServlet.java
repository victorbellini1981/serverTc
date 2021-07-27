package global.util;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

public class UploadServlet extends UploadAction {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setContentType("application/json;charset=UTF-8");
		}
		public static final String PASTA = "/var/lib/tomcat7/webapps/agelyArquivos/";

		private static final long serialVersionUID = 1L;

		Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
		/**
		 * Maintain a list with received files and their content types.
		 */
		Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

		/**
		 * Override executeAction to save the received files in a custom place and
		 * delete this items from session.
		 */
		@Override
		public String executeAction(HttpServletRequest request,
				List<FileItem> sessionFiles) throws UploadActionException {
			String response = "";
			for (FileItem item : sessionFiles) {
				if (false == item.isFormField()) {
					try {
						// / Create a new file based on the remote file name in the
						// client
						String saveName = item.getName().replaceAll(
								"[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");

						File f = new File("");
						String pasta = PASTA;
						File f2 = new File("/home/agely/servidor.txt");
						if (! f2.exists()) {
						
							if (System.getProperty("os.name").equals("Linux")) {
								pasta = new File("").getAbsolutePath() + "//"+pasta;
							} else {
								pasta = new File("").getAbsolutePath() + "\\"+pasta;
							}	
							
						}	
						
						File dir = new File(pasta);
						if (!dir.exists()) {
							dir.mkdirs();
						}

						File file = new File(pasta + saveName);
						int num = 1;
						while (file.exists()) {
							file = new File(pasta + num + "_" + saveName);
							num++;
						}
						System.out.println("write agora"+ file.getAbsolutePath());
						
						item.write(file);

						
						receivedFiles.put(item.getFieldName(), file);
						receivedContentTypes.put(item.getFieldName(),
								item.getContentType());
						
						if(!response.equals(""))
							response += ";";
						response +=  file.getName();

					} catch (Exception e) {
						throw new UploadActionException(e);
					}
				}
			}
			removeSessionFileItems(request);
			return response;
		}

		/**
		 * Get the content of an uploaded file.
		 */
		@Override
		public void getUploadedFile(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String fieldName = request.getParameter(UConsts.PARAM_SHOW);
			File f = receivedFiles.get(fieldName);
			if (f != null) {
				response.setContentType(receivedContentTypes.get(fieldName));
				FileInputStream is = new FileInputStream(f);
				copyFromInputStreamToOutputStream(is, response.getOutputStream());
			} else {
				renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
			}
		}

		/**
		 * Remove a file when the user sends a delete request.
		 */
		@Override
		public void removeItem(HttpServletRequest request, String fieldName)
				throws UploadActionException {
			File file = receivedFiles.get(fieldName);
			receivedFiles.remove(fieldName);
			receivedContentTypes.remove(fieldName);
			if (file != null) {
				file.delete();
			}
		}
}
