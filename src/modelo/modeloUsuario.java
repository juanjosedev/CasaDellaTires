package modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

import include.Usuario;

public class modeloUsuario extends Conexion{
	
	public Usuario getUsuario(Usuario usuario_login) {
		Usuario u = null;
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, usuario_login.getUsuario());
			objSta.setString(2, encriptar(usuario_login.getClave()));
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				String usuario = tabla.getString("usuario");
				String clave = tabla.getString("clave");;
				String nombre = tabla.getString("nombre");;
				String primer_apellido = tabla.getString("primer_apellido");;
				String segundo_apellido = tabla.getString("segundo_apellido");;
				String telefono = tabla.getString("telefono");;
				String direccion = tabla.getString("direccion");;
				String tipo = tabla.getString("tipo");
				String color = tabla.getString("color");
				
				u = new Usuario(usuario, clave, nombre, primer_apellido, segundo_apellido, telefono, direccion, tipo, color);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null)
					objSta.close();
				if (tabla != null)
					tabla.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return u;
	}
	
	//Método sobrecargado getAllOperadores() y getAllOperadores(int page)
	
	public ArrayList<Usuario> getAllOperadores(){
		
		ArrayList<Usuario> lista_operadores = new ArrayList<>();
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT * FROM usuarios WHERE tipo NOT LIKE 'Admin'";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				
				String usuario = tabla.getString("usuario");
				String nombre = tabla.getString("nombre");
				String primer_apellido = tabla.getString("primer_apellido");
				String segundo_apellido = tabla.getString("segundo_apellido");
				String telefono = tabla.getString("telefono");
				String direccion = tabla.getString("direccion");
				String tipo = tabla.getString("tipo");
				String color = tabla.getString("color");
				
				Usuario u = new Usuario(usuario, nombre, primer_apellido, segundo_apellido, telefono, direccion, tipo, color);
				
				lista_operadores.add(u);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(objSta != null) {
					objSta.close();
				}
				if(tabla != null) {
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista_operadores;
		
	}
	
	public ArrayList<Usuario> getAllOperadores(int page){
		
		ArrayList<Usuario> lista_operadores = new ArrayList<>();
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT * FROM usuarios WHERE tipo NOT LIKE 'Admin' LIMIT ?, 10";
			objSta = getConnection().prepareStatement(sql);
			objSta.setInt(1, (page*10) - 10);
			tabla = objSta.executeQuery();
			
			while(tabla.next()) {
				String usuario = tabla.getString("usuario");
				String nombre = tabla.getString("nombre");
				String primer_apellido = tabla.getString("primer_apellido");
				String segundo_apellido = tabla.getString("segundo_apellido");
				String telefono = tabla.getString("telefono");
				String direccion = tabla.getString("direccion");
				String tipo = tabla.getString("tipo");
				String color = tabla.getString("color");
				
				Usuario u = new Usuario(usuario, "", nombre, primer_apellido, segundo_apellido, telefono, direccion, tipo, color);
				
				lista_operadores.add(u);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(objSta != null) {
					objSta.close();
				}
				if(tabla != null) {
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lista_operadores;
		
	}
	
	public boolean registrarOperador(Usuario user) {
		
		boolean flag = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "INSERT INTO usuarios (usuario, clave, nombre, primer_apellido, segundo_apellido, telefono, direccion) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, user.getUsuario());
			objSta.setString(2, encriptar(user.getClave()));
			objSta.setString(3, user.getNombre());
			objSta.setString(4, user.getPrimer_apellido());
			objSta.setString(5, user.getSegundo_apellido());
			objSta.setString(6, user.getTelefono());
			objSta.setString(7, user.getDireccion());
			
			flag = objSta.executeUpdate() == 1;
			
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
		
		return flag;
	}
	
	public boolean autenticarUsuario(Usuario user) {
		boolean flag = false;
		
		PreparedStatement objSta = null;
		ResultSet tabla = null;
		
		try {
			
			String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, user.getUsuario());
			objSta.setString(2, encriptar(user.getClave()));
			
			tabla = objSta.executeQuery();
			
			flag = tabla.absolute(1);
			
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
		
		return flag;
	}
	
	public String encriptar(String clave)  throws NoSuchAlgorithmException{
		return modeloUsuario.sha1(clave);
	}
	
	protected static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	
	public static void main(String[] args) {

		//Usuario u = new Usuario("operador", "operador", "María", "Dominguez", "López", "651651", "Sabaneta", "Operador");
		//System.out.println(mu.registrarOperador(u) ? "Registrado" : "Fallo");
		
		modeloUsuario mu = new modeloUsuario();
		
		ArrayList<Usuario> lista_operadores = mu.getAllOperadores(1);
		
		for(Usuario u : lista_operadores) {
			System.out.println(u+"\n");
		}
		
	}
	
}