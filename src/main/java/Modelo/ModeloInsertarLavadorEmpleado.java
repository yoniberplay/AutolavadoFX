package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ModeloInsertarLavadorEmpleado {

	
	private String nombre, apellido ;
	private ModeloConexion conexion;    
	private Connection con;
	private double porcentaje;
	
	private String insertasql = "INSERT INTO empleados_lavadores(Nombre, Apellido,Cedula,Fecha,GananciaXVehiculo) VALUES (?,?,?,NOW(),?)";
	
	
	public ModeloInsertarLavadorEmpleado(String nombre, String apellido,String cedula,double porcentaje) {
		conexion = new ModeloConexion();
		con = conexion.getConexion();
		this.nombre=nombre;
		this.apellido=apellido;
		this.porcentaje=porcentaje;
		
		try {
			PreparedStatement sentencia = con.prepareStatement(insertasql);
			sentencia.setString(1, nombre);
			sentencia.setString(2, apellido);
			sentencia.setString(3, cedula);
			sentencia.setString(4, String.valueOf(porcentaje));
			sentencia.executeUpdate();
			con.close();
			sentencia.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Actualizar datos de un empleado
	private String actualizasql = "UPDATE empleados_lavadores set Nombre=?,Apellido=?,Cedula=?,GananciaXVehiculo=? where codigo=?";
	public ModeloInsertarLavadorEmpleado(String nombre, String apellido,String cedula,double porcentaje,int codigo) {
		conexion = new ModeloConexion();
		con = conexion.getConexion();
		this.nombre=nombre;
		this.apellido=apellido;
		this.porcentaje=porcentaje;
		
		try {
			PreparedStatement sentencia = con.prepareStatement(actualizasql);
			sentencia.setString(1, nombre);
			sentencia.setString(2, apellido);
			sentencia.setString(3, cedula);
			sentencia.setString(4, String.valueOf(porcentaje));
			sentencia.setInt(5, codigo);
			sentencia.executeUpdate();
			con.close();
			sentencia.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
