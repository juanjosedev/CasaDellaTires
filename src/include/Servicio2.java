package include;

import java.util.ArrayList;

public class Servicio2 {

	long id;
	String nombre;
	ArrayList<DetalleServicio> lista_detalle;
	
	public Servicio2(String nombre, ArrayList<DetalleServicio> lista_detalle) {
		
		this.id = 0;
		this.nombre = nombre;
		this.lista_detalle = lista_detalle;
		
	}
	
	public Servicio2(long id, String nombre, ArrayList<DetalleServicio> lista_detalle) {
		
		this.id = id;
		this.nombre = nombre;
		this.lista_detalle = lista_detalle;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<DetalleServicio> getLista_detalle() {
		return lista_detalle;
	}

	public void setLista_detalle(ArrayList<DetalleServicio> lista_detalle) {
		this.lista_detalle = lista_detalle;
	}

	@Override
	public String toString() {
		return "Servicio2 [id=" + id + ", nombre=" + nombre + ", lista_detalle=" + lista_detalle + "]\n";
	}
	
	
	
}
