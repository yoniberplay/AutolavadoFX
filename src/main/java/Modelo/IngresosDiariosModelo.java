package Modelo;

public class IngresosDiariosModelo {

	public IngresosDiariosModelo( int vehiculos_lavados,String fecha, float ingresos) {
		this.fecha = fecha;
		this.vehiculos_lavados = vehiculos_lavados;
		Ingresos = ingresos;
	}
	
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getVehiculos_lavados() {
		return vehiculos_lavados;
	}
	public void setVehiculos_lavados(int vehiculos_lavados) {
		this.vehiculos_lavados = vehiculos_lavados;
	}
	public float getIngresos() {
		return Ingresos;
	}
	public void setIngresos(float ingresos) {
		Ingresos = ingresos;
	}
	private String fecha;
	private int vehiculos_lavados;
	private float Ingresos;
}
