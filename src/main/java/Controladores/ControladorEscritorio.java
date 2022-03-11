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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import org.openjfx.AutolavadoFX.Reloj;
import org.openjfx.AutolavadoFX.lavados_diarios;

import Modelo.FacturaLavador;
import Modelo.ModeloConexion;
import Modelo.ModeloInsertaLavado;
import Modelo.ModeloLogginAdm;
import Modelo.PreciosModelo;
import Modelo.ModeloImpresion;
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

			Double total = GestionaTotal(vehiculo);
			objlavado = new lavados_diarios(vehiculo, Marca.toUpperCase(), codigolavador, cantidadlavados, total.intValue(), "",
					placa);
			new ModeloInsertaLavado(objlavado);
			
			Impresora(placa.toUpperCase(),vehiculo, descripcionimpresora(arraylavado), ControladorLoggin.nombre_usuario, total,codigolavador);

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

	Double GestionaTotal(String VEHICULO) {

		Double total = 00.0;
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
				System.out.println(Integer.parseInt(txtpreciootro.getText()));
				
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
			}
		}
		
		return cond;
		
	}
	
	@FXML
	void Accionimprimir(ActionEvent event) {
		
		if (VerificarOtro()) {
			Detalles_Lavado();
			mostrandolavadosdiarios();
		}else if (Lotro.isSelected()==false) {
			Detalles_Lavado();
			mostrandolavadosdiarios();
		}
		else {
			VentanaEmergente.AvisoEmergente("El precio del tipo de lavado debe ser numerico.");
		}
		
		
	}
	
	void Impresora(String placa,String Vehiculo,String descripcion,String cajero ,double monto,String codlavador) {
		
		 String hora = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
		 String fecha = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		 //StringBuilder builder = new StringBuilder("             YONBER CARWASH\r\n");
		 StringBuilder builder = new StringBuilder("");
		 int cantd =1;
		 ModeloImpresion impresion = new ModeloImpresion(String.valueOf(cantd) , descripcion, cajero);
		
			// Message
		 	builder.append("   AV Hermanas Mirabal 101, Villa Mella\r\n");
		 	builder.append("            Santo Domingo Norte\r\n");
		 	builder.append("                829-988-4791\r\n");
		    builder.append("Fecha: "+fecha+"\r\n");
		    builder.append("Hora:  "+hora+"\r\n");
		    builder.append("Lavador No. "+codlavador+"\r\n");
		    builder.append("Placa: "+placa+"\r\n");
		    builder.append("Vehiculo: "+Vehiculo+"\r\n");
		    builder.append("------------------------------------------\r\n");
		    builder.append("CANT  DESCRIPCION               MONTO\r\n");
		    builder.append("------------------------------------------\r\n");
			builder.append(impresion.getCantidad()).append("").append(impresion.getDescripcion()).append("$").append(monto).append("\r\n");
			builder.append("\r\n");
			builder.append("------------------------------------------\r\n");
			builder.append("Total a pagar:                  $"+monto).append("\r\n");
			builder.append("------------------------------------------\r\n");
			builder.append("------------------------------------------\r\n");
			builder.append("Gracias por confiar en las mejores manos.\r\n");
			builder.append("Cajer@ "+cajero+" \r\n");
			builder.append(" \r\n");
			builder.append(" \r\n");
			builder.append(" \r\n");
			builder.append(" \r\n");
			
			//CORTAR EL PAPEL
			char[] ESC_CUT_PAPER = new char[]{0x1B, 'm'};
			builder.append(ESC_CUT_PAPER);
			
		String texto = builder.toString(); 
		System.out.println(texto);
		PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
		DocPrintJob docPrintJob = printService.createPrintJob();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(texto.getBytes(), flavor, null);
		try {
			docPrintJob.print(doc, null);
			Facturalavador();
		} catch (PrintException e) {
			e.printStackTrace();
		}
	}
	private String descripcionimpresora(CheckBox[] tipolavado) {
		String lavado = "";
			for (CheckBox a : tipolavado) {
				if (a.isSelected()) {
					lavado += a.getText().substring(0,4) + " ";
				}
			}
			return lavado;
		}
	
	private void Facturalavador() {
		String sql = "CALL PROC_ultimolavado()";
		FacturaLavador fact = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				fact = new FacturaLavador(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
			}
			ImprimeFacturalavador(fact);
			
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		
	}
	private void ImprimeFacturalavador(FacturaLavador fact) {
		// Message
		StringBuilder builder = new StringBuilder("");
	    builder.append("------------------------------------------\r\n");
	    builder.append("DETALLES LAVADO\r\n");
	    builder.append("------------------------------------------\r\n");
		builder.append("Factura No. "+fact.getId()+"\r\n");
		builder.append("Lavador: "+fact.getNombre()+" "+fact.getApellido()+"\r\n");
		builder.append("Lavador No. "+fact.getCodigo()+"\r\n");
		builder.append("Marca: "+fact.getMarca()+"\r\n");
		builder.append("Lavados: "+fact.getTiposlavados()+"\r\n");
		builder.append("------------------------------------------\r\n");
		builder.append("Total a pagado:                  $"+fact.getTotal()).append("\r\n");
		builder.append("------------------------------------------\r\n");
		builder.append(" \r\n");
		builder.append(" \r\n");
		builder.append(" \r\n");
		builder.append(" \r\n");
		
		//CORTAR EL PAPEL
		char[] ESC_CUT_PAPER = new char[]{0x1B, 'm'};
		builder.append(ESC_CUT_PAPER);
		
	String texto = builder.toString(); 
	System.out.println(texto);
	PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
	DocPrintJob docPrintJob = printService.createPrintJob();
	DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	Doc doc = new SimpleDoc(texto.getBytes(), flavor, null);
	try {
		docPrintJob.print(doc, null);
	} catch (PrintException e) {
		e.printStackTrace();
	}
	}

}
