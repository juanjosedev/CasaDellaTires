package include;

public class Servicio {

	private long id;
	private long id_tipo_vehiculo;
	private String nombre;
	private int precio;

	public Servicio(long id, long id_tipo_vehiculo, String nombre, int precio) {
		this.id = id;
		this.id_tipo_vehiculo = id_tipo_vehiculo;
		this.nombre = nombre;
		this.precio = precio;
	}

	public Servicio(long id_tipo_vehiculo, String nombre, int precio) {
		this.id_tipo_vehiculo = id_tipo_vehiculo;
		this.nombre = nombre;
		this.precio = precio;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getId_tipo_vehiculo() {
		return id_tipo_vehiculo;
	}
	public void setId_tipo_vehiculo(long id_tipo_vehiculo) {
		this.id_tipo_vehiculo = id_tipo_vehiculo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", id_tipo_vehiculo=" + id_tipo_vehiculo + ", nombre=" + nombre + ", precio="
				+ precio + "]\n";
	}
}
