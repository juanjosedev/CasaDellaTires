package include;

public class Vehiculo {
	
	private String placa;
	private tipoVehiculo tipo;
	private String marca;
	private String modelo;
	
	public Vehiculo(String placa, tipoVehiculo tipo, String marca, String modelo) {
		this.placa = placa;
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
	}
	
	public Vehiculo(tipoVehiculo tipo, String marca, String modelo) {
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
	}

	public String getPlaca() {
		return placa;
	}
	
	public String getBeautyPlaca() {
		return placa.substring(0, 3) + " - " + placa.substring(3, 6);
	}
	
	public String getFirstPlaca() {
		return placa.substring(0, 3);
	}
	
	public String getSecondPlaca() {
		return placa.substring(3, 6);
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public tipoVehiculo getTipo() {
		return tipo;
	}

	public void setTipo(tipoVehiculo tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@Override
	public String toString() {
		return "Vehiculo [placa=" + placa + ", tipo=" + tipo.getNombre() + ", marca=" + marca + ", modelo=" + modelo + "]\n";
	}
	
}
