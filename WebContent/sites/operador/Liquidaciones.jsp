<%@ page import="include.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	if(username == null){
		response.sendRedirect("http://localhost:8080/CasaDellaTires/");
	}else{
		Usuario u = (Usuario) username;
		if(!u.getTipo().equals("Operador")){
			response.sendRedirect("../../index.jsp");
		}	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../../templates/cabecera.jsp"></jsp:include>
<script src="../../js/liquidaciones.js"></script>
<title>Admin | Liquidaciones</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2 class="text-uppercase"><span class="icon-library_books"></span> Liquidaciones <span class="label pull-right" id="label-numeroDeliquidaciones">0</span></h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">
				
			<div class="row">
				<div class="col-md-12">
					<a href="#agregarLiquidacion" data-toggle="modal"
						class="boton <%= u.getColor() %> boton-chico pull-right sombra">Nueva liquidación</a>
					<div class="modal fade" id="agregarLiquidacion">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<h3 class="modal-header-title text-uppercase"><span class="icon-assignment"></span> Crear Liquidación</h3>
								</div>
								<form action="servletliquidaciones" method="post"
									id="frm_liquidar" name="frm_liquidar">
									<div class="modal-body text-left">
										<div id="formulario_liquidacion">
											<h4><span class="table-title <%= u.getColor() %>"></span>Datos del cliente</h4>
											<div class="form-group">
												<input name="cc" type="text" id="inp_cc_lqr"
													class="form-control" placeholder="Cédula de la persona">
												<div class="alert alert-danger" id="art_vacio_cc"
													role="alert">Rellene este campo</div>
												<div class="alert alert-danger" id="art_uni_num__cc"
													role="alert">Unicamente números</div>
												<div class="alert alert-danger" id="art_cc_no_existe"
													role="alert">
													La cédula no existe <a href="Clientes.jsp?agregar=true"
														class="pull-right"><span class="label label-danger">Registrar</span></a>
												</div>
												<div class="alert alert-success" id="art_cc_existe"
													role="alert">Cédula correcta</div>
											</div>
											<div class="form-group">
												<input name="placa" type="text" id="inp_placa_lqr"
													class="form-control"
													placeholder="Placa del vehículo: AAA111" maxlength="6">
												<div class="alert alert-danger" id="art_vacio_placa"
													role="alert">Rellene este campo</div>
												<div class="alert alert-danger" id="art_val_placa"
													role="alert">Placa inválida (6 caracteres)</div>
												<div class="alert alert-danger" id="art_placa_no_existe"
													role="alert">
													La placa no existe <a href="Vehiculos.jsp?agregar=true"
														class="pull-right"><span class="label label-danger">Registrar</span></a>
												</div>
												<div class="alert alert-success" id="art_placa_existe"
													role="alert">Placa correcta</div>
											</div>
											<div class="form-group" id="tabla_servicios"></div>
											<div class="alert alert-danger" id="art_servicio_cero"
													role="alert">Selecciona por lo menos un servicio</div>
										</div>
										<div id="informacion_liquidacion">
											<div class="row">
												<div class="col-md-6" id="info_cliente">
													
												</div>
												<div class="col-md-6" id="info_vehiculo">
													
												</div>
											</div>
											<hr>
											<div class="table-responsive">
							                    <table class="table table-bordered table-hover">
							                        <thead>
								                        <tr>
								                            <th>Servicio</th>
								                            <th>Precio</th>
								                        </tr>
							                        </thead>
							                        <tbody id="tabla_serviciosi">
							                        </tbody>
							                    </table>
							                </div>
											<hr>
											<div class="row">
												<div class="col-md-6">
													<div class="media">
														<div class="media-left">
															<span class="media-object icon-query_builder fs-em-2"></span>
														</div>
														<div class="media-body">
															<h4 class="media-heading">Información</h4>
															<ul>
																<li><i><b>Consecutivo: </b>00000</i></li>
																<li><i><b>Fecha: </b><var id="info_fecha"></var></i></li>
																<li><i><b>Entrada: </b><var id="info_hora_actual"></var></i></li>
																<li><i><b>Salida: </b>Pendiente</i></li>
															</ul>	
														</div>
													</div>
												</div>
												<div class="col-md-6">
													<div class="media">
														<div class="media-left">
															<span class="media-object icon-attach_money fs-em-2"></span>
														</div>
														<div class="media-body">
															<h4 class="media-heading">Total: $<i><var id="info_total">0</var></i></h4>
															<ul>
																<li><i><b>Subtotal: $</b><var id="info_subtotal">0</var></i></li>
																<li><i><b>Descuento: $</b><var id="info_descuento">0</var></i></li>
															</ul>	
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="alert alert-success" id="alert_liquidar_exitoso"
												role="alert">Liquidación exitosa. No olvides terminarla</div>
										<div class="alert alert-danger" id="alert_liquidar_error"
												role="alert">Error en la liquidación</div>
									</div>
									<div class="modal-footer">
										<button type="button"
											class="boton boton-chico pull-left"
											data-dismiss="modal">Cerrar</button>
										<button type="button"
											class="boton boton-chico bg-cian" id="btn_volver">Volver</button>
										<button type="button"
											class="boton boton-chico <%= u.getColor() %>" id="btn_confirmar">Confirmar</button>	
										<input type="submit" class="boton boton-chico <%= u.getColor() %>"
											id="sbt_new_lqd"
											name="sbt_new_lqd" value="Crear liquidación">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="" id="tabla_lqds_pendientes">
						<jsp:include page="tablaLiquidacionesP.jsp"></jsp:include>
					</div>
					<div class="" id="tabla_lqds_finalizados">
						<jsp:include page="tablaLiquidacionesF.jsp"></jsp:include>
					</div>
				</div>
			</div>
		
	</div>
</body>
</html>
<% } %>