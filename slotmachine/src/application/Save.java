package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Save {

	private File f;
	private PrintWriter pw;
	private int numberOfWins;
	private int numberOflosses;
	private String creditNetted;

	public Save(int numberOfWins, int numberOflosses, String creditNetted) {
		this.numberOfWins = numberOfWins;
		this.numberOflosses = numberOflosses;
		this.creditNetted = creditNetted;
	}

	// Method Save Data to a File.
	public void save() {
		getFile();
		print(getFile());

	}

	// Method to create new file if it exists.
	private File getFile() {
		f = new File("Save.txt");

		if (!(f.exists())) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return f;

	}

	//This is the method print data to file.
	private void print(File f) {
		try {
			pw = new PrintWriter(new FileWriter(f.getAbsolutePath(), true));
			pw.println("---------------------------------------------");
			pw.println("Wins:" + " " + numberOfWins);
			pw.println("Losses:" + " " + numberOflosses);
			pw.println("Credit Netted:" + " " + creditNetted);
			pw.println("Last Played On" + " " + getTime());
			pw.println("---------------------------------------------");
			pw.flush();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Method return current time.
	private String getTime() {

		String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		return time;
	}
	
	
}
