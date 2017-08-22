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
		return isXMarkDisplayed() && !isOMarkDisaplyed();
	}

	/**
	 * Check. Game ended with 'O' win.
	 * 
	 * @return true if game status = 'O' win.
	 */
	public boolean isOWin() {
		return !isXMarkDisplayed() && isOMarkDisaplyed();
	}

	/**
	 * Check. Game ended with draw.
	 * 
	 * @return true if game status = draw.
	 */
	public boolean isDraw() {
		return isXMarkDisplayed() && isOMarkDisaplyed();
	}

	/**
	 * Return array with cells values.
	 * 
	 * @return array with cells values.
	 */
	public int[][] getTableValues() {
		updateTable();
		return arrayWithSigns;
	}

	@Override
	public void waitForPageLoading() {
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
		LOG.debug(String.format("Click on cell (%s, %s)", i, j));
		WaitService.findElement(String.format(CELL_XPATH, i + 1, j + 1)).click();
	}

	/**
	 * Wait for sum of table update.
	 */
	public void waitForSumUpdate() {
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
	 * @return {@link CellCoordinate} of free corner cell.
	 */
	public CellCoordinate getCellToWin(Marks mark) {
		CellCoordinate cellCoordinate = null;
		List<CellCoordinate> list = getAllFreeCells();
		int valueToCompare = getMarkValue(mark) * 2;
		for (CellCoordinate cell : list) {
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
	 * @return {@link CellCoordinate} of free corner cell.
	 */
	public CellCoordinate getCornerCell() {
		CellCoordinate cellCoordinate = null;
		List<CellCoordinate> list = getAllFreeCells();
		for (CellCoordinate cell : list) {
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
	 * @return {@link List} of {@link CellCoordinate} with all free cells.
	 */
	public List<CellCoordinate> getAllFreeCells() {
		updateTable();
		List<CellCoordinate> list = new ArrayList<CellCoordinate>();
		for (int i = 0; i < TABLE_SIZE; i++) {
			for (int j = 0; j < TABLE_SIZE; j++) {
				if (arrayWithSigns[i][j] == EMPTY_CELL_VALUE) {
					list.add(new CellCoordinate(i, j));
				}
			}
		}
		return list;
	}

	private boolean isXMarkDisplayed() {
		return WaitService.isElementExist(String.format(WIN_LOSE_DRAW_XPATH, MARK_X));
	}

	private boolean isOMarkDisaplyed() {
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
		int sum = 0;
		for (int i = 0; i < TABLE_SIZE; i++) {
			sum += arrayWithSigns[i][index];
		}
		return sum;
	}

	/**
	 * Collect actual table state.
	 */
	private void updateTable() {
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
