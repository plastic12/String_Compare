package main;


import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) throws IOException {
		// Create a circle and set its properties
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Scene.fxml"));
		SceneController controller=loader.getController();
		Pane pane=loader.load();

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Compare String"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	/**
	 * The main method is only needed for the IDE with limited
	 * JavaFX support. Not needed for running from the command line.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	public static String select()
	{
		Stage stage=new Stage();
		FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file==null)
        		return "";
        return file.getAbsolutePath();
	}
}
