<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorClientes cc = new controladorClientes();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	if(request.getParameter("cc") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Cliente> lista = cc.getAllClientes(pagina);
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center text-uppercase">C�dula</th>
			<th class="text-center text-uppercase">Nombre</th>
			<th class="text-center text-uppercase">Tel�fono</th>
			<th class="text-center text-uppercase">Direcci�n</th>
			<th class="text-center text-uppercase">Historial</th>
			<th class="text-center text-uppercase">Modificar</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (Cliente valor : lista) {
		%>
		<tr>
			<td class="text-center"><%=valor.getCedula()%></td>
			<td class="text-center"><%=valor.getNombreCompleto()%></td>
			<td class="text-center"><%=valor.getTelefono()%></td>
			<td class="text-center"><%=valor.getDireccion()%></td>
			<td class="text-center"><a href="#detalle" data-toggle="modal">Detalle</a>
				<div class="modal fade" id="detalle">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">HISTORIAL DEL CLIENTE</h2>
							</div>
							<div class="modal-body text-left">
								<p>Selecciona el servicio o el veh�culo para m�s informaci�n</p>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<tr>
												<th>Fecha</th>
												<th>Servicio</th>
												<th>Veh�culo</th>
											</tr>
										</thead>
										<tbody>
											<tr>Aqu� va el historial como est�n en los prototipos
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
				href="#modificarCliente<%=valor.getCedula()%>" data-toggle="modal">Modificar</a>
				<div class="modal fade modal_modificar" id="modificarCliente<%=valor.getCedula()%>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">MODIFICAR CLIENTE</h2>
							</div>
							<form action="" method="post"
								id="form_registro_cliente" name="form_registro_cliente">
								<div class="modal-body text-left">
									<h3>Datos del cliente</h3>
									<div class="form-group">
										<input name="cc_modificar" type="text" id="cc" class="form-control"
											placeholder="C�dula de la persona"
											value="<%=valor.getCedula()%>" readonly="readonly">
									</div>
									<div class="form-group">
										<input name="nombre" type="text" id="input_nombre_modificar"
											class="form-control" placeholder="Nombre"
											value="<%=valor.getNombre()%>">
										<div class="alert alert-danger" id="alert_modificar_nombre"
											role="alert">Rellena este campo</div>
										<div class="alert alert-danger"
											id="alert_validacion_modificar_nombre" role="alert">Unicamente
											letras</div>
									</div>
									<div class="form-group">
										<input name="primer_apellido" type="text" id="input_primer_apellido_modificar"
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
											id="input_segundo_apellido_modificar" class="form-control"
											placeholder="Segundo apellido"
											value="<%=valor.getSegundo_apellido()%>">
										<div class="alert alert-danger"
											id="alert_validar_modificar_segundo_apellido" role="alert">Unicamente
											letras</div>	
									</div>
									<div class="form-group">
										<input name="telefono" type="text" id="input_telefono_modificar"
											class="form-control" placeholder="Tel�fono"
											value="<%=valor.getTelefono()%>">
									</div>
									<div class="form-group">
										<input name="direccion" type="text" id="input_direccion_modificar"
											class="form-control" placeholder="Direcci�n"
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
										id="modificar_cliente" name="modificar_cliente"
										value="Guardar cambios">
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
<%= Paginacion.getPaginacion("Clientes.jsp", cc.getContarClientes()) %>
	<%
		} else {//cierre if
			long cedula_buscar = Long.parseLong(request.getParameter("cc"));
			Cliente buscado = cc.getCliente(cedula_buscar);
			
			if(buscado != null){
				
			
	%>
				<%= buscado %>
	<%	
			} else {//Cierre del if
	%>
				<%= "No encontrado" %>
	<%
			}
	%>
			<button id="volver" class="btn btn-primary">Volver</button>
	<%		
		}//Cierre del else
		cc.cerrarConexiones();
	%>
