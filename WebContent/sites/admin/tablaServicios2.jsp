<%@ page import="java.util.*, modelo.*, include.*, controlador.*"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	
	ModeloServicios2 ms2 = new ModeloServicios2();
	
	if(request.getParameter("buscar") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Servicio2> lista = ms2.getAllServicios();
		
%>
<h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span> Tabla de servicios</h3>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center text-uppercase">Id</th>
			<th class="text-center">Nombre</th>
			<th class="text-center">Detalle</th>
		</tr>
	</thead>
	<tbody>
		<% for(Servicio2 s : lista){ %>
		<tr>
			<td class="text-center"><%= s.getId() %></td>
			<td class="text-center"><%= s.getNombre() %></td>
			<td class="text-center"><a href="Servicios2.jsp?servicio=<%= s.getId() %>" data-toggle="modal">Ver</a></td>
		</tr>
		<% } %>
	</tbody>
</table>
<%
	}
	ms2.cerrarConexion();
%>