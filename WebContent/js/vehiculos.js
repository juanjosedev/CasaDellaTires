$(document).ready(function() {
	
	function esVacio (valor) {
		return valor == "";
	}
	
	function valTamPlaca (val) {
		return val.length == 3;
	}
	
	function valSintaxPlaca1 (val) {
		return /^[a-z]*$/i.test(val.toLowerCase());
	}
	
	function valSintaxPlaca2 (val) {
		return !isNaN(val);
	}
	
	function getAlert (alert) {
		alert.show().delay(10000).fadeOut(500);
	}
	
	function agregarVehiculo () {
		
		var data = $("#frm_agregar").serialize();
		
		$.post("../../crudvehiculos", data, function(res, est, jqXHR){
			if(res == "false"){
				getAlert(art_error);
			} else {
				$("#frm_agregar")[0].reset();
				getAlert(art_exito);
				actualizar_tabla();
				var art_mdf_error = $(".modal #art_mdf_error").hide();
				var art_mdf_success = $(".modal #art_mdf_success").hide();
			}
		});
		
	}
	
	function modificarVehiculo (frm) {
		var data = frm.serialize();
		$.post("../../crudvehiculos", data, function(res, est, jqXHR){
			if(res == "false"){
				getAlert(frm.find("#art_mdf_error"));
			} else {
				getAlert(frm.find("#art_mdf_success"));
				setTimeout(function(){
					$(".modal").modal("hide");
					setTimeout(function(){
						location.href ="Vehiculos.jsp";
					}, 500);		
				}, 3000);
				
			}
		});
	}
	
	function actualizar_tabla() {
		$(".tabla_vehiculos").load("tablaVehiculos.jsp");
	}
	
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	    results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	if(getParameterByName("agregar") == "true") {
		$('#agregarVehiculo').modal({
	        show: 'true'
	    });
	}
	
	//Campos del formulario agregar
	
	var inp_placa_agregar1 = $("#inp_placa_agregar1");
	var inp_placa_agregar2 = $("#inp_placa_agregar2");
	var slt_tipo = $("#slt_tipo");
	var slt_marca = $("#slt_marca");
	var inp_modelo = $("#inp_modelo");
	var sbt_agregar = $("#sbt_agregar");
	
	// Alerts de los campos de agregar
	
	var art_vacio_pl1 = $("#art_vacio_pl1").hide();
	var art_uni_lts_pl1 = $("#art_uni_lts_pl1").hide();
	var art_tam_pl1 = $("#art_tam_pl1").hide();
	
	var art_vacio_pl2 = $("#art_vacio_pl2").hide();
	var art_uni_num_pl2 = $("#art_uni_num_pl2").hide();
	var art_tam_pl2 = $("#art_tam_pl2").hide();
	
	var art_vacio_tipo = $("#art_vacio_tipo").hide();
	
	// Alerts de si se agregó o no el vehículo
	
	var art_error = $("#art_error").hide();
	var art_exito = $("#art_exito").hide();
	
	// Alert si se modificó o no el vehículo
	
	var art_mdf_error = $(".modal #art_mdf_error").hide();
	var art_mdf_success = $(".modal #art_mdf_success").hide();
	
	// Evento al botón agregar
	
	$("#sbt_agregar").click(function(e) {
		e.preventDefault();
		
		var val = inp_placa_agregar1.val();// Placa parte 1
		
		if (!esVacio(val)) {
			if (valTamPlaca(val)) {
				if (valSintaxPlaca1(val)) {
					var val = inp_placa_agregar2.val();// Placa parte 2 
					if (!esVacio(val)) {
						if (valTamPlaca(val)) {
							if (valSintaxPlaca2(val)) {
								var val = slt_tipo.val();// Tipo de vehículo
								if (!esVacio(val)) {
									 agregarVehiculo();
								} else {
									getAlert(art_vacio_tipo);
								}
							} else {
								getAlert(art_uni_num_pl2);
							}
						} else {
							getAlert(art_tam_pl2);
						}
					} else {
						getAlert(art_vacio_pl2);
					}
				} else {// Fin placa parte 1
					getAlert(art_uni_lts_pl1);
				}
			} else {
				getAlert(art_tam_pl1);
			}
		} else {
			getAlert(art_vacio_pl1);
		}
	});
	
	$(".modal #sbt_modificar").click(function(e){
		e.preventDefault();
		
		var formulario = $(this).parent().parent();
		
		modificarVehiculo(formulario);
		
	});
});