package Controladores;


import java.net.URL;
import java.util.ResourceBundle;

import Modelo.DatosEstaticos;
import Modelo.ModeloDatosLavador;
import Modelo.ModeloInsertarLavadorEmpleado;
import VentaEmergenteJFX.VentanaEmergente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorModificarlavadores implements Initializable{
	
	    @FXML
	    private TextField txtnombre;

	    @FXML
	    private TextField txtapellido;

	    @FXML
	    private TextField txtdecula;

	    @FXML
	    private TextField txtganaciaLavador;

	    Stage escenario;
	    
	    @FXML
	    void Guardar(ActionEvent event) {
	    	
	    	escenario = (Stage) txtdecula.getScene().getWindow();
	    	String name = txtnombre.getText();
	    	String last_name = txtapellido.getText();
	    	String cedula = txtdecula.getText();
	    	String porcentaje = txtganaciaLavador.getText();
	    	if (DatosEstaticos.cond) {
	    		
	    		if ( !name.isEmpty() && !last_name.isEmpty() && !cedula.isEmpty()&& !porcentaje.isEmpty()) {	
	    	new ModeloInsertarLavadorEmpleado(name, last_name,cedula,Double.parseDouble(porcentaje),DatosEstaticos.codigo);
	    	DatosEstaticos.nombre="";
	    	DatosEstaticos.apellido="";
	    	DatosEstaticos.cedula ="";
	    	DatosEstaticos.porcentaje ="";
	    	DatosEstaticos.codigo = 0;
	    	DatosEstaticos.cond = false;
	    	escenario = (Stage) txtdecula.getScene().getWindow();
			escenario.close();
	    	
	    		}else {
	    			VentanaEmergente.AvisoEmergente("Debe rellenar todos los campos.");
				}
	    		
				
			}else {
	    	 name = txtnombre.getText();
	    	 last_name = txtapellido.getText();
	    	 cedula = txtdecula.getText();
	    	 porcentaje = txtganaciaLavador.getText();
	    	if ( !name.isEmpty() && !last_name.isEmpty() && !cedula.isEmpty()&& !porcentaje.isEmpty()) {
				new ModeloInsertarLavadorEmpleado(name, last_name,cedula,Double.parseDouble(porcentaje));
				txtnombre.setText("");
				txtapellido.setText("");
				txtdecula.setText("");
				txtganaciaLavador.setText("");
				escenario = (Stage) txtdecula.getScene().getWindow();
				escenario.close();
			}else {
			VentanaEmergente.AvisoEmergente("Debe rellenar todos los campos.");
			}
			}
	    }

		public void initialize(URL location, ResourceBundle resources) {
			txtnombre.setText(DatosEstaticos.nombre);
			txtapellido.setText(DatosEstaticos.apellido);
			txtdecula.setText(DatosEstaticos.cedula);
			txtganaciaLavador.setText(DatosEstaticos.porcentaje);
		
		}

	}


