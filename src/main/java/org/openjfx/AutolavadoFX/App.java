package org.openjfx.AutolavadoFX;

import java.io.IOException;
import java.sql.Connection;
import Modelo.ModeloConexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class App extends Application {
	
	
	public App() {
		conexion = new ModeloConexion();
		con = conexion.getConexion();
		
	}

    private static Scene scene;
    private ModeloConexion conexion;    
	private Connection con;
	
	
    public void start(Stage primaryStage) throws IOException {
        
    	
        if (con!=null) {
			escenario=primaryStage;
			Parent root;
			Scene scene ;
			try {
				root = FXMLLoader.load(getClass().getResource("VistaLoggin.fxml"));
				scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.UNDECORATED);
				primaryStage.show();
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
    }
    
    public Stage getEscenario() {
		return escenario;
	}


	private Stage escenario;
    

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
	    
		launch(args);
	    //Aqui empieza todo el programa.
    	
		
    }
}
