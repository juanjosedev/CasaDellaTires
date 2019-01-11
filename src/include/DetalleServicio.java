package include;

public class DetalleServicio {

	long id;
	tipoVehiculo tipo_vehiculo;
	int precio;
	
	public DetalleServicio (tipoVehiculo tipo_vehiculo, int precio) {
		
		this.id = 0;
		this.tipo_vehiculo = tipo_vehiculo;
		this.precio = precio;
		
	}
	
	public DetalleServicio (long id, tipoVehiculo tipo_vehiculo, int precio) {
		
		this.id = id;
		this.tipo_vehiculo = tipo_vehiculo;
		this.precio = precio;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public tipoVehiculo getTipo_vehiculo() {
		return tipo_vehiculo;
	}

	public void setTipo_vehiculo(tipoVehiculo tipo_vehiculo) {
		this.tipo_vehiculo = tipo_vehiculo;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "DetalleServicio [id=" + id + ", tipo_vehiculo=" + tipo_vehiculo + ", precio=" + precio + "]\n";
	}

}
