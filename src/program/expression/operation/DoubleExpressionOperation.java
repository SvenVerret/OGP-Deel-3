package program.expression.operation;

import program.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class DoubleExpressionOperation extends Operation{

	public DoubleExpressionOperation(Expression e1, Expression e2, 
			SourceLocation sourcelocation) {
		super(sourcelocation);
		value1 = e1;
		value2 = e2;
		
	}
	
	public Expression getValue1() {
		return value1;
	}

	public Expression getValue2() {
		return value2;
	}

	private final Expression value1;
	private final Expression value2;

}
