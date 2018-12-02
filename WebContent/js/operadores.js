$(document).ready(function(){
	
	// inputs formulario de registro
	
	var inp_usuario = $("#inp_usuario");
	var inp_clave = $("#inp_clave");
	var inp_nombre = $("#inp_nombre");
	var inp_primer_apellido = $("#inp_primer_apellido");
	var inp_segundo_apellido = $("#inp_segundo_apellido");
	var inp_telefono = $("#inp_telefono");
	var inp_direccion = $("#inp_direccion");
	
	// alerts formulario de registro
	
	var alert_usuario_error = $("#alert_usuario_error");
	var alert_clave_error = $("#alert_clave_error");
	var alert_nombre_error = $("#alert_nombre_error");
	var alert_primer_apellido_error = $("#alert_primer_apellido_error");
	var alert_segundo_apellido_error = $("#alert_segundo_apellido_error");
	var alert_telefono_error = $("#alert_telefono_error");
	var alert_direccion_error = $("#alert_direccion_error");
	
	var alert_agregar_exitoso = $("#alert_agregar_exitoso");
	var alert_agregar_error = $("#alert_agregar_error");
	
	var sbt_agregar_operador = $("#sbt_agregar_operador");
	
	function hideAlert(alert){
		alert.hide();
	}
	
	function showAlert(alert, mensaje){
		alert.find("var").text(mensaje);
		alert.show();
	}
	
	function formInit(){
		var alerts = [alert_usuario_error, alert_clave_error, alert_nombre_error, alert_primer_apellido_error,
						alert_segundo_apellido_error, alert_telefono_error, alert_direccion_error, alert_agregar_exitoso, alert_agregar_error];
		
		for (var i = 0; i < alerts.length; i++) {
			hideAlert(alerts[i]);
		}
		
		sbt_agregar_operador.click(function(e){
			e.preventDefault();
			validarCampos();
		});
		
	}
	
	function validarCampos(){
		
		var mensaje = "";
		var valor = "";
		
		function contarCaracter(val, caracter){
			return (val.match(new RegExp(caracter, "g")) || []).length;
		}
		function campoVacio(val){
			mensaje = "Campo obligatorio";
			return val == "";
		}
		function campoVacioSoloEspacios(val){
			var largo = val.length;
			var espacios = contarCaracter(val, " ");
			mensaje = "Inválido";
			return largo == espacios;
		}
		function campoAlfaNumerico(val){
			mensaje = "Solo caracteres alfanuméricos";
			return /^[ a-z0-9\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1]*$/i.test(val);
		}
		function campoContieneEspacios(val){
			mensaje = "No puede contener espacios";
			return contarCaracter(val, " ") != 0;
		}
		
		//crear una funcion para validar cada campo ejemplo function validarUsuario()
		
		val = inp_usuario.val();
		if (!campoVacio(val)) {
			if(!campoVacioSoloEspacios(val)){
				if(campoAlfaNumerico(val)){
					if(!campoContieneEspacios(val)){
						
					} else {
						showAlert(alert_usuario_error, mensaje);
					}
				} else {
					showAlert(alert_usuario_error, mensaje)
				}
			} else {
				showAlert(alert_usuario_error, mensaje)
			}
		} else {
			showAlert(alert_usuario_error, mensaje);
		}
		
	}
	

	formInit();
	
});