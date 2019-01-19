$(document).ready(function() {
	
	function validar_letras(cadena) {
		
		if (solo_espacios(cadena)){
			
			return true;
		
		} else {
			
			return !/^[ a-z\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1]*$/i.test(cadena);
			
		}
		
	}
	
	function solo_espacios(cadena) {
		
		espacio = " ";
		cont = 0;
		
		for (var i = 0; i < cadena.length; i++) {
			
			cont = espacio == cadena[i] ? cont + 1 : cont;
			
		}
		
		return cont == cadena.length;
		
	}
	
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	    results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	if(getParameterByName("agregar") == "true") {
		$('#agregarCliente').modal({
	        show: 'true'
	    });
	}
	
	function getColor() {
    	var color = $(".title_maestro").css("background-color");
    	return color;
    }
	
	$(".boton-vacio").css("color", getColor());
	$(".boton-vacio").mouseover(function(){
		$(this).css("background-color", getColor());
		$(this).css("color", "#fff");
	}).mouseout(function(){
		$(this).css("background-color", "transparent");
		$(this).css("color", getColor());
	});
	
	function agregar_cliente() {
		var data = $("#frm_nuevo_cliente").serialize();
		$.post("../../crearcliente", data, function(res, est, jqXHR){
			if(res == "false"){
				alert_agregar_error.show().delay(2000).fadeOut(500);
			} else {
				$("#frm_nuevo_cliente")[0].reset();
				alert_agregar_exitoso.show().delay(2000).fadeOut(500);
				actualizar_tabla();
			}
		});
	}
	
	function actualizar_tabla() {
		$("#tabla_clientes").load("tablaClientes.jsp", function(){
			tableDesign();
		});
		setTimeout(function(){
			
			var alert_modificar_exitoso = $(".modal_modificar #alert_modificar_exitoso").hide();
			var alert_modificar_error = $(".modal_modificar #alert_modificar_error").hide();
			
			var alert_modificar_nombre = $(".modal_modificar #alert_modificar_nombre").hide();
			var alert_validacion_modificar_nombre = $(".modal_modificar #alert_validacion_modificar_nombre").hide();
			var alert_modificar_apellido = $(".modal_modificar #alert_modificar_apellido").hide();
			var alert_validar_modificar_apellido = $(".modal_modificar #alert_validar_modificar_apellido").hide();
			var alert_validar_modificar_segundo_apellido = $(".modal_modificar #alert_validar_modificar_segundo_apellido").hide();
			
			$(".modal_modificar #modificar_cliente").click(function(e) {
				e.preventDefault();
				
				var data = $(this).parent().parent().serialize();
				
				modificar_cliente(data, $(this).parent().parent());
				
			});
		}, 1000);
	}

	
	function modificar_cliente(data, formulario) {
		
		var frm = formulario;
		
		$.post("../../crearcliente", data, function(res, est, jqXHR){
			if (res == "true") {
				frm.find("#alert_modificar_exitoso").show().delay(2000).fadeOut(500);
				$("#varTelefono").html($("#input_telefono_modificar").val());
				$("#varDireccion").html($("#input_direccion_modificar").val());
				setTimeout(function(){
					$(".modal").modal("hide");
				}, 2500);		
			} else {
				frm.find("#alert_modificar_error").show().delay(2000).fadeOut(500);
			}
		});
	}
	
	function reloadAlert() {
		var alert_modificar_exitoso = $(".modal_modificar #alert_modificar_exitoso").hide();
		var alert_modificar_error = $(".modal_modificar #alert_modificar_error").hide();
	}
	
	// Sección agregar cliente
	
	var alert_agregar_exitoso = $("#alert_agregar_exitoso").hide();
	var alert_agregar_error = $("#alert_agregar_error").hide();
	
	var alert_agregar_cc = $("#alert_agregar_cc").hide();
	var alert_validacion_cc = $("#alert_validacion_cc").hide();
	var alert_agregar_nombre = $("#alert_agregar_nombre").hide();
	var alert_validacion_nombre = $("#alert_validacion_nombre").hide();
	var alert_agregar_apellido = $("#alert_agregar_apellido").hide();
	var alert_validar_apellido = $("#alert_validar_apellido").hide();
	var alert_validar_segundo_apellido = $("#alert_validar_segundo_apellido").hide();
	
	var input_cc = $("#input_cc");
	var input_nombre = $("#input_nombre");
	var input_primer_apellido = $("#input_primer_apellido");
	var input_segundo_apellido = $("#input_segundo_apellido");
	var input_telefono = $("#input_telefono");
	var input_direccion = $("#input_direccion");
	
	// Sección modificar
	
	var alert_modificar_exitoso = $(".modal_modificar #alert_modificar_exitoso").hide();
	var alert_modificar_error = $(".modal_modificar #alert_modificar_error").hide();
	
	var alert_modificar_nombre = $(".modal_modificar #alert_modificar_nombre").hide();
	var alert_validacion_modificar_nombre = $(".modal_modificar #alert_validacion_modificar_nombre").hide();
	var alert_modificar_apellido = $(".modal_modificar #alert_modificar_apellido").hide();
	var alert_validar_modificar_apellido = $(".modal_modificar #alert_validar_modificar_apellido").hide();
	var alert_validar_modificar_segundo_apellido = $(".modal_modificar #alert_validar_modificar_segundo_apellido").hide();
	
	var input_nombre_modificar = $(".modal_modificar #input_nombre_modificar");
	var input_primer_apellido_modificar = $(".modal_modificar #input_primer_apellido_modificar");
	var input_segundo_apellido_modificar = $(".modal_modificar #input_segundo_apellido_modificar");
	var input_telefono_modificar = $(".modal_modificar #input_telefono_modificar");
	var input_direccion_modificar = $(".modal_modificar #input_direccion_modificar");
	
	$("#submit_nuevo_cliente").click(function(e) {
		e.preventDefault();
		
		var sw = true;
		
		if(input_cc.val() == "") {
			showAlert(alert_validacion_cc, " Rellena el campo cédula");
			sw = false;
		} else {
			var cc = parseInt(input_cc.val());
			if (isNaN(cc)) {
				showAlert(alert_validacion_cc, " Unicamente números");
				sw = false;
			} else {
				if (input_nombre.val() == "") {
					showAlert(alert_agregar_nombre, " Rellena el campo nombre");
					sw = false;
				} else {
					var nombre = input_nombre.val();
					nombre = nombre.toLowerCase();
					if (validar_letras(nombre)) {
						showAlert(alert_agregar_nombre, " Unicamente letras");
						sw = false;
					} else {
						if (input_primer_apellido.val() == "") {
							showAlert(alert_agregar_apellido, " Rellena el campo primer apellido");
//							alert_agregar_apellido.show().delay(2000).fadeOut(500);
							sw = false;
						} else {
							var primer_apellido = input_primer_apellido.val();
							primer_apellido = primer_apellido.toLowerCase();
							if (validar_letras(primer_apellido)) {
								showAlert(alert_agregar_apellido, " Unicamente letras");
								sw = false;
							} else {
								if(input_segundo_apellido.val() != "") {
									var segundo_apellido = input_segundo_apellido.val();
									segundo_apellido = segundo_apellido.toLowerCase();
									if(validar_letras(segundo_apellido)) {
										showAlert(alert_validar_segundo_apellido, " Unicamente letras");
										sw = false;
									}
								}
							}
						}
					}	
				}
			}
		}
		
		if(sw) {

			agregar_cliente()

		}
	});
	
	$(".modal_modificar #modificar_cliente").click(function(e) {
		e.preventDefault();

		var sw = true;
		var frm = $(this).parent().parent();
		
		if (frm.find(input_nombre_modificar).val() == "") {
			frm.find(alert_modificar_nombre).show().delay(2000).fadeOut(500);
			sw = false;
		} else {
			var nombre = frm.find(input_nombre_modificar).val();
			nombre = nombre.toLowerCase();
			if (validar_letras(nombre)) {
				frm.find(alert_validacion_modificar_nombre).show().delay(2000).fadeOut(500);
				sw = false;
			} else {
				if (frm.find(input_primer_apellido_modificar).val() == "") {
					frm.find(alert_modificar_apellido).show().delay(2000).fadeOut(500);
					sw = false;
				} else {
					var primer_apellido = frm.find(input_primer_apellido_modificar).val();
					primer_apellido = primer_apellido.toLowerCase();
					if (validar_letras(primer_apellido)) {
						frm.find(alert_validar_modificar_apellido).show().delay(2000).fadeOut(500);
						sw = false;
					} else {
						if(frm.find(input_segundo_apellido_modificar).val() != "") {
							var segundo_apellido = frm.find(input_segundo_apellido_modificar).val();
							segundo_apellido = segundo_apellido.toLowerCase();
							if(validar_letras(segundo_apellido)) {
								frm.find(alert_validar_modificar_segundo_apellido).show().delay(2000).fadeOut(500);
								sw = false;
							}
						}
					}
				}
			}	
		}
		
		
		if(sw) {

			var data = $(this).parent().parent().serialize();
			
			modificar_cliente(data, $(this).parent().parent());

		}
		
	});
	
	$("#buscar_cc").click(function(e) {
		if($("#input_buscar_cc").val() == "") {
			alert("Introduce un criterio de busqueda");
		} else {
			var valor = $("#input_buscar_cc").val();
			location.href ="Clientes.jsp?query="+valor;
		} 
		
	});
	
	$("#volver").click(function(e) {
		location.href ="Clientes.jsp";
	});
	
});