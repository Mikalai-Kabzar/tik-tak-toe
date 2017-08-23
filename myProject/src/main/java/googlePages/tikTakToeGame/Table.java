package googlePages.tikTakToeGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.shaman.common.Services.WaitService;

import googlePages.AbstractPage;
import googlePages.tikTakToeGame.enums.Marks;

public class Table extends AbstractPage {

	private static final byte MAX_COUNT = 20;

	private static final int TIMEOUT = 500;
	private static final int TABLE_SIZE = 3;
	private static final int EMPTY_CELL_VALUE = 0;
	private static final int X_CELL_VALUE = 1;
	private static final int O_CELL_VALUE = 100;

	private static final String MARK_X = "X";
	private static final String MARK_O = "O";

	private static final String MAIN_WEBELEMENT_XPATH = ".//div[@class='_Wtj mod']";
	private static final String MAIN_GAME_TABLE_XPATH = MAIN_WEBELEMENT_XPATH + "//table[contains(@class,'i7i')]";
	private static final String WIN_LOSE_DRAW_XPATH = "//div[contains(@style,'inherit')]//*[@aria-label='%s']";
	private static final String CELL_XPATH = MAIN_GAME_TABLE_XPATH + "//tr[%s]/td[%s]";
	private static final String ELEMENT_WITH_NONE_XPATH = MAIN_GAME_TABLE_XPATH
			+ "//tr[%s]/td[%s]/*[@aria-label='%s' and contains(@style,'none')]";

	private int[][] arrayWithSigns = new int[TABLE_SIZE][TABLE_SIZE];

	private int sum;

	{
		LOG.debug("Fill array with start values.");
		for (int[] row : arrayWithSigns) {
			Arrays.fill(row, EMPTY_CELL_VALUE);
		}
	}

	public static int getEmptyCellValue() {
		return EMPTY_CELL_VALUE;
	}

	public static int getxCellValue() {
		return X_CELL_VALUE;
	}

