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
<h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span>Tabla de Clientes</h3>
<div class="table-responsive">
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th class="text-center text-uppercase">C�dula</th>
				<th class="text-center text-uppercase">Nombre</th>
				<th class="text-center text-uppercase">Tel�fono</th>
				<th class="text-center text-uppercase">Direcci�n</th>
				<th class="text-center text-uppercase">Ver perfil</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Cliente valor : lista) {
			%>
			<tr>
				<td class="text-center"><%=valor.getCedula()%></td>
				<td class="text-center"><%=valor.getNombreCompleto()%></td>
				<td class="text-center"><%=valor.getTelefono()%></td>
				<td class="text-center"><%=valor.getDireccion()%></td>
				<td class="text-center"><a href="Clientes.jsp?user=<%= valor.getCedula() %>" data-toggle="modal">Ver perfil</a></td>
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
