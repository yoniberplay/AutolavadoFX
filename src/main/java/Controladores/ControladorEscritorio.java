package Controladores;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.openjfx.AutolavadoFX.Reloj;
import org.openjfx.AutolavadoFX.lavados_diarios;

import Modelo.ModeloConexion;
import Modelo.ModeloInsertaLavado;
import Modelo.ModeloLogginAdm;
import Modelo.PreciosModelo;
import VentaEmergenteJFX.VentanaEmergente;
import VentaEmergenteJFX.VentanaSalir;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControladorEscritorio implements Initializable {

	public ControladorEscritorio() {
		conexion = new ModeloConexion();
		con = conexion.getConexion();
	}

	private ToggleGroup tipovehiculo;
	private ModeloConexion conexion;
	private Connection con;
	private Statement st;
	

	@FXML
	private ComboBox<String> MarcaVehiculos;

	@FXML
	private ComboBox<String> COMcodelavador;

	@FXML
	private Label SalidaPrograma;
	private Stage escenario;

	@FXML
	private RadioButton Rcarro;

	@FXML
	private RadioButton Rjeep;

	@FXML
	private RadioButton Rmotor;

	@FXML
	private RadioButton Rotro;

	@FXML
	private RadioButton Rcamion;

	
	@FXML
	private CheckBox Lotro;

	@FXML
	private TextField txtpreciootro;
	
	@FXML
	private CheckBox Lsencillo;

	@FXML
	private CheckBox Linterior;

	@FXML
	private CheckBox Lmotor;

	@FXML
	private CheckBox Linteriorgrafito;

	@FXML
	private Button btnimprimir;

	@FXML
	private Button btnimprimirseleccion;

	@FXML
	private TableView<lavados_diarios> tabla;

	@FXML
	private TableColumn<lavados_diarios, String> colvehic;

	@FXML
	private TableColumn<lavados_diarios, String> colmarc;

	@FXML
	private TableColumn<lavados_diarios, String> collavador;

	@FXML
	private TableColumn<lavados_diarios, String> coltotal;

	@FXML
	private TableColumn<lavados_diarios, String> colplaca;

	@FXML
	private TableColumn<lavados_diarios, String> colfecha;

	@FXML
	private TableColumn<lavados_diarios, String> coltiposlavados;

	@FXML
	private Label lblreloj;

	@FXML
	private Label Minimixar;

	@FXML
	private DatePicker diadelavado;

	@FXML
	private Button btnbuscar;

	@FXML
	private TextField txtplacabusqueda;

	@FXML
	private TextField txtplaca;

	@FXML
	void ocultar(MouseEvent event) {
		escenario = (Stage) SalidaPrograma.getScene().getWindow();
		escenario.setIconified(true);

	}

	@FXML
	void Accionimprimir(ActionEvent event) {
		if (VerificarOtro()) {
			Detalles_Lavado();
			mostrandolavadosdiarios();
			System.out.println(ControladorLoggin.nombre_usuario);
		}else if (Lotro.isSelected()==false) {
			Detalles_Lavado();
			mostrandolavadosdiarios();
			System.out.println(ControladorLoggin.nombre_usuario);
		}
		else {
			VentanaEmergente.AvisoEmergente("El precio del tipo de lavado debe ser numerico.");
		}
		
		
	}

	@FXML
	void Cerrar(MouseEvent event) {

		boolean condicion = VentanaSalir.AvisoEmergente();
		if (condicion) {
			escenario = (Stage) SalidaPrograma.getScene().getWindow();
			escenario.close();
		}

	}

	private String catidaddelavados(CheckBox[] tipolavado, boolean desactivar) {
		String lavado = "";
		if (desactivar) {
			for (CheckBox a : tipolavado) {
				if (a.isSelected()) {
					lavado += a.getText() + " ";
					a.setSelected(false);
				}
			}
			return lavado;
		} else {
			for (CheckBox a : tipolavado) {
				if (a.isSelected()) {
					lavado += a.getText() + " ";
				}
			}
			return lavado;
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		tipovehiculo = new ToggleGroup();
		Rcarro.setSelected(true);
		RellenandoMarcas();
		RellenandoLavadores();
		Rcarro.setToggleGroup(tipovehiculo);
		Rjeep.setToggleGroup(tipovehiculo);
		Rmotor.setToggleGroup(tipovehiculo);
		Rcamion.setToggleGroup(tipovehiculo);
		// Rotro.setToggleGroup(tipovehiculo);
		mostrandolavadosdiarios();
		new Reloj(lblreloj);
		

	}

	private void RellenandoMarcas() {
		
		String consultamarcas = "CALL PROC_marcavehiculoMarcavehiculo";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(consultamarcas);
			ObservableList<String> list = FXCollections.observableArrayList();
			while (rs.next()) {
				list.add(rs.getString(1).toUpperCase());
			}
			MarcaVehiculos.setItems(list);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void RellenandoLavadores() {
		String consultacodigo = "CALL PROC_empleados_lavadores()";
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(consultacodigo);
			ObservableList<String> list = FXCollections.observableArrayList();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			COMcodelavador.setItems(list);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public ObservableList<lavados_diarios> obteniendolavadosdiarios(int tipoconsulta) {
		// Tipo de consulta 1-consulta de todos, 2-Consulta con parametros
		if (tipoconsulta == 1) {
			ObservableList<lavados_diarios> lista = FXCollections.observableArrayList();
			//String consulta = "SELECT id,Vehiculo,Marca,Lavador,Tipos_Lavados,Total,DATE_FORMAT(Fecha, '%d/%m/%Y') Fecha,Placa FROM lavados_diarios ORDER BY id DESC";
			String consulta = "CALL PROC_obteniendolavadosdiarios()";
			Statement man;
			try {
				man = con.createStatement();
				ResultSet rs = man.executeQuery(consulta);
				lavados_diarios lavados;
				while (rs.next()) {
					lavados = new lavados_diarios(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getInt(6), rs.getString(7), rs.getString(8));
					lista.add(lavados);

				}
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			return lista;
		} else if (tipoconsulta == 2) {
			// Fecha actual
			//java.util.Date date = new Date();
			LocalDate fechalocal = diadelavado.getValue();
			java.sql.Date dia = java.sql.Date.valueOf(fechalocal);
			//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			//String fechaactual = dateFormat.format(date);
			//String fecha = dateFormat.format(dia);
			ObservableList<lavados_diarios> lista = FXCollections.observableArrayList();
			//String consulta = "SELECT id,Vehiculo,Marca,Lavador,Tipos_Lavados,Total,DATE_FORMAT(Fecha, '%d/%m/%Y') Fecha,Placa FROM lavados_diarios where Fecha BETWEEN ? AND ? AND Placa=? ORDER BY id DESC";
			String consulta = "CALL PROC_obteniendolavadosdiarios2(?,?,?)";
			PreparedStatement ps;

			try {
				ps = con.prepareStatement(consulta);
				ps.setDate(1, dia);
				ps.setDate(2, java.sql.Date.valueOf(fechalocal));
				ps.setString(3, txtplacabusqueda.getText());
				ResultSet rs = ps.executeQuery();
				lavados_diarios lavados;
				while (rs.next()) {
					lavados = new lavados_diarios(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getInt(6), rs.getString(7), rs.getString(8));

					lista.add(lavados);

				}
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			return lista;
		} else if (tipoconsulta == 3) {

			LocalDate fechalocal = diadelavado.getValue();
			//java.sql.Date dia = java.sql.Date.valueOf(fechalocal);
			//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			//String fecha = dateFormat.format(dia);
			ObservableList<lavados_diarios> lista = FXCollections.observableArrayList();
			//String consulta = "SELECT id,Vehiculo,Marca,Lavador,Tipos_Lavados,Total,DATE_FORMAT(Fecha, '%d/%m/%Y') Fecha,Placa FROM lavados_diarios where Fecha=?  ORDER BY id ASC";
			String consulta = "CALL PROC_obteniendolavadosdiarios3(?)";
			PreparedStatement ps;

			try {

				ps = con.prepareStatement(consulta);
				ps.setDate(1, java.sql.Date.valueOf(fechalocal));
				// ps.setString(2, fechaactual);
				// ps.setString(3, txtplacabusqueda.getText());
				ResultSet rs = ps.executeQuery();
				lavados_diarios lavados;
				while (rs.next()) {
					lavados = new lavados_diarios(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getInt(6), rs.getString(7), rs.getString(8));

					lista.add(lavados);

				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return lista;
		} else if (tipoconsulta == 4) {

			ObservableList<lavados_diarios> lista = FXCollections.observableArrayList();
			//String consulta = "SELECT id,Vehiculo,Marca,Lavador,Tipos_Lavados,Total,DATE_FORMAT(Fecha, '%d/%m/%Y') Fecha,Placa FROM lavados_diarios where Placa=? ORDER BY id DESC";
			String consulta = "CALL PROC_obteniendolavadosdiarios4(?)";
			PreparedStatement ps;

			try {

				ps = con.prepareStatement(consulta);
				ps.setString(1, txtplacabusqueda.getText());
				ResultSet rs = ps.executeQuery();
				lavados_diarios lavados;
				while (rs.next()) {
					lavados = new lavados_diarios(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getInt(6), rs.getString(7), rs.getString(8));

					lista.add(lavados);

				}
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			return lista;
		} else {
			return null;
		}

	}

	public void mostrandolavadosdiarios() {
		ObservableList<lavados_diarios> lista = obteniendolavadosdiarios(1);

		colvehic.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("vehiculo"));
		colmarc.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("marca"));
		collavador.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("lavador"));
		coltotal.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("total"));
		coltiposlavados.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("Tipo_lavado"));
		colfecha.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("fecha"));
		colplaca.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("Placa"));

		tabla.setItems(lista);
	}

	@FXML
	void imprimeseleccion(ActionEvent event) {
		String detalles = tabla.getSelectionModel().getSelectedItems().toString();
		detalles = detalles.substring(1, detalles.length() - 1);
		String escritorio = System.getProperty("user.home");
		if (detalles.isEmpty()) {
			System.out.println("No ha seleccionado nada");
		} else {
			try {
				FileOutputStream os = new FileOutputStream(escritorio + "\\Desktop" + "\\Impresora.txt", true);
				PrintStream ps = new PrintStream(os);
				ps.println(detalles + "\n");
				ps.close();
				System.out.println("Impresion Realizada.");
				tabla.getSelectionModel().clearSelection();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public Label getLblreloj() {
		return lblreloj;
	}

	private void Detalles_Lavado() {

		lavados_diarios objlavado;
		RadioButton vehiculoseleccionado = (RadioButton) tipovehiculo.getSelectedToggle();
		String vehiculo = vehiculoseleccionado.getText().toUpperCase();

		CheckBox[] arraylavado = { Lsencillo, Linterior, Lmotor, Linteriorgrafito,Lotro };

		String cantidadlavados = catidaddelavados(arraylavado, false).toUpperCase();
		String Marca = MarcaVehiculos.getSelectionModel().getSelectedItem();
		String codigolavador = COMcodelavador.getSelectionModel().getSelectedItem();
		String placa = txtplaca.getText().toUpperCase();
		
		
		if (Marca != null && (codigolavador!=null) && !(cantidadlavados.isEmpty()) && !(placa.isEmpty()) ) {

			int total = GestionaTotal(vehiculo);

			objlavado = new lavados_diarios(vehiculo, Marca.toUpperCase(), codigolavador, cantidadlavados, total, "",
					placa);
			new ModeloInsertaLavado(objlavado);

			// Botones y Text a su valor inical
			Rcarro.setSelected(true);
			catidaddelavados(arraylavado, true);
			txtplaca.setText("");
			MarcaVehiculos.getSelectionModel().clearSelection();
			COMcodelavador.getSelectionModel().clearSelection();
			txtpreciootro.setText("Precio $");
			txtpreciootro.setDisable(true);
		} else {
			VentanaEmergente.AvisoEmergente("Debes rellenar todos los datos.");
		}

	}

	int GestionaTotal(String VEHICULO) {

		int total = 0;
		PreciosModelo precios = obtenerprecios(VEHICULO);
		int sencillo = precios.getSencillo();
		int interior = precios.getInterior();
		int inferior = precios.getInferior();
		int motor = precios.getMotor();

		switch (VEHICULO) {
		case "CARRO": {
			if (Lsencillo.isSelected()) {
				total += sencillo;
			}
			if (Linterior.isSelected()) {
				total += interior;
			}
			if (Lmotor.isSelected()) {
				total += motor;
			}
			if (Linteriorgrafito.isSelected()) {
				total += inferior;
			}
			if (Lotro.isSelected()) {
				total += Integer.parseInt(txtpreciootro.getText());
			}
			break;
		}
		case "JEEP": {
			if (Lsencillo.isSelected()) {
				total += sencillo;
			}
			if (Linterior.isSelected()) {
				total += interior;
			}
			if (Lmotor.isSelected()) {
				total += motor;
			}
			if (Linteriorgrafito.isSelected()) {
				total += inferior;
			}
			if (Lotro.isSelected()) {
				total += Integer.parseInt(txtpreciootro.getText());
			}
			break;
		}
		case "MOTOR": {
			if (Lsencillo.isSelected()) {
				total += sencillo;
			}
			if (Linterior.isSelected()) {
				total += interior;
			}
			if (Lmotor.isSelected()) {
				total += motor;
			}
			if (Linteriorgrafito.isSelected()) {
				total += inferior;
			}
			if (Lotro.isSelected()) {
				total += Integer.parseInt(txtpreciootro.getText());
			}
			break;
		}
		case "CAMION": {
			if (Lsencillo.isSelected()) {
				total += sencillo;
			}
			if (Linterior.isSelected()) {
				total += interior;
			}
			if (Lmotor.isSelected()) {
				total += motor;
			}
			if (Linteriorgrafito.isSelected()) {
				total += inferior;
			}
			if (Lotro.isSelected()) {
				total += Integer.parseInt(txtpreciootro.getText());
			}
			break;
		}
		}
		return total;
	}

	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	void Mousearrastrado(MouseEvent event) {
		escenario = (Stage) SalidaPrograma.getScene().getWindow();
		escenario.setX(event.getScreenX() - xOffset);
		escenario.setY(event.getScreenY() - yOffset);

	}

	@FXML
	void Mousepresionado(MouseEvent event) {
		Scene escene = SalidaPrograma.getScene();
		escene.setCursor(Cursor.HAND);
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}

	@FXML
	void MouseSuelto(MouseEvent event) {
		Scene escene = SalidaPrograma.getScene();
		escene.setCursor(Cursor.DEFAULT);

	}

	@FXML
	void busqueda(ActionEvent event) {

		ObservableList<lavados_diarios> lista = null;
		// LocalDate dia = diadelavado.getValue();
		String placa = txtplacabusqueda.getText();
		if (placa.isEmpty() && diadelavado.getValue() == null) {
			VentanaEmergente.AvisoEmergente("Debe completar al menos un campo, para continuar con la busqueda.");
		} else if ((placa.isEmpty() && !(diadelavado.getValue() == null))) {
			lista = obteniendolavadosdiarios(3);
		} else if ((!placa.isEmpty() && diadelavado.getValue() == null)) {
			lista = obteniendolavadosdiarios(4);
		} else {
			lista = obteniendolavadosdiarios(2);
		}

		if (lista == null) {

		} else {
			colvehic.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("vehiculo"));
			colmarc.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("marca"));
			collavador.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("lavador"));
			coltotal.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("total"));
			colfecha.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("fecha"));
			coltiposlavados.setCellValueFactory(new PropertyValueFactory<lavados_diarios, String>("Tipo_lavado"));

			tabla.setItems(lista);
		}
		txtplacabusqueda.setText("");
		diadelavado.setValue(null);
	}

	@FXML
	void ConfigurarionAccion(MouseEvent event) {
		new ModeloLogginAdm();
	}

	@FXML
	private FontAwesomeIconView labconfiguracion;

	PreciosModelo obtenerprecios(String vehiculo) {
		PreciosModelo precios = null;

		//String consulta = "SELECT * FROM precios_lavados where vehiculo=?";
		String consulta = "Call PROC_obtenerprecios(?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(consulta);
			ps.setString(1, vehiculo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				precios = new PreciosModelo(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}

		return precios;
	}
	
	@FXML
    void Activarprecio(ActionEvent event) {
		if (Lotro.isSelected()) {
			txtpreciootro.setDisable(false);
			txtpreciootro.setText("");
		}
		else {
			txtpreciootro.setText("Precio $");
			txtpreciootro.setDisable(true);
		}
		
		
    }
	boolean VerificarOtro() {
		boolean cond = false;
		
		if (Lotro.isSelected()) {
			try {
				Integer.parseInt(txtpreciootro.getText());
				cond = true;
			} catch (Exception e) {
				cond = false;
			}
		}
		
		return cond;
		
	}

}
