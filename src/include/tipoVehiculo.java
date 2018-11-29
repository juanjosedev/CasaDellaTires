package include;

public class tipoVehiculo {
	
	private long id;
	private String nombre;
	
	public tipoVehiculo(String nombre) {
		
		id = 0;
		this.nombre = nombre;
		
	}
	
	public tipoVehiculo(long id, String nombre) {
		
		this.id = id;
		this.nombre = nombre;
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "tipoVehiculo [id=" + id + ", nombre=" + nombre + "]\n";
	}

}
