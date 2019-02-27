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
	
	@Before
	public void setUpBefore() {
		ml = new modeloLiquidaciones();
	}
	
	@After
	public void tearDownAfter() {
		ml.cerrarConexion();
	}
	
	@Test
	public void testAgregarNuevaLiquidacion() {
		
		Cliente cliente = new Cliente(1311, "nombre", "primer_apellido", "segundo_apellido", "telefono", "direccion");
		Vehiculo vehiculo = new Vehiculo("QWE789", null, "marca", "modelo");
		ArrayList<Detalle> lista_detalles = new ArrayList<>();
		lista_detalles.add(new Detalle(101, "Lavado", 22000));
		Calendar entrada = Calendar.getInstance();
		entrada.set(2019, 1, 5, 14, 50);
		Calendar salida = Calendar.getInstance();
		salida.set(2019, 1, 5, 16, 45);
		int subtotal = 22000;
		int descuento = 0;
		int total = 22000;
		
		Liquidacion nueva_liquidacion = new Liquidacion(5, cliente, vehiculo, lista_detalles, entrada, salida, subtotal, descuento, total);
		
		boolean res = ml.agregarNuevaLiquidacion(nueva_liquidacion);
		
		assertTrue(res);
		
	}

	@Test
	public void testTerminarLiquidacion() {
	
		boolean res = ml.terminarLiquidacion(2);
	
		assertTrue(res);
	
	}

}
