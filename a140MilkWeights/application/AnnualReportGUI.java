package application;

import java.util.ArrayList;

import application.FarmReportGUI.AddPurchaseHandler;
import application.FarmReportGUI.RemovePurchaseHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
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

//		// get farm info from database
//		ArrayList<Farm> allFarms = new ArrayList<Farm>(db.allFarms);
//		Farm thisFarm = new Farm();
//		for (Farm f : allFarms) {
//			if (f.farmID == farmID) {
//				thisFarm = f;
//			}
//		}
//
//		// create HBox and label to display total milk weight
//		HBox totalMilkBox = new HBox();
//		Label totalMilkLabel = new Label("Total Milk Weight: " + thisFarm.getFarmWeight() + " pounds purchased");
//		totalMilkLabel.setFont(new Font(FONT, 24));
//		totalMilkBox.getChildren().addAll(totalMilkLabel);
//
//		// create new Hbox to hold percent by month, all purchases, and add purchase
//		// section
//		HBox purchasesBox = new HBox();
//
//		// crate VBox to hold percent by month
//		VBox pByMonthBox = new VBox();
//
//		Label pByMonthLabel = new Label("Percent Weight by Month");
//		pByMonthLabel.setAlignment(Pos.CENTER);
//		pByMonthLabel.setFont(new Font(FONT, 15));
//
//		TableView pByMonthTable = generatePByMonthTable(thisFarm.getFarmWeight(), thisFarm);
//
//		pByMonthBox.getChildren().addAll(pByMonthLabel, pByMonthTable);
//
//		// crate VBox to hold all purchases table
//		VBox allPurchasesBox = new VBox();
//
//		// create a label to go above the all purchases table
//		Label allPurchasesLabel = new Label("All Purchases");
//		allPurchasesLabel.setAlignment(Pos.CENTER);
//		allPurchasesLabel.setFont(new Font(FONT, 15));
//
//		// call generateAllPurchasesTable() to create and load the atble view
//		TableView allPurchasesTable = generateAllPurchasesTable(thisFarm);
//
//		// create selection model to delete purchase
//		TableViewSelectionModel<Purchase> purchaseSelection = allPurchasesTable.getSelectionModel();
//		purchaseSelection.setSelectionMode(SelectionMode.SINGLE);
//		ObservableList<Purchase> selectedPurchase = purchaseSelection.getSelectedItems();
//
//		Button removeSelectedPurchase = new Button("Remove Selected Purchase");
//		removeSelectedPurchase.setOnAction(new RemovePurchaseHandler(selectedPurchase, thisFarm));
//
//		// add the label and table to the VBox to hold the allPurchases table
//		allPurchasesBox.getChildren().addAll(allPurchasesLabel, allPurchasesTable, removeSelectedPurchase);
//
//		// create a vBox to hold the add Purchase UI Elements
//		VBox addPurchaseBox = getAddPurchaseUI();
//
//		// get the button to add purchase out of the VBox
//		Button addPurchaseButton = (Button) addPurchaseBox.getChildren().get(3);
//
//		// event handler to add purchase
//		addPurchaseButton.setOnAction(new AddPurchaseHandler(addPurchaseBox, thisFarm));
//
//		// add percent by month, all purchase, and add purchase sections to purchasesBox
//		purchasesBox.getChildren().addAll(pByMonthBox, allPurchasesBox, addPurchaseBox);
//
//		// add Hboxes to main Vbox
//		frBody.getChildren().addAll(totalMilkBox, purchasesBox);

		return frBody;
	}
}
