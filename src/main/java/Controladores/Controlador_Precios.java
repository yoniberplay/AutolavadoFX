package Controladores;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import Modelo.IngresosDiariosModelo;
import Modelo.ModeloConexion;
import Modelo.Modelo_Balance;
import Modelo.Modelo_caja_efectivo;
import Modelo.PreciosModelo;
import VentaEmergenteJFX.VentanaEmergente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Controlador_Precios implements Initializable {

	public Controlador_Precios() {
		conexion = new ModeloConexion();
		con = conexion.getConexion();
	}

	@FXML
	private AnchorPane VistaPrecios;

	@FXML
	private TableView<PreciosModelo> tabla;

	@FXML
	private TableColumn<PreciosModelo, String> colo_vehi;

	@FXML
	private TableColumn<PreciosModelo, Integer> col_sencillo;

	@FXML
	private TableColumn<PreciosModelo, Integer> col_interior;

	@FXML
	private TableColumn<PreciosModelo, Integer> col_motor;

	@FXML
	private TableColumn<PreciosModelo, Integer> col_inferior;

	@FXML
	private JFXComboBox<String> btntipo_vehiculo;

	private ModeloConexion conexion;
	private Connection con;

	@FXML
	private TextField txtsencillo;

	@FXML
	private TextField txtinferior;

	@FXML
	private TextField txtmotor;

	@FXML
	private TextField txtinterior;

	@FXML
	private TableView<IngresosDiariosModelo> Tablaingresos;

	@FXML
	private TableColumn<IngresosDiariosModelo, Integer> col_vehiculoslavados;

	@FXML
	private TableColumn<IngresosDiariosModelo, String> col_fecha;

	@FXML
	private TableColumn<IngresosDiariosModelo, Float> col_ingresos;

	@FXML
	private DatePicker fechaganaciadesde;

	@FXML
	private DatePicker fechaganaciahasta;

	@FXML
	void ActulizarPrecios(ActionEvent event) {
		int sencillo, motor, interior, inferior;

		if (btntipo_vehiculo.getValue() != null) {

			try {

				// Validar que todos sean numericos
				sencillo = Integer.parseInt(txtsencillo.getText());
				motor = Integer.parseInt(txtmotor.getText());
				interior = Integer.parseInt(txtinterior.getText());
				inferior = Integer.parseInt(txtinferior.getText());

				ActualizarSQL(sencillo, interior, motor, inferior, btntipo_vehiculo.getValue());

				MostrarPrecios();
				btntipo_vehiculo.getSelectionModel().clearSelection();
				txtmotor.setText("");
				txtsencillo.setText("");
				txtinterior.setText("");
				txtinferior.setText("");

			} catch (NumberFormatException e) {
				VentanaEmergente.AvisoEmergente("Ingrese solo valores numericos.");
			}

		} else {
			VentanaEmergente.AvisoEmergente("Selecione el tipo de vehiculo");
		}
	}

	private void RellenandoVehiculos() {

		String consultaVehicular = "CALL PROC_Vehiculo_precios_lavados";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(consultaVehicular);
			ObservableList<String> list = FXCollections.observableArrayList();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			btntipo_vehiculo.setItems(list);
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
	}

	public ObservableList<PreciosModelo> RellenadoTabla() {

		ObservableList<PreciosModelo> lista = FXCollections.observableArrayList();
		String consulta = "CALL PROC_RellenadoTabla()";
		Statement man;
		try {
			man = con.createStatement();
			ResultSet rs = man.executeQuery(consulta);
			PreciosModelo precios;
			while (rs.next()) {
				precios = new PreciosModelo(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				lista.add(precios);
			}

		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		return lista;

	}

	void MostrarPrecios() {

		ObservableList<PreciosModelo> lista = RellenadoTabla();

		colo_vehi.setCellValueFactory(new PropertyValueFactory<PreciosModelo, String>("vehiculo"));
		col_sencillo.setCellValueFactory(new PropertyValueFactory<PreciosModelo, Integer>("sencillo"));
		col_interior.setCellValueFactory(new PropertyValueFactory<PreciosModelo, Integer>("interior"));
		col_motor.setCellValueFactory(new PropertyValueFactory<PreciosModelo, Integer>("motor"));
		col_inferior.setCellValueFactory(new PropertyValueFactory<PreciosModelo, Integer>("inferior"));

		tabla.setItems(lista);

	}

	void ActualizarSQL(int sencillo, int motor, int interior, int inferior, String vehiculo) {

		//String consulta = "UPDATE precios_lavados set Sencillo=?, Interior=?,Motor=?,Inferior=? WHERE Vehiculo=?";
		String consulta = "CALL PROC_ActualizarSQLPRECIOS(?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(consulta);
			ps.setInt(1, sencillo);
			ps.setInt(2, interior);
			ps.setInt(3, motor);
			ps.setInt(4, inferior);
			ps.setString(5, vehiculo);
			ps.executeUpdate();
			System.out.println("Actualizacion realizada, correctamente.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	void MostrarIngresos() {

		ObservableList<IngresosDiariosModelo> lista = RellenadoTablaIngresos();

		col_vehiculoslavados
				.setCellValueFactory(new PropertyValueFactory<IngresosDiariosModelo, Integer>("vehiculos_lavados"));
		col_fecha.setCellValueFactory(new PropertyValueFactory<IngresosDiariosModelo, String>("fecha"));
		col_ingresos.setCellValueFactory(new PropertyValueFactory<IngresosDiariosModelo, Float>("ingresos"));

		Tablaingresos.setItems(lista);

	}

	public ObservableList<IngresosDiariosModelo> RellenadoTablaIngresos() {

		String cantidadfechasslq = "CALL PROC_RellenadoTablaIngresos";
		//String cantidadfechasslq = "SELECT DISTINCT Fecha FROM lavados_diarios ORDER BY  Fecha DESC LIMIT 0,30";
		ArrayList<String> listafecha = new ArrayList<String>();

		Statement man;
		try {
			man = con.createStatement();
			ResultSet rs = man.executeQuery(cantidadfechasslq);
			while (rs.next()) {
				listafecha.add(rs.getString(1));
			}

		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		ObservableList<IngresosDiariosModelo> lista = FXCollections.observableArrayList();
		//String consulta = "SELECT COUNT(Vehiculo),DATE_FORMAT(Fecha, '%d/%m/%Y')Fecha,SUM(Total) FROM lavados_diarios WHERE FECHA=? ";
		String consulta = "CALL PROC_LISTAFECHAS(?)";
				
		PreparedStatement ps;
		for (int i = 0; i < listafecha.size(); i++) {
			try {
				ps = con.prepareStatement(consulta);
				ps.setString(1, listafecha.get(i));
				ResultSet rs = ps.executeQuery();
				IngresosDiariosModelo IngresoDiario;
				while (rs.next()) {
					IngresoDiario = new IngresosDiariosModelo(rs.getInt(1), rs.getString(2), rs.getInt(3));
					lista.add(IngresoDiario);
				}

			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
		}

		return lista;

	}



	String consultaNombreLavador(int codigoL) {
		String consulta = "CALL PROC_consultaNombreLavador(?)";
		//String consulta = "SELECT NOMBRE,Apellido FROM empleados_lavadores WHERE Codigo=?";
		String datos = "";

		try {
			PreparedStatement ps = con.prepareStatement(consulta);
			ps.setInt(1, codigoL);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				datos += rs.getString(1) + " " + rs.getString(2);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return datos;
	}


	public void initialize(URL location, ResourceBundle resources) {
		MostrarPrecios();
		MostrarIngresos();
		RellenandoVehiculos();
		MuestraHistorial();

	}
	
	@FXML
    private DatePicker Datedesde;

	@FXML
    private DatePicker DateHasta;
    
    @FXML
    private TableView<Modelo_Balance> TablaBalance;

    @FXML
    private TableColumn<Modelo_Balance, String> col_nombre;

    @FXML
    private TableColumn<Modelo_Balance, Integer> colo_codigo;

    @FXML
    private TableColumn<Modelo_Balance, Float> col_porcentaje;

    @FXML
    private TableColumn<Modelo_Balance, Float> col_acumulado;
    
    @FXML
    private JFXTextArea txtresumenbalance;
    
    boolean impresion=false;
    
    @FXML
    void AjusteContable(ActionEvent event) {
    	
    	if (!(Datedesde.getValue() == null) && !(DateHasta.getValue() == null)) {
    		impresion=true;
    		
    		MostrarCalculoBalance();
    		float ganancialavadores = totalacumuladoxlavador();
    		float gananciaBruta = totalganaciasbrutas();
    		float gananciaNeta = gananciaBruta-ganancialavadores;
    		txtresumenbalance.setText("");
    		txtresumenbalance.appendText("\nGanancias Lavadores:    "+ganancialavadores);
    		txtresumenbalance.appendText("\nGanancia bruta:              "+gananciaBruta);
    		txtresumenbalance.appendText("\nGanancia Neta:               "+gananciaNeta);
    		
		}else {
			VentanaEmergente.AvisoEmergente("Debe seleccionar las fechas.");
		}
    }
    
    void MostrarCalculoBalance() {

		ObservableList<Modelo_Balance> lista = RellenadoTablaBalance();

		col_nombre.setCellValueFactory(new PropertyValueFactory<Modelo_Balance, String>("nombre"));
		colo_codigo.setCellValueFactory(new PropertyValueFactory<Modelo_Balance, Integer>("codigo"));
		col_porcentaje.setCellValueFactory(new PropertyValueFactory<Modelo_Balance, Float>("acumulado"));
		col_acumulado.setCellValueFactory(new PropertyValueFactory<Modelo_Balance, Float>("porcentaje"));
		TablaBalance.setItems(lista);
	}
 
    
   ArrayList<Modelo_Balance> filasbalance  = new ArrayList<Modelo_Balance>();
    
   private ObservableList<Modelo_Balance> RellenadoTablaBalance(){
    	ObservableList<Modelo_Balance> lista = FXCollections.observableArrayList();
		String consulta = "CALL PROC_Balance_caja(?,?)";
		LocalDate fecha1 = Datedesde.getValue();
		LocalDate fecha2 = DateHasta.getValue();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(consulta);
			ps.setDate(1, java.sql.Date.valueOf(fecha1));
			ps.setDate(2, java.sql.Date.valueOf(fecha2));
			ResultSet rs = ps.executeQuery();
			Modelo_Balance balance;
			while (rs.next()) {
				balance = new Modelo_Balance(rs.getString(1), rs.getInt(2), rs.getFloat(3), rs.getFloat(4));
				filasbalance.add(balance);
				lista.add(balance);
			}

		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		return lista;
    	
    }
   
   private float totalacumuladoxlavador() {
	   String consulta = "CALL PROC_totalacumuladoxlavador(?,?)";
	   LocalDate fecha1 = Datedesde.getValue();
	   LocalDate fecha2 = DateHasta.getValue();
	   float total = 0;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(consulta);
			ps.setDate(1, java.sql.Date.valueOf(fecha1));
			ps.setDate(2, java.sql.Date.valueOf(fecha2));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				total+=rs.getFloat(1);
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
	   return total;
   }

	private float totalganaciasbrutas() {
		float total = 0;
		String consulta = "CALL PROC_totalganaciasbrutas(?,?)";
		LocalDate fecha1 = Datedesde.getValue();
		LocalDate fecha2 = DateHasta.getValue();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(consulta);
			ps.setDate(1, java.sql.Date.valueOf(fecha1));
			ps.setDate(2, java.sql.Date.valueOf(fecha2));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				total=rs.getInt(1);
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		
		return total;
	}
	
	@FXML
	void Imprimirresumen(ActionEvent event) {
		String consulta = "CALL PROC_insertadatoscalculo(?,?,?)";
		
		if (impresion && col_nombre.getCellData(0) != null ) {
			float ganancialavadores = totalacumuladoxlavador();
    		float gananciaBruta = totalganaciasbrutas();
    		float gananciaNeta = gananciaBruta-ganancialavadores;
    		
    		PreparedStatement ps;
    		try {
    			ps = con.prepareStatement(consulta);
    			ps.setDouble(1, gananciaBruta);
    			ps.setDouble(2, ganancialavadores);
    			ps.setDouble(3, gananciaNeta);
    			ps.executeQuery();
    			
    		} catch (SQLException e1) {
    			System.out.println(e1.getMessage());
    		}
    		
    		for (Modelo_Balance i : filasbalance) {
				System.out.println(i.getCodigo()+" "+i.getNombre());
			}
    		System.out.println("\nGanancias Lavadores:    "+ganancialavadores);
    		System.out.println("\nGanancia bruta:              "+gananciaBruta);
    		System.out.println("\nGanancia Neta:               "+gananciaNeta);
    		MuestraHistorial();
    		limpiarbalance(null);
		}else {
			VentanaEmergente.AvisoEmergente("No ha realizado ningun calculo.");
		}
		
	}
	
	
	@FXML
	void limpiarbalance(ActionEvent event) {
		Datedesde.setValue(null);
		DateHasta.setValue(null);
		TablaBalance.setItems(null);
		txtresumenbalance.setText("");
		impresion=false;
	}
	
	    @FXML
	    private TableView<Modelo_caja_efectivo> TablaHistorial;

	    @FXML
	    private TableColumn<Modelo_caja_efectivo, String> col_fechahistorial;

	    @FXML
	    private TableColumn<Modelo_caja_efectivo, Float> col_brutohistorial;

	    @FXML
	    private TableColumn<Modelo_caja_efectivo, Float> col_lavadores_historial;

	    @FXML
	    private TableColumn<Modelo_caja_efectivo, Float> col_neta_historial;

	
	    private ObservableList<Modelo_caja_efectivo> RellenadoTablahistorial(){
	    ObservableList<Modelo_caja_efectivo> lista = FXCollections.observableArrayList();
		String query = "CALL PROC_HISTORIALCALCULOS()";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			Modelo_caja_efectivo a;
			while (rs.next()) {
				a = new Modelo_caja_efectivo(rs.getString(1),rs.getFloat(2),rs.getFloat(3),rs.getFloat(4));
				lista.add(a);
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		return lista;
	}
	    
	void MuestraHistorial() {
		ObservableList<Modelo_caja_efectivo> lista = RellenadoTablahistorial();

		col_fechahistorial.setCellValueFactory(new PropertyValueFactory<Modelo_caja_efectivo, String>("Ultimo_retiro"));
		col_brutohistorial.setCellValueFactory(new PropertyValueFactory<Modelo_caja_efectivo, Float>("ganancia_bruta"));
		col_lavadores_historial.setCellValueFactory(new PropertyValueFactory<Modelo_caja_efectivo, Float>("ganancia_lavadores"));
		col_neta_historial.setCellValueFactory(new PropertyValueFactory<Modelo_caja_efectivo, Float>("ganancia_neta"));
		TablaHistorial.setItems(lista);

	}
	
}
