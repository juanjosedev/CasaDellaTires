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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="../../templates/cabecera.jsp"></jsp:include>
<script src="../../js/operadores.js"></script>
<title>Admin | OPERADORES</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-uppercase"><span class="icon-group"></span> Operadores</h2>
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
						<input type="text" class="form-control" placeholder="Buscar..." id="input_buscar_cc" name="input_buscar_cc">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" id="buscar_cc">
								<span class="icon-search"></span>
							</button>
						</span>
					</div>
				</div>
				<div class="col-md-8">
					<a href="#agregarOperador" data-toggle="modal"
						class="boton <%= u.getColor() %> boton-chico pull-right sombra">Nuevo operador</a>
					<div class="modal fade" id="agregarOperador">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h2 class="modal-header-title">REGISTRAR</h2>
								</div>
								<form action="post" method="crearcliente"
									id="frm_nuevo_cliente" name="frm_nuevo_cliente">
									<div class="modal-body text-left">
										<h3>Datos del operador</h3>
										<i>Los campos con asterisco (*) son obligatorios</i>
										<div class="form-group">
											<input name="cc_agregar" type="text" id="input_cc" class="form-control"
												placeholder="Cédula de la persona*">
											<div class="alert alert-danger" id="alert_agregar_cc"
												role="alert">Rellena este campo</div>
											<div class="alert alert-danger" id="alert_validacion_cc"
												role="alert">Unicamente números</div>
										</div>
										<div class="form-group">
											<input name="nombre" type="text" id="input_nombre"
												class="form-control" placeholder="Nombre*">
											<div class="alert alert-danger" id="alert_agregar_nombre"
												role="alert">Rellena este campo</div>
											<div class="alert alert-danger"
												id="alert_validacion_nombre" role="alert">Unicamente
												letras</div>
										</div>
										<div class="form-group">
											<input name="primer_apellido" type="text"
												id="input_primer_apellido" class="form-control"
												placeholder="Primer apellido*">
											<div class="alert alert-danger" id="alert_agregar_apellido"
												role="alert">Rellena este campo</div>
											<div class="alert alert-danger"
												id="alert_validar_apellido" role="alert">Unicamente
												letras</div>
										</div>
										<div class="form-group">
											<input name="segundo_apellido" type="text"
												id="input_segundo_apellido" class="form-control"
												placeholder="Segundo apellido">
											<div class="alert alert-danger"
												id="alert_validar_segundo_apellido" role="alert">Unicamente
												letras</div>	
										</div>
										<div class="form-group">
											<input name="telefono" type="text" id="input_telefono"
												class="form-control" placeholder="Teléfono">
										</div>
										<div class="form-group">
											<input name="direccion" type="text" id="input_direccion"
												class="form-control" placeholder="Dirección">
										</div>
										<div class="alert alert-success" id="alert_agregar_exitoso"
												role="alert">El cliente se registró correctamente</div>
										<div class="alert alert-danger" id="alert_agregar_error"
												role="alert">Cédula ya registrada</div>
									</div>
									<div class="modal-footer">
										<button type="button"
											class="boton boton-chico pull-left"
											data-dismiss="modal">Cerrar</button>
										<input type="submit" class="boton boton-chico <%= u.getColor() %>"
											id="submit_nuevo_cliente" name="submit_nuevo_cliente"
											value="Agregar">
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
					<div class="table-responsive" id="tabla_operadores">
						<jsp:include page="tablaOperadores.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>