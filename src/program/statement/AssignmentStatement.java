package program.statement;

import java.util.Map;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.type.DoubleType;
import program.type.Type;

public class AssignmentStatement extends Statement{
	/**
	 * 
	 * @param variableName
	 * @param variableType2
	 * @param value
	 * @param loc
	 */
	public AssignmentStatement(String variableName, Type variableType, Expression<?> value, 
			SourceLocation sourceLocation){
		super(sourceLocation);
		VariableName = variableName;
		VariableType = variableType;
		VariableValue = value;
	}

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;
	private String VariableName;
	private Type VariableType;
	private Expression<?> VariableValue;


	@Override
	public void advanceTime(double dt, Map<String, Type> globalVariables) {
		if(!isExecutionComplete()){
			DoubleType value = (DoubleType) VariableValue.evaluate(globalVariables);
			globalVariables.put(VariableName,value);
			ExecutionDone = true;		
			}

	}

	@Override
	public boolean isExecutionComplete() {
		return ForceReset||ExecutionDone;
	}

	@Override
	public void forceReset() {
		ForceReset = true;

	}

	@Override
	public void Reset() {
		ExecutionDone = false;

	}
}
