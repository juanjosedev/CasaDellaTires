$(document).ready(function(){
	
	var inp_usuario = $("#usuario");
	var inp_clave = $("#clave");
	
	var alert_usuario = $("#alert_obligatorio_usuario");
	var alert_clave = $("#alert_obligatorio_clave");
	var alert_error = $("#alert_error");
	
	var submit = $("#sbt_iniciar_sesion");
	
	hideAlert(alert_usuario);
	hideAlert(alert_clave);
	hideAlert(alert_error);
	
	function getInpVal(inp){
		return inp.val();
	}
	
	function hideAlert(alert){
		alert.hide();
	}
	
	function showAlert(alert){
		alert.show();
	}
	
	function onlySpaces(cadena) {
		espacio = " ";
		cont = 0;
		for (var i = 0; i < cadena.length; i++) {
			cont = espacio == cadena[i] ? cont + 1 : cont;
		}
		return cont == cadena.length;
	}
	
	function validarCampos(){
		var valor_usuario = getInpVal(inp_usuario);
		var valor_clave = getInpVal(inp_clave);
		var flag_usuario = false;
		var flag_clave = false;
		if(valor_usuario == ""){
			showAlert(alert_usuario);	
		}else if (onlySpaces(valor_usuario)){
			showAlert(alert_usuario);			
		}else{
			flag_usuario = true;
		}
		if(valor_clave == ""){
			showAlert(alert_clave);
		}else if (onlySpaces(valor_clave)){
			showAlert(alert_clave);						
		}else{
			flag_clave = true;
		}
		return flag_usuario && flag_clave;
	}
	
	function getData(){
		return $("#frm_inicio").serialize();
	}
	
	function enviarDatos(data){
		$.post("usuarios_s", data, function(res, est, jqXHR){
			if(res == "true"){
				window.location="index.jsp";
			}else{
				showAlert(alert_error);
			}
		})
	}
	
	submit.click(function(e){
		e.preventDefault();
		if(validarCampos()){
			var data = getData();
			data += "&login=true";
			enviarDatos(data);
		}
	});
	
});