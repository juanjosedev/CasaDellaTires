<%@ page import="java.util.ArrayList, include.*, controlador.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<script src="../../js/servicios.js"></script>
<title>Admin | SERVICIOS</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-uppercase"><span class="icon-settings"></span> Servicios</h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">
		<div class="row">
			<div class="col-md-12">
				<a href="#agregarLiquidacion" data-toggle="modal"
					class="boton <%= u.getColor() %> boton-chico pull-right sombra">Nuevo servicio</a>
				<div class="modal fade" id="agregarLiquidacion">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">REGISTRAR</h2>
							</div>
							<form action="" method="post" id="frm_agregar" name="frm_agregar"> 
								<div class="modal-body text-left">
									<h3>Datos del servicio</h3>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<select name="tipo" id="slt_tipo" class="form-control">
													<option value="">TIPO</option>
													<%
														controladorTipoVehiculos ctv = new controladorTipoVehiculos();
														ArrayList<tipoVehiculo> lista = ctv.getAllTipoVehiculos(-1);
														for (tipoVehiculo v : lista) {
													%>
													<option value="<%= v.getId() %>"><%= v.getNombre() %></option>
													<% 
														}
														ctv.cerrarConexion();
													%>
												
												</select>
												<div class="alert alert-danger"
													id="art_vacio_tipo" role="alert">Selecciona el tipo de vehículo</div>
											</div>
										</div>	
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<input name="nombre" type="text" id="inp_nombre_agregar" class="form-control"
													placeholder="NOMBRE">
												<div class="alert alert-danger"
													id="art_vacio_nombre" role="alert">Rellena este campo</div>
												<div class="alert alert-danger"
													id="art_uni_alfanum_nombre" role="alert">Unicamente caracteres alfanuméricos</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<input name="precio" type="text" id="inp_precio_agregar" class="form-control"
													placeholder="PRECIO">
												<div class="alert alert-danger"
													id="art_vacio_precio" role="alert">Rellena este campo</div>
												<div class="alert alert-danger"
													id="art_uni_num_precio" role="alert">Unicamente números</div>
											</div>
										</div>
									</div>
									<div class="alert alert-danger"
										id="art_error" role="alert">ERROR al agregar el servicio</div>
									<div class="alert alert-success"
										id="art_exito" role="alert">Se agregó el servicio  correctamente</div>	
								</div>
								<div class="modal-footer">
									<button type="button"
										class="boton boton-chico pull-left"
										data-dismiss="modal">Cerrar</button>
									<input type="submit" class="boton boton-chico <%= u.getColor() %>"
										id="sbt_agregar" name="sbt_agregar" value="Agregar">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="table-responsive tabla_servicios">
					<jsp:include page="tablaServicios.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<% } %>