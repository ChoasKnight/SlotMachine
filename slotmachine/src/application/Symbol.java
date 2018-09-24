package application;

public class Symbol implements ISymbol {

	private String url;//This has location of an image
	private int value; // This has the symbol value for each image.

	
	public Symbol(String url, int value) {		
		this.url = url;
		this.value = value;
	}

	@Override
	//Getter and Setter methods for String url and int value.
	public void setImage(String url) {
		this.url = url;

	}

	@Override
	public String getImage() {
		return url;
	}

	@Override
	public void setValue(int v) {
		value = v;
	}

	@Override
	public int getValue() {
		return value;
	}

}
