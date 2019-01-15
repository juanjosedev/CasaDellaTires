<%@ page import="include.*" %>
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
		<div class="row">
			<div class="col-md-5">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Buscar cliente..." id="input_buscar_cc" name="input_buscar_cc">
					<span class="input-group-btn">
						<button class="boton <%= u.getColor() %>" type="button" id="buscar_cc">
							<span class="icon-search"></span>
						</button>
					</span>
				</div>
			</div>
			<div class="col-md-7">
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
										<input name="usuario" type="text" id="inp_usuario" class="form-control"
											placeholder="Nombre de usuario*">
										<div class="alert alert-danger" id="alert_usuario_error"
											role="alert"><var id="mensaje_usuario_error"></var></div>
									</div>
									<div class="form-group">
										<input name="clave" type="password" id="inp_clave" class="form-control"
											placeholder="Contraseña*">
										<div class="alert alert-danger" id="alert_clave_error"
											role="alert"><var id="mensaje_clave_error"></var></div>
									</div>
									<div class="form-group">
										<input name="nombre" type="text" id="inp_nombre" class="form-control"
											placeholder="Nombre del operador*">
										<div class="alert alert-danger" id="alert_nombre_error"
											role="alert"><var id="mensaje_nombre_error"></var></div>
									</div>
									<div class="form-group">
										<input name="primer_apellido" type="text" id="inp_primer_apellido" class="form-control"
											placeholder="Primer apellido*">
										<div class="alert alert-danger" id="alert_primer_apellido_error"
											role="alert"><var id="mensaje_primer_apellido_error"></var></div>
									</div>
									<div class="form-group">
										<input name="segundo_apellido" type="text" id="inp_segundo_apellido" class="form-control"
											placeholder="Segundo apellido">
										<div class="alert alert-danger" id="alert_segundo_apellido_error"
											role="alert"><var id="mensaje_segundo_apellido_error"></var></div>
									</div>
									<div class="form-group">
										<input name="telefono" type="text" id="inp_telefono" class="form-control"
											placeholder="Teléfono*">
										<div class="alert alert-danger" id="alert_telefono_error"
											role="alert"><var id="mensaje_telefono_error"></var></div>
									</div>
									<div class="form-group">
										<input name="direccion" type="text" id="inp_direccion" class="form-control"
											placeholder="Dirección*">
										<div class="alert alert-danger" id="alert_direccion_error"
											role="alert"><var id="mensaje_direccion_error"></var></div>
									</div>
									<div class="alert alert-success" id="alert_agregar_exitoso"
											role="alert">El operador se registró correctamente</div>
									<div class="alert alert-danger" id="alert_agregar_error"
											role="alert">El usuario ya existe</div>
								</div>
								<div class="modal-footer">
									<button type="button"
										class="boton boton-chico pull-left"
										data-dismiss="modal">Cerrar</button>
									<input type="submit" class="boton boton-chico <%= u.getColor() %>"
										id="sbt_agregar_operador" name="sbt_agregar_operador"
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
</body>
</html>
<% } %>