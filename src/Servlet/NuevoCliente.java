package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.controladorClientes;
import include.Cliente;

/**
 * Servlet implementation class NuevoCliente
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/crearcliente" })
public class NuevoCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
				throws ServletException, IOException {
	    	
    	response.setContentType("text/html;charset=UTF-8");
    	
    	//Recibe datos del formulario y los almacena en variables
    	
    	if(request.getParameter("cc_agregar") != null) {
    		long cedula = Long.parseLong(request.getParameter("cc_agregar"));
        	String nombre = request.getParameter("nombre");
        	String primer_apellido = request.getParameter("primer_apellido");
        	String segundo_apellido = request.getParameter("segundo_apellido");
        	String telefono = request.getParameter("telefono");
        	String direccion = request.getParameter("direccion");
        	//Creamos el objeto cliente y le pasamos las variables
        	Cliente nuevo_cliente = new Cliente(cedula, nombre, primer_apellido, segundo_apellido, telefono, direccion);
        	// creamos el objeto controlador cliente
        	controladorClientes cont_c = new controladorClientes();
        	// ejecutamos y validamos si insertó o no
        	if(cont_c.agregarCliente(nuevo_cliente)) response.getWriter().print(true);
        	else {
        		response.getWriter().print(false);
        	}
    	} else if (request.getParameter("cc_modificar") != null) {
    		
    		long cedula = Long.parseLong(request.getParameter("cc_modificar"));
        	String nombre = request.getParameter("nombre");
        	String primer_apellido = request.getParameter("primer_apellido");
        	String segundo_apellido = request.getParameter("segundo_apellido");
        	String telefono = request.getParameter("telefono");
        	String direccion = request.getParameter("direccion");
    		
        	Cliente modificar_cliente = new Cliente(cedula, nombre, primer_apellido, segundo_apellido, telefono, direccion);
        	
        	controladorClientes cont_c = new controladorClientes();
        	
    		response.getWriter().print(cont_c.editarCliente(modificar_cliente));
    		
    	}
 	
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

}
