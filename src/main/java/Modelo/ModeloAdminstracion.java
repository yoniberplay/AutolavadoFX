package Modelo;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModeloAdminstracion {

	public ModeloAdminstracion() {
		primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/org/openjfx/AutolavadoFX/VistaAdmGeneral.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.setTitle("Administrar Sistema");
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Stage primaryStage;


}
