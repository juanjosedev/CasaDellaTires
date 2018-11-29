package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.controladorVehiculos;
import include.Vehiculo;
import include.tipoVehiculo;

/**
 * Servlet implementation class servletVehiculos
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/crudvehiculos" })
public class servletVehiculos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		if (request.getParameter("placa1") != null) {
			
			String placa = request.getParameter("placa1").toUpperCase() + request.getParameter("placa2");
			String tipo = request.getParameter("tipo");
			String marca = request.getParameter("marca");
			String modelo = request.getParameter("modelo");
			
			controladorVehiculos ctv = new controladorVehiculos();
			tipoVehiculo tv = new tipoVehiculo(Long.parseLong(tipo), "");
			boolean sw = ctv.agregaNuevoVehiculo(new Vehiculo(placa, tv, marca, modelo));
			
			ctv.cerrarConexiones();
			
			response.getWriter().print(sw);
			
		} else if (request.getParameter("placa1_mdf") != null) {
			
			String placa = request.getParameter("placa1_mdf").toUpperCase() + request.getParameter("placa2");
			String tipo = request.getParameter("tipo");
			String marca = request.getParameter("marca");
			String modelo = request.getParameter("modelo");
			
			controladorVehiculos ctv = new controladorVehiculos();
			tipoVehiculo tv = new tipoVehiculo(Long.parseLong(tipo), "");
			boolean sw = ctv.modificarVehiculo(new Vehiculo(placa, tv, marca, modelo));
			
			ctv.cerrarConexiones();
			
			response.getWriter().print(sw);
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
