package program.statement;


import jumpingalien.part3.programs.SourceLocation;
import program.expression.Expression;

public class IfStatement extends Statement{
	
	/**
	 * 
	 * @param e
	 * @param ifBody
	 * @param elseBody
	 * @param loc
	 */
	public IfStatement(Expression condition, Statement ifBody, Statement elseBody, 
			SourceLocation sourceLocation){
		super(sourceLocation);
	}


}
