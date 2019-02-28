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
	public void testGetAllClientes(){
		assertTrue(mc.getAllClientes(1).size() != 0);
	}

	@Test
	public void testAgregarNuevoCliente() {
		Cliente nuevo_cliente = new Cliente(1012, "Mariano", "Delgado", "", "5146245", "Córdoba");		
		assertTrue(mc.agregarNuevoCliente(nuevo_cliente));
	}

	@Test
	public void testEditarCliente() {
		Cliente cliente = new Cliente(1311, "Lucía", "Álvarez", "", "333333", "Madrid");
		assertTrue(mc.editarCliente(cliente));
	}

	@Test
	public void testGetContarClientes() {
		assertTrue(mc.getContarClientes() != 0);
	}
	
	@Test
	public void testGetBusqueda() {
		ArrayList<Cliente> lista_clientes = mc.getBusqueda("va");
		assertTrue(lista_clientes.size() != 0);
	}

}
