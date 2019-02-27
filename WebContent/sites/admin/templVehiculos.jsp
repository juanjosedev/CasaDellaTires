<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, controlador.*, include.*, modelo.*"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	
	if(username == null){
		response.sendRedirect("http://localhost:8080/CasaDellaTires/");
	}else{
		
		Usuario u = (Usuario) username;
		controladorVehiculos cv = new controladorVehiculos();
%>
<div class="row">
	<div class="col-md-6">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Buscar vehículo..." id="input_vehiculo_cc" name="input_vehiculo_cc">
			<span class="input-group-btn">
				<button class="boton <%= u.getColor() %>" type="button" id="buscar_vehiculo">
<!-- 					<span class="icon-search"></span> -->
				</button>
			</span>
		</div>
	</div>
	<div class="col-md-6">
		<a href="Vehiculos.jsp" class="boton boton-chico pull-right <%= u.getColor() %>"><span class="icon-navigate_before"></span> Volver a la tabla</a>
	</div>
</div><br>
<%		
		if(request.getParameter("query") != null){
			try{
				
				String query = request.getParameter("query");
				ArrayList<Vehiculo> lista = cv.getBusqueda(query);
%>
<div class="row">
	<div class="col-md-12">
		<div class="table-responsive">
			<table class="table table-hover sombra">
				<thead>
					<tr>
						<td colspan="2">Se encontraron <b><%= lista.size() %></b> resultados.</td>
					</tr>
				</thead>
				<tbody>
					<tr class="bg-ddd">
						<th>Placa</th>
						<th>Tipo de vehículo</th>
						<th>Marca</th>
						<th>Modelo</th>
						<th class="text-center">Ver perfil</th>
					</tr>		
<%			
				for(Vehiculo v: lista){
%>
					<tr>
						<td><%= v.getBeautyPlaca() %></td>
						<td><%= v.getTipo().getNombre() %></td>
						<td><%= v.getMarca() %></td>
						<td><%= v.getModelo() %></td>
						<td class="text-center"><a href="Vehiculos.jsp?profile=<%= v.getFirstPlaca()+v.getSecondPlaca() %>"><span class="icon-remove_red_eye"></span></a></td>
					</tr>			
<%
				}
%>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%			
			} catch(NullPointerException e) {
				response.getWriter().print("No encontrado");
			}
			
		} else if (request.getParameter("profile") != null){
				modeloLiquidaciones ml = new modeloLiquidaciones();
			try{
				
				String placa = request.getParameter("profile");
				Vehiculo v = cv.getVehiculo(placa);
				ArrayList<Liquidacion> historial = ml.getLiquidacionesByCliente(placa);
%>
<%= v.getBeautyPlaca() == null ? null : "" %>
<div class="row">
	<div class="col-md-12">
		<div class="table-responsive">
			<table class="table table-hover sombra">
				<thead>
					<tr>
						<th class="text-center">Información del vehículo</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<div class="media">
								<div class="media-left media-middle">
									<span class="media-object icon-loyalty fs-em-2"></span>
								</div>
								<div class="media-body">
									<h4 class="media-heading"><%= v.getBeautyPlaca() %></h4>
									<var class="media-heading-small">Placa</var>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="media">
								<div class="media-left media-middle">
									<span class="media-object icon-directions_car fs-em-2"></span>
								</div>
								<div class="media-body">
									<h4 class="media-heading"><%= v.getTipo().getNombre() %></h4>
									<var class="media-heading-small">Tipo de vehículo</var>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="media">
								<div class="media-left media-middle">
									<span class="media-object icon-local_offer fs-em-2"></span>
								</div>
								<div class="media-body">
									<h4 class="media-heading"><%= !v.getMarca().equals("") ? v.getMarca() : " - " %></h4>
									<var class="media-heading-small">Marca</var>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="media">
								<div class="media-left media-middle">
									<span class="media-object icon-local_offer fs-em-2"></span>
								</div>
								<div class="media-body">
									<h4 class="media-heading"><%= !v.getModelo().equals("") ? v.getModelo() : " - " %></h4>
									<var class="media-heading-small">Modelo</var>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="col-md-12">
		<div class="table-responsive">
			<table class="table table-hover sombra">
				<thead>
					<tr>
						<th class="text-center" colspan="4">Liquidaciones del vehículo</th>
					</tr>
				</thead>
				<tbody>
					<tr class="bg-ddd">
						<th class="text-center">Consecutivo</th>
						<th class="text-center">Cliente</th>
						<th class="text-center">Fecha</th>
						<th class="text-center">Detalle</th>
					</tr>
				<% 
					for(Liquidacion lqd: historial){ 
						ArrayList<Detalle> lista_dlls = lqd.getLista_detalles();
				%>
					<tr>
						<td class="text-center"><%= lqd.getConsecutivo() %></td>
						<td class="text-center"><a href="Clientes.jsp?user=<%= lqd.getCliente().getCedula() %>" title="<%= lqd.getCliente().getNombreCompleto() %>"><%= lqd.getCliente().getCedula() %></a></td>
						<td class="text-center"><%= lqd.infoTiempo(lqd.getEntrada(), lqd.formatoDDMMMYYYYHHMM()) %></td>
						<td class="text-center"><a href="#detalle<%= lqd.getConsecutivo() %>" data-toggle="modal"><span class="icon-dehaze"></span></a>
							<div class="modal fade" id="detalle<%= lqd.getConsecutivo() %>">
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
																<li><i><%= lqd.getCliente().getNombreCompleto() %></i></li>
																<li><i><%= lqd.getCliente().getCedula() %></i></li>
																<li><i><%= lqd.getCliente().getTelefono() %></i></li>
																<li><i><%= lqd.getCliente().getDireccion() %></i></li>
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
																<li><i><%= lqd.getVehiculo().getBeautyPlaca() %></i></li>
																<li><i><%= lqd.getVehiculo().getTipo().getNombre() %></i></li>
																<li><i><%= lqd.getVehiculo().getMarca() %></i></li>
																<li><i><%= lqd.getVehiculo().getModelo() %></i></li>
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
																<li><i><b>Consecutivo: </b><%= lqd.getConsecutivo() %></i></li>
																<li><i><b>Fecha entrada: </b><%= lqd.infoTiempo(lqd.getEntrada(), lqd.formatoDDMMMYYYYHHMM()) %></i></li>
																<li><i><b>Fecha salida: </b><%= lqd.getSalida() != null ? lqd.infoTiempo(lqd.getSalida(), lqd.formatoDDMMMYYYYHHMM()) : "Pendiente" %></i></li>
																<li><i><b>Duración: </b><%= lqd.getSalida() != null ? lqd.getDuracion() : "Pendiente" %></i></li>
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
															<h4 class="media-heading">Total: $<i><%= lqd.getTotal() %></i></h4>
															<ul>
																<li><i><b>Subtotal: $</b><%= lqd.getSubtotal() %></i></li>
																<li><i><b>Descuento: $</b><%= lqd.getDescuento() %></i></li>
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
				<% } %>
				</tbody>
			</table>
		</div>
	</div>
</div>					
<%
						
			} catch (NullPointerException e) {
%>
<div class="row">
	<div class="col-md-12">
		<div class="alert bg-ambar" role="alert">El vehículo con la placa <strong><%= request.getParameter("profile") %></strong> no fue encontrado.</div>	
	</div>
</div>
<%
			} finally {
				ml.cerrarConexion();
			}
			
		}

		cv.cerrarConexiones();
	
	}
%>