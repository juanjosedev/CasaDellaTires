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
	private int subtotal;
	private int descuento;
	private int total;
	
	public Liquidacion(long consecutivo, Cliente cliente, Vehiculo vehiculo, ArrayList<Detalle> lista_detalles, Calendar entrada, Calendar salida,
			int subtotal, int descuento, int total) {
		this.consecutivo = consecutivo;
		this.cliente = cliente;
		this.vehiculo = vehiculo;
		this.lista_detalles = lista_detalles;
		this.entrada = entrada;
		this.salida = salida;
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.total = total;
	}
	
	public Liquidacion(Cliente cliente, Vehiculo vehiculo, ArrayList<Detalle> lista_detalles, Calendar entrada,
			Calendar salida, int subtotal, int descuento, int total) {
		this.consecutivo = 0;
		this.cliente = cliente;
		this.vehiculo = vehiculo;
		this.lista_detalles = lista_detalles;
		this.entrada = entrada;
		this.salida = salida;
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
	
	//////////////////////////////////////////////////////////////////////////
	
    public boolean esCompleta() {
        return salida != null;
    }
    
    public long diferenciaMinutos(){
        long difMinutos = -1;
        long milSegundos_e;
        long milSegundos_s;
        long difMilisegundos;
        
        if (esCompleta()) {
            milSegundos_e = entrada.getTimeInMillis();
            milSegundos_s = salida.getTimeInMillis();
            difMilisegundos = milSegundos_s - milSegundos_e;
            //System.out.println(difMilisegundos);
            difMinutos = (Math.abs(difMilisegundos / (60*1000)));   
        }
        
        return difMinutos;
    }
    
    public long diferenciaHoras(){
        long difHoras = -1;
        long milSegundos_e;
        long milSegundos_s;
        long difMilisegundos;
        
        if (esCompleta()) {
            milSegundos_e = entrada.getTimeInMillis();
            milSegundos_s = salida.getTimeInMillis();
            difMilisegundos = milSegundos_s - milSegundos_e;
            //System.out.println(difMilisegundos);
            difHoras = (Math.abs(difMilisegundos / (60*60*1000)));   
        }
        
        return difHoras;
    }
    
    public long diferenciaDias(){
        long difDias = -1;
        long milSegundos_e;
        long milSegundos_s;
        long difMilisegundos;
        
        if (esCompleta()) {
            milSegundos_e = entrada.getTimeInMillis();
            milSegundos_s = salida.getTimeInMillis();
            difMilisegundos = milSegundos_s - milSegundos_e;
            //System.out.println(difMilisegundos);
            difDias = (Math.abs(difMilisegundos / (24*60*60*1000)));   
        }
        
        return difDias;
    }
    
    public String infoTiempo(){
        return "Fecha de entrada: "+this.entrada.getTime()+"\nFecha de salida: "+this.salida.getTime()+"\n";
    }

	@Override
	public String toString() {
		return "Liquidacion [consecutivo=" + consecutivo + ", cliente=" + cliente + ", vehiculo=" + vehiculo
				+ ", lista_detalles=" + lista_detalles + ", entrada=" + entrada + ", salida=" + salida
				+ ", subtotal=" + subtotal
				+ ", descuento=" + descuento + ", total=" + total + "]";
	}

}
