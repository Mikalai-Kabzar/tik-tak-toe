package googlePages.tikTakToeGame.Gamers;

import java.util.List;

import googlePages.tikTakToeGame.CellCoordinate;
import googlePages.tikTakToeGame.Table;
import googlePages.tikTakToeGame.enums.Marks;

public final class Genius extends Gamer {

	{
		gamerIntellect = "Genius";
	}

	public Genius(Table table, Marks mark) {
		super(table, mark, gamerIntellect);
	}

	@Override
	protected CellCoordinate selectCellCoordinate() {
		List<CellCoordinate> list = table.getAllFreeCells();
		CellCoordinate centerCellCoordinate = null;

		// try to click on center cell
		if (list.stream().anyMatch(p -> p.getX() == 1 && p.getY() == 1)) {
			centerCellCoordinate = new CellCoordinate(1, 1);
		}

		// try to win right NOW
		if (centerCellCoordinate == null) {
			centerCellCoordinate = table.getCellToWin(gamerBasicValue);
		}

		// try to avoid loose
		if (centerCellCoordinate == null) {
			centerCellCoordinate = table.getCellToWin(anotherBasicValue);
		}

		// try to find corner cell
		if (centerCellCoordinate == null) {
			centerCellCoordinate = table.getCornerCell();
		}

		// Select any cell
		if (centerCellCoordinate == null) {
			int cellCoordinateNumber = RANDOOM.nextInt(list.size());
			centerCellCoordinate = list.get(cellCoordinateNumber);
		}
		return centerCellCoordinate;
	}
}