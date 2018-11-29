package modelo;

import java.sql.*;
import java.util.ArrayList;

import include.tipoVehiculo;

public class modeloTiposVehiculos extends Conexion {

	public tipoVehiculo getTipoVehiculo(long id_tipo_vehiculo) {
		tipoVehiculo buscado = null;

		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT * FROM tipos_vehiculos WHERE id = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, id_tipo_vehiculo);
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long id = tabla.getLong("id");
				String nombre = tabla.getString("nombre");

				buscado = new tipoVehiculo(id, nombre);

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
		return buscado;

	}

	public ArrayList<tipoVehiculo> getAllTipoVehiculos(int page) {

		ArrayList<tipoVehiculo> lista_tipos_vehiculos = new ArrayList<>();

		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {
			
			if(page == -1) {
				
				String sql = "SELECT * FROM tipos_vehiculos";
				objSta = getConnection().prepareStatement(sql);
				
			} else {
				
				String sql = "SELECT * FROM tipos_vehiculos LIMIT ?, ?";
				objSta = getConnection().prepareStatement(sql);
				objSta.setInt(1, (page*10) - 10);
				objSta.setInt(2, 10);
				
			}
			
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long id = tabla.getLong("id");
				String nombre = tabla.getString("nombre");

				lista_tipos_vehiculos.add(new tipoVehiculo(id, nombre));

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

		return lista_tipos_vehiculos;
	}
	
	public boolean agregarNuevoTipoVehiculo(tipoVehiculo tv) {
		
		boolean sw = false;
		
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "INSERT INTO tipos_vehiculos (nombre) VALUES (?)";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, tv.getNombre());
			if (objSta.executeUpdate() == 1) sw = true;
			
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
	
	public boolean modificarTipoVehiculo(tipoVehiculo tv) {
		boolean sw = false;
		
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "UPDATE tipos_vehiculos SET nombre = ? WHERE id = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, tv.getNombre());
			objSta.setLong(2, tv.getId());
			
			sw = objSta.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objSta.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return sw;
	}
	
	public int getContarTipoVehiculos() {
		
		int i = 0;
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT COUNT(*) AS cant_tipoVehiculos FROM tipos_vehiculos";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				i = tabla.getInt("cant_tipoVehiculos");
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
	
	
	
	public static void main(String[] args) {
		modeloTiposVehiculos mtv = new modeloTiposVehiculos();
		
		//ArrayList<tipoVehiculo> lista = mtv.getAllTipoVehiculos();
		
		//System.out.println(mtv.getTipoVehiculo(2));

		//for (tipoVehiculo valor : lista) {
			//System.out.println(valor);
		//}
		
		tipoVehiculo tv = new tipoVehiculo(14, "Cuatri motoss");
		//System.out.println(mtv.agregarNuevoTipoVehiculo(tv) ? "Se agregó correctamente" : "No se agregó el nuevo tipo de vehículo");
		System.out.println(mtv.modificarTipoVehiculo(tv) ? "Bien" : "Mal");
	}
	
	

}
