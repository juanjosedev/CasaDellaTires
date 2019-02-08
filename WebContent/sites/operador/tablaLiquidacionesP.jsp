<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorLiquidaciones cl = new controladorLiquidaciones();
	ArrayList<Liquidacion> lista_pendientes = cl.getLiquidacionesPendientes();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
%>
<!-- <h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span>Pendientes </h3> -->
<br>
<div class="table-responsive">	
	<table class="table table-hover sombra">
		<thead>
			<tr>
				<th class="text-center" colspan="5">Tabla de liquidaciones pendientes <span class="label <%= u.getColor() %> pull-right" id="label_pendientes"><%= lista_pendientes.size() %></span></th>
			</tr>
		</thead>
		<tbody>
			<tr class="bg-ddd">
				<th class="text-center">Consecutivo</th>
				<th class="text-center">Placa</th>
				<th class="text-center">Cédula</th>
				<th class="text-center">Fecha</th>
				<th class="text-center">Detalle</th>
			</tr>
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
					data-toggle="modal"><span class="icon-dehaze"></span></a>
					<div class="modal fade" id="detalle<%= l.getConsecutivo() %>">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header  <%= u.getColor() %>">
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
									<button type="button"
										class="boton boton-chico <%= u.getColor() %> btn_terminar"
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
</div>