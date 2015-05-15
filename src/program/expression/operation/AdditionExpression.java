package program.expression.operation;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class AdditionExpression<T,E1,E2> extends BinaryExpressionOperation<T,E1,E2>{

	public AdditionExpression(Expression<E1> e1, Expression<E2> e2,
			SourceLocation sourcelocation) {
		super(e1, e2, sourcelocation);
		super.setOutcome();
	}
}
