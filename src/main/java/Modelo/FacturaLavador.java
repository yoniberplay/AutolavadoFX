package Modelo;

public class FacturaLavador {
	private String id,marca,tiposlavados,total,nombre,apellido,codigo;

	public FacturaLavador(String id, String marca, String tiposlavados, String total, String nombre, String apellido,
			String codigo) {
		this.id = id;
		this.marca = marca;
		this.tiposlavados = tiposlavados;
		this.total = total;
		this.nombre = nombre;
		this.apellido = apellido;
		this.codigo = codigo;
		if (tiposlavados.length() > 32) {
			this.tiposlavados = tiposlavados.substring(0, 31);
		}
	}

	public String getId() {
		return id;
	}

	public String getMarca() {
		return marca;
	}

	public String getTiposlavados() {
		return tiposlavados;
	}

	public String getTotal() {
		return total;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCodigo() {
		return codigo;
	}
	
	

}
