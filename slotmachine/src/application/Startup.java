package application;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Startup extends Application {

	private Button spin;
	private Button addCoin;
	private Button betMax;
	private Button betOne;
	private Button reset;
	private Button statistics;
	private Button bet_Display;
	private Button credit_Display;
	private Label reel1;
	private Label reel2;
	private Label reel3;
	private Label betDisplay;
	private Label creditDisplay;
	private Label logo;
	private Image image;
	private ImageView imageView;

	private Thread t;
	private Thread t2;
	private Thread t3;

	private Reel r;
	private Reel r2;
	private Reel r3;

	private Stage window;

	private int bet_Max = 3;
	private int initial_Credits = 10;// Credits Given at Start of the Game.
	private int bet_One = 1;
	private int bet = 0; // Amount bet by player.

	// These three counts will be used to check if all reels have stopped spinning.
	private int count = 0;
	private int count2 = 0;
	private int count3 = 0;

	private int numberOfWins = 0;
	private int numberOfLosses = 0;
	private double creditsNetted = 0;// number of credits won per game.

	// program execution begins here
	public static void main(String[] args) {
		launch(args); // sets up program as JavaFx application
	}

	@Override
	// Overriding the start method in application class
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		window.setTitle(" SlotMachine ");

		BorderPane bd = new BorderPane();
		bd.setTop(addLogo());
		bd.setBottom(addButton());
		bd.setCenter(addImages());
		bd.getStyleClass().add("borderPane");

		Scene scene = new Scene(bd);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.setResizable(false);
		window.show();

	}

	// Method Which add user interacting buttons to GridPane and return GridPane.
	private GridPane addButton() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(50);
		grid.setHgap(50);

		// Spin Button
		spin = new Button();
		spin.setText("Spin");
		spin.setPrefWidth(100);
		spin.prefHeight(150);
		spin.setDisable(true);// Disabling the Spin Button
		spin.getStyleClass().add("userButtons");// Adding Style Sheet to Spin Button

		// setting an action listener to spin button.
		spin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				r = new Reel(reel1);
				r2 = new Reel(reel2);
				r3 = new Reel(reel3);

				t = new Thread(r);
				t2 = new Thread(r2);
				t3 = new Thread(r3);

				t.start(); // starting of the thread begins here.
				t2.start();
				t3.start();

				disableButton();// Once reels start spinning all the buttons will be disabled.

			}

		});

		GridPane.setConstraints(spin, 0, 0);// Setting spin button in first column and first row of the grid.

		// Add Coin Button
		addCoin = new Button();
		addCoin.setText("Add Coin");
		addCoin.setPrefWidth(100);
		addCoin.prefHeight(150);
		addCoin.getStyleClass().add("userButtons");// Adding a styles to addCoin button using Css.
		addCoin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				initial_Credits = initial_Credits + 1; // increase the initial credits by one.
				creditDisplay.setText("Credits:" + " " + "$" + initial_Credits);// display the credits onto the screen.
			}

		});

		GridPane.setConstraints(addCoin, 1, 0);

		// BetOne Button and its handling action events is done here.
		betOne = new Button();
		betOne.setText(" Bet One");
		betOne.setPrefWidth(100);
		betOne.prefHeight(150);
		betOne.getStyleClass().add("userButtons");
		betOne.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (initial_Credits > 0) {
					initial_Credits = initial_Credits - bet_One;
					bet = bet + bet_One;
					betDisplay.setText("Bet:" + " " + "$" + bet);
					creditDisplay.setText("Credits:" + " " + "$" + initial_Credits);

					spin.setDisable(false);

				} else {
					displayError("Out Of Coins", "Please Add Coins To Continue");
				}

			}
		});

		GridPane.setConstraints(betOne, 2, 0);

		// BetMax Button
		betMax = new Button();
		betMax.setText("Bet Max");
		betMax.setPrefWidth(100);
		betMax.prefHeight(150);
		betMax.getStyleClass().add("userButtons");
		betMax.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (initial_Credits > 3) { // U can only click this button if initial credits > 0
					initial_Credits = initial_Credits - bet_Max;
					bet = bet + bet_Max;
					betDisplay.setText("Bet:" + " " + "$" + bet);
					creditDisplay.setText("Credits:" + " " + "$" + initial_Credits);

					betMax.setDisable(true);// Disabling the betMax button so i can only be clicked once.
					spin.setDisable(false);// Activating the spin button.

				} else {
					displayError("Insufficient Credits", "Your Credits Are Below $3. Please Add Coins To Contiue");// Displaying
																													// error
																													// if
																													// credit
																													// <
																													// 3
				}
			}
		});

		GridPane.setConstraints(betMax, 0, 1);

		// Reset Button
		reset = new Button();
		reset.setText("Reset");
		reset.setPrefWidth(100);
		reset.prefHeight(150);
		reset.getStyleClass().add("userButtons");
		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (bet > 0) {
					initial_Credits = initial_Credits + bet;
					bet = bet - bet;

					betDisplay.setText("Bet:" + " " + "$" + bet);
					creditDisplay.setText("Credits:" + " " + "$" + initial_Credits);

					spin.setDisable(true);// disabling spin button
					betMax.setDisable(false);// activating betMax button
				}
			}

		});

		GridPane.setConstraints(reset, 1, 1);

		// Statistics Button
		statistics = new Button();
		statistics.setText("Statistics");
		statistics.setPrefWidth(100);
		statistics.prefHeight(150);
		statistics.getStyleClass().add("userButtons");
		statistics.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (numberOfWins > 0 || numberOfLosses > 0) { //
					Statistics stat = new Statistics(numberOfWins, numberOfLosses, creditNetted());
					stat.getStatistics();
				} else {
					displayError("No Statistics Available", "You Haven't Played Single Game."); // Display error message
																								// if game is not played
																								// at least once
				}
			}

		});

		GridPane.setConstraints(statistics, 2, 1);

		grid.getChildren().addAll(spin, addCoin, betOne, betMax, reset, statistics);
		grid.setAlignment(Pos.CENTER);
		return grid;

	}

	// This method will add the Reels to a GridPane and return it.
	private GridPane addImages() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(30);
		FileInputStream fs1;

		// setting image to first reel
		try {

			fs1 = new FileInputStream("Images\\cherry.png");
			image = new Image(fs1);
			imageView = new ImageView(image);
			imageView.setFitWidth(200);
			imageView.setFitHeight(300);
			reel1 = new Label("");
			reel1.setGraphic(imageView);
			reel1.getStyleClass().add("reels");
			reel1.setOnMouseClicked(new EventHandler<MouseEvent>() { // Method to stop reel1 by clicking on it.
				@SuppressWarnings("deprecation")
				@Override
				public void handle(MouseEvent event) {
					try {
						if ((t.isAlive())) {// check wether thread is running
							count++;
							t.stop();
							if (count > 0 && count2 > 0 && count3 > 0) {// if all counts are greater zero that means
																		// reel has been clicked at leastOnce.
								
								creditsWon(); // Method to check number of credits player has won or loss.
								winCheck();	 // Check if the player has won the game.
								enableButton(); //enabling all buttons except spin after three reels have stopped spinning.
								creditsNetted();//calculating credits netted per game.
								count = 0;
								count2 = 0;
								count3 = 0;
							}

						}
					} catch (NullPointerException e) {
						displayError("Please Spin the Reels!", "You haven't Spun The Reels.Add Coin Click Spin Button");
					}
				}

			});
			GridPane.setConstraints(reel1, 0, 0);

			fs1 = new FileInputStream("Images\\lemon.png");
			image = new Image(fs1);
			imageView = new ImageView(image);
			imageView.setFitWidth(200);
			imageView.setFitHeight(300);
			reel2 = new Label("", imageView);
			reel2.getStyleClass().add("reels");
			reel2.setOnMouseClicked(new EventHandler<MouseEvent>() { // Method to stop reel2 by clicking on it.

				@SuppressWarnings("deprecation")
				@Override
				public void handle(MouseEvent event) {
					try {
						if ((t2.isAlive())) {
							count2++;
							t2.stop();
							if (count > 0 && count2 > 0 && count3 > 0) {
								creditsWon();
								winCheck();
								enableButton();
								creditsNetted();
								count = 0;
								count2 = 0;
								count3 = 0;
							}

						}
					} catch (NullPointerException e) {
						displayError("Please Spin the Reels!", "You haven't Spun The Reels.Add Coin Click Spin Button");
					}
				}

			});

			GridPane.setConstraints(reel2, 1, 0);

			fs1 = new FileInputStream("Images\\plum.png");
			image = new Image(fs1);
			imageView = new ImageView(image);
			imageView.setFitWidth(200);
			imageView.setFitHeight(300);
			reel3 = new Label("", imageView);
			reel3.getStyleClass().add("reels");
			reel3.setOnMouseClicked(new EventHandler<MouseEvent>() { // Method to stop reel3 by clicking on it.

				@SuppressWarnings("deprecation")
				@Override
				public void handle(MouseEvent event) {

					try {
						if ((t3.isAlive())) {
							count3++;
							t3.stop();
							if (count > 0 && count2 > 0 && count3 > 0) {
								creditsWon();
								winCheck();
								enableButton();
								creditsNetted();
								count = 0;
								count2 = 0;
								count3 = 0;
							}

						}

					} catch (NullPointerException e) {
						displayError("Please Spin the Reels!", "You haven't Spun The Reels.Add Coin Click Spin Button");
					}
				}
			});

			GridPane.setConstraints(reel3, 2, 0);

			grid.getChildren().addAll(reel1, reel2, reel3);
			grid.setAlignment(Pos.CENTER);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return grid;
	}

	// Method Display Error Alerts.
	private void displayError(String error, String errorCause) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("SlotMachine");
		alert.setHeaderText(error);
		alert.setContentText(errorCause);
		alert.showAndWait();
	}

	// Method to Display Information Alerts
	private void displayInfo(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("SlotMachine");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// This method returns a boolean value if all the three or two reels are same
	private boolean reelsSame() {
		if (r.getRandomUrl().equals(r2.getRandomUrl()) || r.getRandomUrl().equals(r3.getRandomUrl())
				|| r2.getRandomUrl().equals(r.getRandomUrl()) || r2.getRandomUrl().equals(r3.getRandomUrl())
				|| r3.getRandomUrl().equals(r.getRandomUrl()) || r3.getRandomUrl().equals(r2.getRandomUrl())) {
			return true;
		} else {
			return false;
		}
	}

	// Method Check if Player Has Worn
	private void winCheck() {
		if (reelsSame()) {
			displayInfo("JACKPOT!! YOU WON");
		} else {
			displayError("YOU LOSE!", "Reels Don't Match");
		}
	}

	// Method to check number of credits player has won or loss.
	private void creditsWon() {
		if (reelsSame()) {

			numberOfWins++;
			initial_Credits = initial_Credits + creditWin();
			bet = 0;
			creditDisplay.setText("Credits:" + " " + "$" + initial_Credits);
			betDisplay.setText("Bet:" + " " + "$" + bet);

		} else {
			numberOfLosses++;
			bet = 0;
			creditDisplay.setText("Credits:" + " " + "$" + initial_Credits);
			betDisplay.setText("Bet:" + " " + "$" + bet);
		}

	}

	// Method returning a reel Object if only two objects match
	private Reel getMatchReel() {
		if (r.getRandomUrl().equals(r2.getRandomUrl()) || r.getRandomUrl().equals(r3.getRandomUrl())) {
			return r;
		} else if (r2.getRandomUrl().equals(r.getRandomUrl()) || r2.getRandomUrl().equals(r3.getRandomUrl())) {
			return r2;
		} else {
			return r3;
		}

	}

	// Method returning credits won according to reels matched.
	private int creditWin() {

		int creditsWon = 0;

		if (r.getRandomUrl().equals(r2.getRandomUrl()) && r.getRandomUrl().equals(r3.getRandomUrl())) {// if all three reels matched
			for (Symbol symbol : r.spin()) {
				if (symbol.getImage().equals(r.getRandomUrl())) {
					return creditsWon = symbol.getValue() * 3;
				}
			}
		} else {
			for (Symbol symbol : r.spin()) {// if only two reels matched.
				if (symbol.getImage().equals(getMatchReel().getRandomUrl())) {
					return creditsWon = symbol.getValue() * 2;
				}
			}
		}
		return creditsWon;
	}

	// Method Disable All Buttons
	private void disableButton() {
		spin.setDisable(true);
		addCoin.setDisable(true);
		betOne.setDisable(true);
		betMax.setDisable(true);
		reset.setDisable(true);
		statistics.setDisable(true);

	}

	// Method to Activate all buttons except Spin
	private void enableButton() {

		addCoin.setDisable(false);
		betOne.setDisable(false);
		betMax.setDisable(false);
		reset.setDisable(false);
		statistics.setDisable(false);

	}

	// returning creditsNetted per game as a String
	private String creditNetted() {
		return String.format("%.2f", creditsNetted);
	}

	// Method to calculate creditsNetted
	private void creditsNetted() {
		if (reelsSame()) {
			creditsNetted = (initial_Credits - 10) / (numberOfWins);
		}
	}

	// Method returns a gridPane which contains logo, bet label and credits label.
	private GridPane addLogo() {

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(30);

		try {//commands are executed inside a try block.
			FileInputStream fs1 = new FileInputStream("logo\\logo.png");
			image = new Image(fs1);
			imageView = new ImageView(image);
			logo = new Label();
			logo.setGraphic(imageView);
			logo.getStyleClass().add("reels");
			GridPane.setConstraints(logo, 1, 0);

			betDisplay = new Label();
			betDisplay.setText("Bet:" + " " + "$" + bet);
			betDisplay.getStyleClass().add("labelButton");

			bet_Display = new Button();
			bet_Display.setGraphic(betDisplay);
			bet_Display.setPrefWidth(200);
			bet_Display.prefHeight(200);
			bet_Display.getStyleClass().add("transparetButtons");
			bet_Display.setMouseTransparent(true);
			GridPane.setConstraints(bet_Display, 0, 0);

			creditDisplay = new Label();
			creditDisplay.setText("Credits:" + " " + "$" + initial_Credits);
			creditDisplay.getStyleClass().add("labelButton");

			credit_Display = new Button();
			credit_Display.setGraphic(creditDisplay);
			credit_Display.setPrefWidth(200);
			credit_Display.prefHeight(200);
			credit_Display.getStyleClass().add("transparetButtons");
			credit_Display.setMouseTransparent(true);
			GridPane.setConstraints(credit_Display, 2, 0);

			grid.getChildren().addAll(bet_Display, credit_Display, logo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return grid;

	}

}
