$(document).ready(function() {
	function getDataBarChartServiciosPrestados() {
    	
		$.post("../../inicio", "peticion=getBarChartServiciosPrestados", function(res, est, jqXHR){
			
			var json = JSON.parse(res);			
			var array = jsonToArray(json);
			graficarServiciosPrestados_barras(array);
			
		});
    	
    }
	
	function getDataPieChartServiciosPrestados() {
    	
		$.post("../../inicio", "peticion=getDataPieChartServiciosPrestados", function(res, est, jqXHR){
			
			var json = JSON.parse(res);			
			var array = jsonToArrayPie(json);
			console.log(array);
			graficarServiciosPrestados_pie(array);
			
		});
    	
    }
	
	function jsonToArrayPie(data) {
		
//		[
//            ['Servicio', 'Pedidos'],
//            ['Balanceo',  1],
//            ['Cambio de aceite',  1],
//            ['Lavado', 2],
//            ['Pintura', 0],
//            ['Tuneado', 0]
//        ]
		
		var array = new Array();
		var header_array = new Array("Servicios", "Pedidos");
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
    google.charts.setOnLoadCallback(getDataBarChartServiciosPrestados);
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(getDataPieChartServiciosPrestados);
//    getDataPieChartServiciosPrestados();
    
    function graficarServiciosPrestados_pie(array){
    	
        var data = google.visualization.arrayToDataTable(array);
//        {"Balanceo":1,"Cambio de Aceite":1,"Lavado":2,"Pintura":0,"Tuneado":0}
        var options = {
            //pieHole: .1,
            legend: {
                position: 'bottom',
                maxLines: 3
            },
            //colors: ['#0d47a1', '#1e88e5', '#90caf9']
        };
    
        var chart = new google.visualization.PieChart(document.getElementById('chart_pie'));
        chart.draw(data, options);
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
            titleTextStyle: {
            fontSize: 24
            },
            bars: 'vertical',
            hAxis: {
                format: '#'
                
            },
//            colors: ['#0d47a1', '#1e88e5', '#90caf9'],
            fontName: 'Roboto',
            //isStacked: true
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