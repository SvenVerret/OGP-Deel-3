package program.expression;

import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression {

	public Expression(SourceLocation sourcelocation){
		SL = sourcelocation;
	}
	
	public SourceLocation getSourceLocation() {
		return SL;
	}

	private final SourceLocation SL;
}
