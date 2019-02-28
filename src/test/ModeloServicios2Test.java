package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import include.*;
import modelo.ModeloServicios2;
import modelo.modeloServicios;

public class ModeloServicios2Test {

	ModeloServicios2 ms2;
	modeloServicios ms;

	@Before
	public void setUpBefore() {
		ms2 = new ModeloServicios2();
		ms = new modeloServicios();
	}

	@After
	public void tearDownAfter() {
		ms2.cerrarConexion();
		ms.cerrarConexion();
	}
	
	
	@Test
	public void testGetAllServiciosPage() {
		assertTrue(ms.getAllServicios(1).size() != 0);
	}
	
	@Test
	public void testGetAllServicios() {
		assertTrue(ms.getAllServicios().size() != 0);
	}
	
	@Test
	public void testAgregarNuevoServicio() {

		ArrayList<DetalleServicio> lista_detalles = new ArrayList<>();
		lista_detalles.add(new DetalleServicio(new tipoVehiculo(12, "Automóvil"), 66));
		Servicio2 nuevo_servicio = new Servicio2("Polichado", lista_detalles);

		assertTrue(ms2.agregarNuevoServicio(nuevo_servicio));

	}

	@Test
	public void testEliminarDetalle() {
		assertTrue(ms2.eliminarDetalle(0));
	}

	@Test
	public void testEditarPrecio() {
		boolean res = ms2.editarPrecio(132, 30);
		assertTrue(res);
	}

}
