package modelo;

import java.sql.PreparedStatement;

import include.Auditoria;

public class ModeloAuditorias extends Conexion{
	
	public ModeloAuditorias() {
	}
	
	public boolean insertarAuditoria(Auditoria aud) {
		
		boolean flag = false;
		PreparedStatement objSta = null;
		
		try {
			
			String sql = "INSERT INTO auditorias (id_usuario, fecha, modulo, operacion) VALUES (?, NOW(), ?, ?)";
			objSta = getConnection().prepareStatement(sql);
			objSta.setString(1, aud.getId_usuario());
			objSta.setString(2, aud.getModulo());
			objSta.setString(3, aud.getOperacion());
			
			flag = objSta.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	public static void main(String[] args) {
		
//		ModeloAuditorias ma = new ModeloAuditorias();
//		
//		Auditoria aud = new Auditoria("Admin", "clientes", "Crear");
//		
//		System.out.println(ma.insertarAuditoria(aud));
		
	}
	
}
