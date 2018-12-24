 package modelo;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import include.*;
  
public class modeloLiquidaciones extends Conexion {
	
	public boolean agregarNuevaLiquidacion(Liquidacion lqd) {
		
		boolean sw = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql_liquidaciones = "INSERT INTO liquidaciones (consecutivo, cc, placa, hora_inicio, subtotal, descuento, total) VALUES (?, ?, ?, NOW(), ?, ?, ?)";
			//String sql_detalle = "INSERT INTO detalle (consecutivo_liquidaciones, id_servicios, id_tipo_vehiculo_servicios, precio) VALUES (?, ?, ?, ?)";
			
			objSta = getConnection().prepareStatement(sql_liquidaciones);
			objSta.setLong(1, lqd.getConsecutivo());
			objSta.setLong(2, lqd.getCliente().getCedula());
			objSta.setString(3, lqd.getVehiculo().getPlaca());
			objSta.setInt(4, lqd.getSubtotal());
			objSta.setInt(5, lqd.getDescuento());
			objSta.setInt(6, lqd.getTotal());
			
			sw = objSta.executeUpdate() == 1 ? true : false;
			
			ArrayList<Detalle> lista = lqd.getLista_detalles();
			
			for(Detalle dll : lista) {
				
				agregarDetalle(dll);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sw;
		
	}
	
	public int getSiguienteConsecutivo() {

		int ultimo = 0;
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql= "SELECT consecutivo AS ultimo FROM liquidaciones ORDER BY consecutivo DESC LIMIT 1;";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				ultimo = tabla.getInt("ultimo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ultimo + 1;
	}
	
	public ArrayList<Detalle> getDetalles(long consecutivo) {
		
		ArrayList<Detalle> lista_detalles = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT * FROM detalle WHERE consecutivo_liquidaciones = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, consecutivo);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				Detalle dll = new Detalle(tabla.getLong("consecutivo_liquidaciones"), tabla.getString("nombre"), tabla.getInt("precio"));
				lista_detalles.add(dll);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista_detalles;
		
	}
	
	public ArrayList<Liquidacion> getAllLiquidaciones(int page) {
		
		ArrayList<Liquidacion> lista = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		modeloClientes mc = new modeloClientes();
		modeloVehiculos mv = new modeloVehiculos();
		
		try {
			if(page == -1) {
				String sql = "SELECT * FROM liquidaciones ORDER BY hora_inicio ASC";
				objSta = getConnection().prepareStatement(sql);
			}else {
				String sql = "SELECT * FROM liquidaciones ORDER BY hora_inicio ASC LIMIT ?, ?";
				objSta = getConnection().prepareStatement(sql);
				objSta.setInt(1, (page*10) - 10);
				objSta.setInt(2, 10);
			}
			
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				long consecutivo = tabla.getLong("consecutivo");
				Cliente cliente = mc.getCliente(tabla.getLong("cc"));
				Vehiculo vehiculo = mv.getVehiculo(tabla.getString("placa"));
				ArrayList<Detalle> lista_detalles = getDetalles(consecutivo);
				Calendar entrada = toCalendar(tabla.getDate("hora_inicio"));
				Calendar salida = toCalendar(tabla.getDate("hora_final"));
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");
				
				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida, subtotal, descuento, total);
				
				lista.add(l);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
					mc.cerrarConexion();
					mv.cerrarConexion();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;
	}
	
	public ArrayList<Liquidacion> getLiquidacionesPendientes() {
		
		ArrayList<Liquidacion> lista = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		modeloClientes mc = new modeloClientes();
		modeloVehiculos mv = new modeloVehiculos();
		
		try {
			
			String sql = "SELECT * FROM liquidaciones WHERE hora_final IS NULL ORDER BY hora_inicio";
			objSta = getConnection().prepareStatement(sql);
			
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				long consecutivo = tabla.getLong("consecutivo");
				Cliente cliente = mc.getCliente(tabla.getLong("cc"));
				Vehiculo vehiculo = mv.getVehiculo(tabla.getString("placa"));
				ArrayList<Detalle> lista_detalles = getDetalles(consecutivo);
				String entrada_str = tabla.getString("hora_inicio");
				Calendar entrada = dateTimeSQLToCalendar(entrada_str);
				Calendar salida = null;
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");
				
				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida, subtotal, descuento, total);
				
				lista.add(l);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
					mc.cerrarConexion();
					mv.cerrarConexion();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;
		
	}
	
	public ArrayList<Liquidacion> getLiquidacionesCompletas(int page) {
		
		ArrayList<Liquidacion> lista = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		modeloClientes mc = new modeloClientes();
		modeloVehiculos mv = new modeloVehiculos();
		
		try {
			
			String sql = "SELECT * FROM liquidaciones WHERE hora_final IS NOT NULL ORDER BY hora_inicio DESC LIMIT ?, ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setInt(1, (page*10) - 10);
			objSta.setInt(2, 10);
			
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				long consecutivo = tabla.getLong("consecutivo");
				Cliente cliente = mc.getCliente(tabla.getLong("cc"));
				Vehiculo vehiculo = mv.getVehiculo(tabla.getString("placa"));
				ArrayList<Detalle> lista_detalles = getDetalles(consecutivo);
				
				
				String entrada_str = tabla.getString("hora_inicio");
				String salida_str = tabla.getString("hora_final");
				
				Calendar entrada = dateTimeSQLToCalendar(entrada_str);
				Calendar salida = dateTimeSQLToCalendar(salida_str);
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");
				
				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida, subtotal, descuento, total);
				
				lista.add(l);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
					mc.cerrarConexion();
					mv.cerrarConexion();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;
		
	}
	
	public boolean agregarDetalle(Detalle dll) {
		
		boolean sw = false;
		PreparedStatement objSta = null;
		//modeloServicios ms = new modeloServicios();
		//modeloTiposVehiculos mtv = new modeloTiposVehiculos();
		
		try {
			String sql_detalle = "INSERT INTO detalle (consecutivo_liquidaciones, nombre, precio) VALUES (?, ?, ?)";
			objSta = getConnection().prepareStatement(sql_detalle);
			objSta.setLong(1, dll.getConsecutivo_liquidacion());
			objSta.setString(2, dll.getNombre());
			objSta.setInt(3, dll.getPrecio());
			sw = objSta.executeUpdate() == 1 ? true : false;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					//ms.cerrarConexion();
					//mtv.cerrarConexion();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sw;
	}
	
	public int getSubTotal(ArrayList<Detalle> lista_detalles) {
		int subtotal = 0;
		
		for(Detalle d : lista_detalles) {
			subtotal += d.getPrecio();
		}
		
		return subtotal;
	}

	public int getDescuento(ArrayList<Detalle> lista_detalles) {
		
		int descuento = 0;
		
		if (lista_detalles.size() >= 4) {
			descuento = lista_detalles.get(0).getPrecio();
			for(Detalle d : lista_detalles) {
				if (d.getPrecio() < descuento) {
					descuento = d.getPrecio();
				}
			}
		}
		
		return descuento;
	}
	
	public int getTotal(ArrayList<Detalle> lista_detalles) {
		return getSubTotal(lista_detalles) - getDescuento(lista_detalles);
	}
	
	public int getContarLiquidacionesCompletas() {
		int i = 0;
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		try {
			String sql = "SELECT COUNT(*) as cant_liquidaciones FROM liquidaciones WHERE hora_final IS NOT NULL";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				i = tabla.getInt("cant_liquidaciones");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// SELECT tipos_vehiculos.id, tipos_vehiculos.nombre, servicios.nombre, servicios.precio FROM servicios INNER JOIN tipos_vehiculos ON servicios.id_tipo_vehiculo = tipos_vehiculos.id WHERE tipos_vehiculos.id = ?;
	
	public boolean terminarLiquidacion(long consecutivo) {
		
		boolean sw = false;
		
		PreparedStatement objSta = null;
		
		try {
			
			String sql= "UPDATE liquidaciones SET hora_final = NOW() WHERE consecutivo = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, consecutivo);
			sw = objSta.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sw;
		
	}
	  /////////////////////////////////////////
	 ///GRAFICAS//////////////////////////////
	/////////////////////////////////////////
	public String getDataBarChartServiciosPrestados(int lastDays) {
		
		JsonObject json_data = getServiciosPrestados(lastDays);
		Gson gson = new Gson();
		
		String data = gson.toJson(json_data);
		
		return data;
	}
	
	private JsonObject getServiciosPrestados(int lastDays) {
		
		// 1. Fecha límite
		Calendar date_start = getDateStart(lastDays);
		//2 JsonObject que vamos a retornar
//		{
//			"17 jul": {
//				"Balanceo": 3,
//				"Polichado": 1,
//				"Lavado": 0
//			},
//			"18 jul": {
//				"Balanceo": 0,
//				"Polichado": 2,
//				"Lavado": 3
//			},
//			"19 jul": {
//				"Balanceo": 1,
//				"Polichado": 2,
//				"Lavado": 2
//			}
//		}
		JsonObject json_services = getJsonServicesInstance();
		JsonObject json_fechas = getJsonDatesInstance(lastDays, json_services);
		// Llamo mi tabla con los datos resultset
		ResultSet tabla = null;
		PreparedStatement objSta = null;
	
		Calendar fecha = null;
		String nombre_servicio = "";
		String fecha_key = "";
		JsonObject json_fecha = null;
		int val_service_by_date = 0;
		
		try {
			tabla = getTablaServiceName_Date(date_start);
			
			while(tabla.next()) {
				
				fecha = dateTimeSQLToCalendar(tabla.getString("fecha"));
				nombre_servicio = tabla.getString("nombre");
				
				fecha_key = getKeyDate(fecha);
				json_fecha = (JsonObject) json_fechas.get(fecha_key);
				
				val_service_by_date = json_fecha.get(nombre_servicio).getAsInt();
				
				json_fecha.addProperty(nombre_servicio, val_service_by_date+1);
//				System.out.println(nombre_servicio+" - "+n + " - "+fecha_key);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("fecha: "+fecha.getTime());
		} finally {
			// uunicamente cerrar el statement
			try {
				if (objSta != null) {
					objSta.close();
				}
				if (tabla != null) {
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return json_fechas;
	}
	private ResultSet getTablaServiceName_Date(Calendar startDate) {
		
		ResultSet tabla = null;
		PreparedStatement objSta = null;
		String fecha_limite = dateTimeCalendarToSQL(startDate);
		String fecha_actual = dateTimeCalendarToSQL(Calendar.getInstance());
//		System.out.println("---------->"+fecha_actual);
		fecha_limite = fecha_limite.substring(0, 10);
		fecha_actual = fecha_actual.substring(0, 10);
		
		try {
			
			String sql = "SELECT nombre, hora_inicio AS fecha FROM liquidaciones, detalle "
					+ "WHERE liquidaciones.consecutivo = detalle.consecutivo_liquidaciones AND hora_inicio > ? AND hora_inicio < ?";
//			 AND hora_final IS NOT NULL
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, fecha_limite);
			objSta.setString(2, fecha_actual);
			tabla = objSta.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// uunicamente cerrar el statement
			try {
				if (tabla != null) {
//					objSta.close();
//					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		
		return tabla;
		
	}
	
	public String getDataPieChartServiciosPrestados() {
		
		Gson gson = new Gson();
		JsonObject json_data = getServiciosPrestados();
		
		String data = gson.toJson(json_data);
		
		return data;
	}
	
	private JsonObject getServiciosPrestados() {
		// 1. Fecha límite
//		Calendar actualDate = Calendar.getInstance();
		//2 JsonObject que vamos a retornar
//				{
//					"Balanceo": 3,
//					"Polichado": 1,
//					"Lavado": 0
//				}
		JsonObject jsonServices = getJsonServicesInstance();
		// Llamo mi tabla con los datos resultset
		ResultSet tabla = null;
		try {
			tabla = getTablaServiceName_Count();
			
			while(tabla.next()) {
				
				String nombre_servicio = tabla.getString("nombre");
				int cant_prestaciones = tabla.getInt("cantidad");
				
//				String fecha_key = getKeyDate(fecha);
//				JsonObject json_fecha = (JsonObject) json_fechas.get(fecha_key);
				
//				int val_service = jsonServices.get(nombre_servicio).getAsInt();
				jsonServices.addProperty(nombre_servicio, cant_prestaciones);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// uunicamente cerrar el statement
			try {
				if (tabla != null) {
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return jsonServices;
	}
	
	private ResultSet getTablaServiceName_Count() {
		
		Calendar actualDate = Calendar.getInstance();
		ResultSet tabla = null;
		PreparedStatement objSta = null;
		String actualDateSQL = dateTimeCalendarToSQL(actualDate);
		actualDateSQL = actualDateSQL.substring(0, 10);
		
		try {
			
			String sql = "SELECT nombre, COUNT(*) AS cantidad FROM liquidaciones, detalle WHERE consecutivo = consecutivo_liquidaciones AND hora_inicio > ? GROUP BY nombre;";
//			 AND hora_final IS NOT NULL
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, actualDateSQL);
			tabla = objSta.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// uunicamente cerrar el statement
			try {
				if (tabla != null) {
//					objSta.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return tabla;
	}
	
	public String getDataBarChartLiquidacionesPrestadas(int lastDays) {
		Gson gson = new Gson();
		JsonObject json_data = getLiquidacionPrestadas(lastDays);
		String json = gson.toJson(json_data);
		return json;
	}
	
	private JsonObject getLiquidacionPrestadas(int lastDays) {
		
		Calendar dateStart = getDateStart(lastDays);
		
		JsonObject jsonLiquidaciones = getJsonDatesInstance(lastDays, 0);
		ResultSet tabla = null;
		
		try {
			
			tabla = getTablaLiquidacionesByDate(dateStart);
			
			while (tabla.next()) {
				
				Calendar fecha = dateTimeSQLToCalendar(tabla.getString("fecha"));
				int liquidacionesPrestadas = tabla.getInt("liquidaciones");
				
				String keyDate = getKeyDate(fecha);
				
				jsonLiquidaciones.addProperty(keyDate, liquidacionesPrestadas);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return jsonLiquidaciones;
		
	}
	
	private ResultSet getTablaLiquidacionesByDate(Calendar dateStart) {
		
		ResultSet tabla = null;
		PreparedStatement objSta = null;
		
		Calendar dateLimite = Calendar.getInstance();
		
		dateLimite.set(Calendar.HOUR_OF_DAY, 0);
		dateLimite.set(Calendar.SECOND, 0);
		
		String fechaInicio = dateTimeCalendarToSQL(dateStart);
		String fechaLimite = dateTimeCalendarToSQL(dateLimite);
		
		try {
		
			String sql = "SELECT DATE_FORMAT(hora_inicio, '%Y-%m-%d 00:00:00') AS fecha, COUNT(*) AS liquidaciones FROM liquidaciones WHERE hora_inicio > ? AND hora_inicio < ? GROUP BY fecha;";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, fechaInicio);
			objSta.setString(2, fechaLimite);
			tabla = objSta.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return tabla;
		
	}
	
	public String getDataBarChartGanancias(int lastDays) {
		Gson gson = new Gson();
		JsonObject json_data = getGanancias(lastDays);
		String json = gson.toJson(json_data);
		return json;
	}
	
	private JsonObject getGanancias(int lastDays) {
		
		Calendar dateStart = getDateStart(lastDays);
		JsonObject jsonGanancias = getJsonDatesInstance(lastDays, 0);
		ResultSet tabla = null;
		
		try {
			
			tabla = getTablaGanancias(dateStart);
			
			while(tabla.next()) {
				
				Calendar fecha = dateTimeSQLToCalendar(tabla.getString("fecha"));
				int ganancias = tabla.getInt("ganancias");
				
				String keyFecha = getKeyDate(fecha);
				
				jsonGanancias.addProperty(keyFecha, ganancias);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonGanancias;
	}
	
	private ResultSet getTablaGanancias(Calendar dateStart) {
		
		Calendar fechaLimite = Calendar.getInstance();
		fechaLimite.set(Calendar.HOUR_OF_DAY, 0);
		fechaLimite.set(Calendar.MINUTE, 0);
		
		String sqlFechaInicio = dateTimeCalendarToSQL(dateStart);
		String sqlFechaLimite = dateTimeCalendarToSQL(fechaLimite);
		
		ResultSet tabla = null;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "SELECT DATE_FORMAT(hora_inicio, '%Y-%m-%d 00:00:00') AS fecha, SUM(total) AS ganancias FROM liquidaciones WHERE hora_inicio > ? AND hora_inicio < ? GROUP BY fecha;";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, sqlFechaInicio);
			objSta.setString(2, sqlFechaLimite);
			tabla = objSta.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tabla;
		
	}
	
	public String getDataHeader() {
		Gson gson = new Gson();
		String data = gson.toJson(new JsonObject());
		return data;
	}
	
	private JsonObject getHeader() {
		
		JsonObject json = new JsonObject();
		
		int clientes = 0;
		int liquidaciones = 0;
		int ganancias = 0;
		int servicios = 0;
		
		
		
		return json;
		
	}
	
	private Calendar dateTimeSQLToCalendar(String datetime) {
		// str format: "YYYY-MM-DD HH:MM:SS"
		//              0123456789012345678
		Calendar cal = Calendar.getInstance();
		
		int year = Integer.parseInt(datetime.substring(0, 4));
		int mes = Integer.parseInt(datetime.substring(5, 7)) - 1;
		int dia = Integer.parseInt(datetime.substring(8, 10));
		int hora = Integer.parseInt(datetime.substring(11, 13));
		int minutos = Integer.parseInt(datetime.substring(14, 16));
		
		cal.set(year, mes, dia, hora, minutos, 0);
		
		return cal;
	}
	
	private String dateTimeCalendarToSQL(Calendar date) {
		// str format: "YYYY-MM-DD HH:MM:SS"
		//              0123456789012345678
//		"d MMM yyyy HH:mm"
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-d HH:mm:00");
		String fecha = formato_fecha.format(date.getTime());
		
		
		return fecha;
	}
	
	public static Calendar toCalendar(Date date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	private Calendar getDateStart(int dias) {
		
		Calendar dateStart = Calendar.getInstance();
		dateStart.add(Calendar.DAY_OF_MONTH, dias*-1);
		dateStart.set(Calendar.HOUR_OF_DAY, 0);
		dateStart.set(Calendar.MINUTE, 0);
		return dateStart;
	}
	
	private static String getKeyDate(Calendar fecha) {
		SimpleDateFormat formato_fecha = new SimpleDateFormat("d MMM", new Locale("es", "ES"));
		String keyDate = formato_fecha.format(fecha.getTime());
        return keyDate;
	}
	
//	private static void addValue(JsonObject json, String key, int add) {
//		int n = Integer.parseInt(json.get("key").toString()); 
//		json.addProperty("key", n+add);
//	}
	
	private JsonObject getJsonDatesInstance(int lastDays, JsonObject jsonobject) {
		JsonObject json_dates = new JsonObject();
		Calendar startDate = getDateStart(lastDays);
		
		int i = 0;
		while(i < lastDays) {
			JsonObject json_services = getJsonServicesInstance();
			String key_date = getKeyDate(startDate);
			json_dates.add(key_date, json_services);
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			i++;
		}
		
		return json_dates;
	}
	
	private JsonObject getJsonDatesInstance(int lastDays, int valInit) {
		JsonObject json_dates = new JsonObject();
		Calendar startDate = getDateStart(lastDays);
		
		int i = 0;
		while(i < lastDays) {
			String key_date = getKeyDate(startDate);
			json_dates.addProperty(key_date, valInit);
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			i++;
		}
		
		return json_dates;
	}
	
	private JsonObject getJsonServicesInstance() {//TERMINADO
		
		JsonObject json_services = new JsonObject();
		
		modeloServicios ms = new modeloServicios();
		ArrayList<String> services_list = ms.getTodosLosServicios();
		
		for (String servicio : services_list) {
			
			json_services.addProperty(servicio, 0);
			
		}
		ms.cerrarConexion();
		return json_services;
		
	}
	
	public static void main(String[] args) {
		
		modeloLiquidaciones ml = new modeloLiquidaciones();
		
//		JsonObject json_dates = ml.getServiciosPrestados(13);
		System.out.println(ml.getDataPieChartServiciosPrestados());
		
	}
	
}
