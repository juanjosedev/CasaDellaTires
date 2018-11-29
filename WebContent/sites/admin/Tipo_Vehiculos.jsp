<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="include.*" %>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	if(u == null){
		response.sendRedirect("../../index.jsp");
	}else{
		if(!u.getTipo().equals("Admin")){
			response.sendRedirect("../../index.jsp");
		}
	}
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="../../templates/cabecera.jsp"></jsp:include>
<script src="../../js/tipo_vehiculo.js"></script>
<title>Admin | TIPOS DE VEHÍCULOS</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-uppercase"><span class="icon-local_shipping"></span> TIPOS DE VEHÍCULOS</h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">
		<div class="container-fluid">	
			<br>
			<div class="row">
				<div class="col-md-4">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Buscar...">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button">
								<span class="icon-search"></span>
							</button>
						</span>
					</div>
				</div>
				<div class="col-md-8">
					<a href="#agregarLiquidacion" data-toggle="modal"
						class="boton <%= u.getColor() %> boton-chico pull-right sombra">Nuevo tipo</a>
					<div class="modal fade" id="agregarLiquidacion">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h2 class="modal-header-title">AGREGAR</h2>
								</div>
								<form action="creartipovehiculo" method="post"
									id="frm_nuevo_tipo_vehiculo" name="frm_nuevo_tipo_vehiculo">
									<div class="modal-body text-left">
										<div class="row">
											<div class="col-md-12">
												<div class="form-group">
													<input type="text" name="nombre_tipo_vehiculo"
														id="nombre_tipo_vehiculo" class="form-control"
														placeholder="Tipo de vehículo">
												</div>
												<div class="alert alert-danger" id="alert_agregar_nombre"
													role="alert">Rellena este campo</div>
												<div class="alert alert-success"
													id="alert_agregar_exitoso" role="alert">Se agregó
													el registro correctamente</div>
												<div class="alert alert-danger" id="alert_agregar_error"
													role="alert"">ERROR al agregar el campo</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button"
											class="boton boton-chico pull-left"
											data-dismiss="modal">Cerrar</button>
										<input type="submit" class="boton boton-chico <%= u.getColor() %>"
											id="submit_nuevo_tipo_vehiculo"
											name="submit_nuevo_tipo_vehiculo" value="Agregar">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12">
					<div class="table-responsive">
						<jsp:include page="tablaTipoVehiculos.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>