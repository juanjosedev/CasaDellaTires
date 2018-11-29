<%@ page import="java.util.*, modelo.*, include.*, controlador.*"%>
<%
	modeloServicios mv = new modeloServicios();
	modeloTiposVehiculos mtv = new modeloTiposVehiculos();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	if(request.getParameter("buscar") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Servicio> lista = mv.getAllServicios(pagina);
		
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center text-uppercase">Id</th>
			<th class="text-center">Tipo de vehículo</th>
			<th class="text-center">Precio</th>
			<th class="text-center">Nombre</th>
			<th class="text-center">Eliminar</th>
			<th class="text-center">Modificar</th>
		</tr>
	</thead>
	<tbody>
		<% for(Servicio v : lista){ %>
		<tr>
			<td class="text-center"><%= v.getId() %></td>
			<td class="text-center"><%= mtv.getTipoVehiculo(v.getId_tipo_vehiculo()).getNombre() %></td>
			<td class="text-center"><%= v.getPrecio() %></td>
			<td class="text-center"><%= v.getNombre() %></td>
			<td class="text-center"><a href="#eliminarServicio<%= v.getId() %>"
				data-toggle="modal">Eliminar</a>
				<div class="modal fade" id="eliminarServicio<%= v.getId() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">ELIMINAR SERVICIO</h2>
							</div>
							<div class="modal-body text-left">
								<div class="media">
									<div class="media-left">
										<span class="media-object icon-bin2 fs-em-2"></span>
									</div>
									<div class="media-body">
										<h4 class="media-heading">¿ESTÁ SEGURO QUE QUIERE
											DESHACERSE EL SERVICIO?</h4>
										<i>¿ELIMINAR?</i>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="boton boton-chico"
									data-dismiss="modal">Cerrar</button>
								<input type="submit" class="boton boton-chico bg-rojo"
									id="agregar" name="agregar" value="Eliminar">
							</div>
						</div>
					</div>
				</div>
			</td>
			<td class="text-center"><a
				href="#modificarServicio<%= v.getId() %>" data-toggle="modal">Modificar</a>
				<div class="modal fade modal_modificar" id="modificarServicio<%= v.getId() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">MODIFICAR SERVICIO</h2>
							</div>
							<form action="" method="post"
								id="frm_modificar" name="frm_modificar">
								<div class="modal-body text-left">
									<h3>Datos del servicio</h3>
									<div class="form-group">
										<input name="cc" type="text" id="cc"
											class="form-control" placeholder="PRECIO">
									</div>
									<div class="form-group">
										<input name="cc" type="text" id="cc"
											class="form-control" placeholder="NOMBRE">
									</div>	
									<div class="alert alert-danger"
										id="art_mdf_error" role="alert" hidden>ERROR al modificar el vehículo</div>
									<div class="alert alert-success"
										id="art_mdf_success" role="alert" hidden>GUARDANDO espere un momento...</div>
								</div>
								<div class="modal-footer">
									<button type="button"
										class="boton boton-chico pull-left"
										data-dismiss="modal">Cerrar</button>
									<input type="submit" class="boton boton-chico <%= u.getColor() %>"
										id="sbt_modificar" name="sbt_modificar"
										value="Guardar cambios">
								</div>
							</form>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<% } %>
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
					listas[i].firstChild.className += "<%= u.getColor() %> page_active";;
				}
			}		
		}
		getPaginas();
	});
</script>
<%= Paginacion.getPaginacion("Servicios.jsp", mv.getContarServicios()) %>
<%
	}
	mv.cerrarConexion();
	mtv.cerrarConexion();
%>