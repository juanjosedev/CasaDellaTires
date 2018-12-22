package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.modeloLiquidaciones;

/**
 * Servlet implementation class ControladorInicio
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/inicio" })
public class ControladorInicio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
				throws ServletException, IOException {
		 
		 modeloLiquidaciones ml = new modeloLiquidaciones();
		 
		 String pet = request.getParameter("peticion");
		 String res = "";
		 
		 if (pet != null) {
			 
			 if (pet.equals("getBarChartServiciosPrestados")) {
				 
				 res = ml.getDataBarChartServiciosPrestados(7);
				 res(response, res);
				 
			 } else if (pet.equals("getDataPieChartServiciosPrestados")){
				 res = ml.getDataPieChartServiciosPrestados();
				 res(response, res);
			 }
			 
		 }
		 
	 }
	
	protected void res(HttpServletResponse response, String msg) throws IOException{
		response.getWriter().print(msg);
	}
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
