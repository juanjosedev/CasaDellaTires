package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import include.*;
import modelo.ModeloServicios2;
import modelo.modeloServicios;
import modelo.modeloTiposVehiculos;

/**
 * Servlet implementation class servletServicios
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/crudservicios" })
public class servletServicios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void processRequest(HttpServletRequest request,  HttpServletResponse response)
			throws ServletException, IOException {
		ModeloServicios2 ms2 = new ModeloServicios2();
		
		if (request.getParameter("tipo") != null) {
			
			long id_tipo_vehiculo = Long.parseLong(request.getParameter("tipo"));
			String nombre = request.getParameter("nombre");
			int precio = Integer.parseInt(request.getParameter("precio"));
			
			modeloServicios ms = new modeloServicios();
			Servicio s = new Servicio(id_tipo_vehiculo, nombre, precio);
			
			boolean sw = ms.agregarServicio(s);
			
			ms.cerrarConexion();
			
			response.getWriter().println(sw);
			
		} else if (request.getParameter("json") != null) {
//			{"servicio_nombre":"llamarlo","servicios":{"1":"234324","5":"13443"}}
//			{"1":"234324","5":"13443"}

			String json = request.getParameter("json");
			Gson gson = new Gson();
			JsonObject json_servicio = gson.fromJson(json, JsonObject.class);
			JsonObject json_precios = gson.fromJson(json_servicio.get("servicios"), JsonObject.class);
			
			ArrayList<DetalleServicio> lista_detalle = new ArrayList<>();
			
			modeloTiposVehiculos mtv = new modeloTiposVehiculos();
			
			try {
				
				Map<String, Integer> attributes = new HashMap<String, Integer>();
				Set<Entry<String, JsonElement>> entrySet = json_precios.entrySet();
				
				for(Map.Entry<String,JsonElement> entry : entrySet){
					attributes.put(entry.getKey(),json_precios.get(entry.getKey()).getAsInt());
		        }
				
				for(Map.Entry<String,Integer> att : attributes.entrySet()){
		            
					long id_tipo_vehiculo = Long.parseLong(att.getKey());
					int precio = att.getValue();
					
					tipoVehiculo tv = mtv.getTipoVehiculo(id_tipo_vehiculo);
					
					DetalleServicio dll_servicio = new DetalleServicio(tv, precio);
					lista_detalle.add(dll_servicio);
	            } 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			Servicio2 nuevo_servicio = new Servicio2(json_servicio.get("servicio_nombre").getAsString(), lista_detalle);
			
//			System.out.println(nuevo_servicio);
			
			boolean sw = ms2.agregarNuevoServicio(nuevo_servicio);
//			System.out.println(sw);
			mtv.cerrarConexion();
			
			response.getWriter().println(sw);
		} else if (request.getParameter("eliminar_detalle") != null) {
			long id_detalle = Long.parseLong(request.getParameter("eliminar_detalle"));
			boolean flag = ms2.eliminarDetalle(id_detalle);
			response.getWriter().println(flag);
		} else if (request.getParameter("editar_precio") != null) {
			long id_detalle = Long.parseLong(request.getParameter("id"));
			int precio = Integer.parseInt(request.getParameter("precio"));
			boolean flag = ms2.editarPrecio(id_detalle, precio);
			response.getWriter().println(flag);			
		} else if (request.getParameter("json_add_precio") != null) {
			boolean sw = false;
//			{"id_servicio":"14","servicios":{"14":"78"}}
			String json = request.getParameter("json_add_precio");
			Gson gson = new Gson();
			JsonObject json_servicio = gson.fromJson(json, JsonObject.class);
			JsonObject json_precios_add = gson.fromJson(json_servicio.get("servicios"), JsonObject.class);
			
			ArrayList<DetalleServicio> lista_detalle = new ArrayList<>();
			modeloTiposVehiculos mtv = new modeloTiposVehiculos();
			
			try {
				
				Map<String, Integer> attributes = new HashMap<String, Integer>();
				Set<Entry<String, JsonElement>> entrySet = json_precios_add.entrySet();
				
				for(Map.Entry<String,JsonElement> entry : entrySet){
					attributes.put(entry.getKey(),json_precios_add.get(entry.getKey()).getAsInt());
		        }
				
				for(Map.Entry<String,Integer> att : attributes.entrySet()){
		            
					long id_tipo_vehiculo = Long.parseLong(att.getKey());
					int precio = att.getValue();
					
					tipoVehiculo tv = mtv.getTipoVehiculo(id_tipo_vehiculo);
					
					DetalleServicio dll_servicio = new DetalleServicio(tv, precio);
					lista_detalle.add(dll_servicio);
	            } 
//			Servicio2 nuevo_servicio = new Servicio2(json_servicio.get("id_servicio").getAsLong(), lista_detalle);
				long id_servicio = json_servicio.get("id_servicio").getAsLong();
//			System.out.println(nuevo_servicio);
				
				for(DetalleServicio dll: lista_detalle) {
					sw = ms2.agregarDetalle(dll, id_servicio);					
				}
				
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println(sw);
			mtv.cerrarConexion();
			
			response.getWriter().println(sw);		
		}
		
		ms2.cerrarConexion();
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
