 package modelo;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
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
	
	public String getDataBarChartServiciosPrestados(int lastDays) {
		
		JsonObject json_data = getServiciosPrestados(lastDays);
		Gson gson = new Gson();
		
		String data = gson.toJson(json_data);
		
		return data;
	}
	
	private Calendar getDateStart(int dias) {
		
		Calendar fecha_actual = Calendar.getInstance();
		fecha_actual.add(Calendar.DAY_OF_MONTH, dias*-1);
		return fecha_actual;
	}
	
	private static String getKeyDate(Calendar fecha) {
		SimpleDateFormat formato_fecha = new SimpleDateFormat("d MMM", new Locale("es", "ES"));
		String keyDate = formato_fecha.format(fecha.getTime());
        return keyDate;
	}
	
	private static void addValue(JsonObject json, String key, int add) {
		int n = Integer.parseInt(json.get("key").toString()); 
		json.addProperty("key", n+add);
	}
	
	private ResultSet getTablaServiceName_Date(Calendar startDate) {
		
		ResultSet tabla = null;
		PreparedStatement objSta = null;
		String fecha_limite = dateTimeCalendarToSQL(startDate);
		fecha_limite = fecha_limite.substring(0, 10);
		
		try {
			
			String sql = "SELECT nombre, hora_inicio AS fecha FROM liquidaciones, detalle "
					+ "WHERE liquidaciones.consecutivo = detalle.consecutivo_liquidaciones AND hora_inicio > ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, fecha_limite);
			tabla = objSta.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tabla;
		
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
		JsonObject json_fechas = getJsonDatesInstance(lastDays);
		// Llamo mi tabla con los datos resultset
		ResultSet tabla = null;
		try {
			tabla = getTablaServiceName_Date(date_start);
			
			while(tabla.next()) {
				
				Calendar fecha = dateTimeSQLToCalendar(tabla.getString("fecha"));
				String nombre_servicio = tabla.getString("nombre");
				
				String fecha_key = getKeyDate(fecha);
				JsonObject json_fecha = (JsonObject) json_fechas.get(fecha_key);
				
				int val_service_by_date = json_fecha.get(nombre_servicio).getAsInt();
				json_fecha.addProperty(nombre_servicio, val_service_by_date+1);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// uunicamente cerrar el statement
			try {
				if (tabla != null) {
					//objSta.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		return json_fechas;
	}
	
	private JsonObject getJsonDatesInstance(int lastDays) {
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
	
	private JsonObject getJsonServicesInstance() {//TERMINADO
		
		JsonObject json_services = new JsonObject();
		
		modeloServicios ms = new modeloServicios();
		ArrayList<String> services_list = ms.getTodosLosServicios();
		
		for (String servicio : services_list) {
			
			json_services.addProperty(servicio, 0);
			
		}
		
		return json_services;
		
	}
	
	public static void main(String[] args) {
		
		modeloLiquidaciones ml = new modeloLiquidaciones();
		
//		JsonObject json_dates = ml.getServiciosPrestados(13);
		System.out.println(ml.getDataBarChartServiciosPrestados(13));
		
	}
	
}
