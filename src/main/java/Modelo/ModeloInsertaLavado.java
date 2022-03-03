package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.openjfx.AutolavadoFX.lavados_diarios;

public class ModeloInsertaLavado {
	
	private ModeloConexion conexion;    
	private Connection con;
	private lavados_diarios objinsert;
	private String insertasql = "INSERT INTO lavados_diarios(Vehiculo, Marca, Lavador,Tipos_Lavados,Total,Fecha,Placa) VALUES (?,?,?,?,?,?,?)";
	
	public ModeloInsertaLavado(lavados_diarios lavados) {
		objinsert = lavados;
		conexion = new ModeloConexion();
		con = conexion.getConexion();
		
		LocalDate dia = LocalDate.now();
		try {
			PreparedStatement sentencia = con.prepareStatement(insertasql);
			sentencia.setString(1, objinsert.getVehiculo());
			sentencia.setString(2, objinsert.getMarca());
			sentencia.setString(3, objinsert.getLavador());
			sentencia.setString(4, objinsert.getTipo_lavado());
			sentencia.setInt(5, objinsert.getTotal());
			sentencia.setDate(6, java.sql.Date.valueOf(dia));
			sentencia.setString(7, objinsert.getPlaca());
			sentencia.executeUpdate();
			
			con.close();
			sentencia.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	

}
