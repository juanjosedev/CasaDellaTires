<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorVehiculos cv = new controladorVehiculos();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	if(request.getParameter("placa") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Vehiculo> lista = cv.getAllVehiculos(pagina);
		
%>
<br>
<table class="table table-hover sombra">
	<thead>
		<tr>
			<th class="text-center" colspan="5">Tabla de vehículos</th>
		</tr>
	</thead>
	<tbody>
		<tr class="bg-ddd">
			<th class="text-left">Placa</th>
			<th class="text-left">Tipo de vehículo</th>
			<th class="text-left">Marca</th>
			<th class="text-left">Modelo</th>
			<th class="text-center">Ver perfil</th>
		</tr>
		<% for(Vehiculo v : lista){ %>
		<tr>
			<td class="text-left"><%= v.getBeautyPlaca() %></td>
			<td class="text-left"><%= v.getTipo().getNombre() %></td>
			<td class="text-left"><%= v.getMarca() %></td>
			<td class="text-left"><%= v.getModelo() %></td>
			<td class="text-center"><a href="Vehiculos.jsp?profile=<%= v.getFirstPlaca()+v.getSecondPlaca() %>"><span class="icon-remove_red_eye"></span></a></td>
		</tr>
		<% } %>
	</tbody>
</table>
<%= Paginacion.getPaginacion("Vehiculos.jsp", cv.getContarVehiculos()) %>
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
					listas[i].firstChild.className += "<%= u.getColor() %> page_active";;
				}
			}		
		}
		getPaginas();
	});
</script>
<% } 
   cv.cerrarConexiones();
%>