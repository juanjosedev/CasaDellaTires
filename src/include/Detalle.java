package include;

public class Detalle {
	
	private long consecutivo_liquidacion;
	private String nombre;
	private int precio;
	
	public Detalle(long consecutivo_liquidacion, String nombre, int precio) {
		this.consecutivo_liquidacion = consecutivo_liquidacion;
		this.nombre = nombre;
		this.precio = precio;
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

	public long getConsecutivo_liquidacion() {
		return consecutivo_liquidacion;
	}

	public void setConsecutivo_liquidacion(long consecutivo_liquidacion) {
		this.consecutivo_liquidacion = consecutivo_liquidacion;
	}

	@Override
	public String toString() {
		return "Detalle [consecutivo_liquidacion=" + consecutivo_liquidacion + ", nombre=" + nombre + ", precio="
				+ precio + "]\n";
	}

}
