package Modelo;

public class PreciosModelo {
	
	public PreciosModelo(String vehiculo, int sencillo, int interior, int motor, int inferior) {
		this.vehiculo = vehiculo;
		this.sencillo = sencillo;
		this.interior = interior;
		this.motor = motor;
		this.inferior = inferior;
	}
	
	public String getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}
	public int getSencillo() {
		return sencillo;
	}
	public void setSencillo(int sencillo) {
		this.sencillo = sencillo;
	}
	public int getInterior() {
		return interior;
	}
	public void setInterior(int interior) {
		this.interior = interior;
	}
	public int getMotor() {
		return motor;
	}
	public void setMotor(int motor) {
		this.motor = motor;
	}
	public int getInferior() {
		return inferior;
	}
	public void setInferior(int inferior) {
		this.inferior = inferior;
	}





	private String vehiculo;
	private int sencillo,interior,motor,inferior;

}
