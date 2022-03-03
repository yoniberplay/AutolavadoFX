package org.openjfx.AutolavadoFX;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Reloj implements Runnable {
	
	
	public Reloj(Label lblreloj) {
		this.lblreloj=lblreloj;
		//String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		//this.lblreloj.setText(timeStamp);
		
		hilo = new Thread(this);
		hilo.setPriority(Thread.MIN_PRIORITY);
		hilo.setDaemon(true);
		hilo.start();
		
	}
	
	

	private Label lblreloj;
	private Thread hilo;
	
	
	public void run() {
	
	while (true) {
	
		try {
			Date date = new Date();
			DateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
		String hora = hourFormat.format(date);
		String fecha =  dateFormat.format(date);
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	lblreloj.setText(hora+"\n"+fecha);
            }
        });
		Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
		
	}

}
