package Controladores;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Modelo.ModeloAdminstracion;
import Modelo.ModeloConexion;
import VentaEmergenteJFX.VentanaEmergente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controladorlogginadm {
	
	@FXML
	private PasswordField txtclave;

	@FXML
	private Button btniniciar;
	
	@FXML
	private Label mensajelabel;

	@FXML
	private Button btncancelar;
	
	@FXML
    private TextField admin;
	
	private Statement st;
	private Connection con;
	private ModeloConexion conexion;    
	
	private String consulta_user_adm = "CALL PROC_consulta_user_adm()";

	@FXML
	void accioninicio(ActionEvent event) {
		conexion = new ModeloConexion();
		con = conexion.getConexion();
		String clave = txtclave.getText();
		String usuario = admin.getText();
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(consulta_user_adm);
			boolean condicion = true;
			while(rs.next()) {
				String usuariotemp =  rs.getString(1);
				String clavetemp =  rs.getString(2);
				if (usuariotemp.equalsIgnoreCase(usuario) && clavetemp.equals(clave)) {
					condicion=false;
					escenario = (Stage) btncancelar.getScene().getWindow();
			    	escenario.close();
			    	new ModeloAdminstracion();
					
				}
			}
			if (condicion) {
				mensajelabel.setText("Datos incorrectos.");
				txtclave.setText("");
				txtclave.requestFocus();
			}
			
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		
		
		
		
		/*String clave = txtclave.getText();
		boolean cond=false;
		if (!clave.isEmpty() && clave!=null) {
			
			for (String a : claves) {
				if (a.equals(clave)) {
					cond=true;
				}
			}
			if (cond) {
				
				escenario = (Stage) btncancelar.getScene().getWindow();
		    	escenario.close();
		    	
		    	new ModeloAdminstracion();
		    	
				System.out.println("Panel de administrador.");
			}
			else {
				mensajelabel.setText("Datos incorrectos.");
				txtclave.setText("");
			}
		}*/
	}

	@FXML
	void cancelar(ActionEvent event) {
		escenario = (Stage) btncancelar.getScene().getWindow();
    	escenario.close();
	}
	
	@FXML
	void Enter(KeyEvent event) {
		int enter = event.getCode().getCode();
		
		if (enter==10) {
			accioninicio(null);
		}

	}
	private Stage escenario;
}
