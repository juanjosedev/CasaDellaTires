package controlador;

import java.util.ArrayList;

import include.tipoVehiculo;
import modelo.modeloTiposVehiculos;

public class controladorTipoVehiculos {
	
	private modeloTiposVehiculos mtv;
	
	public controladorTipoVehiculos () {
		this.mtv = new modeloTiposVehiculos();
	}
	
	public boolean agregarTipoVehiculo(tipoVehiculo tv) {
		return mtv.agregarNuevoTipoVehiculo(tv);
	}
	
	public boolean  modificarTipoVehiculo(long id, String nombre) {
		tipoVehiculo tv = new tipoVehiculo(id, nombre);
		return mtv.modificarTipoVehiculo(tv);
	}
	
	public ArrayList<tipoVehiculo> getAllTipoVehiculos(int page) {
		ArrayList<tipoVehiculo> lista = mtv.getAllTipoVehiculos(page);
		return lista;
	}
	
	public int getContarTipoVehiculos() {
		return mtv.getContarTipoVehiculos();
	}
	
	public void cerrarConexion() {
		mtv.cerrarConexion();
	}
	
	/**
	public static void main(String[] args) {
		controladorTipoVehiculos ctv = new controladorTipoVehiculos();
		
		//ArrayList<tipoVehiculo> lista = mtv.getAllTipoVehiculos();
		
		//System.out.println(mtv.getTipoVehiculo(2));

		//for (tipoVehiculo valor : lista) {
			//System.out.println(valor);
		//}
		
		tipoVehiculo tv = new tipoVehiculo("asd");
		System.out.println(ctv.agregarTipoVehiculo(tv) ? "Se agregó correctamente" : "No se agregó el nuevo tipo de vehículo");
		
	}
	*/
	
}
