<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorLiquidaciones cl = new controladorLiquidaciones();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	int pagina = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
	ArrayList<Liquidacion> lista_completas = cl.getLiquidacionesCompletas(pagina);
	int lqdCompletas = cl.getContarLiquidacionesCompletas();
	String paginacion =  Paginacion.getPaginacion("Liquidaciones.jsp", lqdCompletas);
%>
<!-- <h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span>Finalizadas</h3> -->
<div class="table-responsive">
	<table class="table table-hover sombra">
		<thead>
			<tr>
				<th class="text-center" colspan="5">Tabla de liquidaciones finalizadas</th>
			</tr>
		</thead>
		<tbody>
			<tr class="bg-ddd">
				<th class="text-right">Consecutivo</th>
				<th class="text-left">Placa</th>
				<th class="text-right">Cédula</th>
				<th class="text-right">Fecha</th>
				<th class="text-center">Detalle</th>
			</tr>
			<% 
				for(Liquidacion l: lista_completas){
						ArrayList<Detalle> lista_dlls = l.getLista_detalles();
			%>
			<tr>
				<td class="text-right"><%= l.getConsecutivo() %></td>
				<td class="text-left"><%= l.getVehiculo().getBeautyPlaca() %></td>
				<td class="text-right"><%= l.getCliente().getCedula() %></td>
				<td class="text-right"><%= l.infoTiempo(l.getEntrada(), l.formatoDDMMMYYYYHHMM()) %></td>
				<td class="text-center"><a href="#detalle<%= l.getConsecutivo() %>"
					data-toggle="modal"><span class="icon-dehaze"></span></a>
					<div class="modal fade" id="detalle<%= l.getConsecutivo() %>">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header <%= u.getColor() %>">
									<h3 class="modal-header-title text-left"><span class="icon-info_outline"></span> INFORMACIÓN</h3>
								</div>
								<div class="modal-body text-left">
									<div class="row">
										<div class="col-md-6">
											<div class="media">
												<div class="media-left">
													<span class="media-object icon-person fs-em-2"></span>
												</div>
												<div class="media-body">
													<h4 class="media-heading">Cliente</h4>
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
													<h4 class="media-heading">Vehículo</h4>
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
										<table class="table table-hover sombra table-modal">
											<thead>
												<tr>
													<th colspan="2">
														<div class="media">
															<div class="media-left">
																<span class="media-object icon-local_car_wash fs-em-2"></span>
															</div>
															<div class="media-body">
																<h4 class="media-heading">Servicios</h4>
															</div>
														</div>
													</th>
												</tr>
											</thead>
											<tbody>
												<tr class="bg-ddd">
													<th>Servicio</th>
													<th class="text-right">Precio</th>
												</tr>
												<% for(Detalle d: lista_dlls){ %>
												<tr>
													<td><%= d.getNombre() %></td>
													<td class="text-right"><%= d.getPrecio() %></td>
												</tr>
												<% } %>
											</tbody>
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
													<h4 class="media-heading">Información</h4>
													<ul>
														<li><i><b>Consecutivo: </b><%= l.getConsecutivo() %></i></li>
														<li><i><b>Fecha entrada: </b><%= l.infoTiempo(l.getEntrada(), l.formatoDDMMMYYYYHHMM()) %></i></li>
														<li><i><b>Fecha salida: </b><%= l.infoTiempo(l.getSalida(), l.formatoDDMMMYYYYHHMM()) %></i></li>
														<li><i><b>Duración: </b><%= l.getDuracion() %></i></li>
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
													<h4 class="media-heading">Total: <var class="pull-right">$<%= l.getTotal() %></var></h4>
													<ul>
														<li><b><i>Subtotal:</i> </b><i class="pull-right">$<%= l.getSubtotal() %></i></li>
														<li><b><i>Descuento:</i> </b><i class="pull-right">$<%= l.getDescuento() %></i></li>
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
<%= paginacion %>
<%
	try{
		cl.cerrarConexionesControlador();
	}catch(Exception e){
		e.printStackTrace();
	}
%>