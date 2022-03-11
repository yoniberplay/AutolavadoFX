package org.openjfx.AutolavadoFX;

public class lavados_diarios {

	private String Vehiculo,Marca,Lavador,fecha,Tipo_lavado,Placa;
	private int total;

	public lavados_diarios(String vehiculo, String marca, String lavador,String Tipo_lavado, int total,
			String fecha,String placa) {
		Vehiculo = vehiculo;
		Marca = marca;
		Lavador = lavador;
		this.total = total;
		this.fecha = fecha;
		this.Tipo_lavado=Tipo_lavado;
		this.Placa=placa;
	}
	
	public String toString() {
		String LavadoDetalles="";
		LavadoDetalles="Tipo de Vehiculo: "+Vehiculo+"\nTipos de lavados: "+Tipo_lavado+
    			"\nMarca Vehiculo: "+Marca+"\nCodigo Lavador: "+Lavador
    			+"\nTotal: : "+total;
		return LavadoDetalles;
	}

	public String getTipo_lavado() {
		return Tipo_lavado;
	}

	public void setTipo_lavado(String tipo_lavado) {
		Tipo_lavado = tipo_lavado;
	}

	public String getVehiculo() {
		return Vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		Vehiculo = vehiculo;
	}

	public String getMarca() {
		return Marca;
	}

	public void setMarca(String marca) {
		Marca = marca;
	}
	
	public String getPlaca() {
		return Placa;
	}
	public void setPlaca(String placa) {
		Placa = placa;
	}

	public String getLavador() {
		return Lavador;
	}

	public void setLavador(String lavador) {
		Lavador = lavador;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	
	
	

}
