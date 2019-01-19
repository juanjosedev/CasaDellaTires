package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controlador.controladorVehiculos;
import include.Auditoria;
import include.Usuario;
import include.Vehiculo;
import include.tipoVehiculo;
import modelo.ModeloAuditorias;

/**
 * Servlet implementation class servletVehiculos
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/crudvehiculos" })
public class servletVehiculos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String modulo = "vehiculos";

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		HttpSession sesion = request.getSession(true);
		Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
		Usuario u = (Usuario) username;
		ModeloAuditorias ma = new ModeloAuditorias();
		Auditoria aud = null;
		
		if (request.getParameter("placa1") != null) {

			String placa = request.getParameter("placa1").toUpperCase() + request.getParameter("placa2");
			String tipo = request.getParameter("tipo");
			String marca = request.getParameter("marca");
			String modelo = request.getParameter("modelo");

			controladorVehiculos ctv = new controladorVehiculos();
			tipoVehiculo tv = new tipoVehiculo(Long.parseLong(tipo), "");
			boolean sw = ctv.agregaNuevoVehiculo(new Vehiculo(placa, tv, marca, modelo));

			if(sw) {
				aud = new Auditoria(u.getUsuario(), modulo, "Crear");
			}
			
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

			if(sw) {
				aud = new Auditoria(u.getUsuario(), modulo, "Editar");
			}
			
			ctv.cerrarConexiones();

			response.getWriter().print(sw);
		}

		ma.cerrarConexion();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
