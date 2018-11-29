<%@ page import="java.util.*, controlador.*, include.*, modelo.*"%>
<%
	controladorTipoVehiculos ctv = new controladorTipoVehiculos();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	int pagina = 1;
	if(request.getParameter("page") != null) {
		pagina = Integer.parseInt(request.getParameter("page"));
	}
	ArrayList<tipoVehiculo> lista = ctv.getAllTipoVehiculos(pagina);
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center text-uppercase">Id</th>
			<th class="text-center text-uppercase">Nombre</th>
			<th class="text-center text-uppercase">Modificar</th>
		</tr>
	</thead>
	<%
		for (tipoVehiculo valor : lista) {
	%>
	<tbody>
		<tr>
			<td class="text-center"><%=valor.getId()%></td>
			<td class="text-center td_nombre"><%=valor.getNombre()%></td>
			<td class="text-center"><a href="#editarServicio<%=valor.getId()%>"
				data-toggle="modal">Modificar</a>
				<div class="modal fade" id="editarServicio<%=valor.getId()%>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">EDITAR SERVICIO</h2>
							</div>
							<div class="modal-body text-left">
								<div class="form-group">
									<input name="nombre" type="text" id="nombre"
										class="form-control" placeholder="NOMBRE" value="<%=valor.getNombre()%>" cod="<%=valor.getId()%>">
									<div class="alert alert-danger" id="alert_agregar_nombre"
														role="alert">No puedes dejar vacío este campo</div>
									<div class="alert alert-danger" id="alert_nombre_invalido"
														role="alert">Nombre inválido</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="boton boton-chico pull-left"
									data-dismiss="modal">Cerrar</button>
								<input type="submit" class="boton boton-chico <%= u.getColor() %>"
									id="modificar" name="modificar" value="Guardar cambios">
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	<%
		}
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
					listas[i].firstChild.className += "<%= u.getColor() %> page_active";;
				}
			}		
		}
		getPaginas();
	});
</script>
<%= Paginacion.getPaginacion("Tipo_Vehiculos.jsp", ctv.getContarTipoVehiculos()) %>
<%
	ctv.cerrarConexion();
%>