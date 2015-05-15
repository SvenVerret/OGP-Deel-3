package program.expression.operation;

import program.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class SingleExpressionOperation<T,E> extends Expression<T>{

	public SingleExpressionOperation(Expression<E> e, SourceLocation sourcelocation) {
		super(sourcelocation);
		value = e;	
	}
	public Expression<E> getValue(){
		return value;
	}
	private final Expression<E> value;
}