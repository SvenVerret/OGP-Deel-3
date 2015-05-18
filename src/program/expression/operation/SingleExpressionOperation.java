package program.expression.operation;

import java.util.Map;
import java.util.function.Function;

import program.expression.Expression;
import program.expression.ValueExpression;
import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class SingleExpressionOperation<R,E> extends Expression<R>{

	public SingleExpressionOperation(ValueExpression<E> e1, Function<E,R> f, SourceLocation sourcelocation) {
		super(sourcelocation);
		this.e = e1;
		function = f;
	}

	private ValueExpression<E> e;
	private Function<E,R> function;

	public ValueExpression<E> getE(){
		return e;
	}

	@Override
	public R evaluate(Map<String, Type> globalVariables){
		return this.function.apply(this.getE().evaluate(globalVariables));
	}
}
