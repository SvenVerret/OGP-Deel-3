package program.expression;

import program.Program;
import jumpingalien.part3.programs.SourceLocation;

public abstract class Expression<T> {

	public Expression(SourceLocation sourcelocation){
		SL = sourcelocation;
	}

	public SourceLocation getSourceLocation() {
		return SL;
	}


	private final SourceLocation SL;

	public abstract Object evaluate(Program program);
}
