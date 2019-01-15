package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controlador.controladorClientes;
import include.Auditoria;
import include.Cliente;
import include.Usuario;
import modelo.ModeloAuditorias;

/**
 * Servlet implementation class NuevoCliente
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/crearcliente" })
public class NuevoCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String modulo = "clientes";

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		// Recibe datos del formulario y los almacena en variables
		HttpSession sesion = request.getSession(true);
		Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
		Usuario u = (Usuario) username;
		ModeloAuditorias ma = new ModeloAuditorias();
		Auditoria aud = null;

		if (request.getParameter("cc_agregar") != null) {

			long cedula = Long.parseLong(request.getParameter("cc_agregar"));
			String nombre = request.getParameter("nombre");
			String primer_apellido = request.getParameter("primer_apellido");
			String segundo_apellido = request.getParameter("segundo_apellido");
			String telefono = request.getParameter("telefono");
			String direccion = request.getParameter("direccion");
			// Creamos el objeto cliente y le pasamos las variables
			Cliente nuevo_cliente = new Cliente(cedula, nombre, primer_apellido, segundo_apellido, telefono, direccion);
			// creamos el objeto controlador cliente
			controladorClientes cont_c = new controladorClientes();
			// ejecutamos y validamos si insertó o no
			if (cont_c.agregarCliente(nuevo_cliente)) {
				aud = new Auditoria(u.getUsuario(), modulo, "Crear");
				ma.insertarAuditoria(aud);
				response.getWriter().print(true);
			} else {
				response.getWriter().print(false);
			}
		} else if (request.getParameter("cc_modificar") != null) {

			long cedula = Long.parseLong(request.getParameter("cc_modificar"));
			String nombre = request.getParameter("nombre");
			String primer_apellido = request.getParameter("primer_apellido");
			String segundo_apellido = request.getParameter("segundo_apellido");
			String telefono = request.getParameter("telefono");
			String direccion = request.getParameter("direccion");

			Cliente modificar_cliente = new Cliente(cedula, nombre, primer_apellido, segundo_apellido, telefono,
					direccion);

			controladorClientes cont_c = new controladorClientes();

			if (cont_c.editarCliente(modificar_cliente)) {
				aud = new Auditoria(u.getUsuario(), modulo, "Editar");
				ma.insertarAuditoria(aud);
				response.getWriter().print(true);
			} else {
				response.getWriter().print(false);
			}

		}

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
