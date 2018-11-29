package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import include.Servicio;
import modelo.modeloServicios;

/**
 * Servlet implementation class servletServicios
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/crudservicios" })
public class servletServicios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getParameter("tipo") != null) {
			
			long id_tipo_vehiculo = Long.parseLong(request.getParameter("tipo"));
			String nombre = request.getParameter("nombre");
			int precio = Integer.parseInt(request.getParameter("precio"));
			
			modeloServicios ms = new modeloServicios();
			Servicio s = new Servicio(id_tipo_vehiculo, nombre, precio);
			
			boolean sw = ms.agregarServicio(s);
			
			ms.cerrarConexion();
			
			response.getWriter().println(sw);
			
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
