package modelo;

import java.sql.*;
import java.util.ArrayList;

import include.Cliente;
import include.Vehiculo;
import include.tipoVehiculo;

public class modeloVehiculos extends Conexion{

	public Vehiculo getVehiculo (String placa) {
		
		Vehiculo buscado = null;
		modeloTiposVehiculos mtv = null;
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			mtv = new modeloTiposVehiculos();
			
			String sql = "SELECT * FROM vehiculos WHERE placa = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, placa);
			tabla = objSta.executeQuery();
			
			while (tabla.next()) {
				
				String placa_v = tabla.getString("placa");
				tipoVehiculo tipo = mtv.getTipoVehiculo(Long.parseLong(tabla.getString("id_tipo_vehiculo")));
				String marca = tabla.getString("marca");
				String modelo = tabla.getString("modelo");
				
				buscado = new Vehiculo(placa_v, tipo, marca, modelo);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
					mtv.cerrarConexion();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return buscado;
		
	}
	
	public ArrayList<Vehiculo> getAllVehiculos(int page) {
		
		ArrayList<Vehiculo> lista = new ArrayList<>();
		modeloTiposVehiculos mtv = null;
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			mtv = new modeloTiposVehiculos();
			
			String sql = "SELECT * FROM vehiculos LIMIT ?, ? ";
			objSta = getConnection().prepareStatement(sql);
			objSta.setInt(1, (page*10) - 10);
			objSta.setInt(2, 10);
			tabla = objSta.executeQuery();
			
			while (tabla.next()) {
				
				String placa_v = tabla.getString("placa");
				tipoVehiculo tipo = mtv.getTipoVehiculo(Long.parseLong(tabla.getString("id_tipo_vehiculo")));
				String marca = tabla.getString("marca");
				String modelo = tabla.getString("modelo");
				
				lista.add(new Vehiculo(placa_v, tipo, marca, modelo));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
					mtv.cerrarConexion();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista;
		
	}
	
	public boolean agregarNuevoVehiculo(Vehiculo v) {
		
		boolean sw = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "INSERT INTO vehiculos (placa, id_tipo_vehiculo, marca, modelo) VALUES (?, ?, ?, ?)";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, v.getPlaca());
			objSta.setLong(2, v.getTipo().getId());
			objSta.setString(3, v.getMarca());
			objSta.setString(4, v.getModelo());
			if (objSta.executeUpdate() == 1) sw = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objSta.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sw;
		
	}
	
	public boolean modificarVehiculo(Vehiculo v) {
		
		boolean sw = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "UPDATE vehiculos SET id_tipo_vehiculo = ?, marca = ?, modelo = ? WHERE placa = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, v.getTipo().getId());
			objSta.setString(2, v.getMarca());
			objSta.setString(3, v.getModelo());
			objSta.setString(4, v.getPlaca());
			
			if (objSta.executeUpdate() == 1) sw = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {/**
			try {
				if (getConnection() != null)
					getConnection().close();
				if (objSta != null)
					objSta.close();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
			
		
		return sw;
	}
	
	public int getContarVehiculos() {
		
		int i = 0;
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT COUNT(*) AS cant_vehiculos FROM vehiculos";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				i = tabla.getInt("cant_vehiculos");
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
	
	public String getHTMLi(Vehiculo v) {
		String HTMLcode = "<div class=\"media\">\r\n" + 
				"			<div class=\"media-left\">\r\n" + 
				"			<span class=\"media-object icon-truck fs-em-2\"></span>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"media-body\">\r\n" + 
				"			<h3 class=\"media-heading\">VEHÍCULO</h3>\r\n" + 
				"			<ul>\r\n" + 
				"			<li><i>"+v.getBeautyPlaca()+"</i></li>\r\n" + 
				"			<li><i>"+v.getTipo().getNombre()+"</i></li>\r\n" + 
				"			<li><i>"+v.getMarca()+"</i></li>\r\n" + 
				"			<li><i>"+v.getModelo()+"</i></li>\r\n" + 
				"			</ul>	\r\n" + 
				"			</div>\r\n" + 
				"			</div>";
		return HTMLcode;
	}
	
	//public static void main(String[] args) {
		
		//modeloVehiculos mv = new modeloVehiculos();
		
		//modeloTiposVehiculos mtv = new modeloTiposVehiculos();
		
		//tipoVehiculo tv = mtv.getTipoVehiculo(4);
		
		//Vehiculo v = new Vehiculo("OQW315", tv, "Renault", "Vitara");
		
		//System.out.println(mv.getVehiculo("ASD789"));//Buscar vehículo
		
		//ArrayList<Vehiculo> lista = mv.getAllVehiculos();
		
		/**
		for(Vehiculo v : lista) {
			System.out.println(v); // Imprimir todos los vehículos de la bbdd
		}
		*/
		
		//System.out.println(mv.getContarVehiculos());// Contar vehículos
		
		//System.out.println(mv.agregarNuevoVehiculo(v) ? "Agregado" : "ERROR");// Agregar vehículo
		
		//System.out.println(mv.modificarVehiculo(v) ? "Modificado" : "ERROR");
		
	//}
}
