package program.expression.operation;

import java.util.function.Function;

import program.expression.Expression;
import program.expression.ValueExpression;
import jumpingalien.part3.programs.SourceLocation;

public class SingleExpressionOperation<R,E> extends Expression<R>{

	public SingleExpressionOperation(ValueExpression<E> e1, Function<E,R> f, SourceLocation sourcelocation) {
		super(sourcelocation);

		function = f;
	}

	private ValueExpression<E> e;
	private Function<E,R> function;

	public ValueExpression<E> getE(){
		return e;
	}

	public R evaluate(){
		return this.function.apply(this.getE().evaluate());
	}
}
