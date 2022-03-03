module org.openjfx.AutolavadoFX {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	requires java.xml;
	requires javafx.graphics;
	requires javafx.base;
	requires de.jensd.fx.glyphs.fontawesome;
	requires com.jfoenix;

    opens org.openjfx.AutolavadoFX to javafx.fxml;
    opens Controladores to javafx.fxml;
    opens Modelo to javafx.fxml;
    
    exports org.openjfx.AutolavadoFX;
    exports Controladores;
    exports Modelo;
}
