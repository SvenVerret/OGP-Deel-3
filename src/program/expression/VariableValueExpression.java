package program.expression;

import jumpingalien.part3.programs.SourceLocation;

public class VariableValueExpression<T> extends ValueExpression<T> {

	public VariableValueExpression(String variableName, T value, SourceLocation sourcelocation) {
		super(value, sourcelocation);
		this.name  = variableName;
	}
	
	private String name;
	public String getName(){
		return name;
	}

}
