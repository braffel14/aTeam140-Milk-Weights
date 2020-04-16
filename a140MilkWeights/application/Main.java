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

		VBox farmReportVBox = getFarmReportVBox(primaryStage);
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
		Label farmReportTitle = new Label("Generate Farm Report");

		// create an HBox for the input required to generate the farm report
		HBox farmIDInput = new HBox();
		farmIDInput.setAlignment(Pos.CENTER);

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
		primaryStage.setTitle("Farm " + farmID + " Report");

		// create a new borderpane for this "new" scene
		BorderPane farmReportBP = new BorderPane();

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
		Label reportLabel = new Label("Farm " + farmID + " Report");
		reportLabel.setFont(new Font(FONT, 36));
		reportLabel.setAlignment(Pos.CENTER);

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
	private VBox farmReportBody(Database db, int farmID) {
		// create VBox to return as the body of the farm Report
		VBox frBody = new VBox();

		// get farm info from database
		ArrayList<Farm> allFarms = new ArrayList<Farm>(db.allFarms);
		Farm thisFarm = new Farm();
		for (Farm f : allFarms) {
			if (f.farmID == farmID) {
				thisFarm = f;
			}
		}

		// create HBox and label to display total milk weight
		HBox totalMilkBox = new HBox();
		Label totalMilkLabel = new Label("Total Milk Weight: " + thisFarm.getFarmWeight() + " pounds purchased");
		totalMilkLabel.setFont(new Font(FONT, 24));
		totalMilkBox.getChildren().addAll(totalMilkLabel);

		// create new Hbox to hold percent by month, all purchases, and add purchase
		// section
		HBox purchasesBox = new HBox();

		// crate VBox to hold percent by month
		VBox pByMonthBox = new VBox();

		Label pByMonthLabel = new Label("Percent Weight by Month");
		pByMonthLabel.setAlignment(Pos.CENTER);
		pByMonthLabel.setFont(new Font(FONT, 15));

		TableView pByMonthTable = generatePByMonthTable(thisFarm.getFarmWeight(), thisFarm);

		pByMonthBox.getChildren().addAll(pByMonthLabel, pByMonthTable);

		// crate VBox to hold all purchases table
		VBox allPurchasesBox = new VBox();

		// create a label to go above the all purchases table
		Label allPurchasesLabel = new Label("All Purchases");
		allPurchasesLabel.setAlignment(Pos.CENTER);
		allPurchasesLabel.setFont(new Font(FONT, 15));

		// call generateAllPurchasesTable() to create and load the atble view
		TableView allPurchasesTable = generateAllPurchasesTable(thisFarm);

		// add the label and table to the VBox to hold the allPurchases table
		allPurchasesBox.getChildren().addAll(allPurchasesLabel, allPurchasesTable);

		// create a vBox to hold the add Purchase UI Elements
		VBox addPurchaseBox = getAddPurchaseUI();

		// get the button to add purchase out of the VBox
		Button addPurchaseButton = (Button) addPurchaseBox.getChildren().get(3);

		// event handler to add purchase
		addPurchaseButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				HBox dateBox = (HBox) addPurchaseBox.getChildren().get(1);
				DatePicker datePicker = (DatePicker) dateBox.getChildren().get(1);

				HBox weightBox = (HBox) addPurchaseBox.getChildren().get(2);
				TextField weightField = (TextField) weightBox.getChildren().get(1);

				LocalDate localDate = datePicker.getValue();
				Date date = convertDate(localDate);

				try {
					int weight = Integer.parseInt(weightField.getText());
					// thisFarm.addPurchase(date, weight);
					// TODO: fix this^
				} catch (NumberFormatException nfe) {
					Alert notIntAlert = new Alert(Alert.AlertType.ERROR);
					notIntAlert.setHeaderText("Weight Must be an Integer");
					notIntAlert.setContentText("You musgt enter a valid integer for weight.");
					notIntAlert.show();
				}

			}

		});

		// add percent by month, all purchase, and add purchase sections to purchasesBox
		purchasesBox.getChildren().addAll(pByMonthBox, allPurchasesBox, addPurchaseBox);

		// add Hboxes to main Vbox
		frBody.getChildren().addAll(totalMilkBox, purchasesBox);

		return frBody;
	}

	// convert a java LocalDate to my own Date object
	protected Date convertDate(LocalDate localDate) {
		return (new Date(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()));
	}

	/**
	 * Createa a VBox and all UI elements to add a new purchase to a farm
	 * 
	 * @return VBox with all UI elemetns to add a new parchase to a farm
	 */
	private VBox getAddPurchaseUI() {
		// Createa a VBox and all UI elements to add a new purchase to a farm
		VBox addPurchaseBox = new VBox();

		// create a label for the header of this area
		Label addPurchaseLabel = new Label("Add Purchase");
		addPurchaseLabel.setAlignment(Pos.CENTER);
		addPurchaseLabel.setFont(new Font(FONT, 15));

		// create a datePicker and label to get the purchase date and store in a Hbox
		DatePicker purchaseDate = new DatePicker();
		purchaseDate.setMaxWidth(110);
		Label purchaseDateLabel = new Label("Date: ");
		HBox dateBox = new HBox();
		dateBox.getChildren().addAll(purchaseDateLabel, purchaseDate);

		// create a textfield and label to get the weight of the purchase and store in
		// an HBox
		Label weightLabel = new Label("Weight: ");
		TextField weightField = new TextField();
		weightField.setPromptText("weight (LB)");
		weightField.setMaxWidth(90);
		HBox weightBox = new HBox();
		weightBox.getChildren().addAll(weightLabel, weightField);

		// create a button to get he purchase
		Button addPurchaseButton = new Button("Add");

		// store all UIelements in main AddPurchase HBox
		addPurchaseBox.getChildren().addAll(addPurchaseLabel, dateBox, weightBox, addPurchaseButton);

		return addPurchaseBox;

	}

	/*
	 * Generate TableView to display percent of sales by month for a farm
	 */
	private TableView generatePByMonthTable(int totalWeight, Farm thisFarm) {

		// generate values for table
		ArrayList<monthPercent> list = new ArrayList<monthPercent>();
		ArrayList<String> percents = thisFarm.getMonthPercentages();

		list.add(new monthPercent("January", percents.get(0)));
		list.add(new monthPercent("February", percents.get(1)));
		list.add(new monthPercent("March", percents.get(2)));
		list.add(new monthPercent("April", percents.get(3)));
		list.add(new monthPercent("May", percents.get(4)));
		list.add(new monthPercent("June", percents.get(5)));
		list.add(new monthPercent("July", percents.get(6)));
		list.add(new monthPercent("August", percents.get(7)));
		list.add(new monthPercent("September", percents.get(8)));
		list.add(new monthPercent("October", percents.get(9)));
		list.add(new monthPercent("November", percents.get(10)));
		list.add(new monthPercent("December", percents.get(11)));

		ObservableList<monthPercent> data = FXCollections.observableList(list);

		TableView<monthPercent> table = new TableView<monthPercent>();

		// Add rows to the TableView
		table.getItems().addAll(data);
		// Add columns to the TableView
		table.getColumns().addAll(getMonthColumn(), getPercentColumn());

		// Set the column resize policy to constrained resize policy
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return table;
	}

	/*
	 * Generate TableView to display percent of sales by month for a farm
	 */
	private TableView generateAllPurchasesTable(Farm thisFarm) {

		// generate values for table
		ArrayList<Purchase> purchases = new ArrayList<Purchase>(thisFarm.getAllPurchases());

		ObservableList<Purchase> data = FXCollections.observableList(purchases);

		TableView<Purchase> table = new TableView<Purchase>();

		// Add rows to the TableView
		table.getItems().addAll(data);
		// Add columns to the TableView
		table.getColumns().addAll(getDateColumn(), getWeightColumn());

		// Set the column resize policy to constrained resize policy
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return table;
	}

	// Returns Month column for percent by month table
	public static TableColumn<monthPercent, String> getMonthColumn() {
		TableColumn<monthPercent, String> monthCol = new TableColumn<>("Month");
		PropertyValueFactory<monthPercent, String> monthCellValueFactory = new PropertyValueFactory<>("month");
		monthCol.setCellValueFactory(monthCellValueFactory);
		return monthCol;
	}

	// Returns percentage column for percent by month table
	public static TableColumn<monthPercent, String> getPercentColumn() {
		TableColumn<monthPercent, String> percentCol = new TableColumn<>("Percent Milk Purchased");
		PropertyValueFactory<monthPercent, String> percentCellValueFactory = new PropertyValueFactory<>("percent");
		percentCol.setCellValueFactory(percentCellValueFactory);
		return percentCol;
	}

	// Returns date column for all purchases table
	public static TableColumn<Purchase, String> getDateColumn() {
		TableColumn<Purchase, String> dateCol = new TableColumn<>("Date");
		PropertyValueFactory<Purchase, String> dateCellValueFactory = new PropertyValueFactory<>("dateString");
		dateCol.setCellValueFactory(dateCellValueFactory);
		return dateCol;
	}

	// Returns weight column for all purchases table
	public static TableColumn<Purchase, String> getWeightColumn() {
		TableColumn<Purchase, String> weightCol = new TableColumn<>("Milk Purchased (Pounds)");
		PropertyValueFactory<Purchase, String> weightCellValueFactory = new PropertyValueFactory<>("weight");
		weightCol.setCellValueFactory(weightCellValueFactory);
		return weightCol;
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