package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import VentaEmergenteJFX.VentanaEmergente;

public class ModeloConexion {
	
	
	public ModeloConexion(){
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpedidos", "yoniber07", "TouH90JOhjk2E9wT");
			if (primeraconec) {
				System.out.println("Conectado a la base de datos.");
				primeraconec = false;
			}
		} catch (SQLException e) {
			VentanaEmergente.AvisoEmergente("Error al conectarse a la Base de datos.");
		}
	}
	
	public Connection getConexion() {
		return conexion;
	}
	
	private static boolean primeraconec = true;
	private Connection conexion;
}
