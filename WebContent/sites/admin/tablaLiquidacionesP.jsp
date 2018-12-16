<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorLiquidaciones cl = new controladorLiquidaciones();
	ArrayList<Liquidacion> lista_pendientes = cl.getLiquidacionesPendientes();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
%>
<h3>Liquidaciones pendientes <span class="label <%= u.getColor() %> pull-right" id="label_pendientes"><%= lista_pendientes.size() %></span></h3>
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
			for(Liquidacion l: lista_pendientes){
					ArrayList<Detalle> lista_dlls = l.getLista_detalles();
		%>
		<tr id="fila<%= l.getConsecutivo() %>" class="diosnoexiste">
			<td class="text-center"><%= l.getConsecutivo() %></td>
			<td class="text-center"><%= l.getVehiculo().getBeautyPlaca() %></td>
			<td class="text-center"><%= l.getCliente().getCedula() %></td>
			<td class="text-center"><%= l.infoTiempo(l.getEntrada(), l.formatoDDMMMYYYYHHMM()) %></td>
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
													<li><i><b>Entrada: </b><%= l.infoTiempo(l.getEntrada(), l.formatoDDMMMYYYYHHMM()) %></i></li>
													<li><i><b>Salida: </b>Pendiente</i></li>
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
								<button type="button"
									class="boton boton-chico bg-cian btn_terminar"
 									id="<%= l.getConsecutivo() %>">Terminar</button>
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<% 
		}
		try{
			cl.cerrarConexionesControlador();
		} catch (Exception e){
			e.printStackTrace();
		}
		%>
	</tbody>
</table>