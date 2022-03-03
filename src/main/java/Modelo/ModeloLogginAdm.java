package Modelo;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ModeloLogginAdm {

	public ModeloLogginAdm() {
		
		primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/org/openjfx/AutolavadoFX/LogginAdmin.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Stage primaryStage;

}
