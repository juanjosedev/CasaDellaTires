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
		alert.show().delay(3000).fadeOut(500);;
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
			if(largo == 0 || espacios == 0){
				return false;
			}
			return largo == espacios
		}
		function campoAlfaNumerico(val){
			mensaje = "Solo caracteres alfanuméricos";
			return /^[ a-z0-9\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1]*$/i.test(val);
		}
		function campoContieneEspacios(val){
			mensaje = "No puede contener espacios";
			return contarCaracter(val, " ") != 0;
		}
		function campoRangoCaracteres(val, inicio, final){
			var len = val.length;
			var flag = false;
			if( len < inicio ){
				mensaje = "Mínimo "+ inicio + " caracteres";
			} else if ( len > final ){
				mensaje = "Máximo "+ final + " caracteres";
			} else {
				flag = true;
			}
			return flag;
			
		}
		function campoAlfa(val){
			mensaje = "Solo caracteres alfabéticos :v";
			return /^[ a-z\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1]*$/i.test(val);
		}
		function campoNumerico(val){
			mensaje = "Solo caracteres numéricos :v";
			return /^[ 0-9]*$/i.test(val);
		}
		
		function validarUsuario() {
			var val = inp_usuario.val();
			var res = false;
			if (!campoVacio(val)) {
				if(!campoVacioSoloEspacios(val)){
					if(campoAlfaNumerico(val)){
						if(!campoContieneEspacios(val)){
							res = true;	
						} else {
							showAlert(alert_usuario_error, mensaje);
						}
					} else {
						
						showAlert(alert_usuario_error, mensaje);
					}
				} else {
					showAlert(alert_usuario_error, mensaje);
				}
			} else {
				showAlert(alert_usuario_error, mensaje);
			}			
			return res;
		}
		
		function validarClave() {
			var val = inp_clave.val();
			var res = false;
			if (!campoVacio(val)) {
				if(!campoVacioSoloEspacios(val)){
					if(campoAlfaNumerico(val)){
						if(!campoContieneEspacios(val)){
							if(campoRangoCaracteres(val, 8, 20)){
								res = true;
							} else {
								showAlert(alert_clave_error, mensaje);
							}
						} else {
							showAlert(alert_clave_error, mensaje);
						}
					} else {
						showAlert(alert_clave_error, mensaje);
					}
				} else {
					showAlert(alert_clave_error, mensaje);
				}
			} else {
				showAlert(alert_clave_error, mensaje);
			}			
			return res;
		}
		
		function validarNombre() {
			var val = inp_nombre.val();
			var res = false;
			if (!campoVacio(val)) {
				if(!campoVacioSoloEspacios(val)){
					if(campoAlfa(val)){
						if(campoRangoCaracteres(val, 1, 50)){
							res = true;
						} else {
							showAlert(alert_nombre_error, mensaje);
						}
					} else {
						showAlert(alert_nombre_error, mensaje);
					}
				} else {
					showAlert(alert_nombre_error, mensaje);
				}
			} else {
				showAlert(alert_nombre_error, mensaje);
			}			
			return res;
		}
		
		function validarPrimerApellido() {
			var val = inp_primer_apellido.val();
			var res = false;
			if (!campoVacio(val)) {
				if(!campoVacioSoloEspacios(val)){
					if(campoAlfa(val)){
						if(campoRangoCaracteres(val, 1, 50)){
							res = true;
						} else {
							showAlert(alert_primer_apellido_error, mensaje);
						}
					} else {
						showAlert(alert_primer_apellido_error, mensaje);
					}
				} else {
					showAlert(alert_primer_apellido_error, mensaje);
				}
			} else {
				showAlert(alert_primer_apellido_error, mensaje);
			}			
			return res;
		}
		
		function validarSegundoApellido() {
			var val = inp_segundo_apellido.val();
			var res = false;
			if(!campoVacioSoloEspacios(val)){
				if(campoAlfa(val)){
					if(campoRangoCaracteres(val, 0, 50)){
						res = true;
					} else {
						showAlert(alert_segundo_apellido_error, mensaje);
					}
				} else {
					showAlert(alert_segundo_apellido_error, mensaje);
				}
			} else {
				showAlert(alert_segundo_apellido_error, mensaje);
			}
						
			return res;
		}
		
		function validarTelefono() {
			var val = inp_telefono.val();
			var res = false;
			if(!campoVacio(val)){
				if(!campoVacioSoloEspacios(val)){
					res = true;
				} else {
					showAlert(alert_telefono_error, mensaje);
				}
			}else{
				showAlert(alert_telefono_error, mensaje);
			}
						
			return res;
		}
		
		function validarDireccion() {
			var val = inp_direccion.val();
			var res = false;
			if(!campoVacio(val)){
				if(!campoVacioSoloEspacios(val)){
					res = true;
				} else {
					showAlert(alert_direccion_error, mensaje);
				}				
			}else{
				showAlert(alert_direccion_error, mensaje);
			}
						
			return res;
		}
		
		function init() {
			if(validarUsuario()){
				if(validarClave()){
					if(validarNombre()){
						if(validarPrimerApellido()){
							if(validarSegundoApellido()){
								if(validarTelefono()){
									if(validarDireccion()){
										registrarOperador();
									}
								}
							}
						}	
					}				
				}				
			}
		}
		
		init();
		
	}
	
	function registrarOperador(){
		
		var formulario = $("#frm_nuevo_cliente");
		
		function getData(){
			return formulario.serialize();
		}
		
		var data = getData()+"&registrar=true";
		
		$.post("../../operadoress", data, function(res, est, jqXHR){
			if (res == "true") {
				showAlert(alert_agregar_exitoso, "El operador se agregó correctamente");
			} else if (res == "false") {
				showAlert(alert_agregar_error, "ERROR al agregar al operador");
			} else if (res != ""){
				showAlert(alert_usuario_error, res);
			}
			formulario[0].reset();
			actualizarTablaOperadores();
		});
		
		function actualizarTablaOperadores(){
			$("#tabla_operadores").load("tablaOperadores.jsp");
		}
	}
	

	formInit();
	
});