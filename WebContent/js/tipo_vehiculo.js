$(document).ready(function() {
	
	tabla_tipo_vehiculos();
	
	var alert_agregar_nombre = $("#alert_agregar_nombre").hide();
	var alert_agregar_exitoso = $("#alert_agregar_exitoso").hide();
	var alert_agregar_error = $("#alert_agregar_error").hide();
	
	var input_agregar_nombre = $("#nombre_tipo_vehiculo");
	
	$("#submit_nuevo_tipo_vehiculo").click(function(e) {
		e.preventDefault();
		
		if(input_agregar_nombre.val() == "") {
			alert_agregar_nombre.show();
		} else {
			var data = $("#frm_nuevo_tipo_vehiculo").serialize()+"&crear_tv=true";
			$.post("../../creartipovehiculo", data, function(res, est, jqXHR){
				if(res == "false"){
					alert_agregar_error.show();
				} else {
					alert_agregar_exitoso.show();	
				}
			});
		}
	});
	
	var alert_agregar_nombre = $("td #alert_agregar_nombre").hide();
	var alert_nombre_invalido = $("td #alert_nombre_invalido").hide();
	
	function getId(btn){
		return btn.parent().parent().find(".form-group #nombre").attr("cod");
	}
	
	function getNombre(btn){
		return btn.parent().parent().find(".form-group #nombre").val();
	}
	
	function modificarTabla(btn, id, nombre){
		var td_nombre = btn.parent().parent().parent().parent().parent().parent().find(".td_nombre");
		td_nombre.text(nombre);
	}
	
	function vacio(val){
		return val == "";
	}
	
	function getAlert (alert) {
		alert.show().delay(2000).fadeOut(500);
	}
	
	function valSintaxNombre (val) {
		var val = val.toLowerCase();
		return !/^[a-z][0-9]*$/i.test(val);
	}
	
	function solo_espacios(cadena) {
		var espacio = " ";
		var cont = 0;
		for (var i = 0; i < cadena.length; i++) {
			cont = espacio == cadena[i] ? cont + 1 : cont;
		}
		return cont == cadena.length;
		
	}
	
	function valAlfaNum(cadena) {
		cadena = cadena.toLowerCase();
		return /^[ a-z0-9\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1]*$/i.test(cadena);
	}
	
	function modificarVehiculo(data) {
		$.post("../../creartipovehiculo", data, function(res, est, jqXHR){
			if(res){
				alert("Se ha modificado correctamente");
			} else{
				alert("Ha ocurrido un fallo");
			}
		});
		return respuesta;
	}
	
	function tabla_tipo_vehiculos(){
		$(".modal-footer #modificar").click(function(e) {
			var id = getId($(this));
			var nombre = getNombre($(this));
			var data = "id="+id+"&nombre="+nombre+"&modificar=true";
			if (!vacio(nombre)){
				if(!solo_espacios(nombre)){
					if (valAlfaNum(nombre)) {
						modificarTabla($(this), id, nombre);
						modificarVehiculo(data);
					} else {
						getAlert(alert_nombre_invalido);
					}				
				} else {
					getAlert(alert_agregar_nombre);
				}
			} else {
				getAlert(alert_agregar_nombre);
			}
		});
	}
	
});