<%@ page import="java.util.ArrayList, include.*, controlador.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	if(username == null){
		response.sendRedirect("http://localhost:8080/CasaDellaTires/");
	}else{
		Usuario u = (Usuario) username;
		if(!u.getTipo().equals("Admin")){
			response.sendRedirect("../../index.jsp");
		}	
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="../../templates/cabecera.jsp"></jsp:include>
<script src="../../js/servicios2.js"></script>
<title>Admin | SERVICIOS2</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2><span class="icon-local_car_wash"></span> Servicios</h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">
		<div class="row">
			<div class="col-md-12">
			<% 
				if (request.getParameter("servicio") == null) {	
			%>
				<a href="#nuevoServicio" data-toggle="modal"
					class="boton <%= u.getColor() %> boton-chico pull-right sombra">Nuevo servicio</a>
				<div class="modal fade" id="nuevoServicio">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h3 class="modal-header-title"><span class="icon-assignment"></span> Crear nuevo servicio</h3>
							</div>
							<form action="" method="post" id="frm_nuevo_servicio" name="frm_nuevo_servicio"> 
								<div class="modal-body text-left">
									<div class="alert bg-ambar" id="alert_campos_obligatorios" role="alert"><span class="icon-warning"></span> Los campos con asterisco (*) son obligatorios</div>
									<div class="form-group">
										<label for="inp_servicio">Nombre del servicio*</label>
										<input type="text" name="inp_servicio" id="inp_servicio" class="form-control" placeholder="Nombre">
										<div class="alert alert-danger" id="alt_servicio" role="alert"><span class="icon-error"></span> <var id="msg"></var></div>
									</div>
									<div class="">
										<label>Tipo de vehículos* <i>(Mínimo un vehículo)</i></label>
										<div class="table-responsive">
											<table class="table table-hover table-modal sombra">
												<thead>
													<tr class="bg-ddd">
														<th>Vehículo</th>
														<th class="text-right">Precio</th>
													</tr>
												</thead>
												<tbody>
													<%
														controladorTipoVehiculos ctv = new controladorTipoVehiculos();
														ArrayList<tipoVehiculo> lista = ctv.getAllTipoVehiculos(-1);
														for (tipoVehiculo v : lista) {
													%>
													<tr>
														<td><%= v.getNombre() %></td>
														<td>
															<div class="input-group">
										                         <input type="text" class="form-control inp_precio" id="inp_precio" value="$" readonly>
										                         <div class="input-group-addon"><input type="checkbox" value="<%= v.getId() %>" name="tipoVehiculo[]" id="0" class="inp_chk_servicio"></div>
										                     </div>
														</td>
													</tr>													
													<% 
														}
														ctv.cerrarConexion();
													%>
												</tbody>
											</table>
										</div>
										<div class="alert alert-danger" id="alt_crear_error" role="alert"><span class="icon-error"></span> <var id="msg"></var></div>
										<div class="alert alert-success" id="alt_crear_exito" role="alert"><span class="icon-check_circle"></span> <var id="msg"></var></div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button"
										class="boton boton-chico pull-left"
										data-dismiss="modal">Cerrar</button>
									<input type="submit" class="boton boton-chico <%= u.getColor() %>"
										id="sbt_crear_servicio" name="sbt_crear_servicio" value="Crear servicio">
								</div>
							</form>
						</div>
					</div>
				</div>
				<% 
				} 
			%>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<% 
					if (request.getParameter("servicio") != null) {
					
				%>
					<jsp:include page="templServicio.jsp"></jsp:include>
				<% 	
					} else {
				%>
				<div class="table-responsive tabla_servicios">
					<jsp:include page="tablaServicios2.jsp"></jsp:include>
				</div>
				<% } %>
			</div>
		</div>
	</div>
</body>
</html>
<% } %>