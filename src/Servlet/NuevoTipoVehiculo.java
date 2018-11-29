package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.controladorTipoVehiculos;
import include.tipoVehiculo;

/**
 * Servlet implementation class NuevoTipoVehiculo
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/creartipovehiculo" })
public class NuevoTipoVehiculo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
			throws ServletException, IOException {
    	
    	response.setContentType("text/html;charset=UTF-8");
    	controladorTipoVehiculos ctv = new controladorTipoVehiculos();
    	
    	boolean sw = false;
    	
    	if(request.getParameter("crear_tv") != null) {
    		
    		String nombre = request.getParameter("nombre_tipo_vehiculo");    		
    		tipoVehiculo nuevo_tipo_vehiculo = new tipoVehiculo(nombre);
    		sw = ctv.agregarTipoVehiculo(nuevo_tipo_vehiculo); 
    		response.getWriter().print(sw);
    		
    	}else if(request.getParameter("modificar") != null) {
    		
    		long id = Long.parseLong(request.getParameter("id"));
    		String nombre = request.getParameter("nombre");
    		sw = ctv.modificarTipoVehiculo(id, nombre);
    		response.getWriter().print(sw);
    		
    	}
    	ctv.cerrarConexion();
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
