package application;

import java.util.ArrayList;
import java.util.HashSet;

import application.FarmReportGUI.AddPurchaseHandler;
import application.FarmReportGUI.RemovePurchaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AnnualReportGUI {
	private final Database db;
	private final String FONT;
	private final double WINDOW_WIDTH;
	private final double WINDOW_HEIGHT;
	private Stage primaryStage;

	public AnnualReportGUI(Database db, String FONT, double WINDOW_WIDTH, double WINDOW_HEIGHT) {
		this.db = db;
		this.FONT = FONT;
		this.WINDOW_HEIGHT = WINDOW_HEIGHT;
		this.WINDOW_WIDTH = WINDOW_WIDTH;
	}

	/**
	 * Create a VBox with the UI elements required for the user to generate a report
	 * for a specific year to be returned to the main UI
	 * 
	 * @param primaryStage
	 * @return VBOX with the UI elements required for the user to generate a report
	 *         for a specific year
	 */
	public VBox getAnnualReportVBox(Stage primaryStage) {

		// save primary stage to instance variable
		this.primaryStage = primaryStage;

		// Create a VBox for the farm report section of the home screen
		VBox annualReportvBox = new VBox();

		// create a label for this section of the home screen
		Label annualReportTitle = new Label("Generate Annual Report");

		// create an HBox for the input required to generate the annual report
		HBox yearInput = new HBox();
		yearInput.setAlignment(Pos.CENTER);

		// create a textField for the user to input theyear
		TextField yearField = new TextField();
		yearField.setPromptText("Year");
		yearField.setMaxWidth(75);

		// create a button to trigger generating the report
		Button annualReportButton = new Button("Generate Annual Report");
		annualReportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					// get the farmID from the textField
					int year = Integer.parseInt(yearField.getText());
					if (db.hasYear(year)) {
						// call method to switch the scene and generate the report
						generateAnnualReport(db, year, primaryStage);
					} else {
						Alert noFarmAlert = new Alert(Alert.AlertType.ERROR);
						noFarmAlert.setHeaderText("Year Not Found");
						noFarmAlert.setContentText("There is data for " + year + " in this database.");
						noFarmAlert.show();
					}
				} catch (NumberFormatException nfe) {
					// if the user doesn't enter an int, show alert
					Alert noFarmAlert = new Alert(Alert.AlertType.ERROR);
					noFarmAlert.setHeaderText("Year Format Error");
					noFarmAlert.setContentText("Year must be input as an integer only");
					noFarmAlert.show();
				}
			}
		});

		// add UI elemetns to HBOX for generating a Farm Report
		yearInput.getChildren().addAll(yearField, annualReportButton);

		// add UI elements to VBox for farm report section of the home screen
		annualReportvBox.getChildren().addAll(annualReportTitle, yearInput);

		return annualReportvBox;

	}

	/**
	 * Switches the scene to the yearReport and generates the report
	 * 
	 * @param db           is the database
	 * @param year         is the year to generate an annual report for
	 * @param primaryStage the primaryStage of the UI
	 */
	private void generateAnnualReport(Database db, int year, Stage primaryStage) {
		// save primary stage attributes to go back
		Scene homeScene = primaryStage.getScene();
		String homeTitle = primaryStage.getTitle();

		// update title
		primaryStage.setTitle("Year " + year + " Report");

		// create a new borderpane for this "new" scene
		BorderPane annualReportBP = new BorderPane();

		// create an Hbox to hold the home button and the top text
		HBox topHBox = new HBox();

		// create a home button
		Button homeButton = new Button("Home");
		homeButton.setAlignment(Pos.CENTER_LEFT);
		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// restore primaryStage scene and title of home
				primaryStage.setScene(homeScene);
				primaryStage.setTitle(homeTitle);
			}

		});

		// create a label for the top text
		Label reportLabel = new Label("Year " + year + " Report");
		reportLabel.setFont(new Font(FONT, 36));
		reportLabel.setAlignment(Pos.CENTER);

		// add home button and top text to topHBox
		topHBox.getChildren().addAll(homeButton, reportLabel);

		// add UI elements to border pane
		annualReportBP.setTop(topHBox);
		annualReportBP.setCenter(annualReportBody(db, year));

		// update primaryStage's scene for this view
		primaryStage.setScene(new Scene(annualReportBP, WINDOW_WIDTH, WINDOW_HEIGHT));
	}

	/**
	 * Generates a VBox to use as the body of the farmReport screen. This method
	 * also generates the report from the database
	 * 
	 * @param db     is the datbase
	 * @param farmID is the ID of the farm to generate the report for
	 * @return VBox with the body of the farmReport Screen
	 */
	private VBox annualReportBody(Database db, int year) {
		// create VBox to return as the body of the farm Report
		VBox frBody = new VBox();

		// get farm info from database
		ArrayList<Farm> allFarms = new ArrayList<Farm>(db.allFarms);

		// get purchases weight from this year
		HashSet<Purchase> purchases = db.getPurchasesInRange(new Date(year, 1, 0), new Date(year, 12, 32));

		// get total milk weight this year
		int weight = 0;
		for (Purchase p : purchases) {
			weight += p.getWeight();
		}

		// create HBox and label to display total milk weight
		HBox totalMilkBox = new HBox();
		Label totalMilkLabel = new Label("Total Milk Weight: " + weight + " pounds purchased");
		totalMilkLabel.setFont(new Font(FONT, 24));
		totalMilkBox.getChildren().addAll(totalMilkLabel);

		// create new Hbox to hold percent by farm
		HBox purchasesBox = new HBox();

		// crate VBox to hold percent by month
		VBox pByFarmBox = new VBox();

		Label pByFarmLabel = new Label("Percent Weight by Farm");
		pByFarmLabel.setAlignment(Pos.CENTER);
		pByFarmLabel.setFont(new Font(FONT, 15));

		TableView pByFarmTable = generatePByFarmTable(weight, allFarms, year);

		pByFarmBox.getChildren().addAll(pByFarmLabel, pByFarmTable);

		// add percent by month, all purchase, and add purchase sections to purchasesBox
		purchasesBox.getChildren().addAll(pByFarmBox);

		// add Hboxes to main Vbox
		frBody.getChildren().addAll(totalMilkBox, purchasesBox);

		return frBody;
	}

	private TableView generatePByFarmTable(int weight, ArrayList<Farm> allFarms, int year) {
		ArrayList<farmPercent> list = new ArrayList<farmPercent>();

		for (Farm f : allFarms) {
			list.add(new farmPercent(f, weight, year));
		}

		ObservableList<farmPercent> data = FXCollections.observableList(list);

		TableView<farmPercent> table = new TableView<farmPercent>();

		table.getItems().addAll(data);
		table.getColumns().addAll(getFarmIDColumn(), getFarmPercentColumn(), getFarmWeightColumn());

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return table;
	}

	// Returns FarmID column for annual report Table
	public static TableColumn<farmPercent, Integer> getFarmIDColumn() {
		TableColumn<farmPercent, Integer> IDCol = new TableColumn<>("FarmID");
		PropertyValueFactory<farmPercent, Integer> IDCellValueFactory = new PropertyValueFactory<>("ID");
		IDCol.setCellValueFactory(IDCellValueFactory);
		return IDCol;
	}

	// Returns percent column for annual report Table
	public static TableColumn<farmPercent, String> getFarmPercentColumn() {
		TableColumn<farmPercent, String> PercentCol = new TableColumn<>("Percent Sold");
		PropertyValueFactory<farmPercent, String> PercentCellValueFactory = new PropertyValueFactory<>("Percent");
		PercentCol.setCellValueFactory(PercentCellValueFactory);
		return PercentCol;
	}

	// Returns weight column for annual report Table
	public static TableColumn<farmPercent, String> getFarmWeightColumn() {
		TableColumn<farmPercent, String> weightCol = new TableColumn<>("Weight Sold");
		PropertyValueFactory<farmPercent, String> weightCellValueFactory = new PropertyValueFactory<>("farmWeight");
		weightCol.setCellValueFactory(weightCellValueFactory);
		return weightCol;
	}
}
