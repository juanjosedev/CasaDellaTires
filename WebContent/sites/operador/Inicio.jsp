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
<html lang="es">
<head>
<jsp:include page="../../templates/cabecera.jsp"></jsp:include>
<script src="../../js/inicio.js"></script>
<title>Admin | INICIO</title>
</head>
<body>
	<div class="container-fluid title_maestro <%= u.getColor() %>">
		<div class="row">
			<div class="col-md-12">
				<h2><span class="icon-poll"></span> Inicio</h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">
		<div class="row">
			<div class="col-md-6">
				<div class="media  <%= u.getColor() %> sombra tag-data1">
					<div class="media-body">
						<h2 class="media-heading" id="label_servicios_prestados_hoy">0</h2>
						<i>SERVICIOS</i>
					</div>
					<div class="media-right media-middle">
						<span class="icon-settings"></span>
					</div>
				</div>	
			</div>
			<div class="col-md-6">
				<div class="media  <%= u.getColor() %> sombra tag-data1">
					<div class="media-body">
						<h2 class="media-heading" id="label_ganancias_servicio_hoy">0</h2>
						<i>GANANCIAS</i>
					</div>
					<div class="media-right media-middle">
						<span class="icon-attach_money"></span>
					</div>
				</div>	
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-9">
                <div class="grafica_ppal sombra">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>SERVICIOS PRESTADOS <span class="label pull-right <%= u.getColor() %>" id="label_servicios_prestados"></span></h3>
                        </div>
                        <div class="col-md-4">
                            <br>
                            <select name="" id="slt_graficoServiciosPrestados" class="form-control pull-right">
                                <option value="ultimos_7" class="opt_grafico">Últimos 7 días</option>
                                <option value="ultimos_15" class="opt_grafico">Últimos 15 días</option>
                                <option value="ultimos_30" class="opt_grafico">Últimos 30 días</option>
                            </select>
                        </div>
                    </div>
                   	<div class="carga">
                   		<span class="icon-poll"></span>
                   	</div>
                    <div id="chart_div">
                    </div>
                </div>
                <div class="grafica_ppal sombra">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>LIQUIDACIONES REALIZADAS <span class="label pull-right <%= u.getColor() %>" id="label_liquidaciones_realizadas"></span></h3>
                        </div>
                        <div class="col-md-4">
                            <br>
                            <select name="" id="slt_graficoLiquidacionesRealizadas" class="form-control pull-right">
                                <option value="ultimos_7" class="opt_grafico">Últimos 7 días</option>
                                <option value="ultimos_15" class="opt_grafico">Últimos 15 días</option>
                                <option value="ultimos_30" class="opt_grafico">Últimos 30 días</option>
                            </select>
                        </div>
                    </div>
                    <div class="carga">
                   		<span class="icon-poll"></span>
                   	</div>
                    <div id="chartLiquidacionesPrestadas">
                    </div>
                </div>
                <div class="grafica_ppal sombra">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>GANANCIAS <span class="label pull-right <%= u.getColor() %>" id="label_ganancias"></span></h3>
                        </div>
                        <div class="col-md-4">
                            <br>
                            <select name="" id="slt_graficoGanancias" class="form-control pull-right">
                                <option value="ultimos_7" class="opt_grafico">Últimos 7 días</option>
                                <option value="ultimos_15" class="opt_grafico">Últimos 15 días</option>
                                <option value="ultimos_30" class="opt_grafico">Últimos 30 días</option>
                            </select>
                        </div>
                    </div>
                    <div class="carga">
                   		<span class="icon-poll"></span>
                   	</div>
                    <div id="chartGanancias">
                    </div>
                </div>
                <div class="grafica_ppal sombra ">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>GANANCIAS POR SERVICIO <span class="label pull-right <%= u.getColor() %>" id="label_ganancias_servicio"></span></h3>
                        </div>
                        <div class="col-md-4">
                            <br>
                            <select name="" id="slt_graficoGananciasServicio" class="form-control pull-right">
                                <option value="ultimos_7" class="opt_grafico">Últimos 7 días</option>
                                <option value="ultimos_15" class="opt_grafico">Últimos 15 días</option>
                                <option value="ultimos_30" class="opt_grafico">Últimos 30 días</option>
                            </select>
                        </div>
                    </div>
                    <div class="carga">
                   		<span class="icon-poll"></span>
                   	</div>
                    <div id="chartGananciasServicio">
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="grafica_2 sombra text-center">
                	<br>
                    <h4>SERVICIOS DE HOY</h4>
                    <div id="chart_pie">
                    </div>
                </div>
                <div class="grafica_2 sombra text-center">
                	<br>
                    <h4>GANANCIAS DE HOY</h4>
                    <div id="pieChartGananciasPorServicio">
                    </div>
                </div>
            </div>				
		</div>
	</div>
</body>
</html>
<% } %>