package Modelo;

public class ModeloImpresion {
	private String Cantidad,Descripcion,Monto;
	

	public ModeloImpresion(String cant, String descripcion, String monto) {
		this.Cantidad = cant;
		this.Descripcion = descripcion;
		this.Monto = monto;
		
		while(Cantidad.length() < 5) {
			Cantidad = Cantidad + " ";
		}
		while(Descripcion.length() < 27) {
			Descripcion = Descripcion + " ";
		}
	}
	

	public String getCantidad() {
		return Cantidad;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public String getMonto() {
		return Monto;
	}
	
	
	
}
