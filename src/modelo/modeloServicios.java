package modelo;

import java.sql.*;
import java.util.ArrayList;

import include.Detalle;
import include.Servicio;
import include.Vehiculo;
import include.tipoVehiculo;

public class modeloServicios extends Conexion {

	public Servicio getServicio(long id) {

		Servicio buscado = null;
		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT * FROM servicios WHERE id = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, id);
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long id_tabla = tabla.getLong("id");
				long id_tipo_vehiculo = tabla.getLong("id_tipo_vehiculo");
				String nombre = tabla.getString("nombre");
				int precio = tabla.getInt("precio");

				buscado = new Servicio(id_tabla, id_tipo_vehiculo, nombre, precio);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return buscado;

	}

	public ArrayList<Servicio> getAllServicios(int page) {

		ArrayList<Servicio> lista_servicios = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT * FROM servicios ORDER BY nombre LIMIT ?, ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setInt(1, (page * 10) - 10);
			objSta.setInt(2, 10);
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long id_tabla = tabla.getLong("id");
				long id_tipo_vehiculo = tabla.getLong("id_tipo_vehiculo");
				String nombre = tabla.getString("nombre");
				int precio = tabla.getInt("precio");

				lista_servicios.add(new Servicio(id_tabla, id_tipo_vehiculo, nombre, precio));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return lista_servicios;

	}
	
	public ArrayList<Servicio> getAllServicios() {

		ArrayList<Servicio> lista_servicios = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT * FROM servicios ORDER BY nombre";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long id_tabla = tabla.getLong("id");
				long id_tipo_vehiculo = tabla.getLong("id_tipo_vehiculo");
				String nombre = tabla.getString("nombre");
				int precio = tabla.getInt("precio");

				lista_servicios.add(new Servicio(id_tabla, id_tipo_vehiculo, nombre, precio));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return lista_servicios;

	}

	public ArrayList<Servicio> getServicios(long id_tipo_vehiculo) {
		ArrayList<Servicio> lista = new ArrayList<>();
		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT tipos_vehiculos.id AS id_tv, tipos_vehiculos.nombre AS nombre_tv, "
					+ "servicios.id AS id_s, servicios.nombre AS nombre_s, servicios.precio AS precio_s "
					+ "FROM servicios INNER JOIN tipos_vehiculos ON servicios.id_tipo_vehiculo = tipos_vehiculos.id "
					+ "WHERE tipos_vehiculos.id = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, id_tipo_vehiculo);
			tabla = objSta.executeQuery();

			while (tabla.next()) {

				long id = tabla.getLong("id_s");
				String nombre = tabla.getString("nombre_s");
				int precio = tabla.getInt("precio_s");

				lista.add(new Servicio(id, 0, nombre, precio));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		

		return lista;
	}

	public boolean agregarServicio(Servicio s) {

		boolean sw = false;
		PreparedStatement objSta = null;

		try {

			String sql = "INSERT INTO servicios (id_tipo_vehiculo, nombre, precio) VALUES (?, ?, ?)";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, s.getId_tipo_vehiculo());
			objSta.setString(2, s.getNombre());
			objSta.setInt(3, s.getPrecio());

			if (objSta.executeUpdate() == 1)
				sw = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sw;

	}

	public int getContarServicios() {

		int i = 0;
		PreparedStatement objSta = null;
		ResultSet tabla = null;

		try {

			String sql = "SELECT COUNT(*) AS cant_servicios FROM servicios";
			objSta = getConnection().prepareStatement(sql);
			tabla = objSta.executeQuery();

			while (tabla.next()) {
				i = tabla.getInt("cant_servicios");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
					tabla.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}

	public boolean eliminarServicio(long id) {

		boolean sw = false;
		PreparedStatement objSta = null;

		try {

			String sql = "DELETE FROM servicios WHERE id = ?";
			objSta = getConnection().prepareStatement(sql);
			objSta.setLong(1, id);
			sw = objSta.executeUpdate() == 1;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objSta != null) {
					objSta.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sw;

	}

	public String getTablaServicios(String placa) {

		modeloVehiculos mv = new modeloVehiculos();
		Vehiculo v = mv.getVehiculo(placa);
		ArrayList<Servicio> lista = getServicios(v.getTipo().getId());

		String tabla = "<hr><h3>Servicios: <small>" + v.getTipo().getNombre()
				+ "</small></h3><div class=\"table-responsive\">\r\n"
				+ "														<table class=\"table table-striped\">\r\n"
				+ "															<thead>\r\n"
				+ "																<tr>\r\n"
				+ "																	<th>Servicio</th>\r\n"
				+ "																	<th>Precio</th>\r\n"
				+ "																</tr>\r\n"
				+ "															</thead>\r\n"
				+ "															<tbody>";
		
		String input_form = "";
		
		for (Servicio s : lista) {
			
			input_form = " <div class=\"input-group\">\r\n" + 
					"                            <input type=\"text\" class=\"form-control\" id=\"inp_precio\" value='"+s.getPrecio()+"' readonly>\r\n" + 
					"                            <div class=\"input-group-addon\"><input type=\"checkbox\" value='"+s.getId()+"' name=\"servicio[]\" id='"+s.getId()+"' class=\"inp_chk_servicio\"></div>\r\n" + 
					"                        </div>";

			tabla = tabla + "<tr>\r\n"
					+ "		<td><var id='"+s.getId()+"'>"+s.getNombre()+"</td>\r\n"
					+ "		<td class=\"text-right\">"
					+ input_form
					+ "		</td>\r\n"
					+ "	</tr>";

		}
		
		mv.cerrarConexion();

		return tabla + "</tbody>\r\n" + "														</table>\r\n"
				+ "													</div>"
				+ "<div class=\"cuentas\">\r\n" + 
				"                    <div class=\"panel panel-default\">\r\n" + 
				"                        <div class=\"panel-body\">\r\n" + 
				"                              <p class=\"text-right\">  <b>Subtotal: </b>\r\n" + 
				"                                <i id=\"valor_subtotal\">0</i></p>" + 
				"                                 <p class=\"text-right\"><b>Descuento: </b>\r\n" + 
				"                                <i id=\"valor_descuento\">0</i>\r\n </p>" + 
				"                        </div>\r\n" + 
				"                        <div class=\"panel-footer\">\r\n" + 
				"                               <p class=\"text-right\"> <b>TOTAL: </b>\r\n" + 
				"                                <i id=\"valor_total\">0</i>\r\n</p>" + 
				"                        </div>\r\n" + 
				"                    </div>\r\n" + 
				"                </div>";

	}
	
	public ArrayList<Detalle> getDetalles(long[] lista_id_servicios, long consecutivo){
		
		ArrayList<Detalle> lista_detalles = new ArrayList<>();
		ArrayList<Servicio> lista_servicios = getAllServicios();
		
		//System.out.println(lista_servicios.size());
		
		for(long i : lista_id_servicios) {
			for(Servicio s : lista_servicios) {
				if(i == s.getId()) {
					lista_detalles.add(new Detalle(consecutivo, s.getNombre(), s.getPrecio()));
					break;
				}
			}
		}
		
		return lista_detalles;
	}

	public static void main(String[] args) {

		modeloServicios ms = new modeloServicios();
		// modeloTiposVehiculos mtv = new modeloTiposVehiculos();

		String tabla = ms.getTablaServicios("qwe123");

		System.out.println(tabla);

		// System.out.println(ms.eliminarServicio(id));

		// Servicio s = new Servicio(3, "Cambio de aceite", 55000);

		// System.out.println(ms.agregarServicio(s) ? "Agregado existosamente" :
		// "Error");

		// Servicio s = ms.getServicio(7);
		// tipoVehiculo tv = mtv.getTipoVehiculo(s.getId_tipo_vehiculo());
		// ArrayList<Servicio> lista_servicios = ms.getAllServicios();
		// ArrayList<Servicio> lista_servicios = ms.getTablaServicios("qwe123");

		// System.out.println(s+""+tv);// Obtener Servicio

		// for(Servicio s: lista_servicios) {
		// System.out.println(s);
		// }

	}

}
