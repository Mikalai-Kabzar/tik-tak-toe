package googlePages.tikTakToeGame.Gamers;

import java.util.List;

import googlePages.tikTakToeGame.CellCoordinate;
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
	protected CellCoordinate selectCellCoordinate() {
		List<CellCoordinate> list = table.getAllFreeCells();
		int cellCoordinateNumber = RANDOOM.nextInt(list.size());
		return list.get(cellCoordinateNumber);
	}
}
