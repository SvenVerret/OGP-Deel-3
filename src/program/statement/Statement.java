package program.statement;

import jumpingalien.part3.programs.SourceLocation;

public abstract class Statement {

	public Statement(SourceLocation sourceLocation) {
		SL = sourceLocation;
	}


	public SourceLocation getSourceLocation() {
		return SL;
	}

	private final SourceLocation SL;
}

