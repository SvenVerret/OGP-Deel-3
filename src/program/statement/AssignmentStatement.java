package program.statement;

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
	public AssignmentStatement(String variableName, Type variableType, Expression value, 
			SourceLocation sourceLocation){
		super(sourceLocation);
	}
}
