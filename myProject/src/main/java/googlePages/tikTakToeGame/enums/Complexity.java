package googlePages.tikTakToeGame.enums;

public enum Complexity {
	EASY("easy"), MEDIUM("medium"), HARD("hard"), PVP("pvp");

	@Override
	public String toString() {
		return text;
	}

	private String text;

	private Complexity(final String text) {
		this.text = text;
	}

}
