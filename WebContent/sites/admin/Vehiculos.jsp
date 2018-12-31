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
<script src="../../js/vehiculos.js"></script>
<!-- MODIFICAR JS -->
<title>Admin | VEHÍCULOS</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-uppercase"><span class="icon-drive_eta"></span> Vehículos</h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">
		<div class="row">
			<div class="col-md-5">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Buscar vehículo..." id="input_buscar_cc" name="input_buscar_cc">
					<span class="input-group-btn">
						<button class="boton <%= u.getColor() %>" type="button" id="buscar_cc">
							<span class="icon-search"></span>
						</button>
					</span>
				</div>
			</div>
			<div class="col-md-7">
				<a href="#agregarVehiculo" data-toggle="modal"
					class="boton <%= u.getColor() %> boton-chico pull-right sombra">Nuevo vehículo</a>
				<div class="modal fade" id="agregarVehiculo">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header <%= u.getColor() %>">
								<h3 class="modal-header-title"><span class="icon-assignment"></span> Registrar vehículo</h3>
							</div>
							<form action="" method="post" id="frm_agregar">
								<div class="modal-body text-left">
									<div class="alert bg-ambar" id="alert_campos_obligatorios" role="alert">Los campos con asterisco (*) son obligatorios</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<input name="placa1" type="text" id="inp_placa_agregar1"
													class="form-control" placeholder="PLACA: ABC*"
													maxlength="3">
												<div class="alert alert-danger"
													id="art_vacio_pl1" role="alert">Rellena este campo</div>
												<div class="alert alert-danger"
													id="art_uni_lts_pl1" role="alert">Unicamente letras</div>
												<div class="alert alert-danger"
													id="art_tam_pl1" role="alert">Son exactamente 3 letras</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<input name="placa2" type="text" id="inp_placa_agregar2"
													class="form-control" placeholder="PLACA: 123*"
													maxlength="3">
												<div class="alert alert-danger"
													id="art_vacio_pl2" role="alert">Rellena este campo</div>
												<div class="alert alert-danger"
													id="art_uni_num_pl2" role="alert">Unicamente números</div>
												<div class="alert alert-danger"
													id="art_tam_pl2" role="alert">Son exactamente 3 números</div>	
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<select name="tipo" id="slt_tipo" class="form-control">
													<option value="">TIPO*</option>
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
												<select name="marca" id="slt_marca" class="form-control">
													<option value="">MARCA</option>
													<option value="Audi">Audi</option>
													<option value="BMW">BMW</option>
													<option value="Chevrolet">Chevrolet</option>
													<option value="Citroen">Citroen</option>
													<option value="Daihatsu">Daihatsu</option>
													<option value="Dodge">Dodge</option>
													<option value="Fiat">Fiat</option>
													<option value="Ford">Ford</option>
													<option value="Honda">Honda</option>
													<option value="Hyundai">Hyundai</option>
													<option value="Jeep">Jeep</option>
													<option value="Kia">Kia</option>
													<option value="Marcopolo">Marcopolo</option>
													<option value="Mazda">Mazda</option>
													<option value="Mercedes">Mercedes</option>
													<option value="Mini">Mini</option>
													<option value="Mitsubishi">Mitsubishi</option>
													<option value="Nissan">Nissan</option>
													<option value="Pedgeot">Pedgeot</option>
													<option value="Porsche">Porsche</option>
													<option value="Renault">Renault</option>
													<option value="Seat">Seat</option>
													<option value="Skoda">Skoda</option>
													<option value="SsangYong">SsangYong</option>
													<option value="Subaru">Subaru</option>
													<option value="Tesla">Tesla</option>
													<option value="Toyota">Toyota</option>
													<option value="Volkswagen">Volkswagen</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<input type="text" class="form-control" name="modelo"
													id="inp_modelo" placeholder="MODELO">
											</div>
										</div>
									</div>
									<div class="alert alert-danger"
										id="art_error" role="alert">Placa ya registrada</div>
									<div class="alert alert-success"
										id="art_exito" role="alert">Se agregó el vehículo correctamente</div>	
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
				<div class="table-responsive tabla_vehiculos">
					<jsp:include page="tablaVehiculos.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<% } %>