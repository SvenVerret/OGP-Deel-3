package program.statement;

import java.util.Map;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.type.DoubleType;
import program.type.Type;

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
		VariableType = variableType;
		VariableValue = value;
	}

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;
	private String VariableName;
	private Object VariableType;
	private Expression<?> VariableValue;


	@Override
	public void advanceTime(double dt,Program program) {
		if(!isExecutionComplete()){
			
			program.getVariables().put(VariableName,VariableValue.evaluate(program));
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
