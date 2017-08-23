package googlePages.tikTakToeGame.Gamers;

import java.util.List;

import googlePages.tikTakToeGame.Cell;
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
	protected Cell selectCell() {
		LOG.debug("Get cell to perform next turn.");
		List<Cell> list = table.getAllFreeCells();
		Cell cell = null;

		if (cell == null) {
			LOG.debug("Get cell to win right now.");
			cell = table.getCellToWin(gamerBasicValue);
		}

		if (cell == null) {
			LOG.debug("Get cell to avoid loose.");
			cell = table.getCellToWin(anotherBasicValue);
		}

		if (cell == null) {
			LOG.debug("Select randoom cell to perform turn.");
			int cellNumber = RANDOOM.nextInt(list.size());
			cell = list.get(cellNumber);
		}
		return cell;
	}
}