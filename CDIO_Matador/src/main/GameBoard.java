package main;

import fields.AbstractFields;

public class GameBoard {

	private AbstractFields[] LogicFields;

	public AbstractFields getLogicField(int i) {

		return LogicFields[i];
	}

}
