package include;

public class Cliente {
	
	long cedula;
	String nombre;
	String primer_apellido;
	String segundo_apellido;
	String telefono;
	String direccion;

	public Cliente(String nombre, String primer_apellido, String segundo_apellido, String telefono, String direccion) {
		this.nombre = nombre;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	public Cliente(long cedula, String nombre, String primer_apellido, String segundo_apellido, String telefono, String direccion) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	public long getCedula() {
		return cedula;
	}
	public void setCedula(long cedula) {
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimer_apellido() {
		return primer_apellido;
	}
	public void setPrimer_apellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}
	public String getSegundo_apellido() {
		return segundo_apellido;
	}
	public void setSegundo_apellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getNombreCompleto() {
		return nombre + " " + primer_apellido + " " + segundo_apellido;
	}
	
	@Override
	public String toString() {
		return "Cliente [cedula=" + cedula + ", nombre=" + nombre + ", primer_apellido=" + primer_apellido
				+ ", segundo_apellido=" + segundo_apellido + ", telefono=" + telefono + ", direccion=" + direccion
				+ "]";
	}
		
}
