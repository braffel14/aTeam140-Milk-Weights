package application;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 700;
	private static final int WINDOW_HEIGHT = 500;
	private static final String APP_TITLE = "Hello World!";
	private static final String FONT = "Arial";

	Database db = new Database();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

//		for (int m = 1; m <= 12; m++) {
//			db.parseInput(
//					"/Users/benjaminraffel/Documents/MilkWeightsProject/a140MilkWeights/csv/small/2019-" + m + ".csv");
//		}

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the vertical box to the center of the root pane
		HBox topLabelBox = new HBox();
		topLabelBox.setAlignment(Pos.CENTER);
		Label topLabel = new Label("Cheese Factory");
		topLabel.setFont(new Font(FONT, 36));
		topLabelBox.getChildren().addAll(topLabel);

		// create FarmReport object to use on home page as well as generate and show a
		// farm Report window
		FarmReportGUI farmReportUI = new FarmReportGUI(db, FONT, WINDOW_WIDTH, WINDOW_HEIGHT);

		// create a VBx for the UI elements for generating a farm report
		VBox farmReportVBox = farmReportUI.getFarmReportVBox(primaryStage);
		farmReportVBox.setAlignment(Pos.CENTER);

		// Creat a HBox for the UI elemetns for importing and saving data files
		HBox dataIOHBox = new HBox();

		// Create a HBox for the UI elements for importing a data file
		HBox importDataHBox = new HBox();

		// Create a file chooser to get the data files
		FileChooser importFileChooser = new FileChooser();
		importFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

		// create a button to import data files
		Button importFileButton = new Button("Import Data From CSV");
		importFileButton.setOnAction(new ParseFileHandler(db, importFileChooser, primaryStage));

		// save UI elements for importing data to their HBOX
		importDataHBox.getChildren().addAll(importFileButton);

		// creat an HBox to hold UI elements to export data
		HBox exportDataHBox = new HBox();

		// create a button to Export data to CSV
		Button exportDataButton = new Button("Export Data to CSV");
		exportDataButton.setOnAction(new ExportCSVHandler(db, primaryStage));

		// save UI elemtns for exporting data to exportDataHBox
		exportDataHBox.getChildren().addAll(exportDataButton);

		// save HBoxes for importing and exporting data to their parent HBoxes
		dataIOHBox.getChildren().addAll(importDataHBox, exportDataHBox);

		root.setTop(topLabelBox);
		root.setCenter(farmReportVBox);
		root.setBottom(dataIOHBox);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * Create event handler to open one or more csv file and import them to the
	 * database
	 * 
	 * @author benjaminraffel
	 *
	 */
	class ParseFileHandler implements EventHandler<ActionEvent> {

		Database db;
		FileChooser fc;
		Stage primaryStage;

		// constructor
		ParseFileHandler(Database db, FileChooser fc, Stage primaryStage) {
			this.db = db;
			this.fc = fc;
			this.primaryStage = primaryStage;
		}

		@Override
		public void handle(ActionEvent arg0) {
			List<File> fileList = fc.showOpenMultipleDialog(primaryStage);
			if (fileList != null) {
				for (File file : fileList) {
					db.importData(file);
				}
			}

		}
	}

	/**
	 * Create event handler to export all data to a csv file
	 * 
	 * @author benjaminraffel
	 *
	 */
	class ExportCSVHandler implements EventHandler<ActionEvent> {

		Database db;
		Stage primaryStage;

		// constructor
		ExportCSVHandler(Database db, Stage primaryStage) {
			this.db = db;
			this.primaryStage = primaryStage;
		}

		@Override
		public void handle(ActionEvent arg0) {
			db.saveDB(primaryStage);
		}

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