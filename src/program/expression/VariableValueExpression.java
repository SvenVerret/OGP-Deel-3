package program.expression;

import program.Program;
import program.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public class VariableValueExpression<T> extends ValueExpression<T> {

	public VariableValueExpression(String variableName, Object variableType, SourceLocation sourcelocation) {
		super(null,sourcelocation);
		setName(variableName);
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	private String name;
	
	@Override
	public Object evaluate(Program program){
		if(program.getVariables().containsKey(getName())){
			return program.getVariables().get(getName());
		} else {
			return null;
		}
		
	}
}
