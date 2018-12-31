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
	<div class="col-md-5">
		<div class="input-group">
			<input type="text" class="form-control" placeholder="Buscar..." id="input_buscar_cc" name="input_buscar_cc">
			<span class="input-group-btn">
				<button class="boton <%= u.getColor() %>" type="button" id="buscar_cc">
					<span class="icon-search"></span>
				</button>
			</span>
		</div>
	</div>
	<div class="col-md-7">
		<button class="boton <%= u.getColor() %> boton-chico pull-right sombra" id="volver"><span class="icon-navigate_before"></span> VOLVER A LA TABLA</button>
	</div>
</div>
<br>
<div class="row">
	<div class="col-md-12">
		<div class="container-profile">
			<div class="row">
				<div class="col-md-12">
					<div class="profile-header sombra">
						<span class="icon-person"></span>
						<h2 class="text-center text-uppercase"><%= c.getNombre() %></h2>
					</div>
				</div>
			</div>
		<div class="row">
			<div class="profile-data col-md-6">
				<h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span> Información personal <a href="#modificarCliente<%=c.getCedula()%>" data-toggle="modal" class="boton-vacio boton-chico pull-right">Editar</a></h3>
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
											value="<%=c.getNombre()%>">
										<div class="alert alert-danger" id="alert_modificar_nombre"
											role="alert">Rellena este campo</div>
										<div class="alert alert-danger"
											id="alert_validacion_modificar_nombre" role="alert">Unicamente
											letras</div>
									</div>
									<div class="form-group">
										<input name="primer_apellido" type="text" id="input_primer_apellido_modificar"
											class="form-control" placeholder="Primer apellido"
											value="<%=c.getPrimer_apellido()%>">
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
											value="<%=c.getSegundo_apellido()%>">
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
				<ul class="sombra">
					<li>
						<div class="media">
							<div class="media-left media-middle">
								<span class="media-object icon-credit_card fs-em-2 bg-cian"></span>
							</div>
							<div class="media-body">
								<h4 class="media-heading"><%= c.getCedula() %></h4>
								<i>Cédula</i>
							</div>
						</div>
					</li>
					<li>
						<div class="media">
							<div class="media-left media-middle">
								<span class="media-object icon-account_box fs-em-2 bg-azul-claro"></span>
							</div>
							<div class="media-body">
								<h4 class="media-heading"><%= c.getNombreCompleto() %></h4>
								<i>Nombre completo</i>
							</div>
						</div>
					</li>
					<li>
						<div class="media">
							<div class="media-left media-middle">
								<span class="media-object icon-phone fs-em-2 bg-azul"></span>
							</div>
							<div class="media-body">
								<h4 class="media-heading"><var id="varTelefono"><%= c.getTelefono() %></var></h4>
								<i>Teléfono</i>
							</div>
						</div>
					</li>
					<li>
						<div class="media">
							<div class="media-left media-middle">
								<span class="media-object icon-location_on fs-em-2 bg-indigo"></span>
							</div>
							<div class="media-body">
								<h4 class="media-heading"><var id="varDireccion"><%= c.getDireccion() %></var></h4>
								<i>Dirección</i>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="profile-data col-md-6">
				<h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span> Liquidaciones <span class="label <%= u.getColor() %> pull-right" id="label_pendientes"><%= listaLiquidaciones.size() %></span></h3><br>
				<div class="table-responsive">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th class="text-center">Consecutivo</th>
								<th class="text-center">Vehículo</th>
								<th class="text-center">Fecha</th>
							</tr>
						</thead>
						<tbody>
						<%
							for(Liquidacion lqd : listaLiquidaciones){
						%>
							<tr>
								<td class="text-center"><a href="#"><%= lqd.getConsecutivo() %></a></td>
								<td class="text-center"><a href="#"><%= lqd.getVehiculo().getBeautyPlaca() %></a></td>
								<td class="text-center"><%= lqd.infoTiempo(lqd.getEntrada(), lqd.formatoDDMMMYYYYHHMM()) %></td>
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
			response.getWriter().print("error al convvertir ");
		} catch (NullPointerException e) {
			response.getWriter().print("no existe el usuario");
		}
	
		ml.cerrarConexion();
	}
%>