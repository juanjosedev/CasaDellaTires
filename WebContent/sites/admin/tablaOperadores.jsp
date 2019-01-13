<%@page import="modelo.modeloUsuario"%>
<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	modeloUsuario mu = new modeloUsuario();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	
	int pagina = 1;
	if(request.getParameter("page") != null) {
		pagina = Integer.parseInt(request.getParameter("page"));
	}
	ArrayList<Usuario> lista = mu.getAllOperadores(pagina);
%>
<table class="table table-hover">
	<thead>
		<tr>
			<th colspan="6" class="text-center">Tabla de operadores</th>
		</tr>
	</thead>
	<tbody>
		<tr class="bg-ddd">
			<th class="text-left text-uppercase">Usuario</th>
			<th class="text-left text-uppercase">Nombre</th>
			<th class="text-right text-uppercase">Teléfono</th>
			<th class="text-left text-uppercase">Dirección</th>
			<th class="text-center text-uppercase">Historial</th>
			<th class="text-center text-uppercase">Modificar</th>
		</tr>
		<%
			for (Usuario valor : lista) {
		%>
		<tr>
			<td class="text-left"><%=valor.getUsuario()%></td>
			<td class="text-left"><%=valor.getNombreCompleto()%></td>
			<td class="text-right"><%=valor.getTelefono()%></td>
			<td class="text-left"><%=valor.getDireccion()%></td>
			<td class="text-center"><a href="#detalle" data-toggle="modal">Detalle</a>
				<div class="modal fade" id="detalle">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">HISTORIAL DEL OPERADOR</h2>
							</div>
							<div class="modal-body text-left">
								<p>Selecciona el servicio o el vehículo para más información</p>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Fecha</th>
												<th>Servicio</th>
												<th>Vehículo</th>
											</tr>
										</thead>
										<tbody>
											<tr>Aquí va el historial como están en los prototipos
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="boton boton-chico pull-left"
									data-dismiss="modal">Cerrar</button>
							</div>
						</div>
					</div>
				</div></td>
			<td class="text-center"><a
				href="#modificarCliente<%=valor.getNombre()%>" data-toggle="modal">Modificar</a>
				<div class="modal fade modal_modificar" id="modificarCliente<%=valor.getNombre()%>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">MODIFICAR OPERADOR</h2>
							</div>
							<form action="" method="post" name="form_registro_cliente">
								<div class="modal-body text-left">
									<h3>Datos del cliente</h3>
									<div class="form-group">
										<input name="cc_modificar" type="text" class="form-control"
											placeholder="Cédula de la persona"
											value="<%=valor.getNombre()%>" readonly="readonly">
									</div>
									<div class="form-group">
										<input name="nombre" type="text"
											class="form-control" placeholder="Nombre"
											value="<%=valor.getNombre()%>">
										<div class="alert alert-danger" id="alert_modificar_nombre"
											role="alert">Rellena este campo</div>
										<div class="alert alert-danger"
											id="alert_validacion_modificar_nombre" role="alert">Unicamente
											letras</div>
									</div>
									<div class="form-group">
										<input name="primer_apellido" type="text"
											class="form-control" placeholder="Primer apellido"
											value="<%=valor.getPrimer_apellido()%>">
										<div class="alert alert-danger" id="alert_modificar_apellido"
											role="alert">Rellena este campo</div>
										<div class="alert alert-danger"
											id="alert_validar_modificar_apellido" role="alert">Unicamente
											letras</div>
									</div>
									<div class="form-group">
										<input name="segundo_apellido" type="text"
											 class="form-control"
											placeholder="Segundo apellido"
											value="<%=valor.getSegundo_apellido()%>">
										<div class="alert alert-danger"
											id="alert_validar_modificar_segundo_apellido" role="alert">Unicamente
											letras</div>	
									</div>
									<div class="form-group">
										<input name="telefono" type="text"
											class="form-control" placeholder="Teléfono"
											value="<%=valor.getTelefono()%>">
									</div>
									<div class="form-group">
										<input name="direccion" type="text"
											class="form-control" placeholder="Dirección"
											value="<%=valor.getDireccion()%>">
									</div>
									<div class="alert alert-success" id="alert_modificar_exitoso"
														role="alert">Guardando espere un momento...</div>
									<div class="alert alert-danger" id="alert_modificar_error"
														role="alert">ERROR al modificar el cliente</div>
								</div>
								<div class="modal-footer">
									<button type="button"
										class="boton boton-chico pull-left"
										data-dismiss="modal">Cerrar</button>
									<input type="submit" class="boton boton-chico <%= u.getColor() %>"
										name="modificar_cliente" value="Guardar cambios">
								</div>
							</form>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<%
			}//Cierre del for
		%>
	</tbody>
</table>
<script>
	$(document).ready(function() {
		function getParameterByName(name) {
		    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		    results = regex.exec(location.search);
		    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
		}
		function getPaginas() {
			var pagina_actual = getParameterByName("page");
			if(pagina_actual == ""){
				pagina_actual = 1;
			}
			var listas = document.getElementsByClassName("page_p");
			for(var i = 0; i < listas.length; i++){
				if(listas[i].innerText == pagina_actual){
					listas[i].firstChild.className += "<%= u.getColor() %> page_active";
				}
			}		
		}
		getPaginas();
	});
</script>
<%= Paginacion.getPaginacion("Operadores.jsp", lista.size()) %>
<%		
	mu.cerrarConexion();
%>
