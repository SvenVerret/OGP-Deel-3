package program.expression;

import java.util.Map;

import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class ValueExpression<T> extends Expression<T> {
	
	// An expression with a value of type T.
	
	public ValueExpression(T value,SourceLocation sourcelocation) {
		super(sourcelocation);
		this.value = value;	
		}
	
	protected T value;
	public void setValue(T value){
		this.value=value;
	}
	
	@Override
	public T evaluate(Map<String, Type> globalVariables) {
		return value;
	}

	public T evaluateWithoutGlobals(){
		return value;
	}

}
