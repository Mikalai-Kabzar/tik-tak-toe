package googlePages.tikTakToeGame.Gamers;

import java.util.List;

import googlePages.tikTakToeGame.Cell;
import googlePages.tikTakToeGame.Table;
import googlePages.tikTakToeGame.enums.Marks;

public final class Doodle extends Gamer {

	{
		gamerIntellect = "Doodle";
	}

	public Doodle(Table table, Marks mark) {
		super(table, mark, gamerIntellect);
	}

	@Override
	protected Cell selectCell() {
		LOG.debug("Select randoom cell to perform turn.");
		List<Cell> list = table.getAllFreeCells();
		int cellNumber = RANDOOM.nextInt(list.size());
		return list.get(cellNumber);
	}
}
