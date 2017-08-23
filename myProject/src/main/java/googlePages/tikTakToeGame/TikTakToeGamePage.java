package googlePages.tikTakToeGame;

import java.util.stream.IntStream;

import com.shaman.common.Services.WaitService;

import googlePages.AbstractPage;
import googlePages.tikTakToeGame.Gamers.Child;
import googlePages.tikTakToeGame.Gamers.Doodle;
import googlePages.tikTakToeGame.Gamers.Gamer;
import googlePages.tikTakToeGame.Gamers.Genius;
import googlePages.tikTakToeGame.enums.Complexity;
import googlePages.tikTakToeGame.enums.Marks;

public class TikTakToeGamePage extends AbstractPage {

	public static final String GAME_REQUEST = "tick-tack-toe";

	private static final String SELECTED_STATE = "1";
	private static final String UNSELECTED_STATE = "0";
	private static final String X_STATE = "1";
	private static final String O_STATE = "2";
	private static final String END_GAME = "3";
	private static final String START_GAME_STATE = "4";

	private static final String COMPLEXITY_DROPDOWN_XPATH = "//g-dropdown-menu";
	private static final String COMPLEXITY_OPTION_XPATH = "//g-menu-item[@data-difficulty='%s']";
	private static final String NEW_GAME_BUTTON_XPATH = "//div/g-raised-button";
	private static final String SELECT_X_BUTTON_XPATH = "//div/*[@aria-label='X']/../..";
	private static final String SELECT_O_BUTTON_XPATH = "//div/*[@aria-label='O']/../..";
	private static final String TURN_SPAN_STATE_XPATH = "//span[%s][@style='opacity: %s;']";

	private static final int MAX_TURNS = 9;

	private static Table table = new Table();

	private static int winsX;
	private static int winsO;
	private static int draws;
	private static int actualGameCount;
	private static int expectedGameCount;

	private static String gamerLevel;

	public TikTakToeGamePage() {
		waitForPageLoading();
	}

	@Override
	public void waitForPageLoading() {
		LOG.debug("Wait for table loading.");
		table.waitForPageLoading();

		LOG.debug("Wait for page loading.");
		WaitService.waitForElementExist(COMPLEXITY_DROPDOWN_XPATH);
		WaitService.waitForElementExist(NEW_GAME_BUTTON_XPATH);
	};

	public static int getWinsX() {
		return winsX;
	}

	public static int getWinsO() {
		return winsO;
	}

	public static int getDraws() {
		return draws;
	}

	/**
	 * Create 'Doodle' gamer.
	 * 
	 * @return {@link Doodle} instance.
	 */
	public Doodle crateDoodleGamer() {
		LOG.info("Create 'Doodle' gamer.");
		return new Doodle(table, Marks.X_MARK);
	}

	/**
	 * Create 'Child' gamer.
	 * 
	 * @return {@link Child} instance.
	 */
	public Child crateChildGamer() {
		LOG.info("Create 'Child' gamer.");
		return new Child(table, Marks.X_MARK);
	}

	/**
	 * Create 'Genius' gamer.
	 * 
	 * @return {@link Genius} instance.
	 */
	public Genius crateGeniusGamer() {
		LOG.info("Create 'Genius' gamer.");
		return new Genius(table, Marks.X_MARK);
	}

