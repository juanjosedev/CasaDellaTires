<%@ page import="java.util.*, controlador.*, include.*, modelo.*"%>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	if(username == null){
		response.sendRedirect("http://localhost:8080/CasaDellaTires/");
	}else{
		Usuario u = (Usuario) username;
		if(!u.getTipo().equals("Admin")){
			response.sendRedirect("../../index.jsp");
		} else {
			
		
%>
<table class="table table-hover table-modal sombra">
	<thead>
		<tr class="bg-ddd">
			<th>Vehículo</th>
			<th class="left">Precio</th>
		</tr>
	</thead>
	<tbody>
		<%
			controladorTipoVehiculos ctv = new controladorTipoVehiculos();
			ModeloServicios2 ms2 = new ModeloServicios2();
			
			
			String nombre_servicio = request.getParameter("servicio");
			Servicio2 s2 = ms2.getServicio(nombre_servicio);
			ArrayList<DetalleServicio> lista_detalles = s2.getLista_detalle();
		
			ArrayList<tipoVehiculo> lista = ctv.getAllTipoVehiculos(-1);
			boolean flag = false;
			
			for (tipoVehiculo v : lista) {
				for (DetalleServicio dll: lista_detalles) {
					
					long id_tipo_vehiculo = dll.getTipo_vehiculo().getId();
					if (id_tipo_vehiculo == v.getId()){
						flag = true;
					}
					
				}
				if(!flag){
					
				%>
				
				<tr>
					<td><%= v.getNombre() %></td>
					<td>
						<div class="input-group">
		                        <input type="text" class="form-control inp_precio" id="inp_precio" value="$" readonly>
		                        <div class="input-group-addon"><input type="checkbox" value="<%= v.getId() %>" name="tipoVehiculo[]" id="0" class="inp_chk_servicio"></div>
		                    </div>
					</td>
				</tr>													
				<%
				}
				flag = false;
			}
			ctv.cerrarConexion();
			ms2.cerrarConexion();
		}
	}
%>
	</tbody>
</table>