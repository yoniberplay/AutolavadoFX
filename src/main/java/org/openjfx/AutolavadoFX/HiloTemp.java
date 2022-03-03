package org.openjfx.AutolavadoFX;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Modelo.ModeloConexion;

public class HiloTemp implements Runnable {
	 Connection con;
	 public HiloTemp() {
		 con = new ModeloConexion().getConexion();
		 Thread hilo = new Thread(this);
		 hilo.start();
		 
	 }
	
	
	
	

	@Override
	public void run() {
		String escritorio = System.getProperty("user.home");
		
		while (true) {
			
		
		try {
			
			Date date = new Date();
			DateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			String hora = hourFormat.format(date);
			String fecha = dateFormat.format(date);
		
			FileOutputStream os = new FileOutputStream(escritorio+"\\Desktop"+"\\TiempoConexion.txt",true);
			PrintStream ps = new PrintStream(os);
			ps.println(fecha + "\n" + hora + "\n"+ con.isClosed() );
			ps.close();
			Thread.sleep(300000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		}
	}

}
