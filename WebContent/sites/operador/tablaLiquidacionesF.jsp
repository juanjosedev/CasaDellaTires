<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorLiquidaciones cl = new controladorLiquidaciones();
	int pagina = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
	ArrayList<Liquidacion> lista_completas = cl.getLiquidacionesCompletas(pagina);
	int lqdCompletas = cl.getContarLiquidacionesCompletas();
	String paginacion =  Paginacion.getPaginacion("Liquidaciones.jsp", lqdCompletas);
%>
<h3>Liquidaciones completas</h3>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center">Consecutivo</th>
			<th class="text-center">Placa</th>
			<th class="text-center">Cédula</th>
			<th class="text-center">Fecha</th>
			<th class="text-center">Detalle</th>
		</tr>
	</thead>
	<tbody>
		<% 
			for(Liquidacion l: lista_completas){
					ArrayList<Detalle> lista_dlls = l.getLista_detalles();
		%>
		<tr>
			<td class="text-center"><%= l.getConsecutivo() %></td>
			<td class="text-center"><%= l.getVehiculo().getBeautyPlaca() %></td>
			<td class="text-center"><%= l.getCliente().getCedula() %></td>
			<td class="text-center"><%= l.getHora_inicio() %></td>
			<td class="text-center"><a href="#detalle<%= l.getConsecutivo() %>"
				data-toggle="modal">Detalle</a>
				<div class="modal fade" id="detalle<%= l.getConsecutivo() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">INFORMACIÓN</h2>
							</div>
							<div class="modal-body text-left">
								<div class="row">
									<div class="col-md-6">
										<div class="media">
											<div class="media-left">
												<span class="media-object icon-person fs-em-2"></span>
											</div>
											<div class="media-body">
												<h3 class="media-heading">CLIENTE</h3>
												<ul>
													<li><i><%= l.getCliente().getNombreCompleto() %></i></li>
													<li><i><%= l.getCliente().getCedula() %></i></li>
													<li><i><%= l.getCliente().getTelefono() %></i></li>
													<li><i><%= l.getCliente().getDireccion() %></i></li>
												</ul>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="media">
											<div class="media-left">
												<span class="media-object icon-drive_eta fs-em-2"></span>
											</div>
											<div class="media-body">
												<h3 class="media-heading">VEHÍCULO</h3>
												<ul>
													<li><i><%= l.getVehiculo().getBeautyPlaca() %></i></li>
													<li><i><%= l.getVehiculo().getTipo().getNombre() %></i></li>
													<li><i><%= l.getVehiculo().getMarca() %></i></li>
													<li><i><%= l.getVehiculo().getModelo() %></i></li>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<hr>
								<div class="table-responsive">
									<table class="table table-bordered">
										<thead>
											<th>Servicio</th>
											<th>Precio</th>
										</thead>
										<% for(Detalle d: lista_dlls){ %>
										<tr>
											<td><%= d.getNombre() %></td>
											<td class="text-right"><%= d.getPrecio() %></td>
										</tr>
										<% } %>
									</table>
								</div>
								<hr>
								<div class="row">
									<div class="col-md-6">
										<div class="media">
											<div class="media-left">
												<span class="media-object icon-query_builder fs-em-2"></span>
											</div>
											<div class="media-body">
												<h3 class="media-heading">INFORMACIÓN</h3>
												<ul>
													<li><i><b>Consecutivo: </b><%= l.getConsecutivo() %></i></li>
													<li><i><b>Entrada: </b><%= l.getHora_inicio() %></i></li>
													<li><i><b>Salida: </b><%= l.getHora_final() %></i></li>
												</ul>	
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="media">
											<div class="media-left">
												<span class="media-object icon-attach_money fs-em-2"></span>
											</div>
											<div class="media-body">
												<h3 class="media-heading">TOTAL: $<i><%= l.getTotal() %></i></h3>
												<ul>
													<li><i><b>Subtotal: $</b><%= l.getSubtotal() %></i></li>
													<li><i><b>Descuento: $</b><%= l.getDescuento() %></i></li>
												</ul>	
											</div>
										</div>
									</div>
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
			}
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
<%= paginacion %>
<%
	try{
		cl.cerrarConexionesControlador();
	}catch(Exception e){
		e.printStackTrace();
	}
%>