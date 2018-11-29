package include;

public class Usuario {

	private String usuario;
	private String clave;
	private String nombre;
	private String primer_apellido;
	private String segundo_apellido;
	private String telefono;
	private String direccion;
	private String tipo;
	private String color;
	
	public Usuario(String usuario, String clave) {
		this.usuario = usuario;
		this.clave = clave;
	}
	
	public Usuario(String usuario, String clave, String nombre, String primer_apellido, String segundo_apellido,
			String telefono, String direccion, String tipo) {
		this.usuario = usuario;
		this.clave = clave;
		this.nombre = nombre;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tipo = tipo;
	}
	
	public Usuario(String usuario, String clave, String nombre, String primer_apellido, String segundo_apellido,
			String telefono, String direccion, String tipo, String color) {
		this.usuario = usuario;
		this.clave = clave;
		this.nombre = nombre;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.tipo = tipo;
		this.color = color;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNombreCompleto() {
		String nombre_completo = nombre+" "+primer_apellido+" "+segundo_apellido;
		return nombre_completo;
	}
	
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", clave=" + clave + ", nombre=" + nombre + ", primer_apellido="
				+ primer_apellido + ", segundo_apellido=" + segundo_apellido + ", telefono=" + telefono + ", direccion="
				+ direccion + ", tipo=" + tipo + ", color=" + color + "]\n";
	}

	
	
}
