package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import include.Usuario;
import modelo.modeloUsuario;

public class ModeloUsuarioTest {

	modeloUsuario mu;
	
	@Before
	public void setUpBefore() {
		mu = new modeloUsuario();
	}

	@After
	public void tearDownAfter() {
		mu.cerrarConexion();
	}

	@Test
	public void testGetUsuario() {
		
		Usuario usuario = mu.getUsuario(new Usuario("juanjosedev", "12345678"));
		
		assertNotNull(usuario);
		
	}

	@Test
	public void testUsuarioYaExiste() {
		
		boolean res = mu.usuarioYaExiste("usuario");
		
		assertTrue(res);
		
	}

	@Test
	public void testRegistrarOperador() {
		
		Usuario nuevo_usuario = new Usuario("usuario", "clave", "nombre", "primer_apellido", "segundo_apellido", "telefono", "direccion");
		
		boolean res = mu.registrarOperador(nuevo_usuario);
		
		assertTrue(res);
		
	}

	@Test
	public void testAutenticarUsuario() {
	
		Usuario usuario_autenticar = new Usuario("usuario", "clave");
		boolean res = mu.autenticarUsuario(usuario_autenticar);
	
		assertTrue(res);
		
	}

}
