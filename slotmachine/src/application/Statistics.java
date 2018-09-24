package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Statistics {

	private int numberOfWins;
	private int numberOfLosses;
	private String creditsNetted;

	private Label wins;
	private Label loss;
	private Label nettedCredits;

	private Button save;
	private Button winsButton;
	private Button lossButton;
	private Button netted_Credits;

	// No Argument Constructor
	public Statistics() {
		super();
	}

	public Statistics(int numberOfWins, int numberOfLosses, String creditsNetted) {
		this.numberOfWins = numberOfWins;
		this.numberOfLosses = numberOfLosses;
		this.creditsNetted = creditsNetted;
	}

	//Method returns statistics within a borderPane
	public BorderPane getStatistics() {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);// this make sure that statistics window has to be closed to play the game again.
		window.setTitle("Game Statistics");

		BorderPane bd = new BorderPane();
		bd.setTop(pieChart());
		bd.setCenter(displayStats());
		bd.setBottom(saveButton());
		bd.getStyleClass().add("borderPane");

		Scene scene = new Scene(bd);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();

		return bd;

	}

	//This method return pieChart inside a GridPane
	private GridPane pieChart() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(50);
		grid.setHgap(50);

		PieChart piechart = new PieChart();

		PieChart.Data wins = new PieChart.Data("Wins", numberOfWins);
		PieChart.Data losses = new PieChart.Data("Losses", numberOfLosses);

		piechart.getData().add(wins);
		piechart.getData().add(losses);

		GridPane.setConstraints(piechart, 0, 0);
		grid.getChildren().addAll(piechart);
		grid.setAlignment(Pos.CENTER);

		return grid;

	}

	//This method return save Button inside a GridPane
	private GridPane saveButton() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(50);
		grid.setHgap(50);

		save = new Button();
		save.setText(" Save Statistics ");
		save.setPrefWidth(200);
		save.prefHeight(200);
		save.getStyleClass().add("saveButton");
		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Save saved = new Save(numberOfWins, numberOfLosses, creditsNetted);
				saved.save();
				displayInfo("Satistics Saved Sucessfully to File");
				save.setDisable(true);

			}

		});

		GridPane.setConstraints(save, 0, 0);
		grid.getChildren().addAll(save);
		grid.setAlignment(Pos.BASELINE_RIGHT);

		return grid;
	}

	//This Method shows the number losses and Wins.
	private GridPane displayStats() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(50);
		grid.setHgap(50);

		// Creating Label that display Number Of Wins and adding it into a button.
		wins = new Label();
		wins.setText("Number Of Wins:" + " " + numberOfWins);
		wins.getStyleClass().add("labelButton");
		
		winsButton = new Button();
		winsButton.setGraphic(wins);
		winsButton.setPrefWidth(200);
		winsButton.prefHeight(200);
		winsButton.getStyleClass().add("transparetButtons");
		winsButton.setMouseTransparent(true);
		GridPane.setConstraints(winsButton, 0, 0);

		// Creating Label that display Number of Losses and adding it into a button
		loss = new Label();
		loss.setText("Number Of Losses:" + " " + numberOfLosses);
		loss.getStyleClass().add("labelButton");
		
		lossButton = new Button();
		lossButton.setGraphic(loss);
		lossButton.setPrefWidth(200);
		lossButton.prefHeight(200);
		lossButton.getStyleClass().add("transparetButtons");
		lossButton.setMouseTransparent(true);
		GridPane.setConstraints(lossButton, 1, 0);

		// Creating Label that display CreditsNetted and adding it into a button
		nettedCredits = new Label();
		nettedCredits.setText("Credits Netted Per Game:" + " " + creditsNetted);
		nettedCredits.getStyleClass().add("labelButton");
		
		netted_Credits = new Button();
		netted_Credits.setGraphic(nettedCredits);
		netted_Credits.setPrefWidth(300);
		netted_Credits.prefHeight(300);
		netted_Credits.getStyleClass().add("transparetButtons");
		netted_Credits.setMouseTransparent(true);
		GridPane.setConstraints(netted_Credits, 0, 1);

		grid.getChildren().addAll(winsButton, lossButton, netted_Credits);
		grid.setAlignment(Pos.CENTER);

		return grid;

	}

	// Method to Display Information Alerts
	private void displayInfo(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("SlotMachine");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
}
