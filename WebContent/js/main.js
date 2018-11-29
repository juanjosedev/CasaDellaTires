$(document).ready(function(){
	$("#close_log").click(function(e){
		e.preventDefault();
		var data = "out_log=true";
		$.post("../../usuarios_s", data, function(res, est, jqXHR){
			if(res == "true"){
				window.location = "../../index.jsp";
			}else{
				alert("We have a letionation mother fucker");
			}
		})
	});
});