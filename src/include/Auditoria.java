package include;

import java.util.Calendar;

public class Auditoria {

	private long id;
	private String id_usuario;
	private Calendar fecha;
	private String modulo;
	private String operacion;
	private String id_modulo;
	
	public Auditoria(String id_usuario, String modulo, String operacion) {
		this.id_usuario = id_usuario;
		this.modulo = modulo;
		this.operacion = operacion;
	}
	
	public Auditoria(String id_usuario, Calendar fecha, String modulo, String operacion) {
		this.id_usuario = id_usuario;
		this.fecha = fecha;
		this.modulo = modulo;
		this.operacion = operacion;
	}

	public Auditoria(String id_usuario, Calendar fecha, String modulo, String operacion, String id_modulo) {
		this.id_usuario = id_usuario;
		this.fecha = fecha;
		this.modulo = modulo;
		this.operacion = operacion;
		this.id_modulo = id_modulo;
	}

	public Auditoria(long id, String id_usuario, Calendar fecha, String modulo, String operacion, String id_modulo) {
		this.id = id;
		this.id_usuario = id_usuario;
		this.fecha = fecha;
		this.modulo = modulo;
		this.operacion = operacion;
		this.id_modulo = id_modulo;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getId_modulo() {
		return id_modulo;
	}
	public void setId_modulo(String id_modulo) {
		this.id_modulo = id_modulo;
	}
	
	

}
