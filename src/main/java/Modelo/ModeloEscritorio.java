package Modelo;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModeloEscritorio {
	
	public ModeloEscritorio() {
		
		primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/org/openjfx/AutolavadoFX/VistaEscritorio.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private Stage primaryStage;
	
}
