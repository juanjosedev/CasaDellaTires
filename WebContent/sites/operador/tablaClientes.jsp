<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorClientes cc = new controladorClientes();
	if(request.getParameter("cc") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Cliente> lista = cc.getAllClientes(pagina);
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center text-uppercase">Cédula</th>
			<th class="text-center text-uppercase">Nombre</th>
			<th class="text-center text-uppercase">Teléfono</th>
			<th class="text-center text-uppercase">Dirección</th>
			<th class="text-center text-uppercase">Historial</th>
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
			<td class="text-center"><a href="#detalle" data-toggle="modal">Detalle</a>
				<div class="modal fade" id="detalle">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">HISTORIAL DEL CLIENTE</h2>
							</div>
							<div class="modal-body text-left">
								<p>Selecciona el servicio o el vehículo para más información</p>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Fecha</th>
												<th>Servicio</th>
												<th>Vehículo</th>
											</tr>
										</thead>
										<tbody>
											<tr>Aquí va el historial como están en los prototipos
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="boton boton-chico pull-left"
									data-dismiss="modal">Cerrar</button>
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<%
			}//Cierre del for
		%>
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
<%= Paginacion.getPaginacion("Clientes.jsp", cc.getContarClientes()) %>
	<%
		} else {//cierre if
			long cedula_buscar = Long.parseLong(request.getParameter("cc"));
			Cliente buscado = cc.getCliente(cedula_buscar);
			
			if(buscado != null){
				
			
	%>
				<%= buscado %>
	<%	
			} else {//Cierre del if
	%>
				<%= "No encontrado" %>
	<%
			}
	%>
			<button id="volver" class="btn btn-primary">Volver</button>
	<%		
		}//Cierre del else
		cc.cerrarConexiones();
	%>
