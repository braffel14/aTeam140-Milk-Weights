package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 200;
	private static final String APP_TITLE = "Hello World!";

	Database db = new Database();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		db.parseInput("/Users/benjaminraffel/Documents/MilkWeightsProject/a140MilkWeights/csv/small/2019-1.csv");

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the vertical box to the center of the root pane
		root.setTop(new Label("Cheese Factory"));
		root.setCenter(getFarmReportVBox(primaryStage));
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * Create a VBox with the UI elements required for the user to generate a report
	 * for a specific farm
	 * 
	 * @param primaryStage
	 * @return VBOX with the UI elements required for the user to generate a report
	 *         for a specific farm
	 */
	private VBox getFarmReportVBox(Stage primaryStage) {

		// Create a VBox for the farm report section of the home screen
		VBox farmReportvBox = new VBox();

		// create a label for this section of the home screen
		Label farmReportTitle = new Label("Generate Farm Report!");

		// create an HBox for the input required to generate the farm report
		HBox farmIDInput = new HBox();

		// create a textField for the user toinput the farmID number
		TextField farmIDField = new TextField();
		farmIDField.setPromptText("FarmID");
		farmIDField.setMaxWidth(75);

		// create a label to appear if the user enters something other than an integer
		Label farmIDErrorLabel = new Label();

		// create a button to trigger generating the report
		Button farmReportButton = new Button("Get Farm Report");
		farmReportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					// get the farmID from the textField
					int farmID = Integer.parseInt(farmIDField.getText());
					if (db.hasFarm(farmID)) {
						// call method to switch the scene and generate the report
						generateFarmReport(db, farmID, primaryStage);
					} else {
						Alert noFarmAlert = new Alert(Alert.AlertType.ERROR);
						noFarmAlert.setHeaderText("Farm Not Found");
						noFarmAlert.setContentText("Farm " + farmID + " does not exist in this database");
						noFarmAlert.show();
					}
					// if the user enters a proper input, update the error label
					farmIDErrorLabel.setText("");
				} catch (NumberFormatException nfe) {
					// if the user doesn't enter an int, set the error label to let them know
					farmIDErrorLabel.setText("FarmID must be an integer only");
				}
			}
		});

		// add UI elemetns to HBOX for generating a Farm Report
		farmIDInput.getChildren().addAll(farmIDField, farmReportButton, farmIDErrorLabel);

		// add UI elements to VBox for farm report section of the home screen
		farmReportvBox.getChildren().addAll(farmReportTitle, farmIDInput);

		return farmReportvBox;

	}

	/**
	 * Switches the scene to the farmReport and generates the report
	 * 
	 * @param db           is the database
	 * @param farmID       is the ID of the farm to generate a report for
	 * @param primaryStage the primaryStage of the UI
	 */
	private void generateFarmReport(Database db, int farmID, Stage primaryStage) {
		// save primary stage attributes to go back
		Scene homeScene = primaryStage.getScene();
		String homeTitle = primaryStage.getTitle();

		// update title
		primaryStage.setTitle("Farm" + farmID + " Report");

		// create a new borderpane for this "new" scene
		BorderPane farmReportBP = new BorderPane();

		// create an Hbox to hold the home button and the top text
		HBox topHBox = new HBox();

		// create a home button
		Button homeButton = new Button("Home");
		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// restore primaryStage scene and title of home
				primaryStage.setScene(homeScene);
				primaryStage.setTitle(homeTitle);
			}

		});

		// create a label for the top text
		Label reportLabel = new Label("Farm" + farmID + " Report");

		// add home button and top text to topHBox
		topHBox.getChildren().addAll(homeButton, reportLabel);

		// add UI elements to border pane
		farmReportBP.setTop(topHBox);
		farmReportBP.setCenter(farmReportBody(db, farmID));

		// update primaryStage's scene for this view
		primaryStage.setScene(new Scene(farmReportBP, WINDOW_WIDTH, WINDOW_HEIGHT));
	}

	/**
	 * Generates a VBox to use as the body of the farmReport screen. This method
	 * also generates the report from the database
	 * 
	 * @param db     is the datbase
	 * @param farmID is the ID of the farm to generate the report for
	 * @return VBox with the body of the farmReport Screen
	 */
	private VBox farmReportBody(Database db2, int farmID) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * Test database
		 */

		// Database d = new Database();
		// d.parseInput("/Users/benjaminraffel/Documents/MilkWeightsProject/a140MilkWeights/csv/small/2019-1.csv");

		launch(args);

	}
}