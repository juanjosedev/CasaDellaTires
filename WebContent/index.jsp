<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="include.*" %>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	if(u != null){
		if(u.getTipo().equals("Admin")){
			response.sendRedirect("sites/admin/Inicio.jsp");
		}else{
			response.sendRedirect("sites/operador/Liquidaciones.jsp");
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta charset="UTF-8">
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/sesion.js"></script>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<!-- <link rel="stylesheet" type="text/css" href="../../fonts_icons/iconos/style.css">  -->
	<link rel="stylesheet" type="text/css" href="fonts_icons/iconos2/style.css"><!-- 
	<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet"> -->
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<title>Casa Della Tires</title>
</head>
<body>
	<div class="container-fluid header_inicio sombra">
		<div class="row">
			<div class="col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-0">
				<h1>Inicio de sesión</h1>
			</div>
		</div>
	</div>
	<br>
	<div class="container-fluid body_inicio">
		<div class="row">
			<div class="caja_form col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-0 sombra">
				<form method="POST" action="" id="frm_inicio" class="frm-inicio">
					<div class="form-group">
						<label for="usuario">Usuario:</label>
						<input type="text" name="usuario" id="usuario" class="form-control" placeholder="Nombre de usuario">
						<div class="alert alert-danger" id="alert_obligatorio_usuario"
							role="alert">Campo obligatorio</div>
					</div>
					<div class="form-group">
						<label for="clave">Contraseña:</label>
						<input type="password" name="clave" id="clave" class="form-control" placeholder="Contraseña">
						<div class="alert alert-danger" id="alert_obligatorio_clave"
							role="alert">Campo obligatorio</div>
					</div>
					<div class="alert alert-danger" id="alert_error"
							role="alert">Usuario o contraseña incorrectas</div>
					<input type="submit" name="iniciar_sesion" id="sbt_iniciar_sesion" class="boton bg-azul-claro sombra boton-bloque" value="Iniciar sesión">
				</form>
			</div>
		</div>
	</div>
</body>
</html>