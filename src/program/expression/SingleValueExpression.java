package program.expression;

import jumpingalien.part3.programs.SourceLocation;

public class SingleValueExpression<T> extends Expression<T> {
	
	public SingleValueExpression(T value,SourceLocation sourcelocation) {
		super(sourcelocation);
		this.value = value;	
		}
	
	private T value;
	public void setValue(T value){
		this.value=value;
	}
	public T getValue(){
		return value;
	}

}
