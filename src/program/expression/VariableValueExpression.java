package program.expression;

import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class VariableValueExpression<T> extends ValueExpression<T> {

	public VariableValueExpression(String variableName,Type T, SourceLocation sourcelocation) {
		super(null,sourcelocation);
		this.variable = new ValueExpression<T>(null,sourcelocation);
		this.name = variableName;
	}
	public ValueExpression<T> getVariable(){
		return variable;
	}
	private ValueExpression<T> variable;
	
	public String getName(){
		return name;
	}
	private String name;
}
