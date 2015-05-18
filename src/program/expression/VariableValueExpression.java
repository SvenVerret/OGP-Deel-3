package program.expression;

import java.util.Map;

import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class VariableValueExpression<T> extends ValueExpression<T> {

	public VariableValueExpression(String variableName,Type T, SourceLocation sourcelocation) {
		super(null,sourcelocation);
		this.name = variableName;
	}
	
	public String getName(){
		return name;
	}
	private String name;
	
	@Override
	public T evaluate(Map<String,Type> globalVariables){
		return value;
	}
}
