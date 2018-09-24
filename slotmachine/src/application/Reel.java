package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Reel implements Runnable {

	private FileInputStream fs;
	private Label reel;
	private ImageView imageView;
	private Image image;
	private String randomUrl;
	
	
 //Constructor which takes Label as an argument.
	public Reel(Label reel) {
		this.reel = reel;
	
	}

	//Method returns a  array of Symbol
	public Symbol[] spin() {

		Symbol[] symbols = new Symbol[6];

		symbols[0] = new Symbol("Images\\bell.png", 6);
		symbols[1] = new Symbol("Images\\cherry.png", 2);
		symbols[2] = new Symbol("Images\\lemon.png", 3);
		symbols[3] = new Symbol("Images\\plum.png", 4);
		symbols[4] = new Symbol("Images\\redseven.png", 7);
		symbols[5] = new Symbol("Images\\watermelon.png", 5);

		return symbols;
	}

	// Method to Set image to label
	public void setLabel(String url, Label reel) {
		try {

			fs = new FileInputStream(url);
			image = new Image(fs);
			imageView = new ImageView(image);
			imageView.setFitWidth(200);
			imageView.setFitHeight(300);
			reel.setGraphic(imageView);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// This method returns a random number between 0 and 6.
	private int randomNumber() {
		Random randomizer = new Random();
		int randomNumber = randomizer.nextInt(6);
		return randomNumber;
	}

	@Override
	public void run() {//Implementing run method in the runnable interface.

		while (true) {

			Platform.runLater(new Runnable() {//Help to update UI and Prevent application from crashing.

				@Override
				public void run() {
					// setting a random image to reels
					randomUrl = spin()[randomNumber()].getImage();
					setLabel(randomUrl, reel);
				}

			});

			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//Method to get randomUrl.
	public String getRandomUrl() {
		return randomUrl;
	}
		

	
}

