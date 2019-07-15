package code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application{

	public static Stage aStage = null;
	
	@Override
	  public void start(Stage stage) throws Exception {
		stage.initStyle(StageStyle.UNDECORATED);
	    Parent root = FXMLLoader.load(getClass().getResource("/visualStuff.fxml"));
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.setTitle("Tumblr Image Scraper");
	    stage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
