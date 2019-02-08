<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorClientes cc = new controladorClientes();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Cliente> lista = cc.getAllClientes(pagina);
%>
<br>
<div class="table-responsive">
	<table class="table table-hover sombra">
		<thead>
			<tr>
				<th class="text-center" colspan="5">Tabla de clientes</th>
			</tr>
		</thead>
		<thead>
		</thead>
		<tbody>
			<tr class="bg-ddd">
				<th class="text-right">Cédula</th>
				<th class="text-left">Nombre</th>
				<th class="text-right">Teléfono</th>
				<th class="text-left">Dirección</th>
				<th class="text-center">Ver perfil</th>
			</tr>
			<%
				for (Cliente valor : lista) {
			%>
			<tr>
				<td class="text-right"><%=valor.getCedula()%></td>
				<td class="text-left"><%=valor.getNombreCompleto()%></td>
				<td class="text-right"><%=valor.getTelefono()%></td>
				<td class="text-left"><%=valor.getDireccion()%></td>
				<td class="text-center"><a href="Clientes.jsp?user=<%= valor.getCedula() %>"><span class="icon-remove_red_eye"></span></a></td>
			<%
				}//Cierre del for
			%>
		</tbody>
	</table>
</div>
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
					listas[i].firstChild.className += "<%= u.getColor() %> page_active";
				}
			}		
		}
		getPaginas();
	});
</script>
<%= Paginacion.getPaginacion("Clientes.jsp", cc.getContarClientes()) %>
	
	<%		
		//Cierre del else
		cc.cerrarConexiones();
	%>
