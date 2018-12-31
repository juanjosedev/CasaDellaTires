package modelo;

import java.sql.*;
import java.util.ArrayList;

import include.Cliente;

public class modeloClientes extends Conexion {

	public Cliente getCliente(long cc) {
		
		Cliente buscado = null;
		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT * FROM clientes WHERE cc = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, cc);
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long cedula = tabla.getLong("cc");
				String nombre = tabla.getString("nombre");
				String primer_apellido = tabla.getString("primer_apellido");
				String segundo_apellido = tabla.getString("segundo_apellido");
				String telefono = tabla.getString("telefono");
				String direccion = tabla.getString("direccion");
				
				buscado = new Cliente(cedula, nombre, primer_apellido, segundo_apellido, telefono, direccion);
				
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
			//System.out.println(getConnection()+"\n"+buscado);
		}

		return buscado;
	}
	
	public ArrayList<Cliente> getAllClientes(int page) {

		ArrayList<Cliente> lista_clientes = new ArrayList<>();

		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT * FROM clientes ORDER BY nombre LIMIT ?, ?";
			
			objSta = getConnection().prepareStatement(sql);
			objSta.setInt(1, (page*10) - 10);
			objSta.setInt(2, 10);
			
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long cedula = tabla.getLong("cc");
				String nombre = tabla.getString("nombre");
				String primer_apellido = tabla.getString("primer_apellido");
				String segundo_apellido = tabla.getString("segundo_apellido");
				String telefono = tabla.getString("telefono");
				String direccion = tabla.getString("direccion");

				lista_clientes.add(new Cliente(cedula, nombre, primer_apellido, segundo_apellido, telefono, direccion));

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

		return lista_clientes;
	}
	
	public boolean agregarNuevoCliente(Cliente c) {
	
		boolean sw = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "INSERT INTO clientes (cc, nombre, primer_apellido, segundo_apellido, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?)";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, c.getCedula());
			objSta.setString(2, c.getNombre());
			objSta.setString(3, c.getPrimer_apellido());
			objSta.setString(4, c.getSegundo_apellido());
			objSta.setString(5, c.getTelefono());
			objSta.setString(6, c.getDireccion());
			if (objSta.executeUpdate() == 1) sw = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/**
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
	
	public boolean editarCliente(Cliente c) {
		
		boolean sw = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "UPDATE clientes SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, telefono = ?, direccion = ? WHERE cc = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, c.getNombre());
			objSta.setString(2, c.getPrimer_apellido());
			objSta.setString(3, c.getSegundo_apellido());
			objSta.setString(4, c.getTelefono());
			objSta.setString(5, c.getDireccion());
			objSta.setLong(6, c.getCedula());
			if (objSta.executeUpdate() == 1) sw = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		
			
			
			
		}
		
		return sw;
		
	}
	
	public boolean eliminarCliente(long cc) {
		
		boolean sw = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "DELETE FROM clientes WHERE cc = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, cc);
			if (objSta.executeUpdate() == 1) sw = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/**
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
	
	public int getContarClientes() {
		int i = 0;
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		try {
			
			String sql = "SELECT COUNT(*) AS cant_clientes FROM clientes";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				i = tabla.getInt("cant_clientes");
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
	
	public String getHTMLi(Cliente c) {
		String HTMLcode = "<div class=\"media\">\r\n" + 
				"		<div class=\"media-left\">\r\n" + 
				"		<span class=\"media-object icon-user fs-em-2\"></span>\r\n" + 
				"		</div>\r\n" + 
				"		<div class=\"media-body\">\r\n" + 
				"		<h3 class=\"media-heading\">CLIENTE</h3>\r\n" + 
				"		<ul>\r\n" +
				"		<li><i>"+c.getNombreCompleto()+"</i></li>\r\n" + 
				"		<li><i>"+c.getCedula()+"</i></li>\r\n" + 
				"		<li><i>"+c.getTelefono()+"</i></li>\r\n" + 
				"		<li><i>"+c.getDireccion()+"</i></li>\r\n" + 
				"		</ul>	\r\n" + 
				"		</div>\r\n" + 
				"		</div>";
		return HTMLcode;
	}
	
	public ArrayList<Cliente> getBusqueda(String criterio) {
		
		ArrayList<Cliente> listaClientes = new ArrayList<>();
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		try {
			
			String sql = "SELECT * FROM clientes WHERE cc LIKE ? OR nombre LIKE ? OR primer_apellido LIKE ? OR segundo_apellido LIKE ? OR telefono LIKE ? OR direccion LIKE ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, "%"+criterio+"%");
			objSta.setString(2, "%"+criterio+"%");
			objSta.setString(3, "%"+criterio+"%");
			objSta.setString(4, "%"+criterio+"%");
			objSta.setString(5, "%"+criterio+"%");
			objSta.setString(6, "%"+criterio+"%");
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				long cedula = tabla.getLong("cc");
				String nombre = tabla.getString("nombre");
				String primer_apellido = tabla.getString("primer_apellido");
				String segundo_apellido = tabla.getString("segundo_apellido");
				String telefono = tabla.getString("telefono");
				String direccion = tabla.getString("direccion");

				listaClientes.add(new Cliente(cedula, nombre, primer_apellido, segundo_apellido, telefono, direccion));
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
		
		return listaClientes;
	}
	
	public void imprimirArrayList(ArrayList<Cliente> listaClientes) {
		
		for(Cliente c : listaClientes) {
			System.out.println(c);
		}
		
	}
	
	public static void main(String[] args) {
		
		//Cliente c_editar = new Cliente(9010, "Mateo", "Leal", "", "3001", "Santa Elena parte baja");
		
		modeloClientes mc = new modeloClientes();
		
		//System.out.println(mc.getCliente(9010));
		
		//System.out.println(mc.editarCliente(c_editar) ? "Editado" : "ERROR");
		
		//System.out.println(mc.getCliente(9010));
		
		//long cc = 8080;mau
		
		mc.imprimirArrayList(mc.getBusqueda("saas"));
		
	}
	
}
