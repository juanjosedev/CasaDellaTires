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
			String nombre_servicio = request.getParameter("servicio");
			Servicio2 s = ms2.getServicio(nombre_servicio);
		
%>
<table class="table table-hover sombra">
	<thead>
		<tr>
			<th class="text-center" colspan="5">Tabla de precios</th>
		</tr>
	</thead>
	<tbody>
		<tr class="bg-ddd">
			<th class="text-right">Id</th>
			<th class="text-left">Nombre</th>
			<th class="text-right">Precio</th>
			<th class="text-center">Eliminar</th>
			<th class="text-center">Editar</th>
		</tr>
	<%
		ArrayList<DetalleServicio> lista_detalles = s.getLista_detalle();
		for(DetalleServicio dll: lista_detalles){
	%>
		<tr>
			<td class="text-right"><var class="var_id"><%= dll.getId() %></var></td>
			<td><%= dll.getTipo_vehiculo().getNombre() %></td>
			<td class="text-right"><var class="var_precio"><%= dll.getPrecio() %></var></td>
			<td class="text-center">
				<a href="#eliminarDetalle<%= dll.getId() %>" data-toggle="modal"><span class="icon-delete"></span></a>
				<div class="modal fade modal_modificar" id="eliminarDetalle<%= dll.getId() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header <%= u.getColor() %>">
								<h3 class="modal-header-title text-left"><span class="icon-edit"></span> Eliminar detalle</h3>
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
			<td class="text-center">
				<a href="#editarDetalle<%= dll.getId() %>" data-toggle="modal"><span class="icon-edit"></span></a>
				<div class="modal fade modal_modificar" id="editarDetalle<%= dll.getId() %>">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header <%= u.getColor() %> text-left">
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
									<div class="alert alert-danger alt_editar_precio_error" id="" role="alert"><span class="icon-error"></span> <var id="msg"></var></div>
									<div class="alert alert-success alt_editar_precio_exito" id="" role="alert"><span class="icon-check_circle"></span> <var id="msg"></var></div>
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