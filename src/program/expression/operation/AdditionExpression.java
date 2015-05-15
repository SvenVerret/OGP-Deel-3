package program.expression.operation;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class AdditionExpression<T,E> extends BinaryExpressionOperation<T,E>{

	public AdditionExpression(Expression<E1> e1, Expression<E2> e2,
			SourceLocation sourcelocation) {
		super(e1, e2, sourcelocation);
		super.setOutcome();
	}
}
