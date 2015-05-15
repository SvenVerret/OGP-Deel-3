package program.expression.inspector;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public abstract class Inspector extends Expression{
	
	public Inspector(Expression e, SourceLocation sourcelocation){
		super(sourcelocation);
		
		expr = e;
		
	}
	
	public Expression getExpression() {
		return expr;
	}

	private final Expression expr;

}