	/**
	 * Print game statistic.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage printStat() {
		LOG.info("**************************************************");
		LOG.info("Expected game count:" + expectedGameCount);
		LOG.info("Actual game count:" + actualGameCount);
		LOG.info("Intellect level:" + gamerLevel);
		LOG.info("X wins:" + winsX);
		LOG.info("O wins:" + winsO);
		LOG.info("Draws:" + draws);
		LOG.info("**************************************************");
		return this;
	}

	/**
	 * Play game NumberOfGames-times as gamer.
	 * 
	 * @param gamer
	 *            - current gamer
	 * @param numberOfGames
	 *            - number of games to play
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage playGame(Gamer gamer, int numberOfGames) {
		LOG.debug("Play game " + numberOfGames + "-times as " + gamer.getGamerIntellect());
		winsX = 0;
		winsO = 0;
		draws = 0;
		actualGameCount = 0;
		expectedGameCount = numberOfGames;
		gamerLevel = gamer.getGamerIntellect();
		IntStream.range(0, numberOfGames).forEach(i -> {
			printGameNumber(i + 1);
			newGame();
			playGame(gamer);
			actualGameCount++;
		});
		return this;
	}

	/**
	 * Start new game.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage newGame() {
		LOG.info("Click 'New game' button.");
		WaitService.findElement(NEW_GAME_BUTTON_XPATH).click();

		LOG.debug("Wait for page with new game loading.");
		waitForPageLoading();
		waitForStates(UNSELECTED_STATE, UNSELECTED_STATE, UNSELECTED_STATE, SELECTED_STATE);
		return this;
	}

	/**
	 * Select 'Easy' game complexity.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage setEasyComplexity() {
		LOG.info("Select 'Easy' game complexity.");
		selectComplexity(Complexity.EASY);
		return this;
	}

	/**
	 * Select 'Medium' game complexity.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage setMediumComplexity() {
		LOG.info("Select 'Medium' game complexity.");
		selectComplexity(Complexity.MEDIUM);
		return this;
	}

	/**
	 * Select 'Hard' game complexity.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage setHardComplexity() {
		LOG.info("Select 'Hard' game complexity.");
		selectComplexity(Complexity.HARD);
		return this;
	}

	/**
	 * Select PVP game mode.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage setPVPComplexity() {
		LOG.info("Select 'PVP' game mode.");
		selectComplexity(Complexity.PVP);
		return this;
	}

	/**
	 * Select 'X' to play game.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage selectX() {
		LOG.info("Select 'X' to play game.");
		WaitService.findElement(SELECT_X_BUTTON_XPATH).click();
		return this;
	}

	/**
	 * Select 'O' to play game.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	public TikTakToeGamePage selectO() {
		LOG.info("Select 'O' to play game.");
		if (!isPVPMode()) {
			WaitService.findElement(SELECT_O_BUTTON_XPATH).click();
			waitForStates(UNSELECTED_STATE, SELECTED_STATE, UNSELECTED_STATE, UNSELECTED_STATE);
		} else {
			LOG.info("You can't select 'O' in PVP mode.");
		}
		return this;
	}

	/**
	 * Select game complexity
	 * 
	 * @param complexity
	 *            - complexity of game
	 * 
	 * @return {@link TikTakToeGamePage} instance
	 * 
	 */
	public TikTakToeGamePage selectComplexity(Complexity complexity) {
		LOG.debug("Click on complexity dropDown.");
		WaitService.findElement(COMPLEXITY_DROPDOWN_XPATH).click();

		LOG.debug("Wait for expand of complexity dropDown.");
		WaitService.waitUntilElementToBeVisible(String.format(COMPLEXITY_OPTION_XPATH, complexity.toString()));

		LOG.debug("Click on complexity dropDown option to select complexity.");
		WaitService.findElement(String.format(COMPLEXITY_OPTION_XPATH, complexity.toString())).click();

		if (WaitService.isElementExist(String.format(COMPLEXITY_OPTION_XPATH, complexity.toString()))) {
			newGame();
		}

		LOG.debug("Wait for selection of complexity option.");
		switch (complexity) {
		case PVP:
			waitForStates(SELECTED_STATE, UNSELECTED_STATE, UNSELECTED_STATE, UNSELECTED_STATE);
			break;
		default:
			waitForStates(UNSELECTED_STATE, UNSELECTED_STATE, UNSELECTED_STATE, SELECTED_STATE);
			break;
		}
		return this;
	}

