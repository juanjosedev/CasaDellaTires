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
	
	google.charts.load('current', {'packages':['bar']});
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(getDataBarChartServiciosPrestados);
    google.charts.setOnLoadCallback(getDataPieChartServiciosPrestados);
    google.charts.setOnLoadCallback(getDataBarChartLiquidacionesRealizadas);
    google.charts.setOnLoadCallback(getDataBarChartGanancias);
//    getDataPieChartServiciosPrestados();
    function getDataPieChartServiciosPrestados() {
    	
		$.post("../../inicio", "getDataPieChartServiciosPrestados=true", function(res, est, jqXHR){
			
			var json = JSON.parse(res);			
			var array = jsonToArraySimple(json, "Pedidos");
			console.log(array);
			graficarServiciosPrestados_pie(array);
			
		});
    	
    }
    
    function graficarServiciosPrestados_pie(array){
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
//    				colors: ['#0d47a1', '#1e88e5', '#90caf9']
    		};
    		
    		var chart = new google.visualization.PieChart(document.getElementById('chart_pie'));
    		chart.draw(data, options);
//    	}
    }

    function getDataBarChartServiciosPrestados(lastDays) {
    	
		if(lastDays == undefined) {
			lastDays = 7;
		}
		
		$.post("../../inicio", "getBarChartServiciosPrestados="+lastDays, function(res, est, jqXHR){
			
			var json = JSON.parse(res);		
			//console.log(json);
			var array = jsonToArray(json);
			graficarServiciosPrestados_barras(array);
			
		});
    	
    }
    
    function graficarServiciosPrestados_barras(array) {
    	
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
//            colors: ['#03A9F4'],
            fontName: 'Roboto',
            //isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chart_div'));
        
        chart.draw(data, google.charts.Bar.convertOptions(options));
        
    }
    
    function getDataBarChartLiquidacionesRealizadas(lastDays) {
    	
		if(lastDays == undefined) {
			lastDays = 7;
		}
		
		$.post("../../inicio", "getBarChartLiquidacionesRealizadas="+lastDays, function(res, est, jqXHR){
//			var res = '{"17 dic":1,"18 dic":2,"19 dic":1,"20 dic":1,"21 dic":2,"22 dic":3,"23 dic":0}';
			var json = JSON.parse(res);		
			console.log(json);
			var array = jsonToArraySimple(json, "Liquidaciones");
			graficarLiquidacionesPrestadas_barras(array);
			
		});
    	
    }
    
    function graficarLiquidacionesPrestadas_barras(array) {
//    	{"16 dic":2,"17 dic":1,"18 dic":2,"19 dic":1,"20 dic":1,"21 dic":2,"22 dic":3}

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
//            colors: ['#03A9F4'],
            fontName: 'Roboto',
            //isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chartLiquidacionesPrestadas'));
        
        chart.draw(data, google.charts.Bar.convertOptions(options));

        
    }
    
    function getDataBarChartGanancias(lastDays) {
    	
		if(lastDays == undefined) {
			lastDays = 7;
		}
		
		$.post("../../inicio", "getBarChartGanancias="+lastDays, function(res, est, jqXHR){
//			var res = '{"17 dic":86000,"18 dic":66000,"19 dic":71000,"20 dic":82000,"21 dic":450000,"22 dic":161000,"23 dic":0}';
			var json = JSON.parse(res);		
			console.log(json);
			var array = jsonToArraySimple(json, "Ganancias");
			graficarGanancias_barras(array);
			
		});
    	
    }
    
    function graficarGanancias_barras(array) {
//    	{"16 dic":2,"17 dic":1,"18 dic":2,"19 dic":1,"20 dic":1,"21 dic":2,"22 dic":3}

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
//            colors: ['#03A9F4'],
            fontName: 'Roboto',
            //isStacked: true
        };
        
        var chart = new google.charts.Bar(document.getElementById('chartGanancias'));
        
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
});