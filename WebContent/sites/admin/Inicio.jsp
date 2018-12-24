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
		if(!u.getTipo().equals("Admin")){
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
				<h2 class="text-uppercase"><span class="icon-home"></span> Inicio</h2>
			</div>
		</div>
	</div>
	<jsp:include page="../../templates/menu.jsp"></jsp:include>
	<div class="col-md-10">
		<div class="row">
			<div class="col-md-3">
				<div class="media  <%= u.getColor() %> sombra tag-data1">
					<div class="media-body">
						<h2 class="media-heading">8</h2>
						<i>SERVICIOS</i>
					</div>
					<div class="media-right media-middle">
						<span class="icon-settings"></span>
					</div>
				</div>	
			</div>
			<div class="col-md-3">
				<div class="media  <%= u.getColor() %> sombra tag-data1">
					<div class="media-body">
						<h2 class="media-heading">7</h2>
						<i>LIQUIDACIONES</i>
					</div>
					<div class="media-right media-middle">
						<span class="icon-library_books"></span>
					</div>
				</div>	
			</div>
			<div class="col-md-3">
				<div class="media  <%= u.getColor() %> sombra tag-data1">
					<div class="media-body">
						<h2 class="media-heading">577050</h2>
						<i>GANANCIAS</i>
					</div>
					<div class="media-right media-middle">
						<span class="icon-attach_money"></span>
					</div>
				</div>	
			</div>
			<div class="col-md-3">
				<div class="media  <%= u.getColor() %> sombra tag-data1">
					<div class="media-body">
						<h2 class="media-heading">5</h2>
						<i>CLIENTES</i>
					</div>
					<div class="media-right media-middle">
						<span class="icon-person"></span>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-9">
                <div class="grafica_ppal sombra ">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>SERVICIOS PRESTADOS</h3>
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
                    <div id="chart_div">
                    </div>
                </div>
                <div class="grafica_ppal sombra">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>LIQUIDACIONES REALIZADAS</h3>
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
                    <div id="chartLiquidacionesPrestadas">
                    </div>
                </div>
                <div class="grafica_ppal sombra">
                    <div class="row">
                        <div class="col-md-8">
                            <h3>GANANCIAS</h3>
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
                    <div id="chartGanancias">
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
            </div>				
		</div>
	</div>
</body>
</html>
<% } %>