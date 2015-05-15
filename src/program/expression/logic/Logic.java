package program.expression.logic;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public abstract class Logic extends Expression{
	
	public Logic(Expression e1, Expression e2, SourceLocation sourcelocation){
		super(sourcelocation);
		Left = e1;
		Right = e2;
	}

	public Expression getLeft() {
		return Left;
	}

	public Expression getRight() {
		return Right;
	}

	final private Expression Left;
	final private Expression Right;
}
