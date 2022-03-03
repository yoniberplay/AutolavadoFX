package VentaEmergenteJFX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaSalir{

	public static boolean AvisoEmergente() {
		escenario = new Stage();
		escenario.initModality(Modality.APPLICATION_MODAL);
		escenario.setTitle("Salir");
		escenario.setMinWidth(250);
		//escenario.setMinHeight(100);
		
		Label etiqueta = new Label();
		etiqueta.setFont(new Font("Arial Black", 14));
		etiqueta.setText("Estas seguro que deseas, salir?");
		Button btnsi = new Button("Si");
		btnsi.setFont(new Font("Arial Black", 14));
		btnsi.setOnAction(e->Btnsi());
		Button btnno = new Button("No");
		btnno.setFont(new Font("Arial Black", 14));
		btnno.setOnAction(e->Btnno());
		
		HBox pane = new HBox(20);
		pane.getChildren().addAll(btnsi,btnno);
		pane.setAlignment(Pos.CENTER);
		
		//btn.setOnAction(e->escenario.close());
		
		BorderPane lamina = new BorderPane();
		Scene escena = new Scene(lamina, 250, 100);
		lamina.setTop(etiqueta);
		lamina.setCenter(pane);
		escenario.setScene(escena);
		escenario.showAndWait();
		
		return condicion;
	}

	private static void Btnsi() {
		escenario.close();
		condicion=true;
	}
	private static void Btnno() {
		condicion=false;
		escenario.close();
		
	}
	private static Stage escenario;
	private static boolean condicion;

}
