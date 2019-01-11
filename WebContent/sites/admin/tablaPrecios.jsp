<%@ page import="java.util.*, controlador.*, include.*, modelo.*"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	
	if(username == null){
		response.sendRedirect("http://localhost:8080/CasaDellaTires/");
	}else{
		Usuario u = (Usuario) username;
		
		ModeloServicios2 ms2 = new ModeloServicios2();
		
		try{
			long id_servicio = Long.parseLong(request.getParameter("servicio"));
			Servicio2 s = ms2.getServicio(id_servicio);
		
%>
<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th>Id</th>
			<th>Nombre</th>
			<th class="text-right">Precio</th>
			<th>Eliminar</th>
			<th>Editar</th>
		</tr>
	</thead>
	<tbody>
	<%
		ArrayList<DetalleServicio> lista_detalles = s.getLista_detalle();
		for(DetalleServicio dll: lista_detalles){
	%>
		<tr>
			<td class="text-right"><var class="var_id"><%= dll.getId() %></var></td>
			<td><%= dll.getTipo_vehiculo().getNombre() %></td>
			<td class="text-right"><var class="var_precio"><%= dll.getPrecio() %></var></td>
			<td>
				<a href="#eliminarDetalle<%= dll.getId() %>" data-toggle="modal">Eliminar</a>
				<div class="modal fade modal_modificar" id="eliminarDetalle<%= dll.getId() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header <%= u.getColor() %>">
								<h3 class="modal-header-title"><span class="icon-edit"></span> Eliminar detalle</h3>
							</div>
							<div class="modal-body text-left">
								<div class="media">
									<div class="media-left media-middle">
										<span class="media-object icon-delete fs-em-2"></span>
									</div>
									<div class="media-body">
										<h4 class="media-heading">¿Está seguro de que quiere eliminar este servicio para este vehículo?</h4>
										<var class="media-heading-small">Eliminar</var>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="boton boton-chico pull-left"
									data-dismiss="modal">Cerrar</button>
								<button class="boton boton-chico <%= u.getColor() %> inp_eliminar">Eliminar</button>
							</div>
						</div>
					</div>
				</div>
			</td>
			<td>
				<a href="#editarDetalle<%= dll.getId() %>" data-toggle="modal">Editar</a>
				<div class="modal fade modal_modificar" id="editarDetalle<%= dll.getId() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header <%= u.getColor() %>">
								<h3 class="modal-header-title"><span class="icon-edit"></span> Editar detalle</h3>
							</div>
							<form>
								<div class="modal-body text-left">
									<div class="form-group">
										<input name="inp_id_update" type="text" id="inp_id_update" class="form-control" placeholder="Id del servicio" value="<%= dll.getId() %>" readonly="readonly">
									</div>
									<div class="form-group">
										<input name="inp_nombre_update" type="text" id="inp_nombre_update" class="form-control" placeholder="Nombre del servicio" value=<%= dll.getTipo_vehiculo().getNombre() %> readonly="readonly">
									</div>
									<div class="form-group">
										<input name="inp_precio_update" type="text" id="inp_precio_update" class="form-control" placeholder="Precio del servicio" value="<%= dll.getPrecio() %>">
									</div>
								</div>
								<div class="modal-footer">
									<button type="button"
										class="boton boton-chico pull-left"
										data-dismiss="modal">Cerrar</button>
									<input type="submit" class="boton boton-chico <%= u.getColor() %> inp_update_precio" name="inp_update" value="Guardar cambios">
								</div>
							</form>
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
<%
		} catch (NumberFormatException e) {
			response.getWriter().print("<h2>Error con el dato de entrada</h2>");
		} catch (NullPointerException e){
			response.getWriter().print("<h2>El servicio no existe </h2>");
		}
		ms2.cerrarConexion();
	}
%>