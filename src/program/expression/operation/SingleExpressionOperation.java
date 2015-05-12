package program.expression.operation;

import program.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class SingleExpressionOperation extends Operation{

	public SingleExpressionOperation(Expression e, SourceLocation sourcelocation) {
		super(sourcelocation);
		expr = e;
	}

	public Expression getExpression() {
		return expr;
	}

	private final Expression expr;
}
