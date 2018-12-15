package modelo;

import java.sql.*;
import java.util.ArrayList;

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
				String hora_inicio = tabla.getString("hora_inicio");
				String hora_final = tabla.getString("hora_final");
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");
				
				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, hora_inicio, hora_final, subtotal, descuento, total);
				
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
				String hora_inicio = tabla.getString("hora_inicio");
				String hora_final = tabla.getString("hora_final");;
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");
				
				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, hora_inicio, hora_final, subtotal, descuento, total);
				
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
				String hora_inicio = tabla.getString("hora_inicio");
				String hora_final = tabla.getString("hora_final");
				int subtotal = tabla.getInt("subtotal");
				int descuento = tabla.getInt("descuento");
				int total = tabla.getInt("total");
				
				Liquidacion l = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, hora_inicio, hora_final, subtotal, descuento, total);
				
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
	
	public static void main(String[] args) {
		
		modeloLiquidaciones ml = new modeloLiquidaciones();
		ArrayList<Liquidacion> lista_lqds = ml.getLiquidacionesCompletas(1);
		for(Liquidacion l : lista_lqds) {
			System.out.println(l);
		}
		
	}
	
}
