package code;

import java.io.File;
//import java.util.Set;

import code.ScraperThread;
import code.App;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
//import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class Controller {
	
	@FXML
	private Button dirButton;
	
	@FXML
	private Label progBarLabel;
	
	@FXML
	private ProgressBar progBar;
	
	@FXML
	private AnchorPane anchor;
	
	@FXML
	private Button submitButton;
	
	@FXML
	private TextField tumblrURL;
	
	@FXML
	private TextField saveIn;
	
	@FXML 
	private Text textStuff;
	
	@FXML
	public void exit()
	{
		Platform.exit();
		System.exit(0);
		System.out.println("Memem");
	}
	

	
	
	@FXML
	protected void startScrape()
	{
		//System.out.println("Tumblr URL: " + tumblrURL.getText());
		//System.out.println("Save Dir: " + saveIn.getText());
		String deskPath = ((new File(System.getProperty("user.home"), "Desktop")).getAbsolutePath());
		String savePath = (saveIn.getText().equals("")) ? deskPath : saveIn.getText();
		ScraperThread toStop = new ScraperThread(tumblrURL.getText(), savePath);
		textStuff.textProperty().bind(toStop.stringProperty);
		progBar.progressProperty().bind(toStop.progressProperty);
		Thread t = new Thread(toStop);
		t.start();
		submitButton.setDisable(true);
		submitButton.setVisible(false);
		tumblrURL.setMouseTransparent(true);		
		saveIn.setText(savePath);		
		dirButton.setMouseTransparent(true);
		dirButton.setText("Saving to: ");	
		textStuff.setVisible(true);		
		progBar.setDisable(false);
		progBar.setVisible(true);	
	}

	@FXML
	protected void viewDir()
	{
	    DirectoryChooser directoryChooser = new DirectoryChooser();
	    directoryChooser.setTitle("Open Directory to Save Images");
	    File selectedFile = directoryChooser.showDialog(App.aStage);
	    saveIn.setText(selectedFile.getAbsolutePath());
	    System.out.println(selectedFile);
	}
		
	
}
