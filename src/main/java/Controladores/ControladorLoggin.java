package Controladores;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Modelo.ModeloConexion;
import Modelo.ModeloEscritorio;
import VentaEmergenteJFX.VentanaSalir;
import VentaEmergenteJFX.VentanaEmergente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControladorLoggin implements Initializable{
	
	private ModeloConexion conexion;    
	private Connection con;
	private Statement st;
	private String consultausuario = "CALL PROC_consultausuario()";
	public static String nombre_usuario = null;
	    
	public ControladorLoggin() {
		
	}
	

	@FXML
	private PasswordField txtpasswd;

	@FXML
	private Button btninicio;

	@FXML
	private Button btnsalir;
	
	@FXML
	private TextField txtusuario;

	
	@FXML
	void BotonInicio(ActionEvent event) {
		String clave = txtpasswd.getText();
		String usuario = txtusuario.getText();
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(consultausuario);
			boolean condicion = true;
			while(rs.next()) {
				String usuariotemp =  rs.getString(1);
				String clavetemp =  rs.getString(2);
				if (usuario.equalsIgnoreCase(usuariotemp) && clave.equals(clavetemp)) {
					nombre_usuario = rs.getString(3)+" "+rs.getString(4);
					new ModeloEscritorio();
					condicion=false;
					escenario = (Stage) btnsalir.getScene().getWindow();
			    	escenario.close();
				}
			}
			if (condicion) {
				VentanaEmergente.AvisoEmergente("Datos de usuario, invalidos.");
				txtpasswd.setText("");
				txtusuario.setText("");
				txtusuario.requestFocus();
			}
			
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
	}
	
	
	    @FXML
	    void salir(ActionEvent event) {
	    	
	    	boolean condicion = VentanaSalir.AvisoEmergente();
	    	if (condicion) {
	    		escenario = (Stage) txtusuario.getScene().getWindow();
		    	escenario.close();
			}
	    }
	    
	    

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		conexion = new ModeloConexion();
		con = conexion.getConexion();
		
		
	}
	
	private Stage escenario;
	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	void Mousearrastrado(MouseEvent event) {
		escenario = (Stage) txtusuario.getScene().getWindow();
		escenario.setX(event.getScreenX() - xOffset);
		escenario.setY(event.getScreenY() - yOffset);
		
	}

	
	@FXML
	void Mousepresionado(MouseEvent event) {
		Scene escene = txtusuario.getScene();
    	escene.setCursor(Cursor.HAND);
		 xOffset = event.getSceneX();
         yOffset = event.getSceneY();
	}
	
	@FXML
    void MouseSuelto(MouseEvent event) {
    	Scene escene = txtusuario.getScene();
    	escene.setCursor(Cursor.DEFAULT);
    	
    }
    
	@FXML
	void Enter(KeyEvent event) {
		int enter = event.getCode().getCode();
		
		if (enter==10) {
			BotonInicio(null);
		}

	}

}
