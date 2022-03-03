package Modelo;

public class ModeloDatosLavador {

	private String nombre,apellido,codigo,fechainicio,cedula,porcentaje;
	
	public ModeloDatosLavador(String nombre, String apellido,String cedula, String codigo, String fechainicio,String porcentaje) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.cedula=cedula;
		this.codigo = codigo;
		this.fechainicio = fechainicio;
		this.porcentaje = porcentaje;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}

	public String toString() {
		return nombre+" "+apellido+" "+codigo+" "+fechainicio;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	
	

}
