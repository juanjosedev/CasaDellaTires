<%@ page import="java.util.*, controlador.*, include.*, modelo.*"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	
	if(username == null){
		response.sendRedirect("http://localhost:8080/CasaDellaTires/");
	}else{
		
		Usuario u = (Usuario) username;
		controladorClientes cc = new controladorClientes();
		
		if(!u.getTipo().equals("Admin")){
			response.sendRedirect("../../index.jsp");
		}
		
		modeloLiquidaciones ml = new modeloLiquidaciones();
		try {
			long cedula = Long.parseLong(request.getParameter("user"));
			Cliente c = cc.getCliente(cedula);
			ArrayList<Liquidacion> listaLiquidaciones = ml.getLiquidacionesByCliente(c.getCedula());
%>

<div class="row">
	<div class="col-md-6">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Buscar..." id="input_buscar_cc" name="input_buscar_cc">
			<span class="input-group-btn">
				<button class="boton <%= u.getColor() %>" type="button" id="buscar_cc">
					<span class="icon-search"></span>
				</button>
			</span>
		</div>
	</div>
	<div class="col-md-6">
		<button class="boton <%= u.getColor() %> boton-chico pull-right sombra" id="volver"><span class="icon-navigate_before"></span> VOLVER A LA TABLA</button>
	</div>
</div><br>
<div class="row">
	<div class="col-md-12">
		<div class="container-profile">
			<div class="row">
				<div class="profile-data col-md-12">
					<div class="row">
						<div class="col-md-12">
							<a href="#modificarCliente<%=c.getCedula()%>" data-toggle="modal" class="boton-vacio boton-chico pull-right">Editar</a>
						</div>
					</div>
					<div class="modal fade modal_modificar" id="modificarCliente<%=c.getCedula()%>">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header <%= u.getColor() %>">
									<h3 class="modal-header-title"><span class="icon-edit"></span> Editar cliente</h3>
								</div>
								<form action="" method="post"
									id="form_registro_cliente" name="form_registro_cliente">
									<div class="modal-body text-left">
										<div class="form-group">
											<input name="cc_modificar" type="text" id="cc" class="form-control"
												placeholder="Cédula de la persona"
												value="<%=c.getCedula()%>" readonly="readonly">
										</div>
										<div class="form-group">
											<input name="nombre" type="text" id="input_nombre_modificar"
												class="form-control" placeholder="Nombre"
												value="<%=c.getNombre()%>" readonly="readonly">
											<div class="alert alert-danger" id="alert_modificar_nombre"
												role="alert">Rellena este campo</div>
											<div class="alert alert-danger"
												id="alert_validacion_modificar_nombre" role="alert">Unicamente
												letras</div>
										</div>
										<div class="form-group">
											<input name="primer_apellido" type="text" id="input_primer_apellido_modificar"
												class="form-control" placeholder="Primer apellido"
												value="<%=c.getPrimer_apellido()%>" readonly="readonly">
											<div class="alert alert-danger" id="alert_modificar_apellido"
												role="alert">Rellena este campo</div>
											<div class="alert alert-danger"
												id="alert_validar_modificar_apellido" role="alert">Unicamente
												letras</div>
										</div>
										<div class="form-group">
											<input name="segundo_apellido" type="text"
												id="input_segundo_apellido_modificar" class="form-control"
												placeholder="Segundo apellido"
												value="<%=c.getSegundo_apellido()%>" readonly="readonly">
											<div class="alert alert-danger"
												id="alert_validar_modificar_segundo_apellido" role="alert">Unicamente
												letras</div>	
										</div>
										<div class="form-group">
											<input name="telefono" type="text" id="input_telefono_modificar"
												class="form-control" placeholder="Teléfono"
												value="<%=c.getTelefono()%>">
										</div>
										<div class="form-group">
											<input name="direccion" type="text" id="input_direccion_modificar"
												class="form-control" placeholder="Dirección"
												value="<%=c.getDireccion()%>">
										</div>
										<div class="alert alert-success" id="alert_modificar_exitoso"
															role="alert">Se ha editado correctamente</div>
										<div class="alert alert-danger" id="alert_modificar_error"
															role="alert">ERROR al modificar el cliente</div>
									</div>
									<div class="modal-footer">
										<button type="button"
											class="boton boton-chico pull-left"
											data-dismiss="modal">Cerrar</button>
										<input type="submit" class="boton boton-chico <%= u.getColor() %>"
											id="modificar_cliente" name="modificar_cliente"
											value="Guardar cambios">
									</div>
								</form>
							</div>
						</div>
					</div>
					<br>
					<div class="table-responsive">
						<table class="table table-hover sombra">
							<thead>
								<tr>
									<th class="text-center">Información personal</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<div class="media">
											<div class="media-left media-middle">
												<span class="media-object icon-credit_card fs-em-2"></span>
											</div>
											<div class="media-body">
												<h4 class="media-heading"><%= c.getCedula() %></h4>
												<var class="media-heading-small">Cédula</var>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="media">
											<div class="media-left media-middle">
												<span class="media-object icon-account_box fs-em-2"></span>
											</div>
											<div class="media-body">
												<h4 class="media-heading"><%= c.getNombreCompleto() %></h4>
												<var class="media-heading-small">Nombre completo</var>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="media">
											<div class="media-left media-middle">
												<span class="media-object icon-phone fs-em-2"></span>
											</div>
											<div class="media-body">
												<h4 class="media-heading"><var id="varTelefono"><%= c.getTelefono() %></var></h4>
												<var class="media-heading-small">Teléfono</var>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="media">
											<div class="media-left media-middle">
												<span class="media-object icon-location_on fs-em-2"></span>
											</div>
											<div class="media-body">
												<h4 class="media-heading"><var id="varDireccion"><%= c.getDireccion() %></var></h4>
												<var class="media-heading-small">Dirección</var>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="profile-data col-md-12">
					<div class="table-responsive">
						<table class="table table-hover sombra">
							<thead>
								<tr>
									<th colspan="4" class="text-center">Liquidaciones <span class="label <%= u.getColor() %> pull-right" id="label_pendientes"><%= listaLiquidaciones.size() %></span></th>
								</tr>
							</thead>
							<tbody>
								<tr class="bg-ddd">
									<th class="text-left">Consecutivo</th>
									<th class="text-left">Vehículo</th>
									<th class="text-right">Fecha</th>
									<th class="text-center">Detalle</th>
								</tr>
							<%
								ArrayList<Detalle> lista_dlls = null;
								for(Liquidacion lqd : listaLiquidaciones){
									lista_dlls = lqd.getLista_detalles();
							%>
								<tr>
									<td class="text-left"><a href="#"><%= lqd.getConsecutivo() %></a></td>
									<td class="text-left"><a href="Vehiculos.jsp?profile=<%= lqd.getVehiculo().getFirstPlaca()+lqd.getVehiculo().getSecondPlaca() %>" title="<%= lqd.getVehiculo().getTipo().getNombre() %>"><%= lqd.getVehiculo().getBeautyPlaca() %></a></td>
									<td class="text-right"><%= lqd.infoTiempo(lqd.getEntrada(), lqd.formatoDDMMMYYYYHHMM()) %></td>
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
		</div>
	</div>
</div>
<% 
		} catch (NumberFormatException e){
%>
<div class="row">
	<div class="col-md-6">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Buscar..." id="input_buscar_cc" name="input_buscar_cc">
			<span class="input-group-btn">
				<button class="boton <%= u.getColor() %>" type="button" id="buscar_cc">
					<span class="icon-search"></span>
				</button>
			</span>
		</div>
	</div>
	<div class="col-md-6">
		<button class="boton <%= u.getColor() %> boton-chico pull-right sombra" id="volver"><span class="icon-navigate_before"></span> VOLVER A LA TABLA</button>
	</div>
</div><br>
<div class="row">
	<div class="col-md-12">
		<div class="alert alert-danger" role="alert">Error en el dato de entrada: <strong>"<%= request.getParameter("user") %>"</strong>.</div>	
	</div>
</div>
<%
		} catch (NullPointerException e) {
%>
<div class="row">
	<div class="col-md-6">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Buscar..." id="input_buscar_cc" name="input_buscar_cc">
			<span class="input-group-btn">
				<button class="boton <%= u.getColor() %>" type="button" id="buscar_cc">
					<span class="icon-search"></span>
				</button>
			</span>
		</div>
	</div>
	<div class="col-md-6">
		<button class="boton <%= u.getColor() %> boton-chico pull-right sombra" id="volver"><span class="icon-navigate_before"></span> VOLVER A LA TABLA</button>
	</div>
</div><br>
<div class="row">
	<div class="col-md-12">
		<div class="alert bg-ambar" role="alert">El cliente con el número de cédula <strong><%= request.getParameter("user") %></strong> no fue encontrado.</div>	
	</div>
</div>
<%
		}
		cc.cerrarConexiones();
		ml.cerrarConexion();
	}
%>