package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import include.*;
import modelo.modeloLiquidaciones;

public class ModeloLiquidacionesTest {

	modeloLiquidaciones ml;
	Cliente cliente;
	Vehiculo vehiculo;
	ArrayList<Detalle> lista_detalles;
	Calendar entrada;
	int subtotal;
	int descuento;
	int total;
	
	@Before
	public void setUpBefore() {
		ml = new modeloLiquidaciones();
		cliente = new Cliente(1311, "nombre", "primer_apellido", "segundo_apellido", "telefono", "direccion");
		vehiculo = new Vehiculo("QWE789", null, "marca", "modelo");
		lista_detalles = new ArrayList<>();
		lista_detalles.add(new Detalle(6, "Lavado", 28));
		entrada = Calendar.getInstance();
		subtotal = 28;
		descuento = 0;
		total = 28;
	}
	
	@After
	public void tearDownAfter() {
		ml.cerrarConexion();
	}
	
	@Test
	public void testAgregarNuevaLiquidacion() {
		
		Liquidacion nueva_liquidacion = new Liquidacion(6, cliente, vehiculo, lista_detalles, entrada, entrada, subtotal, descuento, total);
		
		assertTrue(ml.agregarNuevaLiquidacion(nueva_liquidacion));
		
	}
	
	@Test
	public void testGetDetalles() {
		
		assertTrue(ml.getDetalles(1).size() != 0);
		
	}
	
	@Test
	public void testGetAllLiquidaciones() {
		
		assertTrue(ml.getAllLiquidaciones(1).size() != 0);
		
	}

	@Test
	public void testGetLiquidacionesPendientes() {
		assertTrue(ml.getLiquidacionesPendientes().size() != 0);
	}
	
	@Test
	public void testGetLiquidacionesCompletas() {
		assertTrue(ml.getLiquidacionesCompletas(1).size() != 0);
	}
	
	@Test
	public void testGetLiquidacionesByCliente() {
		assertTrue(ml.getLiquidacionesByCliente(cliente.getCedula()).size() != 0);
	}

	@Test
	public void testGetLiquidacionesByVehiculo() {
		assertTrue(ml.getLiquidacionesByCliente(vehiculo.getPlaca()).size() != 0);
	}
	
	@Test
	public void testGetSubTotal() {
		assertTrue(ml.getSubTotal(lista_detalles) == 28);
	}
	
	@Test
	public void testGetDescuento() {
		assertTrue(ml.getDescuento(lista_detalles) == 0);
	}
	
	@Test
	public void testGetTotal() {
		assertTrue(ml.getTotal(lista_detalles) == 28);
	}
	
	@Test
	public void testTerminarLiquidacion() {
		assertTrue(ml.terminarLiquidacion(5));
	}
	
//	@Test
//	public void testMetodo() {
//		assertTrue(true);
//	}

}
