package Controladores;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


import Modelo.ModeloConexion;
import Modelo.ModeloDatosLavador;
import Modelo.ModeloInsertarLavadorEmpleado;
import VentaEmergenteJFX.VentanaEmergente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ControladorAdministrarLavadores implements Initializable{

	public ControladorAdministrarLavadores() {
		conexion = new ModeloConexion();
		con = conexion.getConexion();
		
	}
	
	private ModeloConexion conexion;    
	private Connection con;
	
	@FXML
    private AnchorPane VistaAdminLavadores;

    @FXML
    private TableView<ModeloDatosLavador> tabla;

    @FXML
    private TableColumn<ModeloDatosLavador, String> col_nombre;

    @FXML
    private TableColumn<ModeloDatosLavador, String> col_apellido;

    @FXML
    private TableColumn<ModeloDatosLavador, String> col_codigo;

    @FXML
    private TableColumn<ModeloDatosLavador, String> col_fechaentrada;
    
    @FXML
    private TableColumn<ModeloDatosLavador, String> col_cedula;
    
    @FXML
    private TableColumn<ModeloDatosLavador, String> col_porcentaje;

    @FXML
    private TextField txtnombre;

    @FXML
    private TextField txtapellido;
    
    @FXML
    private TextField txtdecula;
    
    @FXML
    private TextField txtganaciaLavador;
    
    
    @FXML
    private TextField txtmodificarnombre;

    @FXML
    private TextField txtmodificarapellido;

    @FXML
    private TextField txtmodificarcedula;

    @FXML
    private TextField txtmodificarganancia;

    @FXML
    void Agregar_lavador(ActionEvent event) {
    	String name = txtnombre.getText();
    	String last_name = txtapellido.getText();
    	String cedula = txtdecula.getText();
    	String porcentaje = txtganaciaLavador.getText();
    	if ( !name.isEmpty() && !last_name.isEmpty() && !cedula.isEmpty()&& !porcentaje.isEmpty()) {
    		
    		
			new ModeloInsertarLavadorEmpleado(name, last_name,cedula,porcentaje);
			txtnombre.setText("");
			txtapellido.setText("");
			txtdecula.setText("");
			txtganaciaLavador.setText("");
			MostrarTabla();
		}else {
		VentanaEmergente.AvisoEmergente("Debe rellenar todos los campos.");
		}
    }
    
    @FXML
    void ModificarEmpleado(ActionEvent event) {
    	try {
    		ModeloDatosLavador temp = tabla.getSelectionModel().getSelectedItem();
    		if (temp!=null) {
    			String name = txtmodificarnombre.getText();
    	    	String last_name = txtmodificarapellido.getText();
    	    	String cedula = txtmodificarcedula.getText();
    	    	String porcentaje = txtmodificarganancia.getText();
    	    	int codigo = Integer.parseInt(temp.getCodigo());
    			if ( !name.isEmpty() && !last_name.isEmpty() && !cedula.isEmpty()&& !porcentaje.isEmpty()) {
    				new ModeloInsertarLavadorEmpleado(name, last_name,cedula,porcentaje,codigo);
    				txtmodificarnombre.setText("");
    				txtmodificarapellido.setText("");
    				txtmodificarcedula.setText("");
    				txtmodificarganancia.setText("");
    				MostrarTabla();
    				tabla.requestFocus();
    			}else {VentanaEmergente.AvisoEmergente("DEBE RELLENAR TODOS LOS DATOS DEL EMPLEADO A CAMBIAR.");}
			}else {VentanaEmergente.AvisoEmergente("NO HA SELECCIONADO NINGUN EMPLEADO.");}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
    }

    
    public ObservableList<ModeloDatosLavador> RellenadoTabla(){
    	
    	ObservableList<ModeloDatosLavador> lista = FXCollections.observableArrayList();
    	String consulta = "CALL PROC_RellenadoTabla_ADMLAVADORES()";
		//String consulta = "SELECT Nombre,Apellido,Cedula,Codigo,DATE_FORMAT(Fecha, '%d/%m/%Y'),GananciaXVehiculo FROM empleados_lavadores ORDER BY CODIGO DESC";
		Statement man;
		try {
			man = con.createStatement();
			ResultSet rs = man.executeQuery(consulta);
			ModeloDatosLavador lavador;
			while (rs.next()) {
				lavador = new ModeloDatosLavador(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6));
				lista.add(lavador);
			}
			
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		
		return lista;
    	
    }

	public void initialize(URL location, ResourceBundle resources) {
		
		 MostrarTabla();
		
	}
    
	public void MostrarTabla(){
		ObservableList<ModeloDatosLavador> lista = RellenadoTabla();

		col_nombre.setCellValueFactory(new PropertyValueFactory<ModeloDatosLavador, String>("nombre"));
		col_apellido.setCellValueFactory(new PropertyValueFactory<ModeloDatosLavador, String>("apellido"));
		col_codigo.setCellValueFactory(new PropertyValueFactory<ModeloDatosLavador, String>("codigo"));
		col_fechaentrada.setCellValueFactory(new PropertyValueFactory<ModeloDatosLavador, String>("fechainicio"));
		col_cedula.setCellValueFactory(new PropertyValueFactory<ModeloDatosLavador, String>("cedula"));
		col_porcentaje.setCellValueFactory(new PropertyValueFactory<ModeloDatosLavador, String>("porcentaje"));

		tabla.setItems(lista);
		
	}
	
	@FXML
    void EliminarLavador(ActionEvent event) {
		
		if (tabla.getSelectionModel().getSelectedIndex()!=-1) {
			ModeloDatosLavador datos =  (ModeloDatosLavador) tabla.getSelectionModel().getSelectedItems().get(0);
			
			//String insertasql = "DELETE FROM empleados_lavadores where Codigo=?";
			String deletesql = "CALL PROC_DELETEempleados_lavadores(?)";
			
			try {
				PreparedStatement sentencia = con.prepareStatement(deletesql);
				sentencia.setString(1, datos.getCodigo());
				sentencia.executeUpdate();
				MostrarTabla();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
    }
	

	
}
