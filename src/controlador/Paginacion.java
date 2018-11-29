package controlador;

public class Paginacion {
	
	public static String getPaginacion(String nombre, int numero) {
		String htmlcode = "";
		
		int i = 1;
		
		int resto = numero % 10; // 2 % 10
		numero = numero / 10; // 20 / 10 = 2
		
		
		htmlcode = "<center>\r\n" + 
				"	<nav>\r\n" + 
				"		<ul class=\"pagination\">\r\n" + 
				"			<li><a href=\"\">&laquo;</a></li>";
		
		String li = "";
		
		if(resto != 0) {
			numero += 1;
		}
		
		while (i <= numero) {
			li = li + "<li class=\"page_p\"><a href=\'"+nombre+"?page="+i+"'>"+i+"</a></li>";
			i += 1;
		}
		
		htmlcode = htmlcode + li +  "<li><a href=\"\" class=\"\">&raquo;</a></li></ul>\r\n" + 
				"	</nav>\r\n" + 
				"</center>";
		
		return htmlcode;
	}
	
	public static void main(String[] args) {
		System.out.println(getPaginacion("Liquidaciones.jsp", 36));
	}
	
}
