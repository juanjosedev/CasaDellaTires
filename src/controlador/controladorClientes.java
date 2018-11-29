package controlador;

import java.util.ArrayList;

import include.Cliente;
import modelo.modeloClientes;

public class controladorClientes {
	
	private modeloClientes mtv;
	
	public controladorClientes() {
		this.mtv = new modeloClientes();
	}
	
	public void cerrarConexiones() {
		mtv.cerrarConexion();
	}
	
	public boolean agregarCliente(Cliente c) {
		return mtv.agregarNuevoCliente(c);
	}
	
	public boolean editarCliente(Cliente c) {
		return mtv.editarCliente(c);
	}
	
	public boolean eliminarCliente(long cc) {
		return mtv.eliminarCliente(cc);
	}
	
	public Cliente getCliente(long cc) {
		return mtv.getCliente(cc);
	}
	
	public ArrayList<Cliente> getAllClientes(int page) {
		ArrayList<Cliente> lista = mtv.getAllClientes(page);
		return lista;
	}
	
	public int getContarClientes () {
		return mtv.getContarClientes();
	}
	
	public String getHTMLi(Cliente c) {
		return mtv.getHTMLi(c);
	}
	
}
