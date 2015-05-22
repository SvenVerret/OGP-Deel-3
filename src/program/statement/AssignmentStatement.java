package program.statement;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;

public class AssignmentStatement extends Statement{
	/**
	 * 
	 * @param variableName
	 * @param variableType
	 * @param value
	 * @param loc
	 */
	public AssignmentStatement(String variableName, Object variableType, Expression<?> value, 
			SourceLocation sourceLocation){
		super(sourceLocation);
		VariableName = variableName;
		VariableValue = value;
	}

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;
	private String VariableName;
	private Expression<?> VariableValue;


	@Override
	public void advanceTime(double dt,Program program) {
		if(!isExecutionComplete()){
			program.getVariables().put(VariableName,VariableValue.evaluate(program));
			ExecutionDone = true;
			program.decreaseRemainingTime();
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
