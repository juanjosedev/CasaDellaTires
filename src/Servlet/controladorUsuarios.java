package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import include.Usuario;
import modelo.modeloUsuario;

/**
 * Servlet implementation class controladorUsuarios
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/usuarios_s" })
public class controladorUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
			throws ServletException, IOException {
		
		modeloUsuario mu = new modeloUsuario();
		Boolean msg = false;				
		
		if (getVal(request, "login") != null) {
			
			String usuario = getVal(request, "usuario");
			String clave = getVal(request, "clave");
			
			Usuario usuario_login = new Usuario(usuario, clave);
			msg = mu.autenticarUsuario(usuario_login);
			
			if(msg) {
				usuario_login = mu.getUsuario(usuario_login);
				iniciarSesion(request, usuario_login);
			}
		} else if(getVal(request, "out_log") != null) {
			cerrarSesion(request, response);
			msg = true;
		}
		
		res(response, msg);
		mu.cerrarConexion();
	}
	
	protected String getVal(HttpServletRequest request, String name) throws ServletException{
		String val = "";
		val = request.getParameter(name);
		return val;
	}
	
	protected void res(HttpServletResponse response, Boolean msg) throws IOException{
		response.getWriter().print(msg);
	}
	
	protected void iniciarSesion(HttpServletRequest request, Usuario u) {
		HttpSession sesion = request.getSession(true);
		sesion.setAttribute("username", u);
	}
	
	protected void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession(true);
		sesion.removeValue("username");
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
