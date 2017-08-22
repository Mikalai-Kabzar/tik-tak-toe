package googlePages.tikTakToeGame.Gamers;

import java.util.List;

import googlePages.tikTakToeGame.CellCoordinate;
import googlePages.tikTakToeGame.Table;
import googlePages.tikTakToeGame.enums.Marks;

public final class Child extends Gamer {

	{
		gamerIntellect = "Child";
	}

	public Child(Table table, Marks mark) {
		super(table, mark, gamerIntellect);
	}

	@Override
	protected CellCoordinate selectCellCoordinate() {
		List<CellCoordinate> list = table.getAllFreeCells();
		CellCoordinate centerCellCoordinate = null;

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