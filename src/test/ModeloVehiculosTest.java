package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import include.Vehiculo;
import include.tipoVehiculo;
import modelo.modeloVehiculos;

public class ModeloVehiculosTest {

	modeloVehiculos mv;
	
	@Before
	public void setUpBefore() {
		mv = new modeloVehiculos();
	}
	
	@After
	public void tearDownAfter() {
		mv.cerrarConexion();
	}
	
	@Test
	public void testGetVehiculo() {
		
		Vehiculo vehiculo = mv.getVehiculo("QWE789");
		
		assertNotNull(vehiculo);
		
	}

	@Test
	public void testGetBusqueda() {
	
		ArrayList<Vehiculo> lista_vehiculo = mv.getBusqueda("chevrolet");
		
		assertTrue(lista_vehiculo.size() > 0);
		
	}

	@Test
	public void testAgregarNuevoVehiculo() {
		
		Vehiculo nuevo_vehiculo = new Vehiculo("POO900", new tipoVehiculo(3, "Bus"), "marca", "modelo");
		boolean res = mv.agregarNuevoVehiculo(nuevo_vehiculo);
		
		assertTrue(res);
		
	}

}
