package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import include.*;
import modelo.ModeloServicios2;

public class ModeloServicios2Test {

	ModeloServicios2 ms2;

	@Before
	public void setUpBefore() {
		ms2 = new ModeloServicios2();
	}

	@After
	public void tearDownAfter() {
		ms2.cerrarConexion();
	}
	
	@Test
	public void testAgregarNuevoServicio() {

		ArrayList<DetalleServicio> lista_detalles = new ArrayList<>();
		lista_detalles.add(new DetalleServicio(new tipoVehiculo(2, "Motocicleta"), 55000));
		Servicio2 nuevo_servicio = new Servicio2("NuevoServicio", lista_detalles);

		boolean res = ms2.agregarNuevoServicio(nuevo_servicio);

		assertTrue(res);

	}

	@Test
	public void testGetServicio() {

		Servicio2 servicio = ms2.getServicio("Lavado");

		assertNotNull(new Object());

	}

	@Test
	public void testEliminarDetalle() {

		boolean res = ms2.eliminarDetalle(117);

		assertTrue(res);

	}

	@Test
	public void testEditarPrecio() {

		boolean res = ms2.editarPrecio(106, 28000);

		assertTrue(res);

	}

}
