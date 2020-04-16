package application;

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

		for (int m = 1; m <= 12; m++) {
			db.parseInput(
					"/Users/benjaminraffel/Documents/MilkWeightsProject/a140MilkWeights/csv/small/2019-" + m + ".csv");
		}

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the vertical box to the center of the root pane
		HBox topLabelBox = new HBox();
		topLabelBox.setAlignment(Pos.CENTER);
		Label topLabel = new Label("Cheese Factory");
		topLabel.setFont(new Font(FONT, 36));
		topLabelBox.getChildren().addAll(topLabel);

		//create FarmReport object to use on home page as well as generate and show a farm Report window
		FarmReport farmReportUI = new FarmReport(db, FONT, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		VBox farmReportVBox = farmReportUI.getFarmReportVBox(primaryStage);
		farmReportVBox.setAlignment(Pos.CENTER);

		root.setTop(topLabelBox);
		root.setCenter(farmReportVBox);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
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