	/**
	 * Print actual table status to LOG. Additional debug method.
	 * 
	 * @return {@link TikTakToeGamePage} instance.
	 */
	@SuppressWarnings("unused")
	private TikTakToeGamePage printActualStateToLOG() {
		LOG.debug("Actual table status:");
		int[][] actualArray = table.getTableValues();
		String str = "\n";
		for (int[] array : actualArray) {
			for (int elem : array) {
				str = str + elem + ".";
			}
			str = str + "\n";
		}
		LOG.debug(str);
		return this;
	}

	/**
	 * Print game number
	 * 
	 * @param gameNumber
	 *            - game number.
	 */
	private void printGameNumber(int gameNumber) {
		LOG.info("**************************************************");
		LOG.info("Game number:" + gameNumber);
		LOG.info("**************************************************");
	}

	/**
	 * Play one game.
	 * 
	 * @param gamer
	 *            - actual gamer to play with PC.
	 */
	private void playGame(Gamer gamer) {
		LOG.info("Start playing.");
		int turn = 0;
		while (turn < MAX_TURNS && !isGameOver()) {
			gamer.doNextTurn();
			turn++;
		}
		LOG.info("Game Over.");
		checkWhoWins();
	}

	/**
	 * Collect win/loose/draw data.
	 */
	private void checkWhoWins() {
		if (table.isXWin()) {
			LOG.info("X wins.");
			winsX++;
		}
		if (table.isOWin()) {
			LOG.info("O wins.");
			winsO++;
		}
		if (table.isDraw()) {
			LOG.info("Draw.");
			draws++;
		}
	}

	/**
	 * Check. Game over status.
	 * 
	 * @return true if game over.
	 */
	private boolean isGameOver() {
		LOG.debug("Check. Game over status.");
		return WaitService.isElementExist(String.format(TURN_SPAN_STATE_XPATH, X_STATE, UNSELECTED_STATE))
				&& WaitService.isElementExist(String.format(TURN_SPAN_STATE_XPATH, O_STATE, UNSELECTED_STATE))
				&& WaitService.isElementExist(String.format(TURN_SPAN_STATE_XPATH, END_GAME, SELECTED_STATE))
				&& WaitService.isElementExist(String.format(TURN_SPAN_STATE_XPATH, START_GAME_STATE, UNSELECTED_STATE));
	}

	/**
	 * Check. PVP mode is selected.
	 * 
	 * @return true if PVP mode is selected
	 */
	private boolean isPVPMode() {
		LOG.debug("Check. PVP mode is selected.");
		return WaitService.isElementExist(String.format(TURN_SPAN_STATE_XPATH, X_STATE, SELECTED_STATE))
				&& WaitService.isElementExist(String.format(TURN_SPAN_STATE_XPATH, START_GAME_STATE, UNSELECTED_STATE));
	}

	/**
	 * Wait for game status marker states
	 * 
	 * @param stateX
	 *            - selected stateX state
	 * @param stateO
	 *            - selected stateO state
	 * @param stateEndGame
	 *            - selected stateEndGame state
	 * @param stateStartGame
	 *            - selected stateStartGame state
	 */
	private void waitForStates(String stateX, String stateO, String stateEndGame, String stateStartGame) {
		LOG.debug("Wait for 'X' status.");
		WaitService.waitForElementExist(String.format(TURN_SPAN_STATE_XPATH, X_STATE, stateX));

		LOG.debug("Wait for 'O' status.");
		WaitService.waitForElementExist(String.format(TURN_SPAN_STATE_XPATH, O_STATE, stateO));

		LOG.debug("Wait for 'End game' status.");
		WaitService.waitForElementExist(String.format(TURN_SPAN_STATE_XPATH, END_GAME, stateEndGame));

		LOG.debug("Wait for 'Start game' status.");
		WaitService.waitForElementExist(String.format(TURN_SPAN_STATE_XPATH, START_GAME_STATE, stateStartGame));
	}
}