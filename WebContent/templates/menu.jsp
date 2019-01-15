<%@ page import="include.*" %>
<%
	HttpSession sesion = request.getSession(true);
	Object username = sesion.getAttribute("username") == null ? null : sesion.getAttribute("username");
	if(username == null){
		response.sendRedirect("../../index.jsp");
	}else{
		Usuario u = (Usuario) username;
%>
<br>
<div class="col-md-2 menu_nav sombra">
	<div class="row  txt-blanco text-center">
		<h1 class=""><span class="icon-person icon-cuenta"></span></h1>
		<p id="tipo_usuario"><%= u.getTipo() %></p>
	</div>
	<div class="row">
		<ul class="nav nav-pills nav-stacked">
			<li role="presentation"><a href="Inicio.jsp">Inicio <span class="icon-poll pull-right"></span></a></li>
			<li role="presentation"><a href="Liquidaciones.jsp">Liquidaciones <span class="icon-library_books pull-right"></span></a></li>
			<li role="presentation"><a href="Clientes.jsp">Clientes <span class="icon-group pull-right"></span></a></li>
			<li role="presentation"><a href="Vehiculos.jsp">Vehículos <span class="icon-drive_eta pull-right"></span></a></li>
			<li role="presentation"><a href="Servicios2.jsp">Servicios <span class="icon-local_car_wash pull-right"></span></a></li>
			<!-- <li role="presentation"><a href="Tipo_Vehiculos.jsp">Tipos Vehículos <span class="icon-local_shipping pull-right"></span></a></li> -->
			<% if(u.getTipo().equals("Admin")){ %>
			<li role="presentation"><a href="Operadores.jsp">Operadores <span class="icon-group pull-right"></span></a></li>
			<% } %>
			<li role="presentation"><a href="#">Cuenta <span class="icon-face pull-right"></span></a></li>
			<li role="presentation"><a href="../../index.jsp" id="close_log">Cerrar sesión <span class="icon-clear pull-right"></span></a></li>
		</ul>
	</div>
</div>
<script>
	$(document).ready(function(){
		var bg_color = $(".title_maestro").css('background-color');
		$(".nav a").hover(function(){
			$(this).css("background-color", bg_color);
		});
		$(".nav a").mouseleave(function() {
		    $(this).css("background-color", "");
		 });
	});
</script>
<% } %>