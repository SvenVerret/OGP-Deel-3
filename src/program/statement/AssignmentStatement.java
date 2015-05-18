package program.statement;

import java.util.Map;

import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;
import program.type.Type;

public class AssignmentStatement extends Statement{
	/**
	 * 
	 * @param variableName
	 * @param variableType
	 * @param value
	 * @param loc
	 */
	public AssignmentStatement(String variableName, Type variableType, Expression<?> value, 
			SourceLocation sourceLocation){
		super(sourceLocation);
		VariableName = variableName;
		VariableType = variableType;
	}

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;
	private String VariableName;
	private Type VariableType;


	@Override
	public void advanceTime(double dt, Map<String, Type> globalVariables) {
		if(!ExecutionDone||!ForceReset){
			globalVariables.put(VariableName, VariableType);
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
