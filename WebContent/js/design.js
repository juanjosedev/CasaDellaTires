function tableDesign(){
	$('.table tbody tr td a span').css('color', getColor());
}
function descriptionDesign(){
	$('.media span').css('color', getColor());
}
function botonDesign(){
	$('.boton-vacio').css('color', getColor());
	$('.boton-vacio').hover(function(){
		$(this).css('background-color', getColor());
		$(this).css('color', '#fff');
	});
	$('.boton-vacio').mouseout(function(){
		$(this).css('background-color', 'transparent');
		$(this).css('color', getColor());
	});
}
$(document).ready(function(){
	tableDesign();
	descriptionDesign();
	botonDesign()
});