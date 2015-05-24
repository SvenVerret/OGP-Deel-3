package program.statement;

import java.util.HashSet;

import jumpingalien.part3.programs.SourceLocation;
import program.Program;
import program.expression.Expression;
import program.type.GameObjectType;

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
		System.out.println("assignment");
		if(variableType instanceof GameObjectType){
			System.out.println("GameObject");
		}
		VariableValue = value;
	}

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

	@Override
	public boolean isWellFormed(HashSet<String> parentStatements) {
		return true;
	}

	private boolean ForceReset = false;
	private boolean ExecutionDone = false;
	private String VariableName;
	private Expression<?> VariableValue;
}
