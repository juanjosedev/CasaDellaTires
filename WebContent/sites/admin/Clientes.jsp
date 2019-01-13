<%@ page import="java.util.*, controlador.*, include.*, modelo.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="../../templates/cabecera.jsp"></jsp:include>
<script src="../../js/clientes.js"></script>
<title>Admin | CLIENTES</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-uppercase"><span class="icon-group"></span> Clientes</h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">	
	<%
		if(request.getParameter("query") != null){
			String criterio = request.getParameter("query");
			ArrayList<Cliente> listaClientes = cc.getBusqueda(criterio);
	%>
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Buscar cliente..." id="input_buscar_cc" name="input_buscar_cc">
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
		</div>
		<div class="row">
			<div class="col-md-12">
				<h3 class="text-uppercase"><span class="table-title <%= u.getColor() %>"></span> Se encontraron <b><%= listaClientes.size() %></b> resultados.</h3>
			</div>
		</div>
	<%	
		if(listaClientes.size() != 0){
	%>
		<div class="row">
		<%			
			for(Cliente c : listaClientes){
		%>
			<div class="col-md-12">
				<div class="container-query sombra">
					<h4><span class="icon-person <%= u.getColor() %>"></span> <%= c.getNombreCompleto() %>
					<a href="Clientes.jsp?user=<%= c.getCedula() %>" class="boton-vacio boton-chico pull-right"> VER PERFIL</a>
					</h4>
				</div> 
			</div>
		<%	
			}
		%>
		</div>
		<%
			}//Cierre del if
		%>
		
	<%
			
	} else if(request.getParameter("user") != null) {//cierre if
		
	%>
	<jsp:include page="templUser.jsp"></jsp:include>
		<%
			} else{
		%>
		<div class="row">
			<div class="col-md-6">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Buscar cliente..." id="input_buscar_cc" name="input_buscar_cc">
					<span class="input-group-btn">
						<button class="boton <%= u.getColor() %>" type="button" id="buscar_cc">
							<span class="icon-search"></span>
						</button>
					</span>
				</div>
			</div>
			<div class="col-md-6">
				<a href="#agregarCliente " data-toggle="modal"
					class="boton <%= u.getColor() %> boton-chico pull-right sombra">Nuevo cliente</a>
				<div class="modal fade" id="agregarCliente">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header <%= u.getColor() %>">
								<h3 class="modal-header-title"><span class="icon-assignment"></span> Registrar cliente</h3>
							</div>
							<form action="post" method="crearcliente"
								id="frm_nuevo_cliente" name="frm_nuevo_cliente">
								<div class="modal-body text-left">
									<div class="alert bg-ambar" id="alert_campos_obligatorios" role="alert">Los campos con asterisco (*) son obligatorios</div>
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
		
		<div class="row">
			<div class="col-md-12">
				<div class="" id="tabla_clientes">
					<jsp:include page="tablaClientes.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<% 	
			}	
cc.cerrarConexiones();
}
%>