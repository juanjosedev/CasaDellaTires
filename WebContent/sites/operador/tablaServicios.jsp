<%@ page import="java.util.*, modelo.*, include.*, controlador.*"%>
<%
	modeloServicios mv = new modeloServicios();
	modeloTiposVehiculos mtv = new modeloTiposVehiculos();
	if(request.getParameter("buscar") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Servicio> lista = mv.getAllServicios(pagina);
		
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center">Id</th>
			<th class="text-center">Tipo de vehículo</th>
			<th class="text-center">Precio</th>
			<th class="text-center">Nombre</th>
		</tr>
	</thead>
	<tbody>
		<% for(Servicio v : lista){ %>
		<tr>
			<td class="text-center"><%= v.getId() %></td>
			<td class="text-center"><%= mtv.getTipoVehiculo(v.getId_tipo_vehiculo()).getNombre() %></td>
			<td class="text-center"><%= v.getPrecio() %></td>
			<td class="text-center"><%= v.getNombre() %></td>
		</tr>
		<% } %>
	</tbody>
</table>
<script>
	$(document).ready(function() {
		function getParameterByName(name) {
		    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		    results = regex.exec(location.search);
		    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
		}
		function getPaginas() {
			var pagina_actual = getParameterByName("page");
			if(pagina_actual == ""){
				pagina_actual = 1;
			}
			var listas = document.getElementsByClassName("page_p");
			for(var i = 0; i < listas.length; i++){
				if(listas[i].innerText == pagina_actual){
					listas[i].firstChild.className += " page_active";
				}
			}		
		}
		getPaginas();
	});
</script>
<%= Paginacion.getPaginacion("Servicios.jsp", mv.getContarServicios()) %>
<%
	}
	mv.cerrarConexion();
	mtv.cerrarConexion();
%>