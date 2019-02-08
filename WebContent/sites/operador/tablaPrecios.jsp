<%@ page import="java.util.*, controlador.*, include.*, modelo.*"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	
	if(username == null){
		response.sendRedirect("http://localhost:8080/CasaDellaTires/");
	}else{
		Usuario u = (Usuario) username;
		
		ModeloServicios2 ms2 = new ModeloServicios2();
		
		try{
			String nombre_servicio = request.getParameter("servicio");
			Servicio2 s = ms2.getServicio(nombre_servicio);
		
%>
<table class="table table-hover sombra">
	<thead>
		<tr>
			<th class="text-center" colspan="5">Tabla de precios</th>
		</tr>
	</thead>
	<tbody>
		<tr class="bg-ddd">
			<th class="text-right">Id</th>
			<th class="text-left">Nombre</th>
			<th class="text-right">Precio</th>
		</tr>
	<%
		ArrayList<DetalleServicio> lista_detalles = s.getLista_detalle();
		for(DetalleServicio dll: lista_detalles){
	%>
		<tr>
			<td class="text-right"><var class="var_id"><%= dll.getId() %></var></td>
			<td><%= dll.getTipo_vehiculo().getNombre() %></td>
			<td class="text-right"><var class="var_precio"><%= dll.getPrecio() %></var></td>
		</tr>
	<% 
		}
	%>
	</tbody>
</table>
<%
		} catch (NumberFormatException e) {
			response.getWriter().print("<h2>Error con el dato de entrada</h2>");
		} catch (NullPointerException e){
			response.getWriter().print("<h2>El servicio no existe </h2>");
		}
		ms2.cerrarConexion();
	}
%>