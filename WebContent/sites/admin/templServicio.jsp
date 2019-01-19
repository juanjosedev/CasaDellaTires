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
			String nombre = request.getParameter("servicio");
			Servicio2 s = ms2.getServicio(nombre);
			//response.getWriter().print("<h2>"+s+"</h2>");
		
%>
	
<div class="container-profile">
	<div class="row">
		<div class="col-md-12">
			<a href="Servicios2.jsp" class="boton boton-chico pull-right <%= u.getColor() %>"><span class="icon-navigate_before"></span> Volver a la tabla</a>
		</div>
	</div>
	<h2><span class="table-title <%= u.getColor() %>"></span> <%= s.getNombre() %></h2>
	<div class="row">
		<div class="profile-data col-md-12">
			<!--<div class="row">
				 <div class="col-md-12">
					<a href="#modificarServicio" data-toggle="modal" class="boton-vacio boton-chico pull-right">Editar</a>
					<div class="modal fade modal_modificar" id="modificarServicio">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header <%= u.getColor() %>">
									<h3 class="modal-header-title"><span class="icon-edit"></span> Editar servicio</h3>
								</div>
								<form action="" method="post"
									id="form_editar_servicio" name="form_editar_servicio">
									<div class="modal-body text-left">
										<div class="form-group">
											<input name="id_modificar" type="text" id="id" class="form-control"
												placeholder="Id del servicio"
												value="<%= s.getId() %>" readonly="readonly">
										</div>
										<div class="form-group">
											<input name="nombre" type="text" id="inp_nombre_modificar"
												class="form-control" placeholder="Nombre"
												value="<%= s.getNombre() %>">
										</div>
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
				</div>
			</div>--><br> 
			<div class="table-responsive">
				<table class="table table-hover sombra">
					<thead>
						<tr>
							<th class="text-center">Informaci�n</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<div class="media">
									<div class="media-left media-middle">
										<span class="media-object icon-credit_card fs-em-2"></span>
									</div>
									<div class="media-body">
										<h4 class="media-heading"><%= s.getId() %></h4>
										<var class="media-heading-small">Id del servicio</var>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="media">
									<div class="media-left media-middle">
										<span class="media-object icon-account_box fs-em-2"></span>
									</div>
									<div class="media-body">
										<h4 class="media-heading"><%= s.getNombre() %></h4>
										<var class="media-heading-small">Nombre del servicio</var>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="profile-data col-md-12">
			<div class="row">
				<div class="col-md-12">
					<a href="#addNewPrecio" data-toggle="modal" class="boton-vacio boton-chico pull-right">A�adir</a>
				</div>
			</div>
			<div class="modal fade" id="addNewPrecio">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h3 class="modal-header-title"><span class="icon-assignment"></span> A�adir servicio a un veh�culo</h3>
						</div>
						<form action="" method="post" id="frm_add_precio" name="frm_add_precio"> 
							<div class="modal-body text-left">
								<div class="alert bg-ambar" id="alert_campos_obligatorios" role="alert"><span class="icon-warning"></span> Los campos con asterisco (*) son obligatorios</div>
								<div class="">
									<label>Tipo de veh�culos* <i>(M�nimo un veh�culo)</i></label>
									<div class="table-responsive" id="container_add_nuevo_precio">
										<jsp:include page="tablaNuevosPrecios.jsp"></jsp:include>
									</div>
									<div class="alert alert-danger" id="alt_crear_error" role="alert"><span class="icon-error"></span> <var id="msg"></var></div>
									<div class="alert alert-success" id="alt_crear_exito" role="alert"><span class="icon-check_circle"></span> <var id="msg"></var></div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button"
									class="boton boton-chico pull-left"
									data-dismiss="modal">Cerrar</button>
								<input type="submit" class="boton boton-chico <%= u.getColor() %>"
									id="sbt_add_precio" name="sbt_add_precio" value="A�adir">
							</div>
						</form>
					</div>
				</div>
			</div>
			<br>
			<div class="alert alert-success" id="alt_eliminar_exito" role="alert"><span class="icon-check_circle"></span> <var id="msg"></var></div>
			<div class="table-responsive" id="container_tabla_precios">
				<jsp:include page="tablaPrecios.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<%
		} catch (NumberFormatException e) {
			response.getWriter().print("<h2>Error con el dato de entrada</h2>");
		} catch (NullPointerException e){
			response.getWriter().print("<h2>El servicio no existe </h2>");
		}finally{
			
		}
		ms2.cerrarConexion();
	}
%>