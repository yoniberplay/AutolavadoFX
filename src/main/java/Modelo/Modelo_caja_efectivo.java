package Modelo;

public class Modelo_caja_efectivo {
	
	String Ultimo_retiro;
	float ganancia_lavadores,Ganancia_bruta,ganancia_neta;
	
	public Modelo_caja_efectivo(String ultimo_retiro, float ganancia_bruta , float ganancia_lavadores,
			float ganancia_neta) {
		
		this.Ultimo_retiro = ultimo_retiro;
		this.ganancia_lavadores = ganancia_lavadores;
		this.Ganancia_bruta = ganancia_bruta;
		this.ganancia_neta = ganancia_neta;
	}

	public String getUltimo_retiro() {
		return Ultimo_retiro;
	}

	public void setUltimo_retiro(String ultimo_retiro) {
		Ultimo_retiro = ultimo_retiro;
	}

	public float getGanancia_lavadores() {
		return ganancia_lavadores;
	}

	public void setGanancia_lavadores(float ganancia_lavadores) {
		this.ganancia_lavadores = ganancia_lavadores;
	}

	public float getGanancia_bruta() {
		return Ganancia_bruta;
	}

	public void setGanancia_bruta(float ganancia_bruta) {
		Ganancia_bruta = ganancia_bruta;
	}

	public float getGanancia_neta() {
		return ganancia_neta;
	}

	public void setGanancia_neta(float ganancia_neta) {
		this.ganancia_neta = ganancia_neta;
	}
	
	
	
	
	

}
