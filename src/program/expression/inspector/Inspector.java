package program.expression.inspector;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public abstract class Inspector<T> extends Expression<SourceLocation>{
	
	public Inspector(Expression<?> e, SourceLocation sourcelocation){
		super(sourcelocation);
		
		this.expr = (T)e;
		
	}
	
	public T getExpression() {
		return expr;
	}

	private T expr;

}
