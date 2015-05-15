package program.statement;

import java.util.List;

import jumpingalien.part3.programs.SourceLocation;


public class SequenceStatement extends Statement{
	/**
	 * 
	 * @param statements
	 *  
	 */
	public SequenceStatement(List<Statement> statements, SourceLocation sourceLocation){
		super(sourceLocation);
	}
}
