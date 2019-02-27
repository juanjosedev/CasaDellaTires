$(document).ready(function() {
	
	function esVacio (valor) {
		return valor == "";
	}
	
	function valCedula (valor) {
		return !isNaN(valor);
	}
	
	function valTamPlaca (val) {
		return val.length == 6;
	}
	
	function valSintaxPlaca (val) {
		return /^[a-z]0-9*$/i.test(val.toLowerCase());
	}
	
	function getCont() {
        var n = $("input:checked").length;
        return n;
    }
	
	function validarCheckbox(){
		return getCont() > 0;
    }

	function hideAlert (alert) {
		alert.hide();
	}
	
	function getAlert (alert) {
		alert.show().delay(2000).fadeOut(500);
	}
	
	function getAlertStay (alert) {
		alert.show().delay(2000);
	}
	
	function getData (name) {
		
		var data = "";
		
	}
	
//	function getColor() {
//    	var color = $(".title_maestro").css("background-color");
//    	return color;
//    }
	
	function cedulaExist (cc) {
		
		var data = "cc_exist="+cc;
		
		$.post("../../crudliquidacion", data, function(res, est, jqXHR){
			var respuesta = res == "true";
			if(respuesta) {
				getAlertStay(art_cc_existe);
				hideAlert(art_cc_no_existe);
			} else {
				getAlertStay(art_cc_no_existe);
				hideAlert(art_cc_existe);
			}
		});
		 
	}
	
	function placaExist (placa) {
		
		var data = "placa_exist="+placa;
		
		$.post("../../crudliquidacion", data, function(res, est, jqXHR){
			document.getElementById("tabla_serviciosi").innerHTML = "";
			var respuesta = res == "true";
			if(respuesta) {
				getAlertStay(art_placa_existe);
				data = "placa_tabla="+placa;
				$.post("../../crudliquidacion", data, function(res, est, jqXHR){
					$("#tabla_servicios").html(res);
					$("#tabla_servicios").show();
					ejecutarCalculos();
					$(".table-title").css("background-color", getColor());
				});
				hideAlert(art_placa_no_existe);
			} else {
				getAlertStay(art_placa_no_existe);
				$("#tabla_servicios").html("");
				hideAlert(art_placa_existe);
			}
		});
		 
	}
	
	function confirmarInformacion() {
		formulario = $("#frm_liquidar").serialize();
		alert(formulario);
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
	
	function getInfoCliente(cedula_cliente){
		var cc = "get_info_cliente="+cedula_cliente;
		$.post("../../crudliquidacion", cc, function(res, est, jqXHR){
			$("#info_cliente").html(res);
			descriptionDesign();
		});
		
	}
	
	function getInfoVehiculo(placa_vehiculo) {
		var placa = "get_info_vehiculo="+placa_vehiculo;
		$.post("../../crudliquidacion", placa, function(res, est, jqXHR){
			$("#info_vehiculo").html(res);
			descriptionDesign();
		});
	}
	
	function getFecha() {
		var objDate = new Date();
		return objDate.getDate()+"-"+(objDate.getMonth()+1)+"-"+objDate.getFullYear();
	}
	
	function setFecha() {
		$("#info_fecha").html(getFecha());
	}
	
	function getHora() {
		var objDate = new Date();
		return objDate.getHours()+":"+objDate.getMinutes();
	}
	
	function setHora() {
		$("#info_hora_actual").html(getHora());
	}
	
	function getInfoLiquidacion() {
		informacion_liquidacion.show();
	}
	
	function generarLiquidacion(datos) {
		$.post("../../crudliquidacion", datos, function(res, est, jqXHR){
			if(res == "true"){
				informacion_liquidacion.hide();
			} else {
				informacion_liquidacion.hide();
			}
			getAlert(alert_liquidar_exitoso);
			$("#tabla_servicios").hide();
			formulario_liquidacion.show();
			art_cc_existe.hide();
			art_placa_existe.hide();
			inp_cc_lqr.val("");
			inp_placa_lqr.val("");
			setTimeout(function(){
				actualizarTablaPendientes();
				setTimeout(function(){
					terminarLiquidacion();
				}, 300);
			}, 300);
			btn_volver.hide();
			btn_confirmar.hide();
			sbt_crear_lqd.show();
			document.getElementById("tabla_serviciosi").innerHTML = "";
		});
	}
	
	function actualizarTablaPendientes() {
		$("#tabla_lqds_pendientes").load("tablaLiquidacionesP.jsp", function(){
			descriptionDesign();
		});
	}
	function actualizarTablaFinalizados() {
		$("#tabla_lqds_finalizados").load("tablaLiquidacionesF.jsp", function(){
			descriptionDesign();
		});
	}
	
	function terminarLiquidacion() {
		$(".modal-footer .btn_terminar").click(function(){
			var btn = $(this);
			var data = "terminar_lqd="+btn.attr("id")+"&hora="+getHora();
			$.post("../../crudliquidacion", data, function(res, est, jqXHR){
				if(res == "true"){
					setTimeout(function(){
						cerrarModalActivo();
						setTimeout(function(){
							removerFila(btn);
							setTimeout(function(){
								actualizarTablaFinalizados();
							}, 600);
						}, 600);	
					}, 300);		
				} else {
					alert("Algo ha salido mal :(");
				}
			});
		});
	}

	function cerrarModalActivo() {
		$(".modal").modal("hide");
	}
	
	function removerFila(btn) {
		var fila = $("#fila"+btn.attr("id"));
		fila.remove();
		var label = document.getElementById("label_pendientes");
        label.innerText -= 1;
	}
	
	terminarLiquidacion();
	
	// Alerts del formulario de liquidar
	
	var art_vacio_cc = $("#art_vacio_cc").hide();
	var art_cc_no_existe = $("#art_cc_no_existe").hide();
	var art_cc_existe =  $("#art_cc_existe").hide();
	var art_uni_num__cc = $("#art_uni_num__cc").hide();
	
	var art_vacio_placa = $("#art_vacio_placa").hide();
	var art_val_placa = $("#art_val_placa").hide();
	var art_placa_no_existe = $("#art_placa_no_existe").hide();
	var art_placa_existe =  $("#art_placa_existe").hide();
	
	var art_servicio_cero = $("#art_servicio_cero").hide();
	
	// Campos de la ventana liquidar
	
	var inp_cc_lqr = $("#inp_cc_lqr");
	var inp_placa_lqr = $("#inp_placa_lqr");
	
	// 	Botón submit liquidar
	
	var sbt_crear_lqd = $("#sbt_new_lqd");
	
	inp_cc_lqr.blur(function(){
		var val = inp_cc_lqr.val();
		if(!esVacio(val)) {
			if(valCedula(val)){
				cedulaExist(val);
			} else {
				getAlert(art_uni_num__cc);
			}
		} else {
			getAlert(art_vacio_cc);
		}
	});
	
	inp_placa_lqr.blur(function(){
		var val = inp_placa_lqr.val();
		if(!esVacio(val)) {
			if(valTamPlaca(val)) {
				placaExist(val);
			} else {
				getAlert(art_val_placa);
				$("#tabla_servicios").html("");
			}
		} else {
			getAlert(art_vacio_placa);
			$("#tabla_servicios").html("");
		}
	});
	
	sbt_crear_lqd.click(function(e){
		e.preventDefault();
		var val = inp_cc_lqr.val();
		if (!esVacio(val)) {
			if (valCedula(val)) {
				cedulaExist(val);
				val = inp_placa_lqr.val();
				if (!esVacio(val)) {
					if (valTamPlaca(val)) {
						//confirmarInformacion();
						if(validarCheckbox()){
							var cedula_cliente = inp_cc_lqr.val();
							var placa_vehiculo = inp_placa_lqr.val();
							getInfoCliente(cedula_cliente);
							getInfoVehiculo(placa_vehiculo);
							informacion_liquidacion.show();
							setFecha();
							setHora();
							formulario_liquidacion.hide();
							btn_volver.show();
							btn_confirmar.show();
							$(".table-title").css("background-color", getColor());
							$(this).hide();
						} else {
							getAlert(art_servicio_cero);
						}
					} else {
						getAlert(art_val_placa);
						$("#tabla_servicios").html("");
					}
				} else {
					getAlert(art_vacio_placa);
					$("#tabla_servicios").html("");
				}
			} else {
				getAlert(art_uni_num__cc);
			}
		} else {
			getAlert(art_vacio_cc);
		}
	});
	
	var informacion_liquidacion = $("#informacion_liquidacion").hide();
	var formulario_liquidacion = $("#formulario_liquidacion");
	var btn_volver = $("#btn_volver").hide();
	var btn_confirmar = $("#btn_confirmar").hide();
	
	var alert_liquidar_exitoso = $("#alert_liquidar_exitoso").hide();
	var alert_liquidar_error = $("#alert_liquidar_error").hide();
	
	btn_volver.click(function(){
		formulario_liquidacion.show();
		informacion_liquidacion.hide();
		$(this).hide();
		btn_confirmar.hide();
		sbt_crear_lqd.show();
	});
	
	btn_confirmar.click(function(){
		var datos = $("#frm_liquidar").serialize()+"&hora="+getHora()+"&liquidar=true";
		generarLiquidacion(datos);
	});
	
	
	
	/////////////////////////////////////////////////
	
	function ejecutarCalculos(){
		
		function getPrecio(checkbox) {
	        var inp_precio = checkbox.parent().parent().find("#inp_precio");
	        var precio = inp_precio.val();
	        return precio;
	    }

	    function setSubtotal(precio, sw) {
	        var subtotal = getSubtotal();
	        if (sw) {
	            subtotal += precio;
	        } else {
	            subtotal -= precio;
	        }
	        $("#valor_subtotal").html(subtotal);
	        setSubTotali(subtotal);
	    }

	    function getSubtotal() {
	        return parseInt($("#valor_subtotal").html());
	    }

	    function setDescuento(valor) {
	        $("#valor_descuento").html(valor);
	        setDescuentoi(valor);
	    }

	    function getDescuento() {
	        return parseInt($("#valor_descuento").html());
	    }

	    function getMenorPrecio() {
	        var vector = getArrayPrecios();
	        var menor = vector[0];
	        for (var i = 0; i < vector.length; i++) {
	            if(vector[i] < menor){
	                menor = vector[i];
	            }
	        }
	        return menor;
	    }

	    function getArrayPrecios() {
	        var vector = [];
	        $("input:checked").each(function(){
				vector.push(parseInt(getPrecio($(this))));
			});
	        return vector;
	    }

	    function calcDescuento() {
	        var descuento = 0;
	        if(getCont() > 3) {
	            var precio_menor = getMenorPrecio();
	            setDescuento(precio_menor);
	        } else {
	            setDescuento(0);
	        }
	        return parseInt(descuento);
	    }

	    function setTotal(total){
	        $("#valor_total").html(total);
	        setTotali(total);
	    }

	    function calcTotal(){
	        var subtotal = getSubtotal();
	        var descuento = getDescuento();
	        var total = subtotal - descuento;
	        return total;
	    }
	    
	    function setTotali(valor) {
			info_total.html(valor);
		}
		
		function setSubTotali(valor) {
			info_subtotal.html(valor);
		}
		
		function setDescuentoi(valor) {
			info_descuento.html(valor);
		}
		
		function getPrecio(checkbox) {
            var inp_precio = checkbox.parent().parent().find("#inp_precio");
            var precio = inp_precio.val();
            return precio;
        }
		
		function getNombre(checkbox){
            var nombre = checkbox.parent().parent().parent().parent().find("var").html();
            return nombre;
        }
		
		function getId(checkbox){
            var id = checkbox.attr("id");
            return id;
        }
		
		var info_total = $("#info_total");
		var info_subtotal = $("#info_subtotal");
		var info_descuento = $("#info_descuento");
		
		function agregarFila(id, nombre, precio, sw){
            var tabla = document.getElementById("tabla_serviciosi");
            var fila = document.createElement("tr");
            var col1 = document.createElement("td");
            var col2 = document.createElement("td");
            var name = document.createTextNode(nombre);
            var val = document.createTextNode(precio);
            if(sw){
                fila.append(col1);
                fila.append(col2);
                col1.append(name);
                col2.append(val);
                tabla.append(fila);
                fila.setAttribute("id", "r"+id);
                col2.setAttribute("class", "text-right");
            } else {
                var fila_buscar = document.getElementById("r"+id)
                tabla.removeChild(fila_buscar);
            }
            
        }
		
	    $("#frm_liquidar .inp_chk_servicio").click(function(e){
	        var precio = parseInt(getPrecio($(this)));
	        var sw = $(this).prop("checked");
	        setSubtotal(precio, sw);
	        calcDescuento();
	        setTotal(calcTotal());
	        agregarFila(getId($(this)), getNombre($(this)), getPrecio($(this)), sw);
	    });
		
	}
	
	function getNumeroDeLiquidaciones() {
		
		$.post("../../crudliquidacion", "getNumeroDeLiquidaciones=true", function(res, est, jqXHR){
			
			console.log(res);
			var json = JSON.parse(res);
			console.log(json);
			$("#label-numeroDeliquidaciones").html(json.numLiquidaciones);
		});
		
	}
	getNumeroDeLiquidaciones();
	
});