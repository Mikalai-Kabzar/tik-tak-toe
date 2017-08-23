package googlePages.tikTakToeGame.Gamers;

import java.util.List;

import googlePages.tikTakToeGame.Cell;
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
	protected Cell selectCell() {
		LOG.debug("Get cell to perform next turn.");
		List<Cell> list = table.getAllFreeCells();
		Cell cell = null;

		if (list.stream().anyMatch(p -> p.getX() == 1 && p.getY() == 1)) {
			LOG.debug("Try to get center cell.");
			cell = new Cell(1, 1);
		}

		if (cell == null) {
			LOG.debug("Get cell to win right now.");
			cell = table.getCellToWin(gamerBasicValue);
		}

		if (cell == null) {
			LOG.debug("Get cell to avoid loose.");
			cell = table.getCellToWin(anotherBasicValue);
		}

		if (cell == null) {
			LOG.debug("Select free corner cell to perform turn.");
			cell = table.getCornerCell();
		}

		if (cell == null) {
			LOG.debug("Select randoom cell to perform turn.");
			int cellNumber = RANDOOM.nextInt(list.size());
			cell = list.get(cellNumber);
		}
		return cell;
	}
}