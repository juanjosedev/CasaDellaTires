<%@ page import="java.util.*, controlador.*, include.*"%>
<%
	controladorVehiculos cv = new controladorVehiculos();
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	Usuario u = (Usuario) username;
	if(request.getParameter("placa") == null) {
		int pagina = 1;
		if(request.getParameter("page") != null) {
			pagina = Integer.parseInt(request.getParameter("page"));
		}
		ArrayList<Vehiculo> lista = cv.getAllVehiculos(pagina);
		
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th class="text-center text-uppercase">Placa</th>
			<th class="text-center text-uppercase">Tipo de veh�culo</th>
			<th class="text-center text-uppercase">Marca</th>
			<th class="text-center text-uppercase">Modelo</th>
			<th class="text-center text-uppercase">Historial</th>
			<th class="text-center text-uppercase">Modificar</th>
		</tr>
	</thead>
	<tbody>
		<% for(Vehiculo v : lista){ %>
		<tr>
			<td class="text-center"><%= v.getBeautyPlaca() %></td>
			<td class="text-center"><%= v.getTipo().getNombre() %></td>
			<td class="text-center"><%= v.getMarca() %></td>
			<td class="text-center"><%= v.getModelo() %></td>
			<td class="text-center"><a href="#detalle<%= v.getPlaca() %>"
				data-toggle="modal">Detalle</a>
				<div class="modal fade" id="detalle<%= v.getPlaca() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">HISTORIAL DEL
									VEH�CULO</h2>
							</div>
							<div class="modal-body text-left">
								<p>Selecciona el servicio o el cliente para m�s
									informaci�n</p>
								<div class="table-responsive">
									<table class="table table-striped">
										<thead>
											<th>Fecha</th>
											<th>Servicio</th>
											<th>Cliente</th>
										</thead>
										<tr>
											<td>21/09/2018</td>
											<td><a href="#">000368</a></td>
											<td><a href="">1020656565</a></td>
										</tr>
										<tr>
											<td>05/09/2018</td>
											<td><a href="#">000333</a></td>
											<td><a href="">1020656565</a></td>
										</tr>
										<tr>
											<td>16/08/2018</td>
											<td><a href="#">000320</a></td>
											<td><a href="">9810021092</a></td>
										</tr>
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
				</div>
			</td>
			<td class="text-center"><a
				href="#modificarCliente<%= v.getPlaca() %>" data-toggle="modal">Modificar</a>
				<div class="modal fade modal_modificar" id="modificarCliente<%= v.getPlaca() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h2 class="modal-header-title">MODIFICAR CLIENTE</h2>
							</div>
							<form action="" method="post"
								id="frm_modificar" name="frm_modificar">
								<div class="modal-body text-left">
									<h3>Datos del veh�culo</h3>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<input name="placa1_mdf" type="text" id="inp_placa_modificar1"
													class="form-control" placeholder="PLACA: AAA"
													maxlength="3" readonly="readonly" value="<%= v.getFirstPlaca() %>">
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<input name="placa2" type="text" id="inp_placa_modificar2"
													class="form-control" placeholder="PLACA: 111"
													maxlength="3" readonly="readonly" value="<%= v.getSecondPlaca() %>">	
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<select name="tipo" id="slt_tipo" class="form-control">
													<option value="<%= v.getTipo().getId() %>"><%= v.getTipo().getNombre() %></option>
													<%
														controladorTipoVehiculos ctv = new controladorTipoVehiculos();
														ArrayList<tipoVehiculo> listaSelect = ctv.getAllTipoVehiculos(-1);
														for (tipoVehiculo w : listaSelect) {
													%>
													<option value="<%= w.getId() %>"><%= w.getNombre() %></option>
													<% 
														}
														ctv.cerrarConexion();
													%>			
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<select name="marca" id="slt_marca" class="form-control">
													<% if(v.getMarca().isEmpty()) { %>
													<option value="">MARCA</option>
													<% } else { %>
													<option value="<%= v.getMarca() %>"><%= v.getMarca() %></option>
													<% } %>
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
													id="inp_modelo" placeholder="MODELO" value="<%= v.getModelo() %>">
											</div>
										</div>
									</div>
										<div class="alert alert-danger"
											id="art_mdf_error" role="alert" hidden>ERROR al modificar el veh�culo</div>
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
<%= Paginacion.getPaginacion("Vehiculos.jsp", cv.getContarVehiculos()) %>
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
<% } 
   cv.cerrarConexiones();
%>