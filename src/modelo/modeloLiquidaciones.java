package modelo;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONObject;

import com.google.gson.*;

import include.*;

public class modeloLiquidaciones extends Conexion {

	public boolean agregarNuevaLiquidacion(Liquidacion lqd) {

		boolean sw = false;
		PreparedStatement objSta = null;

		try {

			String sql_liquidaciones = "INSERT INTO liquidaciones (consecutivo, cc, placa, hora_inicio, subtotal, descuento, total) VALUES (?, ?, ?, NOW(), ?, ?, ?)";

			objSta = getConnection().prepareStatement(sql_liquidaciones);
			objSta.setLong(1, lqd.getConsecutivo());
			objSta.setLong(2, lqd.getCliente().getCedula());
			objSta.setString(3, lqd.getVehiculo().getPlaca());
			objSta.setInt(4, lqd.getSubtotal());
			objSta.setInt(5, lqd.getDescuento());
			objSta.setInt(6, lqd.getTotal());

			sw = objSta.executeUpdate() == 1 ? true : false;

			ArrayList<Detalle> lista = lqd.getLista_detalles();

			for (Detalle dll : lista) {

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

	public boolean agregarDetalle(Detalle dll) {

		boolean sw = false;
		PreparedStatement objSta = null;
		// modeloServicios ms = new modeloServicios();
		// modeloTiposVehiculos mtv = new modeloTiposVehiculos();

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
				if (objSta != null)
					objSta.close();
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

			String sql = "SELECT consecutivo AS ultimo FROM liquidaciones ORDER BY consecutivo DESC LIMIT 1;";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();

			while (tabla.next()) {
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

			while (tabla.next()) {

				Detalle dll = new Detalle(tabla.getLong("consecutivo_liquidaciones"), tabla.getString("nombre"),
						tabla.getInt("precio"));
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
			if (page == -1) {
				String sql = "SELECT * FROM liquidaciones WHERE hora_final IS NOT NULL ORDER BY hora_inicio ASC";
				objSta = getConnection().prepareStatement(sql);
			} else {
				String sql = "SELECT * FROM liquidaciones WHERE hora_final IS NOT NULL ORDER BY hora_inicio ASC LIMIT ?, ?";
				objSta = getConnection().prepareStatement(sql);
				objSta.setInt(1, (page * 10) - 10);
				objSta.setInt(2, 10);
			}

			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long consecutivo = tabla.getLong("consecutivo");
				Cliente cliente = mc.getCliente(tabla.getLong("cc"));
				Vehiculo vehiculo = mv.getVehiculo(tabla.getString("placa"));
				ArrayList<Detalle> lista_detalles = getDetalles(consecutivo);
				Calendar entrada = toCalendar(tabla.getDate("hora_inicio"));
				Calendar salida = toCalendar(tabla.getDate("hora_final"));
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");

				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida,
						subtotal, descuento, total);

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

			while (tabla.next()) {

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

				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida,
						subtotal, descuento, total);

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
			objSta.setInt(1, (page * 10) - 10);
			objSta.setInt(2, 10);

			tabla = objSta.executeQuery();

			while (tabla.next()) {

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

				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida,
						subtotal, descuento, total);

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

	public ArrayList<Liquidacion> getLiquidacionesByCliente(long cc) {
		ArrayList<Liquidacion> lista = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		modeloClientes mc = new modeloClientes();
		modeloVehiculos mv = new modeloVehiculos();

		try {

			String sql = "SELECT * FROM liquidaciones WHERE cc = ? ORDER BY hora_inicio DESC";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, cc);

			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long consecutivo = tabla.getLong("consecutivo");
				Cliente cliente = mc.getCliente(tabla.getLong("cc"));
				Vehiculo vehiculo = mv.getVehiculo(tabla.getString("placa"));
				ArrayList<Detalle> lista_detalles = getDetalles(consecutivo);

				String entrada_str = tabla.getString("hora_inicio");
				String salida_str = tabla.getString("hora_final");

				Calendar entrada = dateTimeSQLToCalendar(entrada_str);
				Calendar salida = salida_str != null ? dateTimeSQLToCalendar(salida_str) : null;
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");

				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida,
						subtotal, descuento, total);

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

	public ArrayList<Liquidacion> getLiquidacionesByCliente(String placa) {
		ArrayList<Liquidacion> lista = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		modeloClientes mc = new modeloClientes();
		modeloVehiculos mv = new modeloVehiculos();

		try {

			String sql = "SELECT * FROM liquidaciones WHERE placa = ? ORDER BY hora_inicio DESC";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, placa);

			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long consecutivo = tabla.getLong("consecutivo");
				Cliente cliente = mc.getCliente(tabla.getLong("cc"));
				Vehiculo vehiculo = mv.getVehiculo(tabla.getString("placa"));
				ArrayList<Detalle> lista_detalles = getDetalles(consecutivo);

				String entrada_str = tabla.getString("hora_inicio");
				String salida_str = tabla.getString("hora_final");

				Calendar entrada = dateTimeSQLToCalendar(entrada_str);
				Calendar salida = salida_str != null ? dateTimeSQLToCalendar(salida_str) : null;
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");

				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, entrada, salida,
						subtotal, descuento, total);

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

	public int getSubTotal(ArrayList<Detalle> lista_detalles) {
		int subtotal = 0;

		for (Detalle d : lista_detalles) {
			subtotal += d.getPrecio();
		}

		return subtotal;
	}

	public int getDescuento(ArrayList<Detalle> lista_detalles) {

		int descuento = 0;

		if (lista_detalles.size() >= 4) {
			descuento = lista_detalles.get(0).getPrecio();
			for (Detalle d : lista_detalles) {
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

			while (tabla.next()) {
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

	// SELECT tipos_vehiculos.id, tipos_vehiculos.nombre, servicios.nombre,
	// servicios.precio FROM servicios INNER JOIN tipos_vehiculos ON
	// servicios.id_tipo_vehiculo = tipos_vehiculos.id WHERE tipos_vehiculos.id = ?;

	public boolean terminarLiquidacion(long consecutivo) {

		boolean sw = false;

		PreparedStatement objSta = null;

		try {

			String sql = "UPDATE liquidaciones SET hora_final = NOW() WHERE consecutivo = ?";
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

	public String getNumeroDeLiquidaciones() {
		String jsonLiquidaciones = "";
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT COUNT(consecutivo) AS numLiquidaciones FROM liquidaciones";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			tabla.next();

			int numLiquidaciones = tabla.getInt("numLiquidaciones");

			json.addProperty("numLiquidaciones", numLiquidaciones);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

		jsonLiquidaciones = gson.toJson(json);

		return jsonLiquidaciones;
	}

	/////////////////////////////////////////
	/// GRÁFICAS//////////////////////////////
	/////////////////////////////////////////

	/**
	 * <h2>getDataBarChartServiciosPrestados(int lastDays)</h2> Se obtienen los
	 * datos de la cantidad de servicios prestados en los últimos <b>n
	 * </b><i>(lastDays)</i> días
	 * 
	 * @param lastDays hace referencia a la cantidad de días que va a devolver el
	 *                 método con dicha información
	 * @return <b>String</b> en formato <b>Json</b>
	 *         <hr>
	 *         <b>Ejemplo:</b> { "17 jul": { "Balanceo": 3, "Polichado": 1,
	 *         "Lavado": 0 }, "18 jul": { "Balanceo": 0, "Polichado": 2, "Lavado": 3
	 *         }, "19 jul": { "Balanceo": 1, "Polichado": 2, "Lavado": 2 } }
	 */
	public String getDataBarChartServiciosPrestados(int lastDays) {

		Gson gson = new Gson();// Creamos el objeto Gson que permite convertir de JsonElement a String
		JsonObject jsonData = getServiciosPrestados(lastDays);// Traemos los datos en formato JsonObject
		String strData = gson.toJson(jsonData);// Convertimos de JsonObject a String
		return strData;
	}

	private JsonObject getServiciosPrestados(int lastDays) {

		Calendar startDate = getDateStart(lastDays);
		JsonObject jsonServices = getJsonServicesInstance();
		JsonObject jsonDates = getJsonDatesInstance(lastDays, jsonServices);
		ResultSet tabla = null;
		Calendar dateSQL = null;
		String serviceNameSQL = "";
		String dateKey = "";
		JsonObject jsonDate = null;
		int valueServiceDate = 0;

		try {
			tabla = getTablaServiceName_Date(startDate);
			while (tabla.next()) {
				dateSQL = dateTimeSQLToCalendar(tabla.getString("fecha"));
				serviceNameSQL = tabla.getString("nombre");
				dateKey = getKeyDate(dateSQL);
				jsonDate = (JsonObject) jsonDates.get(dateKey);
				valueServiceDate = jsonDate.get(serviceNameSQL).getAsInt();

				jsonDate.addProperty(serviceNameSQL, (valueServiceDate + 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (tabla != null) {
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonDates;
	}

	private ResultSet getTablaServiceName_Date(Calendar startDate) {

		ResultSet tabla = null;
		PreparedStatement objSta = null;
		String startDate_str = dateTimeCalendarToSQL(startDate);
		String limitDate = dateTimeCalendarToSQL(Calendar.getInstance());
		startDate_str = startDate_str.substring(0, 10);
		limitDate = limitDate.substring(0, 10);

		try {

			String sql = "SELECT nombre, hora_inicio AS fecha FROM liquidaciones, detalle "
					+ "WHERE liquidaciones.consecutivo = detalle.consecutivo_liquidaciones AND hora_inicio > ? AND hora_inicio < ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, startDate_str);
			objSta.setString(2, limitDate);
			tabla = objSta.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tabla;
	}

	public String getDataPieChartServiciosPrestados() {

		Gson gson = new Gson();
		JsonObject json_data = getJsonServiciosPrestados();

		String data = gson.toJson(json_data);

		return data;
	}

	private JsonObject getJsonServiciosPrestados() {

		// 1 JsonObject que vamos a retornar
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

			while (tabla.next()) {

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

		ResultSet tabla = null;
		PreparedStatement objSta = null;
		Calendar actualDate = Calendar.getInstance();
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

			while (tabla.next()) {

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

	public String getDataPieChartGananciasPorServicio() {

		Gson gson = new Gson();
		String data = gson.toJson(getJsonGananciasPorServicio());

		return data;
	}

	private JsonObject getJsonGananciasPorServicio() {

		JsonObject jsonServices = getJsonServicesInstance();
		ResultSet tabla = null;

		try {

			tabla = getTablaGananciasPorServicios();

			while (tabla.next()) {

				String servicio = tabla.getString("servicio");
				int ganancias = tabla.getInt("ganancias");

				jsonServices.addProperty(servicio, ganancias);

			}

			// Codigo

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

	private ResultSet getTablaGananciasPorServicios() {

		ResultSet tabla = null;
		PreparedStatement objSta = null;

		try {

			String sql = "SELECT nombre AS servicio, SUM(precio) AS ganancias FROM liquidaciones, detalle WHERE consecutivo = consecutivo_liquidaciones AND hora_inicio > DATE_FORMAT(NOW(), '%Y-%m-%d') GROUP BY nombre;";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tabla;

	}

	public String getDataBarChartGananciasPorServicio(int lastDays) {

		Gson gson = new Gson();
		String data = gson.toJson(getJsonGananciasPorServicio(lastDays));

		return data;
	}

	private JsonObject getJsonGananciasPorServicio(int lastDays) {

		Calendar dateStart = getDateStart(lastDays);

		JsonObject jsonServices = getJsonServicesInstance();
		JsonObject jsonDates = getJsonDatesInstance(lastDays, jsonServices);
		ResultSet tabla = null;

		Calendar fecha = null;// tabla
		String nombre_servicio = "";// tabla
		int precio = 0;// tabla

		String fecha_key = "";
		JsonObject json_fecha = null;
		int val_ganancia_by_service = 0;

		try {

			tabla = getTablaGananciasPorServicios(dateStart);

			while (tabla.next()) {

				fecha = dateTimeSQLToCalendar(tabla.getString("fecha"));
				nombre_servicio = tabla.getString("nombre");
				precio = tabla.getInt("precio");

				fecha_key = getKeyDate(fecha);
				json_fecha = (JsonObject) jsonDates.get(fecha_key);

				val_ganancia_by_service = json_fecha.get(nombre_servicio).getAsInt();
				val_ganancia_by_service += precio;

				json_fecha.addProperty(nombre_servicio, val_ganancia_by_service);
//				System.out.println(nombre_servicio+" - "+n + " - "+fecha_key);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (tabla != null) {
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return jsonDates;

	}

	private ResultSet getTablaGananciasPorServicios(Calendar dateStart) {

		ResultSet tabla = null;
		PreparedStatement objSta = null;

		String dateStartSQL = dateTimeCalendarToSQL(dateStart);

		try {

			String sql = "SELECT nombre, hora_inicio AS fecha, precio FROM liquidaciones, detalle WHERE liquidaciones.consecutivo = detalle.consecutivo_liquidaciones AND hora_inicio > ? AND hora_inicio < DATE_FORMAT(NOW(), '%Y-%m-%d');";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, dateStartSQL);
			tabla = objSta.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tabla;

	}

	private Calendar dateTimeSQLToCalendar(String datetime) {
		// str format: "YYYY-MM-DD HH:MM:SS"
		// 0123456789012345678
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
		// 0123456789012345678
//		"d MMM yyyy HH:mm"
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-d HH:mm:00");
		String fecha = formato_fecha.format(date.getTime());

		return fecha;
	}

	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private Calendar getDateStart(int dias) {

		Calendar dateStart = Calendar.getInstance();
		dateStart.add(Calendar.DAY_OF_MONTH, dias * -1);
		dateStart.set(Calendar.HOUR_OF_DAY, 0);
		dateStart.set(Calendar.MINUTE, 0);
		return dateStart;
	}

	private static String getKeyDate(Calendar fecha) {
		SimpleDateFormat formato_fecha = new SimpleDateFormat("E d MMM", new Locale("es", "ES"));
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
		while (i < lastDays) {
			JsonObject json_services = getJsonServicesInstance();
			String key_date = getKeyDate(startDate);
			json_dates.add(key_date, json_services);
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			i++;
		}

		return json_dates;
	}

	/**
	 * <h2>getJsonDatesInstance(int lastDays, int valInit)</h2>
	 * <p>
	 * Se obtiene la 'instancia' de un json cuyas <b>keys</b> (claves), son fechas
	 * </p>
	 * 
	 * @param lastDays indica en qué fecha debe de empezar, ejemplo: si
	 *                 <b>lastDays</b> es igual a 5, la primera fecha sería la fecha
	 *                 actual menos 5 días
	 * @param valInit  indica con qué valor se iniciarán las <b>keys</b>
	 * @return <b>JsonObject</b> con las fechas como keys y los valores de acuerdo
	 *         al @param valInit
	 */
	private JsonObject getJsonDatesInstance(int lastDays, int valInit) {
		JsonObject json_dates = new JsonObject();
		Calendar startDate = getDateStart(lastDays);

		int i = 0;
		while (i < lastDays) {
			String key_date = getKeyDate(startDate);
			json_dates.addProperty(key_date, valInit);
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			i++;
		}

		return json_dates;
	}

	/**
	 * <h2>getJsonServicesInstance()</h2>
	 * <p>
	 * Se obtiene la 'instancia' de un objeto json cuyas <b>keys</b> (claves), son
	 * los servicios que se prestan
	 * </p>
	 * 
	 * @return <b>JsonObject</b> con los nombres de los servicios y sus valores en 0
	 *         <hr>
	 *         <b>Ejemplo:</b> { "Balanceo": 0, "Polichado": 0, "Lavado": 0 }
	 */
	private JsonObject getJsonServicesInstance() {

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

//		{"servicio_nombre":"llamarlo","servicios":{"1":"234324","5":"13443"}}
//		{"1":"234324","5":"13443"}
//
//		String json = "{\"servicio_nombre\":\"llamarlo\",\"servicios\":{\"1\":\"234324\",\"5\":\"13443\"}}";
//		Gson gson = new Gson();
//		JsonObject json_servicio = gson.fromJson(json, JsonObject.class);
//		JsonObject json_precios = gson.fromJson(json_servicio.get("servicios"), JsonObject.class);
//		
//		ArrayList<DetalleServicio> lista_detalle = new ArrayList<>();
//		
//		
//		
//		
//		
//		JsonArray json_precios_array =  json_precios.getAsJsonArray();
//		System.out.println(json_servicio);
//		System.out.println(json_precios);
//		
//		modeloTiposVehiculos mtv = new modeloTiposVehiculos();
//		
//		try {
//			
//			Map<String, Integer> attributes = new HashMap<String, Integer>();
//			Set<Entry<String, JsonElement>> entrySet = json_precios.entrySet();
//			
//			for(Map.Entry<String,JsonElement> entry : entrySet){
//				attributes.put(entry.getKey(),json_precios.get(entry.getKey()).getAsInt());
//	        }
//			
//			for(Map.Entry<String,Integer> att : attributes.entrySet()){
//	            
//				long id_tipo_vehiculo = Long.parseLong(att.getKey());
//				int precio = att.getValue();
//				
//				tipoVehiculo tv = mtv.getTipoVehiculo(id_tipo_vehiculo);
//				
//				DetalleServicio dll_servicio = new DetalleServicio(tv, precio);
//				lista_detalle.add(dll_servicio);
//            } 
//			 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Servicio2 nuevo_servicio = new Servicio2(json_servicio.get("servicio_nombre").getAsString(), lista_detalle);
//		
//		System.out.println(nuevo_servicio);
//		
//		ModeloServicios2 ms2 = new ModeloServicios2();
//		boolean sw = ms2.agregarNuevoServicio(nuevo_servicio);
//		System.out.println(sw);
//		mtv.cerrarConexion();
//		

	}

}
