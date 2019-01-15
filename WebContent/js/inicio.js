$(document).ready(function() {
	
	function jsonToArraySimple(data, name) {
		
//		[
//            ['Servicio', 'Pedidos'],
//            ['Balanceo',  1],
//            ['Cambio de aceite',  1],
//            ['Lavado', 2],
//            ['Pintura', 0],
//            ['Tuneado', 0]
//        ]
		
		var array = new Array();
		var header_array = new Array("Dias", name);
		var json = data;
		
		array.push(header_array);
		for(var key in json) {
			
			if (json.hasOwnProperty(key)){
				array.push(new Array(key));
			}
			
		}
		var index = 1;
		for(var key in json) {
			if (json.hasOwnProperty(key)){
				array[index].push(json[key]);
				index += 1;
			}
		}
		//console.log(array);
		return array;
		
	}
	
    function jsonToArray(data){
        var array = new Array();
        var header_array = new Array("D\u00EDas");
        var json = data;

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
    
    function getSumaJson(json) {
    	
    	var suma = 0;
    	for (var key in json) {
    		if(json.hasOwnProperty(key)) {
    			
    			suma += json[key];
//    			console.log(json[key]);
    		}
    	}
    	return suma;
    }
    
    function getColor() {
    	var color = $(".title_maestro").css("background-color");
    	return color;
    }

	google.charts.load('current', {'packages':['bar']});
    google.charts.load('current', {'packages':['corechart']});

    google.charts.setOnLoadCallback(getDataBarChartServiciosPrestados);
    google.charts.setOnLoadCallback(getDataPieChartServiciosPrestados);
    google.charts.setOnLoadCallback(getDataPieChartGananciasPorServicio);
    google.charts.setOnLoadCallback(getDataBarChartLiquidacionesRealizadas);
    google.charts.setOnLoadCallback(getDataBarChartGanancias);
    google.charts.setOnLoadCallback(getDataBarChartGananciasPorServicio);
//    getDataPieChartGananciasPorServicio();
    function getDataPieChartServiciosPrestados() {
    	
		$.post("../../inicio", "getDataPieChartServiciosPrestados=true", function(res, est, jqXHR){
			
			var json = JSON.parse(res);			
//			console.log(array);
			graficarServiciosPrestados_pie(json);
			
		});
    	
    }
    
    function graficarServiciosPrestados_pie(json){
    	
    	var servicios = getSumaJson(json);
    	$("#label_servicios_prestados_hoy").html(servicios);
    	
    	var array = jsonToArraySimple(json, "Pedidos");
//    	if(array[1][1] == 0){
//    		document.getElementById('chart_pie').innerHTML = "No hay datos<br>";
//    	} else {
    		
    		var data = google.visualization.arrayToDataTable(array);
//        {"Balanceo":1,"Cambio de Aceite":1,"Lavado":2,"Pintura":0,"Tuneado":0}
    		var options = {
//    				pieHole: .1,
    				legend: {
    					position: 'bottom',
    					maxLines: 3
    				},
    				colors: ['#F44336', '#9C27B0', '#3F51B5', '#03A9F4', '#4CAF50', '#2196F3', '#CDDC39', '#FFC107', '#795548']
    		};
    		
    		var chart = new google.visualization.PieChart(document.getElementById('chart_pie'));
    		chart.draw(data, options);
//    	}
    }

    function getDataPieChartGananciasPorServicio() {
    	
		$.post("../../inicio", "getDataPieChartGananciasPorServicio=true", function(res, est, jqXHR){
			
//			alert(res);
			var json = JSON.parse(res);			
			graficarPieGananciasPorServicio(json);
			
		});
    	
    }
    
    function graficarPieGananciasPorServicio(json){
    	
    	var gananciasTotalesHoy = getSumaJson(json);
    	$("#label_ganancias_servicio_hoy").html(gananciasTotalesHoy);
    	
    	var array = jsonToArraySimple(json, "Pedidos");
//    	if(array[1][1] == 0){
//    		document.getElementById('chart_pie').innerHTML = "No hay datos<br>";
//    	} else {
    		
    		var data = google.visualization.arrayToDataTable(array);
//        {"Balanceo":1,"Cambio de Aceite":1,"Lavado":2,"Pintura":0,"Tuneado":0}
    		var options = {
//    				pieHole: .1,
    				legend: {
    					position: 'bottom',
    					maxLines: 3
    				},
    				colors: ['#F44336', '#9C27B0', '#3F51B5', '#03A9F4', '#4CAF50', '#2196F3', '#CDDC39', '#FFC107', '#795548']
    		};
    		
    		var chart = new google.visualization.PieChart(document.getElementById('pieChartGananciasPorServicio'));
    		chart.draw(data, options);
//    	}
    }
    
    function getDataBarChartServiciosPrestados(lastDays) {
    	
    	$("#chart_div").css("visibility", "hidden");
    	
		if(lastDays == undefined) {
			lastDays = 7;
		}
		
		$.post("../../inicio", "getBarChartServiciosPrestados="+lastDays, function(res, est, jqXHR){
			
			var json = JSON.parse(res);		
			
			$("#chart_div").parent().find(".carga").fadeOut(1000);									
			setTimeout(function(){
				graficarServiciosPrestados_barras(json);
				$("#chart_div").fadeIn(1000);
				$("#chart_div").css("visibility", "visible");
			}, 1000);
					
		});
    	
    }
    
    function graficarServiciosPrestados_barras(json) {
    	
    	var suma = 0;
    	var subjson = null;
    	
    	for(var key in json) {
            if(json.hasOwnProperty(key)){
            	subjson = json[key];
                suma += getSumaJson(subjson);
            }
        }
    	
    	var array = jsonToArray(json);
    	
//    	for ()
    	
    	$("#label_servicios_prestados").html(suma);
    	
    	if(array == undefined){
            var data = google.visualization.arrayToDataTable(array);
        }else{
            var data = google.visualization.arrayToDataTable(array);
        }
        
        var options = {
            chart: {
            subtitle: 'SERVICIOS PRESTADOS POR D\u00EDAS DE LA SEMANA',
            },
            legend: {
            	position: 'top'
            },
            titleTextStyle: {
            	fontSize: 24
            },
            bars: 'vertical',
            hAxis: {
                format: '#'
                
            },
            colors: ['#F44336', '#9C27B0', '#3F51B5', '#03A9F4', '#4CAF50', '#2196F3', '#CDDC39', '#FFC107', '#795548'],
            fontName: 'Roboto',
            //isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chart_div'));
        
        chart.draw(data, google.charts.Bar.convertOptions(options));
        
    }
    
    function getDataBarChartLiquidacionesRealizadas(lastDays) {
    	
    	$("#chartLiquidacionesPrestadas").css("visibility", "hidden");
    	
		if(lastDays == undefined) {
			lastDays = 7;
		}
		
		$.post("../../inicio", "getBarChartLiquidacionesRealizadas="+lastDays, function(res, est, jqXHR){

			var json = JSON.parse(res);		
			$("#chartLiquidacionesPrestadas").parent().find(".carga").fadeOut(1000);									
			setTimeout(function(){
				graficarLiquidacionesPrestadas_barras(json);
				$("#chartLiquidacionesPrestadas").fadeIn(1000);
				$("#chartLiquidacionesPrestadas").css("visibility", "visible");
			}, 1000);
		});
    	
    }
    
    function graficarLiquidacionesPrestadas_barras(json) {
//    	{"16 dic":2,"17 dic":1,"18 dic":2,"19 dic":1,"20 dic":1,"21 dic":2,"22 dic":3}
    	$("#label_liquidaciones_realizadas").html(getSumaJson(json));
//    	document.getElementById("label_liquidaciones_realizadas").innerHTML = getSumaJson(json);
    	var array = jsonToArraySimple(json, "Liquidaciones");
    	
    	if(array == undefined){
            var data = google.visualization.arrayToDataTable(array);
        }else{
            var data = google.visualization.arrayToDataTable(array);
        }
        
        var options = {
            chart: {
            subtitle: 'LIQUIDACIONES REALIZADAS POR D\u00EDAS DE LA SEMANA',
            },
            legend: {
            	position: 'top'
            },
            titleTextStyle: {
            	fontSize: 24
            },
            bars: 'vertical',
            hAxis: {
                format: '#'
                
            },
            colors: [getColor()],
            fontName: 'Roboto',
            //isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chartLiquidacionesPrestadas'));
        
        chart.draw(data, google.charts.Bar.convertOptions(options));
   
    }
    
    function getDataBarChartGanancias(lastDays) {
    	
    	$("#chartGanancias").css("visibility", "hidden");
    	
		if(lastDays == undefined) {
			lastDays = 7;
		}
		
		$.post("../../inicio", "getBarChartGanancias="+lastDays, function(res, est, jqXHR){
//			var res = '{"17 dic":86000,"18 dic":66000,"19 dic":71000,"20 dic":82000,"21 dic":450000,"22 dic":161000,"23 dic":0}';
			var json = JSON.parse(res);		
//			console.log(json);
			$("#chartGanancias").parent().find(".carga").fadeOut(1000);									
			setTimeout(function(){
				graficarGanancias_barras(json);
				$("#chartGanancias").fadeIn(1000);
				$("#chartGanancias").css("visibility", "visible");
			}, 1000);
			
		});
    	
    }
    
    function graficarGanancias_barras(json) {
//    	{"16 dic":2,"17 dic":1,"18 dic":2,"19 dic":1,"20 dic":1,"21 dic":2,"22 dic":3}
    	$("#label_ganancias").html("$ "+getSumaJson(json));

    	var array = jsonToArraySimple(json, "Ganancias");
    	if(array == undefined){
            var data = google.visualization.arrayToDataTable(array);
        }else{
            var data = google.visualization.arrayToDataTable(array);
        }
        
        var options = {
            chart: {
            subtitle: 'GANANCIAS POR D\u00EDAS DE LA SEMANA',
            },
            legend: {
            	position: 'top'
            },
            titleTextStyle: {
            	fontSize: 24
            },
            bars: 'vertical',
            hAxis: {
                format: '#'
                
            },
            colors: [getColor()],
            fontName: 'Roboto',
            //isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chartGanancias'));
        
        chart.draw(data, google.charts.Bar.convertOptions(options));

        
    }

    function getDataBarChartGananciasPorServicio(lastDays) {
    	
    	$("#chartGananciasServicio").css("visibility", "hidden");
    	
		if(lastDays == undefined) {
			lastDays = 7;
		}
		
		$.post("../../inicio", "getBarChartGananciasPorServicio="+lastDays, function(res, est, jqXHR){
//			var res = '{"18 dic":{"Alineación de luces":0,"Balanceo":66000,"Cambio de Aceite":43000,"Lavado":0,"Pintura":0,"Tuneado":0},"19 dic":{"Alineación de luces":0,"Balanceo":71000,"Cambio de Aceite":71000,"Lavado":71000,"Pintura":0,"Tuneado":0},"20 dic":{"Alineación de luces":0,"Balanceo":82000,"Cambio de Aceite":82000,"Lavado":82000,"Pintura":0,"Tuneado":0},"21 dic":{"Alineación de luces":0,"Balanceo":422000,"Cambio de Aceite":422000,"Lavado":450000,"Pintura":0,"Tuneado":422000},"22 dic":{"Alineación de luces":95000,"Balanceo":161000,"Cambio de Aceite":138000,"Lavado":95000,"Pintura":0,"Tuneado":0},"23 dic":{"Alineación de luces":0,"Balanceo":0,"Cambio de Aceite":0,"Lavado":0,"Pintura":0,"Tuneado":0},"24 dic":{"Alineación de luces":91000,"Balanceo":114000,"Cambio de Aceite":0,"Lavado":91000,"Pintura":0,"Tuneado":0}}';
			var json = JSON.parse(res);		
//			console.log(getSumaJson(json));
			$("#chartGananciasServicio").parent().find(".carga").fadeOut(1000);	
			setTimeout(function(){
				graficarGananciasPorServicio(json);
				$("#chartGananciasServicio").fadeIn(1000);
				$("#chartGananciasServicio").css("visibility", "visible");
			}, 1000);
		});
    	
    }
    
    function graficarGananciasPorServicio(json) {
    	
    	var suma = 0;
    	var subjson = null;
    	
    	for(var key in json) {
            if(json.hasOwnProperty(key)){
            	subjson = json[key];
                suma += getSumaJson(subjson);
//                console.log(subjson);
            }
        }
//    	console.log(suma);
    	var array = jsonToArray(json);
    	
//    	for ()
    	
    	$("#label_ganancias_servicio").html("$ "+suma);
    	
    	if(array == undefined){
            var data = google.visualization.arrayToDataTable(array);
        }else{
            var data = google.visualization.arrayToDataTable(array);
        }
        
        var options = {
            chart: {
            subtitle: 'SERVICIOS PRESTADOS POR D\u00EDAS DE LA SEMANA',
            },
            legend: {
            	position: 'top'
            },
            titleTextStyle: {
            	fontSize: 24
            },
//            bars: 'vertical',
//            hAxis: {
//                format: '#'
//                
//            },
            colors: ['#F44336', '#9C27B0', '#3F51B5', '#03A9F4', '#4CAF50', '#2196F3', '#CDDC39', '#FFC107', '#795548'],
            fontName: 'Roboto',
//            isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chartGananciasServicio'));
        
        chart.draw(data, google.charts.Bar.convertOptions(options));
        
    }
    
    $("#slt_graficoServiciosPrestados").change(function(){
        var ult = $(this).val();
        if(ult == 'ultimos_7'){
        	getDataBarChartServiciosPrestados(7);
        } else if (ult == 'ultimos_15') {
        	getDataBarChartServiciosPrestados(15);
        } else {
        	getDataBarChartServiciosPrestados(30);
        }
    });
    
    $("#slt_graficoLiquidacionesRealizadas").change(function(){
        var ult = $(this).val();
        if(ult == 'ultimos_7'){
        	getDataBarChartLiquidacionesRealizadas(7);
        } else if (ult == 'ultimos_15') {
        	getDataBarChartLiquidacionesRealizadas(15);
        } else {
        	getDataBarChartLiquidacionesRealizadas(30);
        }
    });
    
    $("#slt_graficoGanancias").change(function(){
        var ult = $(this).val();
        if(ult == 'ultimos_7'){
        	getDataBarChartGanancias(7);
        } else if (ult == 'ultimos_15') {
        	getDataBarChartGanancias(15);
        } else {
        	getDataBarChartGanancias(30);
        }
    });
    
    $("#slt_graficoGananciasServicio").change(function(){
        var ult = $(this).val();
        if(ult == 'ultimos_7'){
        	getDataBarChartGananciasPorServicio(7);
        } else if (ult == 'ultimos_15') {
        	getDataBarChartGananciasPorServicio(15);
        } else {
        	getDataBarChartGananciasPorServicio(30);
        }
    });
});