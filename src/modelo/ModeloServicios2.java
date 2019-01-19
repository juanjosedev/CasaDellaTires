package modelo;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import include.*;

public class ModeloServicios2 extends Conexion{
	
	public boolean agregarNuevoServicio(Servicio2 servicio) {//COMPLETE
		boolean flag = false;
		PreparedStatement objSta = null;
		
		if (servicio.getId() == 0) {
			servicio.setId(getNextIdTablaServicio());
		}
		
		try {
			
//			String sql = "INSERT INTO table_servicios (nombre) VALUES (?)";
//			objSta = getConnection().prepareStatement(sql);
//			objSta.setString(1, servicio.getNombre());
//			
//			flag = objSta.executeUpdate() == 1;
			
			ArrayList<DetalleServicio> lista = servicio.getLista_detalle();
			
			for(DetalleServicio dllsvc : lista) {
				
				agregarServicios(dllsvc, servicio.getNombre());
				
			}
			
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
		
		return flag;
	}
	
	public boolean agregarServicios(DetalleServicio dll, String nombre) {//COMPLETE
		
		boolean flag = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "INSERT INTO servicios (id_tipo_vehiculo, nombre, precio) VALUES (?, ?, ?)";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, dll.getTipo_vehiculo().getId());
			objSta.setString(2, nombre);
			objSta.setInt(3, dll.getPrecio());
			
			flag = objSta.executeUpdate() == 1;
			
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
		
		return flag;
		
	}
	
	public Servicio2 getServicio(String nombre) {
		
		Servicio2 s = null;
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			String sql = "SELECT id, nombre FROM servicios WHERE nombre = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, nombre);
			
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				Long id_s = tabla.getLong("id");
				String nombre_s = tabla.getString("nombre");
				
				ArrayList<DetalleServicio> lista_detalles = getDetalles(nombre);
				
				s = new Servicio2(id_s, nombre_s, lista_detalles);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(tabla != null)
					tabla.close();
				if(objSta != null)
					objSta.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return s;
	}
	
	public ArrayList<Servicio2> getAllServicios() {
		
		ArrayList<Servicio2> lista_servicios = new ArrayList<>();
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
		
			String sql = "SELECT 0 AS id, nombre FROM servicios GROUP BY nombre;";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			
			while (tabla.next()) {
				
				long id = tabla.getLong("id");
				String nombre = tabla.getString("nombre");
				ArrayList<DetalleServicio> lista_detalle = getDetalles(nombre);
				
				Servicio2 svc2 = new Servicio2(id, nombre, lista_detalle);
				
				lista_servicios.add(svc2);
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null)
					objSta.close();
				if (tabla != null)
					tabla.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista_servicios;
	}
	
	public ArrayList<DetalleServicio> getDetalles(String nombre) {
		
		ArrayList<DetalleServicio> lista_detalles = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		modeloTiposVehiculos mtv = new modeloTiposVehiculos();
		try {
			
			String sql = "SELECT id, id_tipo_vehiculo, precio FROM servicios WHERE nombre LIKE ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, nombre);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				long id = tabla.getLong("id");
				long id_tipo_vehiculo = tabla.getLong("id_tipo_vehiculo");
				int precio = tabla.getInt("precio");
				
				tipoVehiculo tv = mtv.getTipoVehiculo(id_tipo_vehiculo);
				
				DetalleServicio dllsvc = new DetalleServicio(id, tv, precio);
				
				lista_detalles.add(dllsvc);
				
			}
			
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
		mtv.cerrarConexion();
		
		return lista_detalles;
	}
	
	public long getNextIdTablaServicio() {
		
		long ai = 0;
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT AUTO_INCREMENT AS AI FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'casadellatires' AND TABLE_NAME = 'table_servicios'";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			tabla.next();
			ai = tabla.getLong("AI");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ai;
		
	}
	
	public boolean eliminarDetalle(long id_detalle) {
	
		boolean flag = false;
		
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "DELETE FROM servicios WHERE id = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, id_detalle);
			flag = objSta.executeUpdate() == 1;			
			
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
		
		return flag;
		
	}
	
	public boolean editarPrecio(long id, int precio) {
		
		boolean flag = false;
		
		PreparedStatement objSta = null;
				
		try {
			
			String sql = "UPDATE servicios SET precio = ? WHERE id = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setInt(1, precio);
			objSta.setLong(2, id);
			flag = objSta.executeUpdate() == 1;			
			
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
		
		return flag;
		
	}
	
	public void imprimirLista(ArrayList<Servicio2> lista) {
		
		for(Servicio2 servicio : lista) {
			System.out.println(servicio.getId()+": "+servicio.getNombre()+"\nServicios:");
			
			ArrayList<DetalleServicio> listadetalles = servicio.getLista_detalle();
			for(DetalleServicio dlls: listadetalles) {
				System.out.println("\t"+dlls.getTipo_vehiculo().getNombre()+": "+dlls.getPrecio());
			}
		}
		
	}
	
	public static void main(String[] args) {
		
//		System.out.println(new ModeloServicios2().getAllServicios().size() > 1);
		
	}
}