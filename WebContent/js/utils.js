function inpVacio (valor) {
	return valor == "";
}
function contarCaracter(val, caracter){
	return (val.match(new RegExp(caracter, "g")) || []).length;
}
function valNumerico (valor) {
	return !isNaN(valor);
}
function inpAlfaNumerico(val){
	return /^[ a-z0-9\u00E1\u00E9\u00ED\u00F3\u00FA\u00FC\u00F1]*$/i.test(val);
}
function inpVacioSoloEspacios(val){
	var largo = val.length;
	var espacios = contarCaracter(val, " ");
	if(largo == 0 || espacios == 0){
		return false;
	}
	return largo == espacios
}
function getContCheckboxChecked() {
    var n = $("input:checked").length;
    return n;
}
function validarCheckboxSeleccionado(){
	return getContCheckboxChecked() > 0;
}
function inpVacioCheckbox(){
	var flag = false;
	$('input:checked').each(function(){
		var inp = $(this).parent().parent().find('.inp_precio');
		if(inpVacio(inp.val())){
			flag = true;
		} else if (inpVacioSoloEspacios(inp.val())) {
			flag = true;
		}
	});
	return flag;
}
function inpNumericoCheckbox(){
	var flag = false;
	$('input:checked').each(function(){
		var inp = $(this).parent().parent().find('.inp_precio');
		if(valNumerico(inp.val())){
			flag = true;
		}
	});
	return flag;
}
function inpAlphaCheckbox(){
	var flag = false;
	$('input:checked').each(function(){
		var inp = $(this).parent().parent().find('.inp_precio');
		if(!valNumerico(inp.val())){
			flag = true;
		}
	});
	return flag;
}
function showAlert(alert, mensaje){
	alert.find("var").text(mensaje);
	alert.show().delay(3000).fadeOut(500);;
}
function limpiarForm(formulario){
	formulario[0].reset();
}function closeActiveModal() {
	$(".modal").modal("hide");
}
function removerFila(fila) {
	fila.remove();
}
function requestGetParameter(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
    results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}
function getColor() {
	var color = $(".title_maestro").css("background-color");
	return color;
}

/////////////////////

