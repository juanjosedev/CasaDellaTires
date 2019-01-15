package controlador;

import java.util.ArrayList;

import include.Vehiculo;
import modelo.modeloVehiculos;

public class controladorVehiculos {

	private modeloVehiculos mv;
	
	public controladorVehiculos () {
		mv = new modeloVehiculos();
	}
	
	public void cerrarConexiones() {
		mv.cerrarConexion();
	}
	
	public boolean agregaNuevoVehiculo(Vehiculo v) {
		return mv.agregarNuevoVehiculo(v);
	}
	
	public ArrayList<Vehiculo> getBusqueda(String query) {
		return mv.getBusqueda(query);
	}
	
	public boolean modificarVehiculo(Vehiculo v) {
		return mv.modificarVehiculo(v);
	} 
	
	public ArrayList<Vehiculo> getAllVehiculos(int page) {
		return mv.getAllVehiculos(page);
	}
	
	public Vehiculo getVehiculo(String placa) {
		return mv.getVehiculo(placa);
	}
	
	public int getContarVehiculos() {
		return mv.getContarVehiculos();
	}
	
	public String getHTMLi(Vehiculo v) {
		return mv.getHTMLi(v);
	}
	
}
