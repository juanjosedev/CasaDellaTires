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
	
	function getDataBarChartServiciosPrestados() {
    	
    	var res = '{"7 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":0,"Pintura":0,"Tuneado":0},"8 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":1,"Tuneado":0},"9 dic":{"Balanceo":0,"Cambio de Aceite":5,"Lavado":0,"Pintura":0,"Tuneado":0},"10 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"11 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":1},"12 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":0,"Pintura":0,"Tuneado":0},"13 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":0,"Pintura":0,"Tuneado":0},"14 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":1,"Pintura":0,"Tuneado":0},"15 dic":{"Balanceo":1,"Cambio de Aceite":4,"Lavado":0,"Pintura":0,"Tuneado":0},"16 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":3,"Pintura":0,"Tuneado":0},"17 dic":{"Balanceo":2,"Cambio de Aceite":2,"Lavado":2,"Pintura":0,"Tuneado":0},"18 dic":{"Balanceo":4,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"19 dic":{"Balanceo":4,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0}}';
        var json = JSON.parse(res);
                
    	return json;
    	
    }
	
    function jsonToArray(){
        var array = new Array();
        var header_array = new Array("D\u00EDas");
        var json = getDataBarChartServiciosPrestados();

        array.push(header_array);

        for(var key in json) {
            if (json.hasOwnProperty(key)){
                array.push(new Array(key));
            }
        }

        var subjson = null;

        for(var key in json) {
            if (json.hasOwnProperty(key)){
                subjson = json[key];
                for(var key2 in subjson){
                    if (subjson.hasOwnProperty(key2)){
                        array[0].push(key2);
                    }
                }
                break;
            }
        }
            
        var index = 1;
        for(var key in json) {
            if(json.hasOwnProperty(key)){
                subjson = json[key];
                for(var key2 in subjson){
                    if(subjson.hasOwnProperty(key2)){
                        array[index].push(subjson[key2]);
                    }
                }
                index += 1;
            }
        }
        
    	return array;
    }
	
	google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(graficarServiciosPrestados_barras);
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(graficarServiciosPrestados_pie);
    
    function graficarServiciosPrestados_pie(){
//        {"7 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":0,"Pintura":0,"Tuneado":0},"8 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"9 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"10 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"11 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"12 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":0,"Pintura":0,"Tuneado":0},"13 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":0,"Pintura":0,"Tuneado":0},"14 dic":{"Balanceo":1,"Cambio de Aceite":1,"Lavado":0,"Pintura":0,"Tuneado":0},"15 dic":{"Balanceo":1,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"16 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"17 dic":{"Balanceo":2,"Cambio de Aceite":2,"Lavado":2,"Pintura":0,"Tuneado":0},"18 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"19 dic":{"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0}}
    	
        var data = google.visualization.arrayToDataTable([
            ['Servicio', 'Pedidos'],
            ['Balanceo',  5],
            ['Cambio de aceite',  4],
            ['Lavado', 8],
        ]);
    
        var options = {
            pieHole: .1,
            legend: {
                position: 'bottom',
                maxLines: 3
            },
            colors: ['#0d47a1', '#1e88e5', '#90caf9']
        };
    
        var chart = new google.visualization.PieChart(document.getElementById('chart_pie'));
        chart.draw(data, options);
    }

    function graficarServiciosPrestados_barras(datos) {
        
        var array = datos;

        if(array == undefined){
            var data = google.visualization.arrayToDataTable(jsonToArray());
        }else{
            var data = google.visualization.arrayToDataTable(array);
        }
        
        var options = {
            chart: {
            subtitle: 'SERVICIOS PRESTADOS POR D\u00EDAS DE LA SEMANA',
            },
            titleTextStyle: {
            fontSize: 24
            },
            bars: 'vertical',
            hAxis: {
                format: '#'
                
            },
//            colors: ['#0d47a1', '#1e88e5', '#90caf9'],
            fontName: 'Roboto',
            isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chart_div'));
        
        chart.draw(data, google.charts.Bar.convertOptions(options));

    }

    $("#slt_grafico").change(function(){
        var ult = $(this).val();
        if(ult == 'ultimos_7'){
            graficarServiciosPrestados_barras(ultimos_7());
        } else if (ult == 'ultimos_15') {
            graficarServiciosPrestados_barras(ultimos_15());
        } else {
            graficarServiciosPrestados_barras(ultimos_30());
        }
    });

    function ultimos_7(){
        var datos = [
            ['D\u00EDas', 'Balanceo', 'Cambio de aceite', 'Lavado'],
            ['10 dic', 9, 18, 0],
            ['11 dic', 3, 12, 4],
            ['12 dic', 2, 11, 3],
            ['13 dic', 3, 11, 3],
            ['14 dic', 5, 16, 5],
            ['15 dic', 7, 18, 6],
            ['16 dic', 11, 19, 11]
        ];
        return datos;
    }

    function ultimos_15(){
        var datos = [
            ['D\u00EDas', 'Balanceo', 'Cambio de aceite', 'Lavado'],
            ['3 dic', 7, 17, 3],
            ['4 dic', 4, 13, 5],
            ['5 dic', 2, 8, 3],
            ['6 dic', 3, 8, 3],
            ['7 dic', 7, 16, 5],
            ['8 dic', 8, 18, 5],
            ['9 dic', 12, 21, 13],
            ['10 dic', 9, 18, 0],
            ['11 dic', 3, 12, 4],
            ['12 dic', 2, 11, 3],
            ['13 dic', 3, 11, 3],
            ['14 dic', 5, 16, 5],
            ['15 dic', 7, 18, 6],
            ['16 dic', 11, 19, 11]
        ];
        return datos;
    }

    function ultimos_30(){
        var datos = [
            ['D\u00EDas', 'Balanceo', 'Cambio de aceite', 'Lavado'],
            ['18 nov', 7, 17, 3],
            ['19 nov', 4, 13, 5],
            ['20 nov', 2, 8, 3],
            ['21 nov', 3, 8, 3],
            ['22 nov', 7, 16, 5],
            ['23 nov', 8, 18, 5],
            ['24 nov', 12, 21, 13],
            ['25 nov', 9, 18, 0],
            ['26 nov', 3, 12, 4],
            ['27 nov', 2, 11, 3],
            ['28 nov', 3, 11, 3],
            ['29 nov', 5, 16, 5],
            ['30 nov', 7, 18, 6],
            ['1 dic', 11, 19, 11],
            ['2 dic', 7, 17, 3],
            ['3 dic', 4, 13, 5],
            ['4 dic', 2, 8, 3],
            ['5 dic', 3, 8, 3],
            ['6 dic', 7, 16, 5],
            ['7 dic', 8, 18, 5],
            ['8 dic', 12, 21, 13],
            ['9 dic', 9, 18, 0],
            ['10 dic', 3, 12, 4],
            ['11 dic', 2, 11, 3],
            ['13 dic', 3, 11, 3],
            ['14 dic', 5, 16, 5],
            ['15 dic', 7, 18, 6],
            ['16 dic', 11, 19, 11]
        ];
        return datos;
    }
	
});