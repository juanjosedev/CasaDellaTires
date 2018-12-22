package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlador.*;
import include.*;
import modelo.*;

/**
 * Servlet implementation class servletLiquidacion
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/crudliquidacion" })
public class servletLiquidacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
			throws ServletException, IOException {
    	
		response.setContentType("text/html;charset=UTF-8");
    	
		controladorClientes cc = new controladorClientes();
		controladorVehiculos cv = new controladorVehiculos();
		controladorLiquidaciones cl = new controladorLiquidaciones();
		
		if (request.getParameter("cc_exist") != null) {
			
			String buscar = request.getParameter("cc_exist");
			Cliente c = cc.getCliente(Long.parseLong(buscar));
			response.getWriter().print(c != null);
			
		} else if(request.getParameter("placa_exist") != null) {
		
			String buscar = request.getParameter("placa_exist");
			Vehiculo v = cv.getVehiculo(buscar);
			response.getWriter().print(v != null);
			
		} else if(request.getParameter("placa_tabla") != null) {
			
			String placa_tabla = request.getParameter("placa_tabla");
			modeloServicios ms = new modeloServicios();
			String tablaServicios = ms.getTablaServicios(placa_tabla);
			ms.cerrarConexion();
			//System.out.println(tablaServicios);
			response.getWriter().print(tablaServicios);
			
		} else if(request.getParameter("get_info_cliente") != null) {
			
			long cedula = Long.parseLong(request.getParameter("get_info_cliente"));
			Cliente c = cc.getCliente(cedula);
			response.getWriter().print(cc.getHTMLi(c));
			
		} else if(request.getParameter("get_info_vehiculo") != null) {
			
			String placa = request.getParameter("get_info_vehiculo");
			Vehiculo v = cv.getVehiculo(placa);
			response.getWriter().print(cv.getHTMLi(v));
			
		} else if(request.getParameter("liquidar") != null) {
			
			long cedula = Long.parseLong(request.getParameter("cc"));
			String placa = request.getParameter("placa");
			String[] servicios = request.getParameterValues("servicio[]");
			boolean sw = cl.agregarNuevaLiquidacion(cedula, placa, servicios);
			
			response.getWriter().print(sw);
			
		} else if (request.getParameter("terminar_lqd") != null) {
			
			long consecutivo = Long.parseLong(request.getParameter("terminar_lqd"));
			boolean sw = cl.terminarLiquidacion(consecutivo);
			
			response.getWriter().print(sw);
			
		} 
		cc.cerrarConexiones();
		cv.cerrarConexiones();
		cl.cerrarConexionesControlador();
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
