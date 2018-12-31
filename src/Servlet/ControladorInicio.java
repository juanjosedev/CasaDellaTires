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
		 
		 response.setContentType("text/html;charset=UTF-8");
		 
		 modeloLiquidaciones ml = new modeloLiquidaciones();
		 
//		 String pet = request.getParameter("peticion");
		 String res = "";
		 int lastDays = 0;
		 
			 
		 if (request.getParameter("getBarChartServiciosPrestados") != null) {
			 
			 lastDays = Integer.parseInt(request.getParameter("getBarChartServiciosPrestados"));
			 
			 res = ml.getDataBarChartServiciosPrestados(lastDays);
//			 ml.cerrarConexion();
			 res(response, res);
			 
		 } else if (request.getParameter("getDataPieChartServiciosPrestados") != null){
			 res = ml.getDataPieChartServiciosPrestados();
			 res(response, res);
		 } else if (request.getParameter("getBarChartLiquidacionesRealizadas") != null) {
			 lastDays = Integer.parseInt(request.getParameter("getBarChartLiquidacionesRealizadas"));
			 res = ml.getDataBarChartLiquidacionesPrestadas(lastDays);
			 res(response, res);
		 } else if (request.getParameter("getBarChartGanancias") != null) {
			 
			 lastDays = Integer.parseInt(request.getParameter("getBarChartGanancias"));
			 res = ml.getDataBarChartGanancias(lastDays);
			 res(response, res);
			 
		 } else if (request.getParameter("getBarChartGananciasPorServicio") != null) {
			 
			 lastDays = Integer.parseInt(request.getParameter("getBarChartGananciasPorServicio"));
			 res = ml.getDataBarChartGananciasPorServicio(lastDays);
			 res(response, res);
			 
		 } else if (request.getParameter("getDataPieChartGananciasPorServicio") != null) {
			 
			 res = ml.getDataPieChartGananciasPorServicio();
			 res(response, res);
			 
		 }
//		 getBarChartGananciasPorServicio 
		 ml.cerrarConexion();
		 
		 
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
