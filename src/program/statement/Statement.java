package program.statement;

import jumpingalien.part3.programs.SourceLocation;
<<<<<<< HEAD

public abstract class Statement {
=======
import program.expression.Expression;

public abstract class Statement extends Expression{

	public Statement(SourceLocation sourcelocation) {
		super(sourcelocation);
		// TODO Auto-generated constructor stub
	}
	
>>>>>>> refs/remotes/origin/master

	public Statement(SourceLocation sourceLocation) {
		SL = sourceLocation;
	}


	public SourceLocation getSourceLocation() {
		return SL;
	}

	private final SourceLocation SL;
}