	public static int getoCellValue() {
		return O_CELL_VALUE;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	/**
	 * Check. Game ended with 'X' win.
	 * 
	 * @return true if game status = 'X' win.
	 */
	public boolean isXWin() {
		LOG.debug("Check. Game ended with 'X' win.");
		return isXMarkDisplayed() && !isOMarkDisaplyed();
	}

	/**
	 * Check. Game ended with 'O' win.
	 * 
	 * @return true if game status = 'O' win.
	 */
	public boolean isOWin() {
		LOG.debug("Check. Game ended with 'O' win.");
		return !isXMarkDisplayed() && isOMarkDisaplyed();
	}

	/**
	 * Check. Game ended with draw.
	 * 
	 * @return true if game status = draw.
	 */
	public boolean isDraw() {
		LOG.debug("Check. Game ended with Draw.");
		return isXMarkDisplayed() && isOMarkDisaplyed();
	}

	/**
	 * Return array with cells values.
	 * 
	 * @return array with cells values.
	 */
	public int[][] getTableValues() {
		LOG.debug("Return array with actual cells values.");
		updateTable();
		return arrayWithSigns;
	}

	@Override
	public void waitForPageLoading() {
		LOG.debug("Wait for table loading.");
		WaitService.waitUntilElementToBeVisible(MAIN_GAME_TABLE_XPATH);
	};

	/**
	 * Click on cell.
	 * 
	 * @param x
	 *            - x index of cell.
	 * @param y
	 *            - y index of cell.
	 */
	public void clickOnCell(int i, int j) {
		LOG.info(String.format("Click on cell (%s, %s)", i, j));
		WaitService.findElement(String.format(CELL_XPATH, i + 1, j + 1)).click();
	}

	/**
	 * Wait for sum of table update.
	 */
	public void waitForSumUpdate() {
		LOG.debug("Wait for PC turn and update of table.");
		int counter = 0;
		while (getActualSum() == getSum() && counter < MAX_COUNT) {
			WaitService.sleep(TIMEOUT);
			counter++;
		}
		setSum(getActualSum());
	}

	/**
	 * Get free cell to win as mark.
	 * 
	 * @param mark
	 *            - mark to find win-cell.
	 * 
	 * @return {@link Cell} of free corner cell.
	 */
	public Cell getCellToWin(Marks mark) {
		LOG.debug("Get free cell to win as " + mark + ".");
		Cell cellCoordinate = null;
		List<Cell> list = getAllFreeCells();
		int valueToCompare = getMarkValue(mark) * 2;
		for (Cell cell : list) {
			int x = cell.getX();
			int y = cell.getY();
			if (isCornerCell(x, y)) {
				if (valueToCompare == calculateDiagonal(x, y)) {
					cellCoordinate = cell;
				}
			}
			if (valueToCompare == calculateRow(x)) {
				cellCoordinate = cell;
			}
			if (valueToCompare == calculateColumn(y)) {
				cellCoordinate = cell;
			}
		}
		return cellCoordinate;
	}

	/**
	 * Get free corner cell.
	 * 
	 * @return {@link Cell} of free corner cell.
	 */
	public Cell getCornerCell() {
		LOG.debug("Get free corner cell.");
		Cell cellCoordinate = null;
		List<Cell> list = getAllFreeCells();
		for (Cell cell : list) {
			int x = cell.getX();
			int y = cell.getY();
			if (isCornerCell(x, y)) {
				cellCoordinate = cell;
				break;
			}
		}
		return cellCoordinate;
	}

	/**
	 * Return list of cellCoordinates for all free cells.
	 * 
	 * @return {@link List} of {@link Cell} with all free cells.
	 */
	public List<Cell> getAllFreeCells() {
		LOG.debug("Return list of cellCoordinates for all free cells.");
		updateTable();
		List<Cell> list = new ArrayList<Cell>();
		for (int i = 0; i < TABLE_SIZE; i++) {
			for (int j = 0; j < TABLE_SIZE; j++) {
				if (arrayWithSigns[i][j] == EMPTY_CELL_VALUE) {
					list.add(new Cell(i, j));
				}
			}
		}
		return list;
	}

	/**
	 * Check. X-win mark displayed.
	 * 
	 * @return true - if X-win mark displayed.
	 */
	private boolean isXMarkDisplayed() {
		LOG.debug("Check. X-win mark displayed.");
		return WaitService.isElementExist(String.format(WIN_LOSE_DRAW_XPATH, MARK_X));
	}

	/**
	 * Check. O-win mark displayed.
	 * 
	 * @return true - if O-win mark displayed.
	 */
	private boolean isOMarkDisaplyed() {
		LOG.debug("Check. O-win mark displayed.");
		return WaitService.isElementExist(String.format(WIN_LOSE_DRAW_XPATH, MARK_O));
	}

	/**
	 * Return value of mark.
	 * 
	 * @param mark
	 *            - current mark.
	 * 
	 * @return value of mark.
	 */
	private int getMarkValue(Marks mark) {
		LOG.debug("Return value of mark.");
		switch (mark) {
		case X_MARK:
			return X_CELL_VALUE;
		case O_MARK:
			return O_CELL_VALUE;
		default:
			return X_CELL_VALUE;
		}
	}

	/**
	 * Get actual sum of all table cells.
	 * 
	 * @return sum of all table cells.
	 */
	private int getActualSum() {
		LOG.debug("Get actual sum of all table cells.");
		int sum = 0;
		updateTable();
		for (int[] row : arrayWithSigns) {
			for (int elem : row) {
				sum += elem;
			}
		}
		return sum;
	}

	/**
	 * Check. Cell = corner cell.
	 * 
	 * @param x
	 *            - x index of cell.
	 * @param y
	 *            - y index of cell.
	 * 
	 * @return true if cell = corner cell.
	 */
	private boolean isCornerCell(int x, int y) {
		LOG.debug("Check. Cell = corner cell.");
		if ((x == 0 && y == 0) || (x == 0 && y == TABLE_SIZE - 1) || (x == TABLE_SIZE - 1 && y == 0)
				|| (x == TABLE_SIZE - 1 && y == TABLE_SIZE - 1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculate sum of diagonal values.
	 * 
	 * @param x
	 *            - x index of diagonal.
	 * @param y
	 *            - y index of diagonal.
	 * 
	 * @return sum of diagonal values.
	 */
	private int calculateDiagonal(int x, int y) {
		LOG.debug("Calculate sum of diagonal values.");
		int sum = arrayWithSigns[1][1];
		if (x == 0 && y == 0) {
			sum += arrayWithSigns[2][2];
		}
		if (x == TABLE_SIZE - 1 && y == 0) {
			sum += arrayWithSigns[0][2];
		}
		if (x == 0 && y == TABLE_SIZE - 1) {
			sum += arrayWithSigns[2][0];
		}
		if (x == TABLE_SIZE - 1 && y == TABLE_SIZE - 1) {
			sum += arrayWithSigns[0][0];
		}
		return sum;
	}

	/**
	 * Calculate sum of row values.
	 * 
	 * @param index
	 *            - index of row.
	 * 
	 * @return sum of row values.
	 */
	private int calculateRow(int index) {
		LOG.debug("Calculate sum of row values.");
		int sum = 0;
		for (int j = 0; j < TABLE_SIZE; j++) {
			sum += arrayWithSigns[index][j];
		}
		return sum;
	}

	/**
	 * Calculate sum of column values.
	 * 
	 * @param index
	 *            - index of column.
	 * 
	 * @return sum of column values.
	 */
	private int calculateColumn(int index) {
		LOG.debug("Calculate sum of column values.");
		int sum = 0;
		for (int i = 0; i < TABLE_SIZE; i++) {
			sum += arrayWithSigns[i][index];
		}
		return sum;
	}

	/**
	 * Collect actual table state to array.
	 */
	private void updateTable() {
		LOG.debug("Collect actual table state to array.");
		boolean isFirstVisible = false;
		boolean isSecondVisible = false;
		for (int i = 0; i < TABLE_SIZE; i++) {
			for (int j = 0; j < TABLE_SIZE; j++) {
				isFirstVisible = WaitService
						.isElementExist(String.format(ELEMENT_WITH_NONE_XPATH, (i + 1), (j + 1), MARK_X));
				isSecondVisible = WaitService
						.isElementExist(String.format(ELEMENT_WITH_NONE_XPATH, (i + 1), (j + 1), MARK_O));
				if (isFirstVisible && isSecondVisible) {
					arrayWithSigns[i][j] = EMPTY_CELL_VALUE;
				} else if (isFirstVisible) {
					arrayWithSigns[i][j] = O_CELL_VALUE;
				} else {
					arrayWithSigns[i][j] = X_CELL_VALUE;
				}
			}
		}
	}
}