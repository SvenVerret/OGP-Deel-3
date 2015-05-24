package program.expression.operation;

import java.util.function.Function;

import program.Program;
import program.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class SingleExpressionOperation<R,E> extends Expression<R>{

	public SingleExpressionOperation(Expression<E> expr, Function<Object,R> f, SourceLocation sourcelocation) {
		super(sourcelocation);
		this.e = expr;
		function = f;
	}

	public Expression<E> getE(){
		return e;
	}

	@Override
	public Object evaluate(Program program){
		Object result = this.function.apply(this.getE().evaluate(program));
		if(result instanceof Integer){
			result =((Integer)result).doubleValue();
		}
		return result;
	}

	private Expression<E> e;
	private Function<Object,R> function;
}
