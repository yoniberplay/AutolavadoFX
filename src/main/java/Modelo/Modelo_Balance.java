package Modelo;

public class Modelo_Balance {
	
	private String nombre;
	private int codigo;
	private float acumulado,porcentaje;
	
	public Modelo_Balance(String nombre, int codigo, float acumulado, float porcentaje) {
		this.nombre = nombre;
		this.codigo = codigo;
		this.acumulado = acumulado;
		this.porcentaje = porcentaje;
	}
	
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public float getAcumulado() {
		return acumulado;
	}
	public void setAcumulado(float acumulado) {
		this.acumulado = acumulado;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	@Override
	public String toString() {
		
		String detalles = nombre+" "+codigo+" "+acumulado+" "+porcentaje;
		
		
		return detalles;
	}
	
	
	

}
