$(document).ready(function() {
	
	function esVacio (valor) {
		return valor == "";
	}
	
	function valPrecio (val) {
		return !isNaN(val);
	}
	
	function valNombre (val) {
		return /^[ a-z0-9\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1]*$/i.test(val.toLowerCase());
	}

	function valNombreEspacios (val) {
		return !/^[ ]*$/i.test(val.toLowerCase());
	}
	
	function getAlert (alert) {
		alert.show().delay(2000).fadeOut(500);
	}
	
	function actualizar_tabla() {
		$(".tabla_servicios").load("tablaServicios.jsp");
	}
	
	function agregarServicio () {
		
		var data = $("#frm_agregar").serialize();
		$.post("../../crudservicios", data, function(res, est, jqXHR){
			if(res == "false") {
				getAlert(art_error);
			} else{
				getAlert(art_exito);
				actualizar_tabla();
			}
		});
		
	}
	
	// Campos del formulario agregar
	
	var slt_tipo = $("#slt_tipo");
	var inp_precio_agregar = $("#inp_precio_agregar");
	var inp_nombre_agregar = $("#inp_nombre_agregar");
	
	// Alerts del formulario agregar
	
	var art_vacio_tipo = $("#art_vacio_tipo").hide();
	var art_vacio_precio = $("#art_vacio_precio").hide();
	var art_uni_num_precio = $("#art_uni_num_precio").hide();
	var art_vacio_nombre = $("#art_vacio_nombre").hide();
	var art_uni_alfanum_nombre = $("#art_uni_alfanum_nombre").hide();
	
	// Alerts de si se agregó o no el servicio
	
	var art_error = $("#art_error").hide();
	var art_exito = $("#art_exito").hide();
	
	$("#sbt_agregar").click(function(e){
		e.preventDefault();
		
		var val = slt_tipo.val();
		
		if (!esVacio(val)) {
			var val = inp_nombre_agregar.val();
			if (!esVacio(val)) {
				if (valNombreEspacios(val)) {
					if (valNombre(val)) {
						val = inp_precio_agregar.val();
						if (!esVacio(val)) {	
							if (valPrecio(val)) {
								agregarServicio();
							} else {
								getAlert(art_uni_num_precio);
							}
						} else {
							
							getAlert(art_vacio_precio);
						}	
					} else {
						
						getAlert(art_uni_alfanum_nombre);
					}
				} else {
					getAlert(art_vacio_nombre);
				}
			} else {
				getAlert(art_vacio_nombre);
			}
		} else {
			getAlert(art_vacio_tipo);
		}
		
	});
	
});