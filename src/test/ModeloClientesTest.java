package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import include.Cliente;
import modelo.modeloClientes;

public class ModeloClientesTest {

	modeloClientes mc;

	@Before
	public void setUpBefore() {

		mc = new modeloClientes();

	}

	@After
	public void tearDownAfter() {

		mc.cerrarConexion();

	}

	@Test
	public void testGetCliente() {

		Cliente cliente = mc.getCliente(1311);

		assertNotNull(cliente);

	}

	@Test
	public void testAgregarNuevoCliente() {
		
		Cliente nuevo_cliente = new Cliente(9999, "nombre", "primer_apellido", "segundo_apellido", "telefono", "direccion");
		
		boolean res = mc.agregarNuevoCliente(nuevo_cliente);
		
		assertTrue(res);
		
	}

	@Test
	public void testEditarCliente() {
		
		Cliente cliente = new Cliente(1312, "Roberto Alonso", "Castillo", "Álvarez", "1651865", "Madrid");
		
		boolean res = mc.editarCliente(cliente);
		
		assertTrue(res);
		
	}

	@Test
	public void testGetBusqueda() {
		
		ArrayList<Cliente> lista_clientes = mc.getBusqueda("va");
		
		assertTrue(lista_clientes.size() > 0);
		
	}

}
