package program.expression;

import jumpingalien.part3.programs.SourceLocation;

public class DoubleValueExpression<T> extends Expression<T>{

	public DoubleValueExpression(T value1, T value2, SourceLocation sourcelocation) {
		super(sourcelocation);
		this.value1 = value1;
		this.value2 = value2;
	}
	
	private T value1;
	private T value2;
	
	public T getValue1(){
		return value1;
	}
	public T getValue2(){
		return value2;
	}

}
