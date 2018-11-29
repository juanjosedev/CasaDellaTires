<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorVehiculos cv = new controladorVehiculos();
	if(request.getParameter("placa") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Vehiculo> lista = cv.getAllVehiculos(pagina);
		
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center text-uppercase">Placa</th>
			<th class="text-center text-uppercase">Tipo de vehículo</th>
			<th class="text-center text-uppercase">Marca</th>
			<th class="text-center text-uppercase">Modelo</th>
			<th class="text-center text-uppercase">Historial</th>
		</tr>
	</thead>
	<tbody>
		<% for(Vehiculo v : lista){ %>
		<tr>
			<td class="text-center"><%= v.getBeautyPlaca() %></td>
			<td class="text-center"><%= v.getTipo().getNombre() %></td>
			<td class="text-center"><%= v.getMarca() %></td>
			<td class="text-center"><%= v.getModelo() %></td>
			<td class="text-center"><a href="#detalle<%= v.getPlaca() %>"
				data-toggle="modal">Detalle</a>
				<div class="modal fade" id="detalle<%= v.getPlaca() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">HISTORIAL DEL
									VEHÍCULO</h2>
							</div>
							<div class="modal-body text-left">
								<p>Selecciona el servicio o el cliente para más
									información</p>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<th>Fecha</th>
											<th>Servicio</th>
											<th>Cliente</th>
										</thead>
										<tr>
											<td>21/09/2018</td>
											<td><a href="#">000368</a></td>
											<td><a href="">1020656565</a></td>
										</tr>
										<tr>
											<td>05/09/2018</td>
											<td><a href="#">000333</a></td>
											<td><a href="">1020656565</a></td>
										</tr>
										<tr>
											<td>16/08/2018</td>
											<td><a href="#">000320</a></td>
											<td><a href="">9810021092</a></td>
										</tr>
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
					listas[i].firstChild.className += " page_active";
				}
			}		
		}
		getPaginas();
	});
</script>
<% } 
   cv.cerrarConexiones();
%>