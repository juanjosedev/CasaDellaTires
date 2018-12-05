package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import include.Usuario;
import modelo.modeloUsuario;

/**
 * Servlet implementation class servletOperadores
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/operadoress" })
public class servletOperadores extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	 protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
				throws ServletException, IOException {
		 response.setContentType("text/html;charset=UTF-8");
		 
		 modeloUsuario mu = new modeloUsuario();
		 String msgstr = "";
		 
		 
		 if (getVal(request, "registrar") != null) {
			 
			String usuario = getVal(request, "usuario");
			
			if(mu.usuarioYaExiste(usuario)) {
				msgstr = "El usuario con el nombre "+usuario+" ya existe, intente con otro nombre";
				res(response, msgstr);
			}else {
				String clave = getVal(request, "clave");
				String nombre = getVal(request, "nombre");
				String primer_apellido = getVal(request, "primer_apellido");
				String segundo_apellido = getVal(request, "segundo_apellido");
				String telefono = getVal(request, "telefono");
				String direccion = getVal(request, "direccion");
				String tipo = getVal(request, "tipo");
				String color = getVal(request, "color");
				
				Usuario nuevo_operador = new Usuario(usuario, clave, nombre, primer_apellido, segundo_apellido, telefono, direccion);
				
				boolean flag = mu.registrarOperador(nuevo_operador);
				res(response, flag);
				
			}
			
		 }
		 mu.cerrarConexion();
				 
	 }
	    	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	protected String getVal(HttpServletRequest request, String name) throws ServletException{
		String val = "";
		val = request.getParameter(name);
		return val;
	}

	protected void res(HttpServletResponse response, Boolean msg) throws IOException{
		response.getWriter().print(msg);
	}
	
	protected void res(HttpServletResponse response, String msg) throws IOException{
		response.getWriter().print(msg);
	}
	
}
