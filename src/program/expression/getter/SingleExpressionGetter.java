package program.expression.getter;

import program.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class SingleExpressionGetter extends Getter{

	public SingleExpressionGetter(Expression e1, SourceLocation sourcelocation) {
		super(sourcelocation);
		expr = e1;
	}

	public Expression getValue() {
		return expr;
	}

	private final Expression expr;
}
