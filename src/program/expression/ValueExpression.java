package program.expression;

import jumpingalien.part3.programs.SourceLocation;

public class ValueExpression<T> extends Expression<T> {
	
	// An expression with a value of type T.
	
	public ValueExpression(T value,SourceLocation sourcelocation) {
		super(sourcelocation);
		this.value = value;	
		}
	
	private T value;
	public void setValue(T value){
		this.value=value;
	}
	public T evaluate() {
		return value;
	}

}
