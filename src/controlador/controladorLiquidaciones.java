package controlador;

import java.util.ArrayList;

import include.*;
import modelo.*;

public class controladorLiquidaciones{
	
	private modeloLiquidaciones ml;
	private modeloClientes mc;
	private modeloVehiculos mv;
	private modeloServicios ms;
	
	public controladorLiquidaciones() {
		this.ml = new modeloLiquidaciones();
		this.mc = new modeloClientes();
		this.mv = new modeloVehiculos();
		this.ms = new modeloServicios();
	}
	
	public ArrayList<Liquidacion> getLiquidacionesPendientes(){
		return ml.getLiquidacionesPendientes();
	}
	
	public ArrayList<Liquidacion> getLiquidacionesCompletas(int page){
		return ml.getLiquidacionesCompletas(page);
	}

	public boolean agregarNuevaLiquidacion(long cedula, String placa, String[] servicios) {
		
		boolean sw = false;
		
		long[] id_servicios = new long[servicios.length];
		
		for (int i = 0; i < servicios.length; i++) {
			id_servicios[i] = Long.parseLong(servicios[i]);
		}
		
		long consecutivo = ml.getSiguienteConsecutivo();
		Cliente cliente = mc.getCliente(cedula);
		Vehiculo vehiculo = mv.getVehiculo(placa);
		ArrayList<Detalle> lista_detalles = ms.getDetalles(id_servicios, consecutivo);
		int subtotal = ml.getSubTotal(lista_detalles);
		int descuento = ml.getDescuento(lista_detalles);
		int total = ml.getTotal(lista_detalles);

		Liquidacion nuevaLqd = new Liquidacion(consecutivo, cliente, vehiculo, lista_detalles, null, null, subtotal, descuento, total);
		
		sw =  ml.agregarNuevaLiquidacion(nuevaLqd);
		
		return sw;
	}
	
	public boolean terminarLiquidacion(long consecutivo) {
		boolean sw = ml.terminarLiquidacion(consecutivo);
		return sw;
	}
	
	public int getContarLiquidacionesCompletas() {
		return ml.getContarLiquidacionesCompletas();
	}
	
	public void cerrarConexionesControlador() {
		this.ml.cerrarConexion();
		this.mc.cerrarConexion();
		this.mv.cerrarConexion();
		this.ms.cerrarConexion();
	}
	/**
	public static void main(String[] args) {
		
		controladorLiquidaciones cl = new controladorLiquidaciones();
		String[] servicios = {"8"};
		boolean sw = cl.agregarNuevaLiquidacion(1060, "poo123", servicios, "21:6");
		System.out.println(sw ? "ÉXITO" : "PAILAS MK");
		
	}
	*/
	
}
