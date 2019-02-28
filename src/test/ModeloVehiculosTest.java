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
		assertNotNull(mv.getVehiculo("QWE789"));
	}

	@Test
	public void testGetAllVehiculos() {
		assertTrue(mv.getAllVehiculos(1).size() != 0);
	}
	
	@Test
	public void testGetBusqueda() {
		assertTrue(mv.getBusqueda("chevrolet").size() > 0);
	}

	@Test
	public void testAgregarNuevoVehiculo() {
		
		Vehiculo nuevo_vehiculo = new Vehiculo("FGH456", new tipoVehiculo(5, "Deportivo"), "Tesla", "S");
		assertTrue(mv.agregarNuevoVehiculo(nuevo_vehiculo));
		
	}

	@Test
	public void testGetContarVehiculos() {
		assertTrue(mv.getContarVehiculos() != 0);
	}
	
}
