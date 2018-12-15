package include;

import java.util.ArrayList;
import java.util.Calendar;

public class Liquidacion {

	private long consecutivo;
	private Cliente cliente;
	private Vehiculo vehiculo;
	private ArrayList<Detalle> lista_detalles;
	private Calendar entrada;
	private Calendar salida;
	
	private String hora_inicio;
	private String hora_final;
	
	private int subtotal;
	private int descuento;
	private int total;
	
	public Liquidacion(long consecutivo, Cliente cliente, Vehiculo vehiculo, ArrayList<Detalle> lista_detalles, Calendar entrada,
			Calendar salida, String hora_inicio, String hora_final, int subtotal, int descuento, int total) {
		this.consecutivo = consecutivo;
		this.cliente = cliente;
		this.vehiculo = vehiculo;
		this.lista_detalles = lista_detalles;
		this.entrada = entrada;
		this.salida = salida;
		
		this.hora_inicio = hora_inicio;
		this.hora_final = hora_final;
		
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.total = total;
	}
	
	public Liquidacion(Cliente cliente, Vehiculo vehiculo, ArrayList<Detalle> lista_detalles, Calendar entrada,
			Calendar salida, String hora_inicio, String hora_final, int subtotal, int descuento, int total) {
		this.consecutivo = 0;
		this.cliente = cliente;
		this.vehiculo = vehiculo;
		this.lista_detalles = lista_detalles;
		this.entrada = entrada;
		this.salida = salida;
		
		this.hora_inicio = hora_inicio;
		this.hora_final = hora_final;
		
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.total = total;
	}

	public long getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(long consecutivo) {
		this.consecutivo = consecutivo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public ArrayList<Detalle> getLista_detalles() {
		return lista_detalles;
	}

	public void setLista_detalles(ArrayList<Detalle> lista_detalles) {
		this.lista_detalles = lista_detalles;
	}

	public Calendar getEntrada() {
		return entrada;
	}

	public void setEntrada(Calendar entrada) {
		this.entrada = entrada;
	}

	public Calendar getSalida() {
		return salida;
	}

	public void setSalida(Calendar salida) {
		this.salida = salida;
	}

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public String getHora_final() {
		return hora_final;
	}

	public void setHora_final(String hora_final) {
		this.hora_final = hora_final;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Liquidacion [consecutivo=" + consecutivo + ", cliente=" + cliente + ", vehiculo=" + vehiculo
				+ ", lista_detalles=" + lista_detalles + ", entrada=" + entrada + ", salida=" + salida
				+ ", hora_inicio=" + hora_inicio + ", hora_final=" + hora_final + ", subtotal=" + subtotal
				+ ", descuento=" + descuento + ", total=" + total + "]";
	}

}
