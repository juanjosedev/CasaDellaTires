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
<br>
<!-- <h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span> Tabla de servicios</h3> -->
<table class="table table-hover sombra">
	<thead>
		<tr>
			<th class="text-center" colspan="3">Tabla de servicios</th>
		</tr>
	</thead>
	<tbody>
		<tr class="bg-ddd">
			<td class="text-right"><b>Id</b></td>
			<td class="text-left"><b>Nombre</b></td>
			<td class="text-center"><b>Detalle</b></td>
		</tr>
		<% for(Servicio2 s : lista){ %>
		<tr>
			<td class="text-right"><%= s.getId() %></td>
			<td class="text-left"><%= s.getNombre() %></td>
			<td class="text-center"><a href="Servicios2.jsp?servicio=<%= s.getNombre() %>" data-toggle="modal"><span class="icon-remove_red_eye"></span></a></td>
		</tr>
		<% } %>
	</tbody>
</table>
<%
	}
	ms2.cerrarConexion();
%>