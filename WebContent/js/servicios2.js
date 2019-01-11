$(document).ready(function(){
	
	var alert_servicio = $('#alt_servicio');
	alert_servicio.hide();
	var alert_crear_error = $('#alt_crear_error');
	alert_crear_error.hide();
	var alert_crear_exito = $('#alt_crear_exito');
	alert_crear_exito.hide();
	
	function crearServicio(jsonstr) {
		var data = "json="+jsonstr;
		$.post("../../crudservicios", data, function(res, est, jqXHR){
			if(res){
				showAlert(alert_crear_exito, 'Se ha registrado un nuevo servicio con éxito');
			} else {
				showAlert(alert_crear_error, 'Oh no... parece que tenemos problemas');
			}
			
			var formulario = $('#frm_nuevo_servicio');
			limpiarForm(formulario);
			
		});
	}
	
	function addPrecio(jsonstr) {
		var data = "json_add_precio="+jsonstr;
		$.post("../../crudservicios", data, function(res, est, jqXHR){
			
			var ans = res.includes('true');
			if(ans){
				// 1. añadir el alert en la ventana modal activa de que hubo éxito en la operación
				showAlert(alert_crear_exito, 'Se ha agregado nuevos vehículos para este servicio');
				// 2. actualizar la tabla precios
				var peticion_servicio = requestGetParameter('servicio');
				$('#container_tabla_precios').load('tablaPrecios.jsp?servicio='+peticion_servicio);
				// 3. actualizar la tabla añadir precios
				$('#container_add_nuevo_precio').load('tablaNuevosPrecios.jsp?servicio='+peticion_servicio, function(){
					init();					
				});
			} else {
				showAlert(alert_crear_error, 'Oh no... parece que tenemos problemas al añadir un nuevo precio');
			}
		});
//		console.log(jsonstr);
	}
	
	function editarPrecio(id, precio, fila) {
		
		var data = "editar_precio=true&id="+id+"&precio="+precio;
		$.post('../../crudservicios', data, function(res, est, jqXHR){
			
			var ans = res.includes('true');
			if (ans) {
				fila.find('.var_precio').text(precio);
			} else {
				console.log('houston!!!!!!');
			}
			
		});
		
	}
	
	function eliminarPrecio(id, fila) {
		
		var data = 'eliminar_detalle='+id;
		$.post('../../crudservicios', data, function(res, est, jqXHR){
			var ans = res.includes("true");
			if(ans) {
				var peticion_servicio = requestGetParameter('servicio');
				$('#container_add_nuevo_precio').load('tablaNuevosPrecios.jsp?servicio='+peticion_servicio, function(){					
					initFrmTablaNuevosPrecios()
				});
//				console.log('Eliminado');
				setTimeout(function(){
					closeActiveModal();
					setTimeout(function(){
						removerFila(fila);						
					}, 300);
				}, 300);
			} else {
				console.log('Houston');
			}
		});
		
	}
	
	function getJson(){
		var json = {};
		var val = $('#inp_servicio').val();
		
		json['servicio_nombre'] = val;
		json['servicios'] = {};
		
		$('input:checked').each(function(){
			var id_servicio = $(this).val();
			var precio = $(this).parent().parent().find('.inp_precio').val();
	
			json['servicios'][id_servicio] = precio;
			
		});
		
		json = JSON.stringify(json);
		
		return json;
	}

	function getJsonAddPrecio(){
		var json = {};
		var id = requestGetParameter('servicio');
//		alert(id);
		json['id_servicio'] = id;
		json['servicios'] = {};
		
		$('input:checked').each(function(){
			var id_servicio = $(this).val();
			var precio = $(this).parent().parent().find('.inp_precio').val();
	
			json['servicios'][id_servicio] = precio;
			
		});
		
		json = JSON.stringify(json);
		
		return json;
	}
	
	$('#sbt_crear_servicio').click(function(e){
		e.preventDefault();
		var val = $('#inp_servicio').val();
		if (!inpVacio(val)){
			if (!inpVacioSoloEspacios(val)) {
				if (inpAlfaNumerico(val)) {
					if (validarCheckboxSeleccionado()){
						if (!inpVacioCheckbox()){
							if (!inpAlphaCheckbox()){
								var jsonstr = getJson();
								crearServicio(jsonstr);
//								console.log(jsonstr);
							} else {
								showAlert(alert_crear_error, 'Precio no válido');
							}
						} else {
							showAlert(alert_crear_error, 'Ingresa un precio en los checkbox seleccionados');
						}
					} else {
						showAlert(alert_crear_error, 'Tienes que escoger un servicio');
					}
				} else {
					showAlert(alert_crear_error, 'Error en el campo nombre');
					showAlert(alert_servicio, 'Unicamente letras y/o números');
				}
			} else {
				showAlert(alert_crear_error, 'Faltan campos por llenar');
				showAlert(alert_servicio, 'El nombre debe contener caracteres');
			}
		} else {
			showAlert(alert_crear_error, 'Faltan campos por llenar');
			showAlert(alert_servicio, 'El campo nombre es obligatorio');
		}
	});
	
	$('#sbt_add_precio').click(function(e){
		e.preventDefault();
		if (validarCheckboxSeleccionado()){
			if (!inpVacioCheckbox()){
				if (!inpAlphaCheckbox()){
					var jsonstr = getJsonAddPrecio();
					console.log(jsonstr);
//					alert('all\'s okay');
					addPrecio(jsonstr);
				} else {
					showAlert(alert_crear_error, 'Precio no válido');
				}
			} else {
				showAlert(alert_crear_error, 'Ingresa un precio en los checkbox seleccionados');
			}
		} else {
			showAlert(alert_crear_error, 'Tienes que escoger un servicio');
		}
	});
	
	function initFrmTablaNuevosPrecios(){
		$(".inp_precio").each(function() {
			$(this).keypress(function(){
				var checkbox = $(this).parent().find(".inp_chk_servicio");
				if (!checkbox.prop('checked')) {
					alert('Activa el checkbox');
				}
			});
		});
		$(".inp_chk_servicio").each(function() {
			$(this).click(function(){
				var input = $(this).parent().parent().find('.inp_precio');
				if ($(this).prop('checked')) {
					input.prop('readonly', false);
					input.focus();
					input.val('');
				} else {
					input.prop('readonly', true);
					input.val('$');
				}
			});
		});
	}
	
	function init(){
		$('.inp_update_precio').click(function(e){
			
			e.preventDefault();
			var fila = $(this).closest('tr');
			var id = fila.find('.var_id').text();
			var precio = fila.find('#inp_precio_update').val();
			
			editarPrecio(id, precio, fila);
			
		});
		
		$('.inp_eliminar').click(function(){
			
			var fila = $(this).closest('tr');
			var id = fila.find('.var_id').text();
			
			eliminarPrecio(id, fila);
			
		});
		
		$(".inp_precio").each(function() {
			$(this).keypress(function(){
				var checkbox = $(this).parent().find(".inp_chk_servicio");
				if (!checkbox.prop('checked')) {
					alert('Activa el checkbox');
				}
			});
		});
		$(".inp_chk_servicio").each(function() {
			$(this).click(function(){
				var input = $(this).parent().parent().find('.inp_precio');
				if ($(this).prop('checked')) {
					input.prop('readonly', false);
					input.focus();
					input.val('');
				} else {
					input.prop('readonly', true);
					input.val('$');
				}
			});
		});
		
	}
	
	init();
	
});