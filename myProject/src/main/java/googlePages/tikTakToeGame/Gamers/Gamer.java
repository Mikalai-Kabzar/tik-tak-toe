package googlePages.tikTakToeGame.Gamers;

import java.util.Random;

import org.apache.log4j.Logger;

import googlePages.tikTakToeGame.CellCoordinate;
import googlePages.tikTakToeGame.Table;
import googlePages.tikTakToeGame.enums.Marks;

public abstract class Gamer {

	protected static final Logger LOG = Logger.getLogger(Gamer.class);

	protected static final Random RANDOOM = new Random();

	protected static final int MAX_TURNS = 9;

	protected static Table table;

	protected static Marks gamerBasicValue;
	protected static Marks anotherBasicValue;

	protected static String gamerIntellect;

	protected Gamer(Table table, Marks mark, String gamerIntellect) {
		Gamer.table = table;
		Gamer.gamerIntellect = gamerIntellect;
		switch (mark) {
		case X_MARK:
			gamerBasicValue = Marks.X_MARK;
			anotherBasicValue = Marks.O_MARK;
			break;
		default:
			gamerBasicValue = Marks.O_MARK;
			anotherBasicValue = Marks.X_MARK;
			break;
		}
	}

	protected abstract CellCoordinate selectCellCoordinate();

	public void doNextTurn() {
		CellCoordinate cellCoordinate = selectCellCoordinate();
		table.clickOnCell(cellCoordinate.getX(), cellCoordinate.getY());
		table.waitForSumUpdate();
	}

	public String getGamerIntellect() {
		return gamerIntellect;
	}
}
